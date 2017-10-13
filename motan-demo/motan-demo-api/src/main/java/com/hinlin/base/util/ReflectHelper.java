package com.hinlin.base.util;

import com.weibo.api.motan.util.LoggerUtil;

/**
 * 描述:	 反射工具类
 * 版权:	 Copyright (c) 2005
 * 公司:	 hinlin
 * 作者:	 hinlin
 * 版本:	 1.0
 * 创建日期: 2006-11-6
 * 创建时间: 21:51:56
 */
public class ReflectHelper
{
    /**
     * 提指定的类载入以系统中
     *
     * @param name 类名称
     * @return 类对象
     * @throws ClassNotFoundException
     */
    public static Class classForName(String name) throws ClassNotFoundException
    {
        try
        {
            return Thread.currentThread().getContextClassLoader().loadClass(name);
        }

        catch (ClassNotFoundException e)
        {
            LoggerUtil.error("类["+name+"]加载出错", e);
        }
        catch (SecurityException e)
        {
        	LoggerUtil.error("类["+name+"]加载出错", e);
        }
        return Class.forName(name);
    }

    /**
     * 根据名称生成指定的对象
     *
     * @param name 类名称
     * @return 具体的对象,若发生异常，则返回null
     */
    public static Object objectForName(String name)
    {
        try
        {
            return Class.forName(name).newInstance();
        }
        catch (Exception ex)
        {
        	LoggerUtil.error("获取对象实例出错",ex);
        }
        return null;
    }
}
