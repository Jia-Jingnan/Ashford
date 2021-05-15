package com.lilith.util;

import org.testng.Assert;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:02 2021/3/16
 */
public class AssertUtil {

    public static String assertEquals(String actualResponseData, String expectedResponseData){
        String result = "通过";
        try {
            Assert.assertEquals(actualResponseData, expectedResponseData);
        } catch (Throwable e){
            result = actualResponseData;
        }
        return result;

    }


}
