package com.appleyk.auth.common.util;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
/**
 * <p>输入(出)流转换工具</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:31
 */
public class SeStreamUtils {
    private SeStreamUtils(){}
    /**
     * request请求的输入流转换为String  == 主要用于Json的反序列化
     */
    public static String inputStream2Str(InputStream in) throws IOException{
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(in, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)  {
            responseStrBuilder.append(inputStr);
        }
        streamReader.close();
        return responseStrBuilder.toString();
    }

    /**
     * 将字符串写进输出流里
     */
    public static void writeContent(HttpServletResponse response,String content) throws  IOException{
        if(content == null){return;}
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        OutputStream oStream = response.getOutputStream();
        oStream.write(content.getBytes());
        oStream.flush();
        oStream.close();
    }

    /**
     * 将字节数组写进输出流里
     */
    public static void writeBytes(HttpServletResponse response, byte[] data) throws IOException{
        OutputStream oStream = response.getOutputStream();
        oStream.write(data);
        oStream.flush();
        oStream.close();
    }

    /**
     * request请求的输入流转换为byte字节数组  == 主要用于文件、PBF等数据流的写入和反序列化
     */
    public static byte[] inputStream2Bytes(InputStream in) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        byte[] b = baos.toByteArray();
        in.close();
        baos.close();
        return b;
    }

    /**
     * byte[]字节数组写入文件
     */
    public static void bytes2File(byte[] data,final String fileName) throws  Exception{
        OutputStream os = new FileOutputStream(fileName);
        InputStream is = new ByteArrayInputStream(data);
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = is.read(buf))!=-1){
            os.write(buf,0,len);
        }
        os.close();
        is.close();
    }

    /**
     * byte[]字节数组写入文件
     */
    public static void bytes2File(byte[] data,final String outputDir ,final String fileName) throws  Exception{
        File file = new File(outputDir);
        if(!file.exists()){
            boolean mkdirs = file.mkdirs();
            if(!mkdirs){
                throw new IllegalArgumentException("创建目录异常：-- "+outputDir);
            }
        }
        String filePath = outputDir+"//"+fileName;
        OutputStream os = new FileOutputStream(filePath);
        InputStream is = new ByteArrayInputStream(data);
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = is.read(buf))!=-1){
            os.write(buf,0,len);
        }
        os.close();
        is.close();
    }

    /**
     * 字节数组转输入流
     */
    public static InputStream byte2InputStream(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }
}
