package com.figure.core.helper;

import com.figure.core.exception.EncodeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;


public class CodecHelper {

    private static final Log log = LogFactory.getLog(CodecHelper.class);

    private final static char[] HEX_CHAR_ARRAY = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f'
    };

    /**
     * 进行 MD5 编码
     */
    public static String encodeMD5(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes("GBK"));
            byte[] digest = md.digest();
            return encodeHex(digest);
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    /**
     * encodeHex() 用来把一个byte类型的数转换成十六进制的ASCII表示，
     * 因为java中的byte的toString无法实现这一点，我们又没有C语言中的
     * sprintf(outbuf,"%02X",ib)
     */
    public static String encodeHex(byte b) {
        char[] ob = new char[2];
        ob[0] = HEX_CHAR_ARRAY[(b >>> 4) & 0X0F];
        ob[1] = HEX_CHAR_ARRAY[b & 0X0F];
        return new String(ob); // new String(ob, "GBK")
    }

    /**
     * encodeHex() 用来把一个byte类型的数转换成十六进制的ASCII表示，
     * 因为java中的byte的toString无法实现这一点，我们又没有C语言中的
     * sprintf(outbuf,"%02X",ib)
     */
    public static String encodeHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(encodeHex(bytes[i]));
        }
        return sb.toString();
    }

    /**
     * encodeHex() 用来把一个byte类型的数转换成十六进制的ASCII表示，
     * 因为java中的byte的toString无法实现这一点，我们又没有C语言中的
     * sprintf(outbuf,"%02X",ib)
     */
    public static String encodeHex(String text) {
        try {
            return encodeHex(text.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            return encodeHex(text.getBytes());
        }
    }

    /**
     * 将一个字符串中大于 128 的字符 编码成 Unicode 形式： 如 &amp;#XXXX; 的形式
     */
    public static String encodeUnicode(String text) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (((int) ch) > 128) {
                buf.append("&#" + ((int) ch) + ";");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * Translates a string into <code>application/x-www-form-urlencoded</code>
     * format using a specific encoding scheme. This method uses the
     * supplied encoding scheme to obtain the bytes for unsafe
     * characters.
     * <p/>
     * <em><strong>Note:</strong> The <a href=
     * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that
     * UTF-8 should be used. Not doing so may introduce
     * incompatibilites.</em>
     */
	public static String encodeURL(String url, String encoding) {
        try {
            return URLEncoder.encode(url, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decodes a <code>application/x-www-form-urlencoded</code> string using a specific
     * encoding scheme.
     * The supplied encoding is used to determine
     * what characters are represented by any consecutive sequences of the
     * form "<code>%<i>xy</i></code>".
     * <p/>
     * <em><strong>Note:</strong> The <a href=
     * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that
     * UTF-8 should be used. Not doing so may introduce
     * incompatibilites.</em>
     */
	public static String decodeURL(String url, String encoding) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(e);
        }
    }

    /**
     * 将 HTML 标签转换成可以直接显示的， 包括 &lt; &gt; &amp; &quot;, \n, \t, 空格
     */
    public static String escapeHTML(String text) {
        return escapeHTMLSpaces(escapeHTMLTags(text));
    }

    /**
     * 转换 HTML 的空格，回车, Tab (保留一个空格不转换)
     */
    public static String escapeHTMLSpaces(String text) {
        return text
                .replaceAll("\t", "    ")
                .replaceAll("( *) ", "$1>")// 保留一个空格不转换
                .replaceAll(" ", "&nbsp;")
                .replaceAll(">", " ")
                .replaceAll("\n", "<br>\n");
/*
        .replaceAll("\t", "    ")
        .replaceAll(" ", "&nbsp;")
        .replaceAll("\r", "")
        .replaceAll("\n", "<br>\n")
        */
    }

    /**
     * 将 HTML 标签转换成可以直接显示的， 包括 &lt; &gt; &amp; &quot;
     */
    public static String escapeHTMLTags(String text) {
        if (text == null) return "";
        char[] content = new char[text.length()];
        text.getChars(0, text.length(), content, 0);
        StringBuffer result = new StringBuffer(content.length + 50);
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case 60: // '<'
                    result.append("&lt;");
                    break;
                case 62: // '>'
                    result.append("&gt;");
                    break;
                case 38: // '&'
                    result.append("&amp;");
                    break;
                case 34: // '"'
                    result.append("&quot;");
                    break;
                case '\'': // "'"
                    result.append("&#39;");
                    break;
                default:
                    result.append(content[i]);
                    break;
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        log.debug("MD5('') = " + encodeMD5(""));
        log.debug("MD5('abcdefghijklmnopqrstuvwxyz') = " + encodeMD5("abcdefghijklmnopqrstuvwxyz"));
        log.debug("Hex('中文') = " + encodeHex("中文"));
    }

}