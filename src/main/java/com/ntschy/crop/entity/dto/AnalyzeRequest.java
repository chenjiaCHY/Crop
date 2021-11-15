package com.ntschy.crop.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AnalyzeRequest {

    // 作物图层选择，夏季、秋季
    @NotBlank(message = "season不能为空")
    private String season;

    // 作物区域选择
    private List<Integer> cropArea;

    // 作物分析维度
    @NotBlank(message = "cropAttribute不能为空")
    private String cropAttribute;

    // 分析图层选择
    @NotBlank(message = "layerName不能为空")
    private String layerName;

    // 分析图层纬度
    @NotBlank(message = "layerAttribute不能为空")
    private String layerAttribute;
}
