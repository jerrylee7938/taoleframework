package com.taole.toolkit.aop;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.taole.framework.aop.AopMethodEventDispatcher;
import com.taole.framework.events.EventTarget;

@Aspect
@Component
public class ToolkitEventDispatcher extends AopMethodEventDispatcher {

	@Resource(name= "tk.eventTarget")
	private EventTarget eventTarget;

	public EventTarget getEventTarget() {
		return eventTarget;
	}

	@Around("execution(* com.taole.toolkit.menu.manager.*.*(..))")
	public Object around3(ProceedingJoinPoint pjp) throws Throwable {
		return super.proceePointcut(pjp);
	}
	
	@Around("execution(* com.taole.toolkit.category.manager.*.*(..))")
	public Object around4(ProceedingJoinPoint pjp) throws Throwable {
		return super.proceePointcut(pjp);
	}

}
