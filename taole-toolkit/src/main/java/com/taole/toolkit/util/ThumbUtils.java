/*
 * @(#)ThumbUtils.java 0.1 Dec 14, 2007 Copyright 2004-2007  Co., Ltd. All
 * rights reserved.
 */

package com.taole.toolkit.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * Class: ThumbHelper Remark: 图片缩放
 * 
 * @author: 
 */
public final class ThumbUtils {

	private ThumbUtils() {
	}

	/**
	 * @param in
	 * @param out
	 * @param formatName
	 * @param size
	 * @throws IOException
	 */
	public static void encodeThumb(InputStream in, OutputStream out, PictureSize limitSize, String formatName) throws IOException {
		BufferedImage image = ImageIO.read(in);
		encodeThumb(image, out, limitSize, formatName);
	}

	public static void encodeThumb(BufferedImage image, OutputStream out, PictureSize limitSize, String formatName) throws IOException {
		PictureSize originalSize = new PictureSize(image.getWidth(null), image.getHeight(null));
		PictureSize thumbSize = getThumbSize(limitSize, originalSize);
		int width = thumbSize.getWidth(), height = thumbSize.getHeight();

		ColorModel dstCM = image.getColorModel();
		BufferedImage dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(width, height), dstCM.isAlphaPremultiplied(), null);

		Image scaleImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		Graphics2D g = dst.createGraphics();
		g.drawImage(scaleImage, 0, 0, width, height, null);
		g.dispose();

		ImageIO.write(dst, formatName, out);
	}
	
	public static void encodeEnlarge(InputStream in, OutputStream out, PictureSize limitSize, String formatName) throws IOException {
		BufferedImage image = ImageIO.read(in);
		PictureSize originalSize = new PictureSize(image.getWidth(null), image.getHeight(null));
		PictureSize enlargeSize = getEnlargeSize(limitSize, originalSize);
		int width = enlargeSize.getWidth(), height = enlargeSize.getHeight();
		
		ColorModel dstCM = image.getColorModel();
		BufferedImage dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(width, height), dstCM.isAlphaPremultiplied(), null);
		
		Image scaleImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		Graphics2D g = dst.createGraphics();
		g.drawImage(scaleImage, 0, 0, width, height, null);
		g.dispose();
		
		ImageIO.write(dst, formatName, out);
	}
	
	/**
	 * 裁剪图片,这里不涉及到图片的缩小和放大。需要在外部处理
	 * 
	 * @param in
	 * @param out
	 * @param cropSize
	 * @param formatName
	 * @throws IOException
	 */
	public static void crop(InputStream in, OutputStream out, PictureSize cropSize, int x, int y, String formatName) throws IOException {
		x = x < 0 ? 0 : x;
		y = y < 0 ? 0 : y;
		BufferedImage image = ImageIO.read(in);
		
		ColorModel dstCM = image.getColorModel();
		BufferedImage dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(cropSize.getWidth(), cropSize.getHeight()),
				dstCM.isAlphaPremultiplied(), null);

		Image subImage = image.getSubimage(x, y, cropSize.getWidth(), cropSize.getHeight());
		
		Graphics2D g = dst.createGraphics();
		g.drawImage(subImage, 0, 0, cropSize.getWidth(), cropSize.getHeight(), null);
		g.dispose();

		ImageIO.write(dst, formatName, out);
	}

	/**
	 * @param limitSize
	 * @param originalSize
	 * @return
	 */
	public static PictureSize getThumbSize(PictureSize limitSize, PictureSize originalSize) {

		int maxWidth = limitSize.getWidth();
		int maxHeight = limitSize.getHeight();
		if (maxWidth == 0 && maxHeight == 0) {
			maxWidth = maxHeight = DEFAULT_THUMB_SIZE;
		}
		if (limitSize.getWidth() > originalSize.getWidth() && limitSize.getHeight() > originalSize.getHeight()) {
			return originalSize;
		}
		int originalWidth = originalSize.getWidth();
		int originalHeight = originalSize.getHeight();
		int width = DEFAULT_THUMB_SIZE, height = DEFAULT_THUMB_SIZE;
		float rate = 0;
		if (maxWidth > 0 && maxHeight > 0) {
			rate = Math.min(maxWidth * 1f / originalWidth, maxHeight * 1f / originalHeight);
		} else if (maxWidth > 0) {
			rate = maxWidth * 1f / originalWidth;
		} else if (maxHeight > 0) {
			rate = maxHeight * 1f / originalHeight;
		} else {
			// no happen.
		}
		width = (int) (originalWidth * rate);
		height = (int) (originalHeight * rate);
		return new PictureSize(width, height);

	}
	
	/**
	 * @param enlargeSize
	 * @param originalSize
	 * @return
	 */
	public static PictureSize getEnlargeSize(PictureSize enlargeSize, PictureSize originalSize) {
		
		int maxWidth = enlargeSize.getWidth();
		int maxHeight = enlargeSize.getHeight();
		if ((maxWidth == 0 && maxHeight == 0) || maxWidth < originalSize.getWidth() || maxHeight < originalSize.getHeight()) {
			return originalSize;
		}
		int originalWidth = originalSize.getWidth();
		int originalHeight = originalSize.getHeight();
		int width = originalSize.getWidth(), height = originalSize.getHeight();
		float rate = 0;
		if (maxWidth > 0 && maxHeight > 0) {
			rate = Math.min(maxWidth * 1f / originalWidth, maxHeight * 1f / originalHeight);
		} else if (maxWidth > 0) {
			rate = maxWidth * 1f / originalWidth;
		} else if (maxHeight > 0) {
			rate = maxHeight * 1f / originalHeight;
		} else {
			// no happen.
		}
		width = (int) (originalWidth * rate);
		height = (int) (originalHeight * rate);
		return new PictureSize(width, height);
		
	}

	public static final int DEFAULT_THUMB_SIZE = 96;

}
