/**
 * Project:member
 * File:EventDispatcher.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.aop;

import javax.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.taole.framework.aop.AopMethodEventDispatcher;
import com.taole.framework.events.EventTarget;
import com.taole.member.ProjectConfig;

@Aspect
@Component
public class EventDispatcher extends AopMethodEventDispatcher  {

	@Resource(name = ProjectConfig.NAME + ".eventTarget")
	private EventTarget eventTarget;

	public EventTarget getEventTarget() {
		return eventTarget;
	}

	@Around("execution(* com.taole.member.manager.*.*(..))")
	public Object around0(ProceedingJoinPoint pjp) throws Throwable {
		return super.proceePointcut(pjp);
	}
}
