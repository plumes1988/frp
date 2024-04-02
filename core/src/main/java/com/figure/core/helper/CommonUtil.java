package com.figure.core.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.figure.core.constant.CommonConstant;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


/**
 * Company : Figure Information Technology Co. Ltd. 说明：监测中心Web端业务共通类
 * 
 * @author jijw
 * @create 2011-5-11 上午11:06:49
 */
public class CommonUtil {

	/**
	 * 系统自适应分隔符
	 */
	public static final String sep = System.getProperty("file.separator");

	/**
	 * 
	 * 说明：判断信号类型是否具有调制方式、符号率
	 * 
	 * @param signal
	 * @return
	 */
	public static boolean disModulation(String signal) {

		return signalList.contains(signal);
	}

	/**
	 * 具有调制方式、符号率的信号类型
	 */
	private static final List<String> signalList = getSignalType();

	/**
	 * 
	 * 说明：具有调制方式、符号率的信号类型
	 * 
	 * @return
	 */
	private static List<String> getSignalType() {
		List<String> signalList = new ArrayList<String>();
		signalList.add("QAM");
		signalList.add("DVB-S2");

		return signalList;
	}

	/**
	 * 格式化时间
	 * 
	 * @param dateStr
	 * @param formatStr
	 *            时间格式yyyyMMddHHmmss
	 * @return
	 */
	public static Date format(String dateStr, String formatStr) {
		DateFormat format = new SimpleDateFormat(formatStr);
		// yyyyMMddHHmmss
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @param formatStr
	 * 
	 * @return
	 */
	public static String formatByDate(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		// yyyyMMddHHmmss

		return sdf.format(date);
	}

	/**
	 * 空格连接字符串
	 * 
	 * @param beginStr
	 * @param endStr
	 * @return
	 */
	public static String concatSpace(String beginStr, String endStr) {
		StringBuffer stb = new StringBuffer();
		stb.append(beginStr).append(" ").append(endStr);

		return stb.toString();
	}

	/**
	 * 信号类型转换
	 * 
	 * @param val
	 *            频点值
	 * @param signalUtil
	 *            信号类型单位
	 * @return 基本信息类型单位 KHZ
	 */
	public static Integer converionSignalUtil(String val, String signalUtil) {
		Double value = Double.valueOf(val);
		if (CommonConstant.signalUtil_KHz.equals(signalUtil)) {
			value = value * 1;
		} else if (CommonConstant.signalUtil_MHz.equals(signalUtil)) {
			value = value * 1000;
		} else if (CommonConstant.signalUtil_GHz.equals(signalUtil)) {
			value = value * 1000 * 1000;
		}
		return value.intValue();
	}

	/**
	 * 信号类型转换
	 * 
	 * @param val
	 *            频点值
	 * @param signalUtil
	 *            信号类型单位
	 * @return 基本信息类型单位 KHZ
	 */
	public static String converionSignalUtilN2B(String val, String signalUtil) {
		Double value = Double.valueOf(val);
		if (CommonConstant.signalUtil_KHz.equals(signalUtil)) {
			value = value / 1;
		} else if (CommonConstant.signalUtil_MHz.equals(signalUtil)) {
			value = value / 1000;
		} else if (CommonConstant.signalUtil_GHz.equals(signalUtil)) {
			value = value / 1000 / 1000;
		}

		// 将转换后的结果变为String
		String valueStr = value.toString();
		// 取得小数点后面的字符串
		String valueStrSub = valueStr.substring(valueStr.indexOf(".") + 1);

		// 定义返回的变量
		String returnStr;

		if (valueStrSub.length() != 1) {
			// 判断小数点后有几位 ：不等于1位
			returnStr = valueStr;
		} else {
			// 判断小数点后有几位 ：等于1位

			// 截取小数点后1位的字符串
			String p = valueStr.substring(valueStr.indexOf(".") + 1);
			// 判断是否为0，若为0则去除
			if ("0".equals(p)) {
				returnStr = valueStr.substring(0, valueStr.indexOf("."));
			} else {
				returnStr = valueStr;
			}
		}

		return returnStr;
	}

	/**
	 * 出站时清空监测站id
	 * 
	 * @param request
	 * @param response
	 */
	public static void clearSessionMonitorId(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("monitorId", "");
	}

	/**
	 * 同步过后清空syFlag
	 * 
	 * @param request
	 * @param response
	 */
	public static void clearSessionSyFlag(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("syFlag", "");
	}

	/**
	 * 扫频同步从频道管理返回频率管理时清空frequencyId
	 * 
	 * @param request
	 * @param response
	 */
	public static void clearSessionFrequencyId(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("frequencyId", "");
	}

	/**
	 * 后台国际化
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	public static String Internationalization(HttpServletRequest request,
			HttpServletResponse response, String code) {
		Locale locale = (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		ResourceBundle re = ResourceBundle.getBundle("messageSource/messages");
//		ResourceBundle re = ResourceBundle.getBundle("messageSource/messages",
//				locale);
		// 取得源文件的相对地址

		// win 根据环境放开注释
		// String newPath =
		// CommonUtil.class.getClassLoader().getResource("spring/messages.xml").getPath();
		// 陕西linux 根据环境放开注释
		// String newPath =
		// "/monitorcenter/WEB-INF/classes/spring/messages.xml";
		//
		// try {
		// newPath = URLDecoder.decode(newPath, "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// // 读取源文件
		// ApplicationContext ctx = new
		// FileSystemXmlApplicationContext(newPath);

//		ServletContext servletContext = request.getSession()
//				.getServletContext();
//		ApplicationContext ctx = WebApplicationContextUtils
//				.getWebApplicationContext(servletContext);
//
//		return ctx.getMessage(code, obj, locale);
		String ss = re.getString(code);
		return ss;
	}

	/**
	 * 用逗号拼接字符数组
	 * 
	 * @param strArr
	 * @return
	 */
	public static String ArrToStringByComma(String[] strArr) {
		StringBuffer logIdArrStr = new StringBuffer();
		for (int i = 0; i < strArr.length; i++) {
			if (i != (strArr.length - 1)) {
				logIdArrStr.append(strArr[i]).append(",");
			} else {
				logIdArrStr.append(strArr[i]);
			}
		}

		return logIdArrStr.toString();
	}

	public static void main(String[] args) {
		String valueStr = "87.0";
		String valueStrSub = valueStr.substring(valueStr.indexOf(".") + 1);
	}

	/**
	 * 修改属性文件中的键值。
	 * 
	 * @param key
	 * @param value
	 */
	public static void writeData(String key, String value) {
		Properties prop = new Properties();
		java.net.URL path = Thread.currentThread().getContextClassLoader()
				.getResource("");
		String newPath = (path.toString() + "resources/globals.properties")
				.substring(6);
		try {
			File file = new File(newPath);
			if (!file.exists())
				file.createNewFile();
			InputStream fis = new FileInputStream(file);
			prop.load(fis);
			fis.close();// 修改值之前关闭输入流fis
			OutputStream fos = new FileOutputStream(newPath);
			prop.setProperty(key, value);
			prop.store(fos, "Update '" + key + "' value");
			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + newPath + " for updating " + value
					+ " value error");
		}
	}

	/**
	 * 重设用户密码时生成密码
	 * 
	 * 
	 */
	public static String createPassWord() {
		return "123456";
	}

	/**
	 * 将从 request中得到的参数 解码
	 * 
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeParameter(String source)
			throws UnsupportedEncodingException {
		source = (source == null) ? null : new String(
				source.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
		return source;
	}

	/**
	 * 说明：位数不足，左补零
	 */
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}

	/**
	 * 说明：位数不足，右补零
	 */
	public static String addZeroRightForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
//				sb.append("0").append(str);//左补0
				sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}


}
