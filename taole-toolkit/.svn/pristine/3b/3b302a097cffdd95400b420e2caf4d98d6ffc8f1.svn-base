/**
 * Project:taole-toolkit
 * File:WatermarkConfig.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Rory
 * @date Sep 20, 2012
 * @version $Id: WatermarkConfig.java 16 2014-01-17 17:58:24Z yedf $
 */
public class WatermarkConfig {

	Watermark watermark;
	
	String markingString;
	
	float opacity = 0.5f;
	
	int x = 20;
	
	int y = 20;
	
	int fontSize = 30;
	
	MarkingImageFetcher markingImageFetcher;
	
	public WatermarkConfig(Watermark watermark, String markingString) {
		this.watermark = watermark;
		this.markingString = markingString;
	}
	
	public WatermarkConfig(Watermark watermark, String markingString, float opacity) {
		this.watermark = watermark;
		this.markingString = markingString;
		this.opacity = opacity;
	}
	
	public WatermarkConfig(Watermark watermark, String markingString, float opacity, int x, int y) {
		this.watermark = watermark;
		this.markingString = markingString;
		this.opacity = opacity;
		this.x = x;
		this.y = y;
	}
	
	public WatermarkConfig(Watermark watermark, MarkingImageFetcher fetcher) {
		this.watermark = watermark;
		this.markingImageFetcher = fetcher;
	}
	
	public WatermarkConfig(Watermark watermark, MarkingImageFetcher fetcher, float opacity) {
		this.watermark = watermark;
		this.markingImageFetcher = fetcher;
		this.opacity = opacity;
	}
	
	public WatermarkConfig(Watermark watermark, MarkingImageFetcher fetcher, float opacity, int x, int y) {
		this.watermark = watermark;
		this.markingImageFetcher = fetcher;
		this.opacity = opacity;
		this.x = x;
		this.y = y;
	}
	
	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public Watermark getWatermark() {
		return watermark;
	}

	public void setWatermark(Watermark watermark) {
		this.watermark = watermark;
	}

	public String getMarkingString() {
		return markingString;
	}

	public void setMarkingString(String markingString) {
		this.markingString = markingString;
	}

	public MarkingImageFetcher getMarkingImageFetcher() {
		return markingImageFetcher;
	}

	public void setMarkingImageFetcher(MarkingImageFetcher markingImageFetcher) {
		this.markingImageFetcher = markingImageFetcher;
	}

	public interface MarkingImageFetcher {
		BufferedImage fetch() throws IOException;
	}
	
}
