package org.seu.acetec.mes2Koala.infra.httpclient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * responseBody 节点处理
 * 
 * @author harlow
 * @date 2015-04-24
 */
public class Node {

	/**
	 * 返responseBody 节点text
	 * 
	 * @param responseBody
	 * @param filterStr
	 * @return NodeList
	 * @throws UnsupportedEncodingException
	 */
	public String getNodeText(byte[] responseBody, String filterStr) {
        try { 
            SAXReader reader = new SAXReader(); 
            InputStream in = new ByteArrayInputStream(responseBody); 
            Document doc = reader.read(in); 
            Element root = doc.getRootElement(); 
            return root.getText();
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
		return "";
	}
}
