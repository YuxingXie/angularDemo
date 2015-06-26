package com.dabast.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public final class MD5 {
	private static char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static Logger logger = LoggerFactory.getLogger(MD5.class);
	
	private MD5(){}

    public static String convert(String s){
        return convert(s,hexChars);
    }

	public static String convert(String s,char[] hex){
		try { 
			byte[] bytes = s.getBytes(); 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(bytes); 
			bytes = md.digest(); 
			int j = bytes.length; 
			char[] chars = new char[j * 2]; 
			int k = 0; 
			for (int i = 0; i < bytes.length; i++) { 
				 byte b = bytes[i]; 
				 chars[k++] = hex[b >>> 4 & 0xf];
				 chars[k++] = hex[b & 0xf];
			} 
			return new String(chars); 
		} 
		catch (Exception e){
			logger.error("MD5 Generate error", e);
			return null; 
		}
	}
	public static void main(String[] args) {
		System.out.println(MD5.convert("a"));
	}

}
