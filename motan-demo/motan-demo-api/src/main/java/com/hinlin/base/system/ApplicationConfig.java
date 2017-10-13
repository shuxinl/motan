package com.hinlin.base.system;

/**
 * 描述:
 * 版权:	 Copyright (c) 2005
 * 公司:	 hinlin
 * 作者:	 hinlin
 * 版本:	 1.0
 * 创建日期: 2006-11-18
 * 创建时间: 17:04:22
 */
public class ApplicationConfig
{
    private static String rootPath          = "";
    
    /**
     * 业务配置文件所在目录名称,系统会从该目录读取需要的配置文件,该目录必须位于环境变量THINKIVE_CONFIG所配置的目录下
     */
    private static String configCatalogName = "";
    
    /**
     * 获得当前应用程序的根目录的路径
     *
     * @return
     */
    public static String getRootPath()
    {
        return rootPath;
    }
    
    public static void setRootPath(String rootPath)
    {
        ApplicationConfig.rootPath = rootPath;
    }
    
    public static String getConfigCatalogName()
    {
        return configCatalogName;
    }
    
    public static void setConfigCatalogName(String configCatalogName)
    {
        ApplicationConfig.configCatalogName = configCatalogName;
    }
    
}
