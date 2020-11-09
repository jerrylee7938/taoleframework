/**
 * Project:taole-toolkit
 * File:Watermark.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.taole.framework.exception.NotSupportedException;

/**
 * 水印,两种类型，文字和图片
 * 
 * @author Rory
 * @date Sep 20, 2012
 * @version $Id: Watermark.java 16 2014-01-17 17:58:24Z yedf $
 */
public enum Watermark {

	Character {

		@Override
		public void mark(Graphics2D g2d, String watermark, int fontSize, float opacity, Position position) {
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
			g2d.setComposite(alpha);
			g2d.setColor(Color.white);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setFont(new Font("Simsun", Font.BOLD, fontSize));
			FontMetrics fontMetrics = g2d.getFontMetrics();
			Rectangle2D rect = fontMetrics.getStringBounds(watermark, g2d);
			g2d.drawString(watermark, position.getImageWidth() - position.getX() - (int)rect.getWidth(), 
					position.getImageHeight() - position.getY() - (int)rect.getHeight());
		}

		@Override
		public void mark(Graphics2D g2d, BufferedImage watermark, float opacity, Position position) {
			throw new NotSupportedException("not support mark image form Character type.");
		}
	},
	Image {

		@Override
		public void mark(Graphics2D g2d, String watermark, int fontSize, float opacity, Position position) {
			throw new NotSupportedException("not support mark character form Image type.");
		}

		@Override
		public void mark(Graphics2D g2d, BufferedImage watermark, float opacity, Position position) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			g2d.drawImage(watermark, position.getImageWidth() - position.getX() - position.getWatermarkWidth(), 
					position.getImageHeight() - position.getY() - position.getWatermarkHeight(), null);
		}
		
	};
	
	public abstract void mark(Graphics2D g2d, String watermark, int fontSize, float opacity, Position position);
	
	public abstract void mark(Graphics2D g2d, BufferedImage watermark, float opacity, Position position);

	public static class Position {
		
		private int x;
		private int y;
		private int imageWidth;
		private int imageHeight;
		private int watermarkWidth;
		private int watermarkHeight;
		
		public Position(int x, int y, int imageWidth, int imageHeight) {
			this.x = x;
			this.y = y;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
		}
		
		public Position(int x, int y, int imageWidth, int imageHeight, int watermarkWidth, int watermarkHeight) {
			this.x = x;
			this.y = y;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.watermarkWidth = watermarkWidth;
			this.watermarkHeight = watermarkHeight;
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

		public int getImageWidth() {
			return imageWidth;
		}

		public void setImageWidth(int imageWidth) {
			this.imageWidth = imageWidth;
		}

		public int getImageHeight() {
			return imageHeight;
		}

		public void setImageHeight(int imageHeight) {
			this.imageHeight = imageHeight;
		}

		public int getWatermarkWidth() {
			return watermarkWidth;
		}

		public void setWatermarkWidth(int watermarkWidth) {
			this.watermarkWidth = watermarkWidth;
		}

		public int getWatermarkHeight() {
			return watermarkHeight;
		}

		public void setWatermarkHeight(int watermarkHeight) {
			this.watermarkHeight = watermarkHeight;
		}

	}
}
