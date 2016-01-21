/**
 * 
 */
package cn.culturemap.pointsmap.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 * @author XP
 * @time   2015年11月13日 下午4:44:53
 */
public class StringUtils {
	
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	// Equals
	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

    /**
     * unix时间戳转换为dateFormat
     * 
     * @param beginDate
     * @return
     */
    public static String getFormatTimeFromMillis(String beginDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis((Long.parseLong(beginDate) *1000L));
        
        return sdf.format(calendar.getTime());
    }
	
	/*
	 * 时间转换为‘yyyy-MM-dd’格式的字符串
	 */
	public static String getFormatTime1(Date time) {
		SimpleDateFormat simpleDateFormatUsed = new SimpleDateFormat(
				"yyyy-MM-dd");

		String dateString = simpleDateFormatUsed.format(time);

		return dateString;

	}

	/*
	 * 时间转换为‘yyyy-MM-dd hh:mm’格式的字符串
	 */
	public static String getFormatTime2(Date time) {
		SimpleDateFormat simpleDateFormatUsed = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");

		String dateString = simpleDateFormatUsed.format(time);

		return dateString;

	}
	
	/*
	 * 时间转换为‘yyyy-MM-dd hh:mm’格式的字符串
	 */
	public static String getFormatTime3(String time) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		
		String dateString = "";
		try{
			Date timeDate = df.parse(time);
			dateString = getFormatTime2(timeDate);
		} catch(Exception e) {
			e.printStackTrace();
			dateString = "";
		} finally {
			return dateString;
		}
	}

	/*
	 * ‘yyyy-MM-dd’格式的字符串转化为时间
	 */
	public static Date getDataFromString(String timeString) {
		SimpleDateFormat simpleDateFormatUsed = new SimpleDateFormat(
				"yyyy-MM-dd");

		Date date = null;
		try {
			date = simpleDateFormatUsed.parse(timeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/*
	 * 正则表达式校验
	 */
	public static boolean regexChk(String reg, String string) {

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string.toLowerCase());

		return matcher.matches();
	}

	/*
	 * float类型的数字转化为 XXX.00 格式的钱币金额表示
	 */
	public static String formatFloatToString1(float number) {

		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		return dcmFmt.format(number);
	}

	/*
	 * double类型的数字转化为 XXX.00 格式的钱币金额表示
	 */
	public static String formatDoubleToString1(double number) {

		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		return dcmFmt.format(number);
	}

	/*
	 * double类型的数字转化为 XXX.0 格式的钱币金额表示
	 */
	public static String formatDoubleToString(double number) {

		DecimalFormat dcmFmt = new DecimalFormat("0.0");
		return dcmFmt.format(number);
	}
	
	/**
	 * 重写Integer.parseInt方法
	 * 
	 * @param value
	 *            转换值
	 * @param defaultValue
	 *            转换失败，默认值
	 */
	public static int paseInt(String value, int defaultValue) {

		int valueInt = defaultValue;

		if (StringUtils.isNotBlank(value)) {
			valueInt = Integer.parseInt(value);
		}

		return valueInt;
	}

	/**
	 * 重写Double.parseDouble方法
	 * 
	 * @param value
	 *            转换值
	 * @param defaultValue
	 *            转换失败，默认值
	 */
	public static double paseDouble(String value, int defaultValue) {

		double valueDouble = defaultValue;

		if (StringUtils.isNotBlank(value)) {
			valueDouble = Double.parseDouble(value);
		}

		return valueDouble;
	}
	
	/**
	 * 重写Double.parseFloat方法
	 * 
	 * @param value
	 *            转换值
	 * @param defaultValue
	 *            转换失败，默认值
	 */
	public static float paseFloat(String value, int defaultValue) {

		float valueDouble = defaultValue;

		if (StringUtils.isNotBlank(value)) {
			valueDouble = Float.parseFloat(value);
		}

		return valueDouble;
	}

	/**
	 * 生成唯一字符串
	 * 
	 * @param length
	 *            需要长度
	 * @param symbol
	 *            是否允许出现特殊字符 -- !@#$%^&*()
	 * @return
	 */
	public static String getUniqueString(int length, boolean symbol) {
		Random ran = new Random();
		int num = ran.nextInt(61);
		String returnString = "";
		String str = "";
		for (int i = 0; i < length;) {
			if (symbol)
				num = ran.nextInt(70);
			else
				num = ran.nextInt(61);
			str = strArray[num];
			if (!(returnString.indexOf(str) >= 0)) {
				returnString += str;
				i++;
			}
		}
		return returnString;
	}

	/**
	 * 给生成唯一字符串使用
	 */
	private static String[] strArray = new String[] { "a", "b", "c", "d", "e",
			"f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "!", "@", "#", "$", "%", "^", "&", "(",
			")" };

	/**
	 * 取得字符组中
	 * 
	 * @param allString
	 * @param index
	 * @return
	 */
	public static String getNumInString(String allString, int index) {

		String regexS = ",";
		StringBuffer reB = new StringBuffer();
		String re = "";

		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(allString);

		reB.append(m.replaceAll(regexS).trim());

		List<String> nums = Arrays.asList(reB.toString().split(regexS));

		List<String> numsT = new ArrayList<String>();

		for (int i = 0; i < nums.size(); i++) {
			String itemV = nums.get(i);

			if (itemV != null && itemV.length() > 0) {
				numsT.add(itemV);
			}
		}

		if (numsT.size() > index) {
			re = numsT.get(index);
		}
		return re.toString();
	}
	
	/**
	 * 取得数组从Array中
	 * @param imgPath
	 * @return
	 */
	public static String[] getArrayFromList(List<String> imgPath) {
		
		String[] values = null;
		
		if(imgPath == null || imgPath.size() <= 0) {
			return values;
		} else {
			values = new String[imgPath.size()];
			
			for (int i = 0; i < values.length; i++) {
				String string = values[i];
				values[i] = string;
			}
		}
		return values;
	}
	
	/**
	 * 邮箱验证
	 * @param str
	 * @return
	 */
	public static boolean isMail(String str) {

		if (isBlank(str)) {
			return false;
		}

		// 电子邮件
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 手机号验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) { 
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		p = Pattern.compile("^1\\d{10}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches(); 
		return b;
	}
	/**
	 * 电话号码验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) { 
		Pattern p1 = null,p2 = null;
		Matcher m = null;
		boolean b = false;  
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
		if(str.length() >9)
		{	m = p1.matcher(str);
 		    b = m.matches();  
		}else{
			m = p2.matcher(str);
 			b = m.matches(); 
		}  
		return b;
	}
	
	/**
	 * 随机生成六位数字
	 * @return
	 */
	public static String random6Int() {
		String randomV = "123456";
		randomV = ((int)((Math.random()*9+1)*100000)) + "";
		return randomV;
	}

}
