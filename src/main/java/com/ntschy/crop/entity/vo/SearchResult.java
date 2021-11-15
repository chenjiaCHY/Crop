package com.ntschy.crop.entity.vo;

import lombok.Data;

@Data
public class SearchResult {

    private String village;

    private String household;

    private String breed;

    private Double acreage;

    private String name;

    private String season;

    private Integer objectId;
}
