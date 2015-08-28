package com.weixin.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.weixin.pojo.TextMessage;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年4月23日 下午8:54:18
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
public class XmlUtils {
	
	public static Map<String, String> xmlToMap(InputStream is)
	{
		Map<String, String> data=new HashMap<String, String>();
		try {
			SAXReader reader=new SAXReader();
			Document doc=reader.read(is);
			Element root=doc.getRootElement();
			List<Element> elementList=root.elements();
			for (Element element : elementList)
			{
				data.put(element.getName(), element.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	public static String textMessageToXml(TextMessage textMessage)
	{
		XStream xStream=new XStream();
		return xStream.toXML(textMessage);
	}

}
