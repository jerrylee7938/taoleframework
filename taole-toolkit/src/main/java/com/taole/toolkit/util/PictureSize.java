/*
 * @(#)PictureSize.java 0.1 Dec 14, 2007 Copyright 2004-2007  Co., Ltd. All
 * rights reserved.
 */

package com.taole.toolkit.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Class: PictureSize
 * @author: 
 */
public class PictureSize {

    int height;

    int width;

    public PictureSize(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
    	   if (obj == null) { return false; }
    	   if (obj == this) { return true; }
    	   if (obj.getClass() != getClass()) {
    	     return false;
    	   }
    	   PictureSize rhs = (PictureSize) obj;
    	   return new EqualsBuilder()
    	                 .append(height, rhs.getHeight())
    	                 .append(width, rhs.getWidth())
    	                 .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
    	return new ToStringBuilder(this).append("width", width).append("height", height).toString();
    }

}
