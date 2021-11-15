package com.ntschy.crop.entity.vo;

import lombok.Data;

@Data
public class FeedbackVO {

    private Integer rowNumber;

    private Integer objectId;

    private String feedback;

    private String feedbackUser;

    private String feedbackTime;

    private String status;

    private String processResult;

    private String processUser;

    private String processTime;
}
