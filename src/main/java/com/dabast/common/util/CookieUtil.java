package com.dabast.common.util;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: DELL
 * Date: 13-4-3
 * Time: 下午8:52
 * To change this template use File | Settings | File Templates.
 */
public class CookieUtil {

    /*保存cookie时的cookieName*/
    private final static String cookieDomainName = "com.shengHai";

    /*加密cookie时的网站自定码*/
    private final static String webKey = "shengHai";

   /*设置cookie有效期是一个星期，根据需要自定义*/
    private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 1;

    /*保存cookie到客户端，传递进来的user对象中封装了在登陆时填写的用户名，指联帐号与密码*/
    public static void saveCookie(HttpServletResponse response) {

        /*设置cookie的有效期*/
        long validTime = System.currentTimeMillis() + (cookieMaxAge * 1000);

        /*MD5加密用户详细信息*/

        /*将要被保存的完整的Cookie值*/



        /*开始保存Cookie*/
        Cookie cookie = new Cookie(cookieDomainName, "fsdfsdfs");
        cookie.setMaxAge(60 * 60 * 24 * 365 * 1);

        /*cookie有效路径是网站根目录*/
        cookie.setPath("/");

        /*向客户端写入*/
        response.addCookie(cookie);

    }

    /*读取cookie,自动完成登录操作*/
    public static void readCookieAndLogon(String cookieValue, HttpServletRequest request, HttpServletResponse response,
                                          FilterChain chain) throws IOException, ServletException,UnsupportedEncodingException {

        /*根据cookieName取cookieValue*/
        Cookie cookies[] = request.getCookies();

        if(cookies!=null){
            for(int i=0;i<cookies.length;i++){
                if (cookieDomainName.equals(cookies[i].getName())) {
                    cookieValue = cookies[i].getValue();
                    break;

                }
            }
        }


        /*如果cookieValue为空,返回*/
        if(cookieValue==null){
            return;
        }

        String cookieValueAfterDecode = new String ("sdfsdfsd");
        String cookieValues[] = cookieValueAfterDecode.split(":");
        if(cookieValues.length!=3){
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("你正在用非正常方式进入本站...");
            out.close();
            return;

        }



        /*判断是否在有效期内,过期就删除Cookie*/
        long validTimeInCookie = new Long(cookieValues[1]);
        if(validTimeInCookie < System.currentTimeMillis()){
            //删除Cookie
            clearCookie(response);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<a href=’logon.jsp’>你的Cookie已经失效,请重新登陆</a>");
            out.close();
            return;

        }


        /*取出cookie中的指联账号,并到数据库中检查这个用户名*/
        String zlAccountCode = cookieValues[0];

        /*根据指联账号到数据库中检查用户是否存在*/




    }

    /*用户注销时,清除Cookie,在需要时可随时调用*/

    public static void clearCookie( HttpServletResponse response){
        Cookie cookie = new Cookie(cookieDomainName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

    }


    /*获取Cookie组合字符串的MD5码的字符串*/

    public static String getMD5(String value) {
        String result = null;
        try{
            byte[] valueByte = value.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(valueByte);
            result = toHex(md.digest());
        } catch (NoSuchAlgorithmException e2){
            e2.printStackTrace();
        }
        return result;

    }



    /*将传递进来的字节数组转换成十六进制的字符串形式并返回*/

    private static String toHex(byte[] buffer){
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++){
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }
        return sb.toString();
    }
}
