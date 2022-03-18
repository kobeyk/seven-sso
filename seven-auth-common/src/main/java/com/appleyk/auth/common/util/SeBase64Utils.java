package com.appleyk.auth.common.util;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * <p>图片base64转换工具</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:08
 */
public class SeBase64Utils {

    /**
     * base64字符串转文件
     */
    public static File base642File(String base64) {
        if(base64==null||"".equals(base64)) {
            return null;
        }
        byte[] buff=Base64.decode(base64);
        File file=null;
        FileOutputStream fout=null;
        try {
            file = File.createTempFile("tmp", null);
            fout=new FileOutputStream(file);
            fout.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fout!=null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 文件转base64字符串
     */
    public static String file2Base64(File file) {
        if(file==null) {
            return null;
        }
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64.encode(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    public static MultipartFile base642Multipart(String base64) {
        try {
            String[] baseStr = base64.split(",");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStr[1]);

            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new GxBASE64DecodedMultipartFile(b, baseStr[0]);
        } catch (IOException e) {
            SeLoggerHelper.error(e.getMessage());
            return null;
        }
    }

    static class GxBASE64DecodedMultipartFile implements MultipartFile{
        private final byte[] imgContent;
        private final String header;

        public GxBASE64DecodedMultipartFile(byte[] imgContent, String header) {
            this.imgContent = imgContent;
            this.header = header.split(";")[0];
        }

        @Override
        public String getName() {
            // TODO - implementation depends on your requirements
            return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
        }

        @Override
        public String getOriginalFilename() {
            // TODO - implementation depends on your requirements
            return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
        }

        @Override
        public String getContentType() {
            // TODO - implementation depends on your requirements
            return header.split(":")[1];
        }

        @Override
        public boolean isEmpty() {
            return imgContent == null || imgContent.length == 0;
        }

        @Override
        public long getSize() {
            return imgContent.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return imgContent;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(imgContent);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            new FileOutputStream(dest).write(imgContent);
        }
    }
}
