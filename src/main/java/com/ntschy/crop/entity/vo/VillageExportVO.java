package com.ntschy.crop.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelTarget("VillageExportVO")
public class VillageExportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号", orderNum = "1", width = 20)
    private Integer rowNumber;

    @Excel(name = "村", orderNum = "2", width = 20)
    private String village;

    @Excel(name = "品种", orderNum = "3", width = 20)
    private String breed;

    @Excel(name = "面积（亩）", orderNum = "4", width = 40)
    private Double acreage;
}
