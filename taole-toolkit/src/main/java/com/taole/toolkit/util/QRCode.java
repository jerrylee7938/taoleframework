package com.taole.toolkit.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author tony.tang
 * 
 */
public class QRCode {

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            二维码中的内容
	 * @param savePath
	 *            二维码生成路径
	 * @return
	 */
	public boolean encode(String content, String savePath) {
		boolean returnResult = false;
		try {
			encode(content, savePath, 300, 300);
			returnResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            二维码中的内容
	 * @param savePath
	 *            二维码生成路径
	 * @return
	 */
	public boolean encode(String content, String savePath, int width, int height) {
		boolean returnResult = false;
		try {
			BufferedImage image = QRCode.toBufferedImage(content, width, height, 6);
			File file = new File(savePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			String extName = getFileExtName(savePath);
			ImageIO.write(image, extName, file);
			returnResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	/**
	 * 解析二维码
	 * 
	 * @param codeImgPath
	 *            要解析的二维码图片路径
	 * @return
	 */
	public String decode(String codeImgPath) {
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			File file = new File(codeImgPath);
			BufferedImage image = ImageIO.read(file);
			;
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			Result result = formatReader.decode(binaryBitmap, hints);
			// System.out.println("result = " + result.toString());
			// System.out.println("resultFormat = " +
			// result.getBarcodeFormat());
			// System.out.println("resultText = " + result.getText());
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据文件全路径获取文件扩展名
	 * 
	 * @param fullFilePath
	 * @return
	 */
	private String getFileExtName(String fullFilePath) {
		if (fullFilePath.lastIndexOf(".") > 0) {
			String extName = fullFilePath.substring(fullFilePath.lastIndexOf(".") + 1, fullFilePath.length());
			return extName.toLowerCase();
		} else {
			return fullFilePath;
		}
	}

	/**
	 * 去掉生成的二维码图片的白边
	 * 
	 * @param matrix
	 * @return
	 */
	private static BitMatrix deleteWhite(BitMatrix matrix) {
		int[] rec = matrix.getEnclosingRectangle();
		int resWidth = rec[2] + 1;
		int resHeight = rec[3] + 1;

		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		for (int i = 0; i < resWidth; i++) {
			for (int j = 0; j < resHeight; j++) {
				if (matrix.get(i + rec[0], j + rec[1]))
					resMatrix.set(i, j);
			}
		}
		return resMatrix;
	}

	/**
	 * 去掉生成的二维码图片的白边
	 * 
	 * @param matrix
	 * @return
	 */
	private static BitMatrix whiteSide(BitMatrix matrix, int sideSize) {
		if (sideSize == 0) {
			return deleteWhite(matrix);
		} else {
			int[] rec = matrix.getEnclosingRectangle();
			int resWidth = rec[2] + 1;
			int resHeight = rec[3] + 1;

			BitMatrix resMatrix = new BitMatrix(resWidth + sideSize * 2, resHeight + sideSize * 2);
			resMatrix.clear();
			for (int i = 0; i < resWidth; i++) {
				for (int j = 0; j < resHeight; j++) {
					if (matrix.get(i + rec[0], j + rec[1]))
						resMatrix.set(i + sideSize, j + sideSize);
				}
			}
			return resMatrix;
		}
	}

	/**
	 * 生成带logo的二维码
	 * 
	 * @param content
	 *            二维码内容
	 * @param imgPath
	 *            要生成的二维码图位置
	 * @param imgSize
	 *            要生成的二维码图尺寸
	 * @param logoPath
	 *            logo图文件位置
	 * @param logoSize
	 *            logo尺寸
	 * @param zoom
	 *            是否按比例缩放logo图
	 * @throws Exception
	 */
	public void encodeWithLogo(String content, String imgPath, int imgSize, String logoPath, int logoSize, boolean zoom) throws Exception {
		BufferedImage qrImage = QRCode.toBufferedImage(content, imgSize, imgSize, 6);
		int qrImageSize = qrImage.getWidth();
		Graphics2D qrGraphics = qrImage.createGraphics();

		Image logoImage = null;
		if (zoom) {
			BufferedImage logoBfImage = zoomImage(logoPath, logoSize, logoSize);
			logoImage = (Image) logoBfImage;
		} else {
			logoImage = ImageIO.read(new File(logoPath));
		}
		int pos = (qrImageSize - logoSize) / 2;

		// 载入logo
		qrGraphics.drawImage(logoImage, pos, pos, null);
		qrGraphics.dispose();
		logoImage.flush();

		File qrcodeFile = new File(imgPath);
		if (!qrcodeFile.exists()) {
			qrcodeFile.createNewFile();
		}
		String extName = getFileExtName(imgPath);
		if (!ImageIO.write(qrImage, extName, qrcodeFile)) {
			throw new IOException("Could not write an image of format " + extName + " to " + imgPath);
		}
	}

	/**
	 * 生成二维码的公共方法
	 * 
	 * @param content
	 *            二维码内容
	 * @param width
	 *            二维码宽度
	 * @param height
	 *            二维码高度
	 * @param whiteSide
	 *            白边像素数
	 * @return
	 * @throws WriterException
	 */
	public static BufferedImage toBufferedImage(String content, int width, int height, int whiteSide) throws WriterException {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		// 设置二维码纠错级别ＭＡＰ
		Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);
		// 创建比特矩阵(位矩阵)的QR码编码的字符串
		BitMatrix byteMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		byteMatrix = whiteSide(byteMatrix, whiteSide);// 处理白边
		// 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// 使用比特矩阵画并保存图像
		graphics.setColor(Color.BLACK);
		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		return image;
	}

	/**
	 * 图片缩放
	 * 
	 * @param src
	 *            图片源
	 * @param toWidth
	 *            要缩放到的宽度
	 * @param toHeight
	 *            要缩放到的高度
	 * @return
	 */
	public static BufferedImage zoomImage(String src, int toWidth, int toHeight) {
		BufferedImage result = null;
		try {
			File srcfile = new File(src);
			if (!srcfile.exists()) {
				System.out.println("文件不存在");

			}
			BufferedImage im = ImageIO.read(srcfile);

			// /* 原始图像的宽度和高度 */
			// int width = im.getWidth();
			// int height = im.getHeight();
			//
			// // 压缩计算
			// float resizeTimes = 0.3f; /* 这个参数是要转化成的倍数,如果是1就是转化成1倍 */
			//
			// /* 调整后的图片的宽度和高度 */
			// int toWidth = (int) (width * resizeTimes);
			// int toHeight = (int) (height * resizeTimes);

			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		} catch (Exception e) {
			System.out.println("创建缩略图发生异常" + e.getMessage());
		}
		return result;
	}

	/**
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String[] arg) throws Exception {
		String content = "http://grbq.wodaibao.cn/grbq?type=3&cardno=12345678&price=1000";
		String qrPath = "e:/temp/1.jpg";
		String logoPath = "e:/temp/logo.jpg";

		QRCode qrcode = new QRCode();
		// 生成带logo的二维码
		qrcode.encodeWithLogo(content, qrPath, 200, logoPath, 50, true);

		System.out.println(qrcode.decode(qrPath));
	}
}
