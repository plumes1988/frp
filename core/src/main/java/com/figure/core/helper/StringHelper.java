/*
 * Copyright (c) 2006-2008, Humpic Organization. All rights reserved.
 * 
 * $HeadURL: http://humpic.googlecode.com/svn/trunk/humpic-framework/src/java/core/com/humpic/framework/helper/StringHelper.java $
 * $Id: StringHelper.java 184 2008-02-20 03:50:18Z subchen $
 * 
 * This library is free. You can redistribute it and/or modify it.
 * Support by http://www.humpic.com/
 */
package com.figure.core.helper;

import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.Locale;

/**
 * 对 commons-lang 中的 StringUtils 的加强
 * <p/>
 * <ul>
 * 		<li>substringAfter/substringAfterLast/substringBefore/substringBeforeLast
 *  	<li>startsWithIgnoreCase/endsWithIgnoreCase
 *  	<li>join
 *  	<li>parseLocale
 *  	<li>wildMatch
 *  	<li>sprintf
 * </ul>
 */
public class StringHelper {
	
	/**
	 * if (separator not in str) return defaultString.<br>
	 * Enganced with {@link StringUtils#substringAfter(String, String)}<br>
	 * <pre>
	 * substringAfter(null, *, "AA")      = "AA"
	 * substringAfter("", *, "AA")        = "AA"
	 * substringAfter(*, null, "AA")      = "AA"
	 * substringAfter("abc", "a", "AA")   = "bc"
	 * substringAfter("abcba", "b", "AA") = "cba"
	 * substringAfter("abc", "c", "AA")   = ""
	 * substringAfter("abc", "d", "AA")   = "AA"
	 * substringAfter("abc", "", "AA")    = "AA"
	 * </pre>
	 */
    public static String substringAfter(String str, String separator, String defaultIfNoFound) {
        if (str == null || str.length() == 0) {
            return defaultIfNoFound;
        }
        if (separator == null || separator.length() == 0) {
            return defaultIfNoFound;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return defaultIfNoFound;
        }
        return str.substring(pos + separator.length());
    }
    
	/**
	 * if (separator not in str) return defaultString.<br>
	 * Enganced with {@link StringUtils#substringAfterLast(String, String)}<br>
	 * <pre>
	 * substringAfterLast(null, *, "AA")      = "AA"
	 * substringAfterLast("", *, "AA")        = "AA"
	 * substringAfterLast(*, null, "AA")      = "AA"
	 * substringAfterLast("abc", "a", "AA")   = "bc"
	 * substringAfterLast("abcba", "b", "AA") = "a"
	 * substringAfterLast("abc", "c", "AA")   = ""
	 * substringAfterLast("abc", "d", "AA")   = "AA"
	 * substringAfterLast("abc", "", "AA")    = "AA"
	 * </pre>
	 */
    public static String substringAfterLast(String str, String separator, String defaultIfNoFound) {
        if (str == null || str.length() == 0) {
            return defaultIfNoFound;
        }
        if (separator == null || separator.length() == 0) {
            return defaultIfNoFound;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return defaultIfNoFound;
        }
        return str.substring(pos + separator.length());
    }
    
	/**
	 * if (separator not in str) return defaultString.<br>
	 * Enganced with {@link StringUtils#substringBefore(String, String)}<br>
	 * <pre>
	 * substringBefore(null, *, "AA")      = "AA"
	 * substringBefore("", *, "AA")        = "AA"
	 * substringBefore(*, null, "AA")      = "AA"
	 * substringBefore("abc", "a", "AA")   = ""
	 * substringBefore("abcba", "b", "AA") = "a"
	 * substringBefore("abc", "c", "AA")   = "ab"
	 * substringBefore("abc", "d", "AA")   = "AA"
	 * substringBefore("abc", "", "AA")    = "AA"
	 * </pre>
	 */
    public static String substringBefore(String str, String separator, String defaultIfNoFound) {
        if (str == null || str.length() == 0) {
            return defaultIfNoFound;
        }
        if (separator == null || separator.length() == 0) {
            return defaultIfNoFound;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return defaultIfNoFound;
        }
        return str.substring(0, pos);
    }
    
