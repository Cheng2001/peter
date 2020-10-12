package com.peter.xiao.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtil {

    /**
     * 生成时间戳编号方法
     *
     * @param format 时间戳格式
     * @param obj    要拼接的数据（String类型最好）
     * @return 生成的标号字符串
     */
    public static String GeneratedNumber(String format, Object... obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat(format).format(new Date()));
        for (Object object : obj) {
            sb.append(object.toString());
        }
        return sb.toString();
    }

    /**
     * 生成订单号方法
     *
     * @param format 时间戳格式
     * @return 生成的订单号
     */
    public static String GeneratedNumber(String format) {
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat(format).format(new Date()));
        int num = 0;
        do {
            num = (int) (Math.random() * 1000);
        } while (num <= 99 || num >= 1000);
        sb.append(num);
        return sb.toString();
    }
}
