package com.ntschy.crop.entity.vo;

import lombok.Data;

@Data
public class CropVO {

    private Integer rowNumber;

    private String village;

    private String community;

    private String household;

    private String phone;

    private Integer fieldNumber;

    private Double acreage;

    private String isTransfer;

    private String isForest;

    private String breed;

    private String season;

    private Integer greenhouseCount;

    private Integer objectId;
}
