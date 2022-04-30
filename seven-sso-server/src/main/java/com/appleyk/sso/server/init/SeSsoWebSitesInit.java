package com.appleyk.sso.server.init;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.core.config.SeSsoProperties;
import com.appleyk.auth.core.container.SeAppSiteContainer;
import com.appleyk.auth.core.helper.SeXmlParseHelper;
import com.appleyk.auth.core.model.SeAppSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Web应用站点配置监测定时器</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/26-16:16
 */
@Component
public class SeSsoWebSitesInit {

    @Autowired
    private SeSsoProperties ssoProperties;

    /**文件唯一身份标识 == 相当于人类的指纹一样*/
    private static String md5 = "";

    /** 5秒检测一次，不能太快也不能太慢，自己体会*/
    @Scheduled(cron = "0/5 * * * * ?")
    protected void timer() throws Exception {
        String webSitesPath = ssoProperties.getWebSitesPath();
        String readMd5 = getMd5(webSitesPath);
        if (SeSsoWebSitesInit.md5 == null || "".equals(SeSsoWebSitesInit.md5)) {
            updateWebSites(webSitesPath, readMd5);
            SeLoggerHelper.info("========Init WebSites========");
        }
        /** 不相等，说明webSites变化了*/
        if (!readMd5.equals(SeSsoWebSitesInit.md5)) {
            SeLoggerHelper.info("========Reread WebSites========");
            updateWebSites(webSitesPath, readMd5);
            SeLoggerHelper.info("========Read WebSites done========");
        }
    }

    private void updateWebSites(String webSitesPath, String readMd5) throws FileNotFoundException {
        File file = ResourceUtils.getFile(webSitesPath);
        Map<Long, SeAppSite> appSiteHashMap = SeXmlParseHelper.xml2AppSites(file);
        SeAppSiteContainer.APP_SITES.putAll(appSiteHashMap == null ? new HashMap<>() : appSiteHashMap);
        SeSsoWebSitesInit.md5 = readMd5;
    }

    /**
     * 获取文件的md5
     */
    public String getMd5(String filePath) throws Exception {
        File file;
        String md5 = "";
        try {
            file = ResourceUtils.getFile(filePath);
            if (file.exists()) {
                FileInputStream is = new FileInputStream(file);
                byte[] data = new byte[is.available()];
                is.read(data);
                md5 = DigestUtils.md5DigestAsHex(data);
                is.close();
            }
        } catch (FileNotFoundException e) {

        }
        return md5;
    }
}
