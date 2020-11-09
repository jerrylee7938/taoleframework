/**
 * Project:HEAFramework 3.0
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Stopwatch;
import com.taole.framework.annotation.AnnotationUtils;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.bind.annotation.ParameterObject;
import com.taole.framework.rest.bind.annotation.ParameterVariable;
import com.taole.framework.rest.bind.annotation.ResourceVariable;
import com.taole.framework.rest.parser.JSONArrayRequestParameters;
import com.taole.framework.rest.parser.JSONObjectRequestParameters;
import com.taole.framework.rest.parser.ParameterParser;
import com.taole.framework.rest.parser.ParameterParserRegistry;
import com.taole.framework.rest.processor.ResponseProcessor;
import com.taole.framework.rest.processor.ResponseProcessorRegistry;
import com.taole.framework.util.BeanUtils;
import com.taole.framework.util.JSONTransformer;
import com.taole.framework.util.StringUtils;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: RestServer.java 9962 2017-12-05 08:12:37Z chengjun $
 * @since May 20, 2011 10:01:09 AM
 */
@Controller
@RequestMapping(RestUrlRuler.REST_ROOT)
public class RestServer {

    static final long WARNING_INVOKE_TIME = 1000;

    /**
     * 在调用rest service 的时候是否要将异常信息抛出去，而不是直接返回500错误。比如oauth2会依赖抛出的异常去做跳转。
     */
    public static final String THROW_EXCEPTIONS_KEY = "$throwExceptions";

    private final Log log = LogFactory.getLog(RestServer.class);

    @Autowired
    private Configurator configurator;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    ResponseProcessorRegistry responseProcessorRegistry;

    @Autowired
    ParameterParserRegistry parameterParserRegistry;

    @Autowired
    private RestServiceRegistry registry;

    @RequestMapping({"/{module}", "/{module}/"})
    public Object handleModuleDefault(@PathVariable("module") String module, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return handleRequest(module, "collection", "view", request, response);
    }

    @RequestMapping({"/{module}/{resource}", "/{module}/{resource}/"})
    public Object handleDefaultAction(@PathVariable("module") String module, @PathVariable("resource") String resource, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return handleRequest(module, resource, "view", request, response);
    }

    @RequestMapping("/{module}/{resource}/{action}")
    public Object handleResourceAction(@PathVariable("module") String module, @PathVariable("resource") String resource, @PathVariable("action") String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return handleRequest(module, resource, action, request, response);
    }

