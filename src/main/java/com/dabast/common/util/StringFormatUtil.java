package com.dabast.common.util;

import java.util.Map;

/**
 * User: lxd
 * Date: 11-5-27
 * Time: 下午2:31
 */
public class StringFormatUtil {

    private StringFormatUtil(){}


    public static String substitute(String tepl, Map<String, String> params) {
        String text = tepl;
        for(Map.Entry<String, String> entry:params.entrySet()){
            String key = entry.getKey();
            if(entry.getValue()!=null){
                    text = text.replaceAll("\\{" + key + "}", safeRegexReplacement(entry.getValue()));
                  }else {
                       text = text.replaceAll("\\{" + key + "}", "");
                  }
        }
        return text;
    }

    private static String safeRegexReplacement(String replacement) {
        if (replacement == null) {
            return replacement;
        }

        return replacement.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
    }
}
