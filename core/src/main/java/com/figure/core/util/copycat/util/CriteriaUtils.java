package com.figure.core.util.copycat.util;

/**
 * 条件查询工具类
 *
 * @author feather
 * @date 2021-04-06
 */
public class CriteriaUtils {

    private CriteriaUtils() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 首字母转大写
     */
    public static String toCapital(final String str) {
        final char[] cs = str.toCharArray();
        if (cs[0] >= 'a' && cs[0] <= 'z') {
            final int n = 0;
            cs[n] -= ' ';
        }

        return String.valueOf(cs);
    }

    /**
     * 首字母转小写
     */
    public static String toLowerCase(final String str) {
        final char[] cs = str.toCharArray();
        if (cs[0] >= 'A' && cs[0] <= 'Z') {
            final int n = 0;
            cs[n] += ' ';
        }
        return String.valueOf(cs);
    }

}
