/**
 * Project:taole-toolkit
 * File:CategoryEventDispatcher.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.dict.aop;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.taole.framework.aop.AopMethodEventDispatcher;
import com.taole.framework.events.EventTarget;
import com.taole.toolkit.dict.BeanConfig;

/**
 * @author Rory
 * @date Aug 10, 2012
 * @version $Id: DictionaryEventDispatcher.java 24 2014-01-18 04:34:18Z yedf $
 */
@Aspect
@Component
public class DictionaryEventDispatcher extends AopMethodEventDispatcher {

	@Resource(name = BeanConfig.EVENT_TARGET_NAME)
	private EventTarget eventTarget;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventTarget getEventTarget() {
		return eventTarget;
	}

	@Around("execution(* com.taole.toolkit.dict.manager.*.*(..))")
	public Object around0(ProceedingJoinPoint pjp) throws Throwable {
		return super.proceePointcut(pjp);
	}

}
