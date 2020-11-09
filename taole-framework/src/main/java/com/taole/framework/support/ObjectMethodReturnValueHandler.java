package com.taole.framework.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.ViewMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;

public class ObjectMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

	private ViewMethodReturnValueHandler viewHandler = new ViewMethodReturnValueHandler();
	private ModelAndViewMethodReturnValueHandler modelAndViewHandler = new ModelAndViewMethodReturnValueHandler();
	private ViewNameMethodReturnValueHandler viewNameHandler = new ViewNameMethodReturnValueHandler();

	private HttpEntityMethodProcessor httpEntityProcessor;

	public ObjectMethodReturnValueHandler() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new org.springframework.http.converter.ByteArrayHttpMessageConverter());
		messageConverters.add(new org.springframework.http.converter.StringHttpMessageConverter());
		messageConverters.add(new org.springframework.http.converter.ResourceHttpMessageConverter());
		// messageConverters.add(new org.springframework.http.converter.xml.SourceHttpMessageConverter ());
		// messageConverters.add(new org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter ());
		// messageConverters.add(new org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter ());
		// messageConverters.add(new org.springframework.http.converter.json.MappingJacksonHttpMessageConverter ());
		messageConverters.add(new org.springframework.http.converter.feed.AtomFeedHttpMessageConverter());
		messageConverters.add(new org.springframework.http.converter.feed.RssChannelHttpMessageConverter());
		//com/rometools/rome/io/FeedException
		httpEntityProcessor = new HttpEntityMethodProcessor(messageConverters);
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		// return RestReturnValue.class.isAssignableFrom(returnType.getParameterType());
		return (Object.class.equals(returnType.getParameterType()));
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
			throws Exception {

		if (returnValue == null) {
			return;
		}

		// Object delegate = ((RestReturnValue)returnValue).getDelegate();
		Object delegate = returnValue;

		if (delegate instanceof View) {
			viewHandler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
		} else if (delegate instanceof ModelAndView) {
			modelAndViewHandler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
		} else if (returnValue instanceof String) {
			viewNameHandler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
		} else if (returnValue instanceof HttpEntity) {
			httpEntityProcessor.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
		}

	}

}
