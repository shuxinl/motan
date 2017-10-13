package com.hinlin.base.service;

import com.hinlin.base.service.exception.ServiceException;
import com.weibo.api.motan.util.LoggerUtil;

/**
 * 描述:
 * 版权:	 Copyright (c) 2005
 * 公司:	 hinlin
 * 作者:	 hinlin
 * 版本:	 1.0
 * 创建日期: 2006-10-11
 * 创建时间: 14:49:14
 */
public class ServiceLocator
{

    /**
     * 返回服务对象
     *
     * @param interfaceClass
     * @return
     */
    public static Object getService(Class interfaceClass)
    {

        String serviceClassName = interfaceClass.getName();
        int idx = serviceClassName.lastIndexOf(".");
        String packageName = "";
        String shortName = "";
        if (idx == -1)
        {
            shortName = serviceClassName;
        }
        else
        {
            packageName = serviceClassName.substring(0, idx);
            shortName = serviceClassName.substring(idx + 1);
        }


        String serviceImplClassName = packageName + ".impl." + shortName + "Impl";
        Class clazz = null;
        try
        {
            clazz = Class.forName(serviceImplClassName);
            return clazz.newInstance();
        }
        catch (ClassNotFoundException ne)
        {
            LoggerUtil.error("没有找到相应的service实现类[" + serviceImplClassName + "]", ne);
            throw new ServiceException("没有找到相应的service实现类[" + serviceImplClassName + "]", ne);
        }
        catch (Exception ex)
        {
            LoggerUtil.error("创建service实现类失败[" + serviceImplClassName + "]", ex);
            throw new ServiceException("创建service实现类失败[" + serviceImplClassName + "]", ex);
        }
    }
}
