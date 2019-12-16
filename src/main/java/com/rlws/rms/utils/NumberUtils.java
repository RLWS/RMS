package com.rlws.rms.utils;


import java.text.NumberFormat;

/**
 * 数字工具类
 *
 * @author rlws
 * @date 2019/12/16  10:07
 */
public class NumberUtils {

    /**
     * @param e      需要处理的小数
     * @param length 小数位长度
     * @return 返回处理后的字符串
     */
    public static String toFix(Double e, int length) {
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMaximumFractionDigits(length);
        return numberInstance.format(e);
    }
}