	/**
	 * if (separator not in str) return defaultString.<br>
	 * Enganced with {@link StringUtils#substringBeforeLast(String, String)}<br>
	 * <pre>
	 * substringBeforeLast(null, *, "AA")      = "AA"
	 * substringBeforeLast("", *, "AA")        = "AA"
	 * substringBeforeLast(*, null, "AA")      = "AA"
	 * substringBeforeLast("abc", "a", "AA")   = ""
	 * substringBeforeLast("abcba", "b", "AA") = "abc"
	 * substringBeforeLast("abc", "c", "AA")   = "ab"
	 * substringBeforeLast("abc", "d", "AA")   = "AA"
	 * substringBeforeLast("abc", "", "AA")    = "AA"
	 * </pre>
	 */
    public static String substringBeforeLast(String str, String separator, String defaultIfNoFound) {
        if (str == null || str.length() == 0) {
            return defaultIfNoFound;
        }
        if (separator == null || separator.length() == 0) {
            return defaultIfNoFound;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return defaultIfNoFound;
        }
        return str.substring(0, pos);
    }
    
    /**
	 * Test if the given String starts with the specified prefix,
	 * ignoring upper/lower case.
	 * @param str the String to check
	 * @param prefix the prefix to look for
	 * @see String#startsWith
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * Test if the given String ends with the specified suffix,
	 * ignoring upper/lower case.
	 * @param str the String to check
	 * @param suffix the suffix to look for
	 * @see String#endsWith
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(suffix.length()).toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

    /**
     * join([a, b, c, d], ", ", "'", "'") == "'a', 'b', 'c', 'd'";
     * @param iter   项目迭代器
     * @param separator  每个项目之间的分割符
     * @param prefix 每个项目的前缀
     * @param suffix 每个项目的后缀
     * @return
     */
    public static String join(Iterator iter, String separator, String prefix, String suffix) {
    	StringBuffer buf = new StringBuffer();
        if (iter.hasNext()) 
        	buf.append(prefix).append(iter.next()).append(suffix);
        while (iter.hasNext()) {
            buf.append(separator).append(prefix).append(iter.next()).append(suffix);
        }
        return buf.toString();
    }
    
    /**
     * join([a, b, c, d], ", ", "'", "'") == "'a', 'b', 'c', 'd'";
     * @param array 字符数组
     * @param separator 每个项目之间的分割符
     * @param prefix 每个项目的前缀
     * @param suffix 每个项目的后缀
     * @return
     */
    public static String join(String[] array, String separator, String prefix, String suffix) {
    	if (array == null) {
    		return "";
    	}
    	StringBuffer buf = new StringBuffer();
    	int i = 0;
    	for (String str : array) {
    		if (i != array.length - 1) {
        		buf.append(prefix).append(str).append(suffix).append(separator);
    		} else {
    			buf.append(prefix).append(str).append(suffix);
    		}
    		i++;
    	}
        return buf.toString();
    }

    public static Locale parseLocale(String locale) {
    	String[] parts = StringUtils.split(locale, "_");
        String language = parts.length <= 0 ? "" : parts[0];
        String country = parts.length <= 1 ? "" : parts[1];
        String variant = parts.length <= 2 ? "" : parts[2];
        return language.length() <= 0 ? null : new Locale(language, country, variant);
    }
    
    /**
     * 用通配符匹配字符串（支持*， ？），不是正则表达式
     * <ul>
     * 		<li>wildMatch("IMG_????.jpg", "IMG_0001") = true
     * 		<li>wildMatch("IMG_*.jpg", "IMG_0001") = true
     * </ul>
     */
    public static boolean wildMatch(String pattern, String str) {
        pattern = toJavaPattern(pattern);
        return java.util.regex.Pattern.matches(pattern, str);
    }

    private static String toJavaPattern(String pattern) {
        String result = "^";
        char[] metachar = { '$', '^', '[', ']', '(', ')', '{', '|', '+', '.', '\\' };
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            boolean isMeta = false;
            for (int j = 0; j < metachar.length; j++) {
                if (ch == metachar[j]) {
                    result += "\\" + ch;
                    isMeta = true;
                    break;
                }
            }
            if (!isMeta) {
                if (ch == '*') {
                    result += "[\u0000-\uffff]*"; // BUG FIXED 2007-12-04
                } else {
                    result += ch;
                }

            }
        }
        result += "$";
        return result;
    }
    
    /**
     * 
     * 说明：判断一个字符串str是否在通过separator连接的字符串strArr中 含非空判断 separator为空时使用半角逗号
     *
     * @param str
     * @param strArr
     * @param separator
     * @return
     */
    public static boolean strInArray(String str, String strArr, String separator) {
    	if (str == null || str.length() == 0) {
			return false;
		}
    	if (strArr == null || strArr.length() == 0) {
    		return false;
    	}
		if (separator == null || separator.length() == 0) {
			separator = ",";
		}
		String[] arr = strArr.split(separator);
		for (String s : arr) {
			if (s.equals(str)) {
				return true;
			}
		}
    	return false;
    }

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
    
    public static void main(String[] args) {
		String[] arr = new String[] {"1","2","3"};
		
	}
}