package com.ntschy.crop.entity.vo;

import lombok.Data;

@Data
public class AnalyzeResult {
    private String cropAttribute;

    private String layerAttribute;

    private Double area;
}
