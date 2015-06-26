package com.dabast.common.util;


public class StringUtils {
	
	private StringUtils(){}
	
	public static String join(Iterable<?> objects, String sep) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for(Object object: objects){
			if (i > 0) {
				builder.append(sep);
			}
			builder.append(object);
			i++;
		}
		return builder.toString();
	}

	public static String insertFileNameSuffixToUrl(String url, String suffix){
		int index = url.lastIndexOf('.');
		if(index > 0){
			StringBuilder sb = new StringBuilder(url);
			sb.insert(index, suffix);
			return sb.toString();
		}
		return url;
	}
	
    /**
     * 数字金额大写转换
     */
    public static String chineaseAmountUppercase(double amount){
        double amountUppercase = amount;
        String fraction[] = {"角", "分"};
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String unit[][] = {{"元", "万", "亿"},
                     {"", "拾", "佰", "仟"}};
 
        String head = amountUppercase < 0? "负": "";
        amountUppercase = Math.abs(amountUppercase);
         
        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int)(Math.floor(amountUppercase * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if(s.length()<1){
            s = "整";   
        }
        int integerPart = (int)Math.floor(amountUppercase);
 
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p ="";
            for (int j = 0; j < unit[1].length && amountUppercase > 0; j++) {
                p = digit[integerPart%10]+unit[1][j] + p;
                integerPart = integerPart/10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

    //---------------------- 以下方法来自apache common lang 2.6 -------------------------
    
    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (str.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is
     *  not empty and not null and not whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

}
