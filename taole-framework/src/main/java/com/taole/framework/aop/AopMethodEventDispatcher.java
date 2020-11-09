package com.taole.framework.aop;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import com.taole.framework.events.EventMethod;
import com.taole.framework.events.EventObject;
import com.taole.framework.events.EventObject.Attribute;
import com.taole.framework.events.EventTarget;
import com.taole.framework.exception.InvokePreventedException;
import com.taole.framework.util.CloneUtils;

public abstract class AopMethodEventDispatcher {

	public static final String AFTER = "after";
	public static final String BEFORE = "before";

	private Log log = LogFactory.getLog(this.getClass());

	public abstract EventTarget getEventTarget();

	private long warnCostTimeMillis = 100;

	public boolean matchMethod(String name) {
		return false;
	}

	protected EventMethod getEventMethod(Signature signature) {
		if (signature instanceof MethodSignature) {
			MethodSignature methodSignature = (MethodSignature) signature;
			return methodSignature.getMethod().getAnnotation(EventMethod.class);
		}
		return null;
	}

	/**
	 * process pointcut
	 * 
	 * @param pjp
	 * @return Object
	 * @throws Throwable
	 */
	public Object proceePointcut(ProceedingJoinPoint pjp) throws Throwable {
		EventMethod eventMethod = getEventMethod(pjp.getSignature());
		if (eventMethod == null) {
			long ts = System.currentTimeMillis();
			Object result = pjp.proceed();
			long cost = System.currentTimeMillis() - ts;
			if (cost >= warnCostTimeMillis) {
				log.warn(pjp.getSignature() + " proceed cost times: " + cost + " ms");
			}
			return result;
		}
		Object target = pjp.getTarget();
		Object[] args = pjp.getArgs();
		Object[] cloneArgs = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			cloneArgs[i] = CloneUtils.cloneQuietly(args[i]);
		}
		String eventName = eventMethod.value();
		if (StringUtils.isBlank(eventName)) {
			eventName = pjp.getSignature().getName();
		}
		eventName = eventName.toLowerCase();
		EventObject beforeEvent = new EventObject(before(eventName), target);
		beforeEvent.setAttribute(Attribute.SOURCE, target);
		beforeEvent.setAttribute(Attribute.ARGUMENTS, args);
		getEventTarget().fireEvent(beforeEvent, false);
		if (beforeEvent.isPreventDefault()) {
			throw new InvokePreventedException(pjp.getSignature() + " has been prevented by event '" + beforeEvent.getType() + "'.");
		}
		long ts = System.currentTimeMillis();
		Object result = pjp.proceed();
		long cost = System.currentTimeMillis() - ts;
		if (cost >= warnCostTimeMillis) {
			log.warn(pjp.getSignature() + " proceed cost times: " + cost + " ms");
		}
		EventObject afterEvent = new EventObject(after(eventName), target);
		afterEvent.setAttribute(Attribute.SOURCE, target);
		afterEvent.setAttribute(Attribute.ARGUMENTS_SNAPSHOTS, cloneArgs);
		afterEvent.setAttribute(Attribute.ARGUMENTS, args);
		afterEvent.setAttribute(Attribute.RESULT, result);
		getEventTarget().fireEvent(afterEvent, true);
		return result;
	}

	public static final String after(String eventMethodName) {
		return AFTER + eventMethodName;
	}

	public static final String before(String eventMethodName) {
		return BEFORE + eventMethodName;
	}

}