    Object handleRequest(String module, String resource, String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            RestContextImpl context = new RestContextImpl(module, resource, action, request, response);
            context.setAttribute("applicationContext", applicationContext);
            RestSessionFactory.initCurrentContext(context);
            Object handler = registry.getRestService(module);

            if (handler != null) {

                try {

                    Method actionMethod = null;
                    ActionMethod restAction = null;
                    Method[] methods = AnnotationUtils.getAnnotationMethodsWithSupers(handler.getClass(), ActionMethod.class);
                    for (Method method : methods) {
                        restAction = method.getAnnotation(ActionMethod.class);
                        String raa = restAction.action();
                        if (StringUtils.isEmpty(raa)) {
                            raa = method.getName();
                        }
                        if (StringUtils.compare(action, raa)) {
                            actionMethod = method;
                            break;
                        }
                    }

                    if (actionMethod == null) {
                        String errMsg = "Handle Request Error:not found rest action '" + module + "." + action + "'.";
                        log.info(errMsg);
                        return new ResponseEntity<String>(errMsg, HttpStatus.NOT_FOUND);
                    }

                    // Request parameters
                    Object reqParams = parseRequestParameterObject(restAction.request(), request);
                    context.setParameterObject(reqParams);

                    Type[] types = actionMethod.getGenericParameterTypes();
                    Object[] params = new Object[types.length];
                    for (int i = 0; i < types.length; i++) {
                        Type type = types[i];
                        if (type == HttpServletRequest.class) {
                            params[i] = request;
                        } else if (type == HttpServletResponse.class) {
                            params[i] = response;
                        } else if (type == RestContext.class) {
                            params[i] = context;
                        } else if (type == RequestParameters.class) {
                            params[i] = reqParams;
                        } else if (type == Range.class) {
                            params[i] = RestUtils.getRange((RequestParameters) reqParams);
                        } else if (type == Sorter.class) {
                            params[i] = RestUtils.getSorter((RequestParameters) reqParams, null);
                        } else {
                            params[i] = null;
                        }
                    }

                    Annotation[][] ass = actionMethod.getParameterAnnotations();
                    for (int i = 0; i < ass.length; i++) {
                        Annotation[] as = ass[i];
                        if (as.length > 0) {
                            for (Annotation a : as) {
                                if (ResourceVariable.class.isInstance(a)) {
                                    params[i] = resource;
                                    break;
                                } else if (ParameterObject.class.isInstance(a)) {
                                    Object delegate = reqParams;
                                    if (JSONObjectRequestParameters.class.isInstance(reqParams)) {
                                        delegate = ((JSONObjectRequestParameters) reqParams).getDelegateObject();
                                    } else if (JSONArrayRequestParameters.class.isInstance(reqParams)) {
                                        delegate = ((JSONArrayRequestParameters) reqParams).getDelegateObject();
                                    }
                                    if (types[i] == delegate.getClass()) {
                                        params[i] = delegate;
                                    } else {
                                        params[i] = reqParams;
                                    }
                                    break;
                                } else if (ParameterVariable.class.isInstance(a) && RequestParameters.class.isInstance(reqParams)) {
                                    ParameterVariable p = (ParameterVariable) a;
                                    Object v = ((RequestParameters) reqParams).getParameter(p.value());
                                    params[i] = convertType(v, types[i]);
                                    break;
                                }
                            }
                        }
                    }
                    Stopwatch stopwatch = Stopwatch.createStarted();
                    Object result = processResponseResult(restAction.response(), actionMethod.invoke(handler, params));
                    warnInvokeTime(stopwatch, actionMethod);
                    return result;
                } catch (Exception ex) {
                    if (context.getAttribute(THROW_EXCEPTIONS_KEY) != null && Boolean.TRUE.equals(context.getAttribute(THROW_EXCEPTIONS_KEY, Boolean.class))) {
                        throw ex;
                    }
                    ex.printStackTrace();
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "text/html");
                    return new ResponseEntity<String>(ex.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {

                String errMsg = "Handle Request Error:not found handler '" + module + "'.";
                log.info(errMsg);

                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "text/html");

                return new ResponseEntity<String>(errMsg, headers, HttpStatus.NOT_FOUND);

            }

        } finally {
            RestSessionFactory.destroyCurrentContext();
        }

    }

    @SuppressWarnings({"rawtypes"})
    Object convertType(Object value, Type type) {
        if (value == null || value == JSONObject.NULL) {
            return null;
        } else if (value instanceof JSONObject && type != JSONObject.class) {
            return JSONTransformer.transformJso2Pojo((JSONObject) value, Object.class);
        } else {
            return BeanUtils.convertType(value, (Class) type);
        }

    }

    private Object processResponseResult(String type, Object result) {
        ResponseProcessor processor = RestSessionFactory.getCurrentContext().getResponseProcessor();
        if (processor == null) {
            String name = configurator.getResponseName();
            if (name == null) {
                name = type;
            }
            processor = responseProcessorRegistry.getResponseProcessor(name);
        }
        if (processor != null) {
            return processor.process(result);
        }
        return result;
    }

    private Object parseRequestParameterObject(String format, HttpServletRequest request) {
        String name = configurator.getResponseName();
        if (name == null) {
            name = format;
        }
        ParameterParser pp = parameterParserRegistry.getParameterParser(format);
        if (pp != null) {
            return pp.parseRequest(request);
        }
        return null;
    }

    private void warnInvokeTime(Stopwatch stopwatch, Method method) {
        long time = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        if (time >= WARNING_INVOKE_TIME) {
            log.warn(String.format("############## rest invoke %s.%s(), cost time:%dms", method.getDeclaringClass().getSimpleName(), method.getName(), time));
        }
    }

}
