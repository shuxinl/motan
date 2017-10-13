package com.hinlin.base.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.hinlin.base.util.StringHelper;
import com.hinlin.base.util.XMLHelper;

/**
 * 描述:	 读取configuration.xml文件
 * 版权:	 Copyright (c) 2005
 * 公司:	 hinlin
 * 作者:	 hinlin
 * 版本:	 1.0
 * 创建日期: 2007-4-20
 * 创建时间: 13:06:42
 */
public class Configuration
{
    
    private static Logger logger           = Logger.getLogger(Configuration.class);
    
    private static Map    items            = new HashMap();
    
    private static String CONFIG_FILE_NAME = "configuration.xml";
    
    static
    {
        loadConfig();
    }
    
    /**
     * 读入配置文件
     */
    private static void loadConfig()
    {
        try
        {
            
            Document document = XMLHelper.getDocument(Configuration.class, CONFIG_FILE_NAME);
            if ( document != null )
            {
                Element systemElement = document.getRootElement();
                List catList = systemElement.elements("category");
                for (Iterator catIter = catList.iterator(); catIter.hasNext();)
                {
                    Element catElement = (Element) catIter.next();
                    String catName = catElement.attributeValue("name");
                    if ( StringHelper.isEmpty(catName) )
                    {
                        continue;
                    }
                    
                    List itemList = catElement.elements("item");
                    for (Iterator itemIter = itemList.iterator(); itemIter.hasNext();)
                    {
                        Element itemElement = (Element) itemIter.next();
                        String itemName = itemElement.attributeValue("name");
                        String value = itemElement.attributeValue("value");
                        if ( !StringHelper.isEmpty(itemName) )
                        {
                            items.put(catName + "." + itemName, value);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            logger.error("读入配置文件出错", ex);
        }
        finally
        {
        }
        
    }
    
    /**
     * 获得字串配置值
     *
     * @param name
     * @return
     */
    public static String getString(String name)
    {
        String value = (String) items.get(name);
        return (value == null) ? "" : value;
    }
    
    /**
     * 获得字串配置值，若为空，则返回缺省值
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getString(String name, String defaultValue)
    {
        String value = (String) items.get(name);
        if ( value != null && value.length() > 0 )
            return value;
        else
            return defaultValue;
    }
    
    /**
     * 获得整型配置值
     *
     * @param name
     * @return
     */
    public static int getInt(String name)
    {
        String value = getString(name);
        if ( null == value || "".equals(value) )
        {
            logger.debug("配置文件key[" + name + "]未配置，默认返回： 0");
            return 0;
        }
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex)
        {
            logger.warn("配置文件key[" + name + "]配置错误，return 0");
            return 0;
        }
    }
    
    /**
     * 获得整型配置值
     *
     * @param name
     * @return
     */
    public static int getInt(String name, int defaultValue)
    {
        String value = getString(name);
        if ( null == value || "".equals(value) )
        {
            logger.debug("配置文件key[" + name + "]未配置，默认返回： " + defaultValue);
            return defaultValue;
        }
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex)
        {
            logger.warn("配置文件key[" + name + "]配置错误，return " + defaultValue);
            return defaultValue;
        }
        
    }
    
    /**
     * 获得布尔型配置值
     *
     * @param name
     * @return
     */
    public static boolean getBoolean(String name)
    {
        String value = getString(name);
        return Boolean.valueOf(value).booleanValue();
    }
    
    /**
     * 获得双精度浮点数配置值
     * 
     * @param name
     * @return
     */
    public static double getDouble(String name)
    {
        String value = getString(name);
        if ( null == value || "".equals(value) )
        {
            logger.debug("配置文件key[" + name + "]未配置，默认返回： 0");
            return 0d;
        }
        try
        {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException ex)
        {
            logger.warn("配置文件key[" + name + "]配置错误，return 0");
            return 0d;
        }
    }
    
    /**
     * 获得双精度浮点数配置值
     * 
     * @param name
     * @return
     */
    public static double getDouble(String name, double defaultValue)
    {
        String value = getString(name);
        if ( null == value || "".equals(value) )
        {
            logger.debug("配置文件key[" + name + "]未配置，默认返回： " + defaultValue);
            return defaultValue;
        }
        try
        {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException ex)
        {
            logger.warn("配置文件key[" + name + "]配置错误，return " + defaultValue);
            return defaultValue;
        }
    }
    
    public static Map getItems()
    {
        return items;
    }
}
