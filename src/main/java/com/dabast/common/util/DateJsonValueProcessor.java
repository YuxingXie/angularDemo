package com.dabast.common.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lrx
 * Date: 13-7-10
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

    private String format = "yyyy-MM-dd HH:mm:ss";

    public DateJsonValueProcessor(){

    }
    public DateJsonValueProcessor(String format){
         this.format=format;
    }
    public Object processArrayValue(Object value, JsonConfig jsonConfig){
         String[] obj={};
        if (value instanceof Date[]){
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date[] dates = (Date[])value;
            obj = new String[dates.length];
            for (int i=0;i<dates.length;i++){
                obj[i]=sf.format(dates[i]);
            }
        }
        return obj;
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig){
        if(value instanceof Date){
            String str = new SimpleDateFormat(format).format((Date) value);
            return str;
        }
        return value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
