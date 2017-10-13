package com.hinlin.server.function.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.hinlin.base.config.Configuration;
import com.hinlin.base.util.StringHelper;
import com.hinlin.base.util.XMLHelper;
import com.weibo.api.motan.util.LoggerUtil;

public class ModuleXMLConfig
{

  private static Map<String, String> FUNCTION_CONFIG = new HashMap();

  private static void init()
  {
    String configName = Configuration.getString("motan.moduleXMLList");
    if (StringHelper.isEmpty(configName))
    {
      LoggerUtil.error("读取功能模块XML列表失败，请检查Configuration.xml配置文件是否定义httpBus.moduleXMLList信息");
    }

    String[] configArr = StringHelper.split(configName, "|");
    Iterator iter;
    for (int i = 0; i < configArr.length; ++i)
    {
      Document document = XMLHelper.getDocument(ModuleXMLConfig.class, configArr[i]);
      if (document == null)
        continue;
      Element rootElement = document.getRootElement();
      if (rootElement == null)
        continue;
      List funcList = rootElement.elements();
      for (iter = funcList.iterator(); iter.hasNext(); )
      {
        Element funcElement = (Element)iter.next();
        String id = funcElement.attributeValue("id");
        Element clazzElement = funcElement.element("class");
        if (clazzElement != null)
        {
          String clazz = clazzElement.getTextTrim();
          FUNCTION_CONFIG.put(id, clazz);
        }
      }
    }
  }

  public static boolean isExists(String id)
  {
    return FUNCTION_CONFIG.containsKey(id);
  }

  public static String getClassImpl(String id)
  {
    if (FUNCTION_CONFIG.containsKey(id))
    {
      return ((String)FUNCTION_CONFIG.get(id));
    }

    return "";
  }

  public static void main(String[] args)
  {
  }

  static
  {
    init();
  }
}