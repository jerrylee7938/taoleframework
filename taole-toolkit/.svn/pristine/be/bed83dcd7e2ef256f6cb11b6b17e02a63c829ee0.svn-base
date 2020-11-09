/**
 * Project:taole-toolkit
 * File:XSSFExcelHtmlHelper.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.util;

import java.util.Formatter;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Rory
 * @date Jun 14, 2012
 * @version $Id: XSSFExcelHtmlHelper.java 16 2014-01-17 17:58:24Z yedf $
 */
public class XSSFExcelHtmlHelper implements ExcelHtmlHelper {

    public XSSFExcelHtmlHelper(XSSFWorkbook wb) {
    }

    /**
	 * {@inheritDoc}
	 */
	@Override
    public void colorStyles(CellStyle style, Formatter out) {
        XSSFCellStyle cs = (XSSFCellStyle) style;
        styleColor(out, "background-color", cs.getFillForegroundXSSFColor());
        styleColor(out, "text-color", cs.getFont().getXSSFColor());
    }

    private void styleColor(Formatter out, String attr, XSSFColor color) {
        if (color == null || color.isAuto())
            return;

        byte[] rgb = color.getRgb();
        if (rgb == null) {
            return;
        }

        // This is done twice -- rgba is new with CSS 3, and browser that don't
        // support it will ignore the rgba specification and stick with the
        // solid color, which is declared first
        out.format("  %s: #%02x%02x%02x;%n", attr, rgb[0], rgb[1], rgb[2]);
        byte[] argb = color.getARgb();
        if (argb == null) {
            return;
        }
        out.format("  %s: rgba(0x%02x, 0x%02x, 0x%02x, 0x%02x);%n", attr,
                argb[3], argb[0], argb[1], argb[2]);
    }
}
