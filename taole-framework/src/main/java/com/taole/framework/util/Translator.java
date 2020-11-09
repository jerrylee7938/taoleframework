/*
 * @(#)Translator.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

public interface Translator<S, D> {

	public D translate(S word);

}
