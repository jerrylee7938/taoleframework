/**
 * Project:member
 * File:HelloActor.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.actor;

import org.springframework.stereotype.Component;

import com.taole.framework.events.AutoDispatchEventListener;
import com.taole.framework.events.EventHandler;
import com.taole.member.ProjectConfig;

@Component
@EventHandler(target = ProjectConfig.PREFIX + "eventTarget")
public class HelloActor extends AutoDispatchEventListener {

}
