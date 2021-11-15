package com.ntschy.crop.utils;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

public class ExcelStyleUtil implements IExcelExportStyler {
    private static final short STRING_FORMAT = (short) BuiltinFormats.getBuiltinFormat("TEXT");
    private static final short FONT_SIZE_TEN = 200;
    private static final short FONT_SIZE_FIFTEEN = 300;

    private CellStyle headerStyle;

    private CellStyle titleStyle;

    private CellStyle styles;

    public ExcelStyleUtil(Workbook workbook) {
        this.init(workbook);
    }

    private void init(Workbook workbook) {
        this.headerStyle = initHeaderStyle(workbook);
        this.titleStyle = initTitleStyle(workbook);
        this.styles = initStyles(workbook);
    }

    @Override
    public CellStyle getHeaderStyle(short color) {
        return headerStyle;
    }

    @Override
    public CellStyle getTitleStyle(short color) {
        return titleStyle;
    }

    @Override
    public CellStyle getStyles(boolean parity, ExcelExportEntity entity) {
        return styles;
    }

    @Override
    public CellStyle getStyles(Cell cell, int dataRow, ExcelExportEntity entity, Object obj, Object data) {
        return getStyles(true, entity);
    }

    @Override
    public CellStyle getTemplateStyles(boolean isSingle, ExcelForEachParams excelForEachParams) {
        return null;
    }

    private CellStyle initHeaderStyle(Workbook workbook) {
        CellStyle style = getBaseCellStyle(workbook);
        style.setFont(getFont(workbook, FONT_SIZE_FIFTEEN, true));
        return style;
    }

    private CellStyle initTitleStyle(Workbook workbook) {
        CellStyle style = getBaseCellStyle(workbook);
        style.setFont(getFont(workbook, FONT_SIZE_TEN, true));
        return style;
    }

    private CellStyle initStyles(Workbook workbook) {
        CellStyle style = getBaseCellStyle(workbook);
        //style.setFont(getFont(workbook, FONT_SIZE_TEN, false));
        //style.setDataFormat(STRING_FORMAT);
        return style;
    }

    private CellStyle getBaseCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        return style;
    }

    private Font getFont(Workbook workbook, short size, boolean isBold) {
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeight(size);
        font.setBold(isBold);
        return font;
    }
}
