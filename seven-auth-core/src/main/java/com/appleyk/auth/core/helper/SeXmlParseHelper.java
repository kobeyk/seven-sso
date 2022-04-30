package com.appleyk.auth.core.helper;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.util.SeDateUtils;
import com.appleyk.auth.core.model.SeAppSite;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class SeXmlParseHelper {
    public static HashMap<Long, SeAppSite> xml2AppSites(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList webSites = document.getElementsByTagName("webSite");
            return parse(webSites);
        } catch (Exception e) {
            SeLoggerHelper.error(e.getMessage(),e);
        }
        return null;
    }
    private static HashMap<Long, SeAppSite> parse(NodeList webSites) throws Exception {
        HashMap<Long, SeAppSite> appSiteHashMap = new HashMap<>(16);
        for (int i = 0; i < webSites.getLength(); i++) {
            Node node = webSites.item(i);
            Long id = Long.valueOf(node.getAttributes().getNamedItem("id").getTextContent());
            String serviceUrl = node.getAttributes().getNamedItem("serviceUrl").getNodeValue();
            String callbackUrl = node.getAttributes().getNamedItem("callbackUrl").getNodeValue();
            Date createTime = SeDateUtils.str2Date(node.getAttributes().getNamedItem("createTime").getNodeValue());
            Date updateTime = SeDateUtils.str2Date(node.getAttributes().getNamedItem("updateTime").getNodeValue());
            SeAppSite appSite = new SeAppSite(id, serviceUrl, callbackUrl, createTime, updateTime);
            appSiteHashMap.put(id, appSite);
        }
        return appSiteHashMap;
    }
}
