package com.hinlin.base.util;

import java.util.Map;

import com.weibo.api.motan.util.LoggerUtil;

/**
 * 描述:
 * 版权:	 Copyright (c) 2009
 * 公司:	 hinlin
 * 作者:	 hinlin
 * 版本:	 1.0
 * 创建日期: 2009-12-23
 * 创建时间: 11:19:27
 */
public class MapHelper
{
    
    /**
     * 获得字串值
     *
     * @param name 键值名称
     * @return 若不存在，则返回空字串
     */
    public static String getString(Map map, String name)
    {
        if ( name == null || name.equals("") )
        {
            return "";
        }
        
        String value = "";
        if ( map.containsKey(name) == false )
        {
            return "";
        }
        Object obj = map.get(name);
        if ( obj != null )
        {
            value = obj.toString();
        }
        obj = null;
        
        return value;
    }
    
    /**
     * 返回整型值
     *
     * @param name 键值名称
     * @return 若不存在，或转换失败，则返回0
     */
    public static int getInt(Map map, String name)
    {
        if ( name == null || name.equals("") )
        {
            return 0;
        }
        
        int value = 0;
        if ( map.containsKey(name) == false )
        {
            return 0;
        }
        
        Object obj = map.get(name);
        if ( obj == null )
        {
            return 0;
        }
        
        if ( !(obj instanceof Integer) )
        {
            try
            {
                value = Integer.parseInt(obj.toString());
            }
            catch (Exception ex)
            {
                LoggerUtil.debug("name[" + name + "]对应的值不是数字，返回0", ex);
                value = 0;
            }
        }
        else
        {
            value = ((Integer) obj).intValue();
            obj = null;
        }
        
        return value;
    }
    
    /**
     * 获取长整型值
     *
     * @param name 键值名称
     * @return 若不存在，或转换失败，则返回0
     */
    public static long getLong(Map map, String name)
    {
        if ( name == null || name.equals("") )
        {
            return 0;
        }
        
        long value = 0;
        if ( map.containsKey(name) == false )
        {
            return 0;
        }
        
        Object obj = map.get(name);
        if ( obj == null )
        {
            return 0;
        }
        
        if ( !(obj instanceof Long) )
        {
            try
            {
                value = Long.parseLong(obj.toString());
            }
            catch (Exception ex)
            {
                LoggerUtil.error("name[" + name + "]对应的值不是数字，返回0", ex);
                value = 0;
            }
        }
        else
        {
            value = ((Long) obj).longValue();
            obj = null;
        }
        
        return value;
    }
    
    /**
     * 获取Float型值
     *
     * @param name 键值名称
     * @return 若不存在，或转换失败，则返回0
     */
    public static float getFloat(Map map, String name)
    {
        if ( name == null || name.equals("") )
        {
            return 0;
        }
        
        float value = 0;
        if ( map.containsKey(name) == false )
        {
            return 0;
        }
        
        Object obj = map.get(name);
        if ( obj == null )
        {
            return 0;
        }
        
        if ( !(obj instanceof Float) )
        {
            try
            {
                value = Float.parseFloat(obj.toString());
            }
            catch (Exception ex)
            {
                LoggerUtil.error("name[" + name + "]对应的值不是数字，返回0", ex);
                value = 0;
            }
        }
        else
        {
            value = ((Float) obj).floatValue();
            obj = null;
        }
        
        return value;
    }
    
    /**
     * 获取Double型值
     *
     * @param name 键值名称
     * @return 若不存在，或转换失败，则返回0
     */
    public static double getDouble(Map map, String name)
    {
        if ( name == null || name.equals("") )
        {
            return 0;
        }
        
        double value = 0;
        if ( map.containsKey(name) == false )
        {
            return 0;
        }
        
        Object obj = map.get(name);
        if ( obj == null )
        {
            return 0;
        }
        
        if ( !(obj instanceof Double) )
        {
            try
            {
                value = Double.parseDouble(obj.toString());
            }
            catch (Exception ex)
            {
                LoggerUtil.error("name[" + name + "]对应的值不是数字，返回0", ex);
                value = 0;
            }
        }
        else
        {
            value = ((Double) obj).doubleValue();
            obj = null;
        }
        
        return value;
    }
    
    /**
     * 获取Bool值
     *
     * @param name 键值名称
     * @return 若不存在，或转换失败，则返回false
     */
    public static boolean getBoolean(Map map, String name)
    {
        if ( name == null || name.equals("") )
        {
            return false;
        }
        
        boolean value = false;
        if ( map.containsKey(name) == false )
        {
            return false;
        }
        Object obj = map.get(name);
        if ( obj == null )
        {
            return false;
        }
        
        if ( obj instanceof Boolean )
        {
            return ((Boolean) obj).booleanValue();
        }
        
        value = Boolean.valueOf(obj.toString()).booleanValue();
        obj = null;
        return value;
    }
}