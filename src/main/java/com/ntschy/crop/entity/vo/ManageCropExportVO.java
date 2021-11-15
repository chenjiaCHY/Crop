package com.ntschy.crop.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelTarget("ManageCropExportVO")
public class ManageCropExportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号", orderNum = "1", width = 10)
    private Integer rowNumber;

    @Excel(name = "村", orderNum = "2", width = 20)
    private String village;

    @Excel(name = "组", orderNum = "3", width = 20)
    private String community;

    @Excel(name = "户", orderNum = "4", width = 20)
    private String household;

    @Excel(name = "联系电话", orderNum = "5", width = 20)
    private String phone;

    @Excel(name = "田块号", orderNum = "6", width = 10)
    private Integer fieldNumber;

    @Excel(name = "面积（亩）", orderNum = "7", width = 20)
    private Double acreage;

    @Excel(name = "是否流转", orderNum = "8", width = 10)
    private String isTransfer;

    @Excel(name = "是否林下", orderNum = "9", width = 10)
    private String isForest;

    @Excel(name = "品种", orderNum = "10", width = 20)
    private String breed;

    @Excel(name = "季节", orderNum = "11", width = 10)
    private String season;

    @Excel(name = "大棚个数", orderNum = "12", width = 20)
    private Integer greenhouseCount;
}
