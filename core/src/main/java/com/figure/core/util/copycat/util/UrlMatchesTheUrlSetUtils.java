package com.figure.core.util.copycat.util;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 *
 * url地址是否匹配指定url集合中的任意一个
 */
public class UrlMatchesTheUrlSetUtils {
    /**
     * 判断指定url地址是否匹配指定url集合中的任意一个
     *
     * @param urlPath 指定url地址
     * @param urls    需要检查的url集合
     * @return 是否匹配  匹配返回true，不匹配返回false
     */
    public static boolean matches(String urlPath, List<String> urls) {
        if (StringUtils.isEmpty(urlPath) || CollectionUtils.isEmpty(urls)) {
            return false;
        }
        for (String url : urls) {
            if (isMatch(url, urlPath)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符
     * * 表示一层路径内的任意字符串，不可跨层级
     * ** 表示任意层路径
     *
     * @param url     匹配规则
     * @param urlPath 需要匹配的url
     * @return
     */
    public static boolean isMatch(String url, String urlPath) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(url, urlPath);
    }
}
