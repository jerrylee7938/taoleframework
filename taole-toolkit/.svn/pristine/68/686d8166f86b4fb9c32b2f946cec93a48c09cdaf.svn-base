package com.taole.toolkit.util;


public class Calculator {

	// 左对齐移动
	public static void main(String args[]) {
		Double totalLength = 600D;// 进度条长度，px
		Double buttonLength = 20D;// 进度条上的按钮长度，px
		Double inputRate = 1.00;// 输入的移动百分比（1表示100%）

		// 画面上，真实移动的百分比
		Double realRate = (MathUtil.div(totalLength - buttonLength, totalLength)) * inputRate;
		realRate = Math.floor(realRate*10000d)/10000;//不四舍五入取5位小数，保留小数位数越多，移动距离越接近结尾，5位足以
		
		// 移动后，按钮的左边所在的像素
		Double buttonleft = totalLength * realRate;
		// 移动后，按钮的右边所在的像素
		Double buttonRight = buttonleft + buttonLength;
		
		System.out.println("真实移动的百分比:"+realRate);
		System.out.println("移动后，按钮的左边所在的像素:"+buttonleft);
		System.out.println("移动后，按钮的右边所在的像素:"+buttonRight);
	}
}
