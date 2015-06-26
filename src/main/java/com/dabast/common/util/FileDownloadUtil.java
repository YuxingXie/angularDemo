package com.dabast.common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-8
 * Time: 上午11:00
 * To change this template use File | Settings | File Templates.
 */
public class FileDownloadUtil {
    public static void fileDownload(HttpServletResponse response, String filePath) throws IOException{
                       // path是指欲下载的文件的路径。
                       File file = new File(filePath);
                       // 取得文件名。
                       String filename = file.getName();
                       // 取得文件的后缀名。
                       //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

                       // 以流的形式下载文件。
                       InputStream fis = new BufferedInputStream(new FileInputStream(file));
                       byte[] buffer = new byte[fis.available()];
                       fis.read(buffer);
                       fis.close();
                       // 清空response
                       response.reset();
                       // 设置response的Header
                       response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"),"ISO-8859-1"));
                       response.addHeader("Content-Length", "" + file.length());
                       OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                       response.setContentType("application/octet-stream");
                       toClient.write(buffer);
                       toClient.flush();
                       toClient.close();

       }
    
}
