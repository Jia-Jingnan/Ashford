package com.lilith.entity;

import lombok.Data;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:30 2021/5/10
 * Case实体类，保存case信息
 */
@Data
public class Case {

    private String caseId;
    private String apiId;
    private String desc;
    private String params;
    private String expectedResponseData;
    private String actualResponseData;
}
