package com.lilith.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果对象，将该对象回写到Excel中
 * @Author:JiaJingnan
 * @Date: 下午12:08 2021/5/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String sheetName;
    private String caseId;
    private String cellName;
    private String res;
}
