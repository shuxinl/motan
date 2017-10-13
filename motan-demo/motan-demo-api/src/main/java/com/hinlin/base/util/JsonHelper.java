package com.hinlin.base.util;

import com.alibaba.fastjson.JSON;
import com.weibo.api.motan.util.LoggerUtil;

/**
 * 描述: 
 * 版权: Copyright (c) 2012 
 * 公司: hinlin 
 * 作者: hinlin
 * 版本: 1.0 
 * 创建日期: Nov 13, 2013 
 * 创建时间: 2:01:13 PM
 * 
 * 修改：hinlin 20170420
   * 　1：由于jsckson2.8以下版本存在漏洞，用fastjson替换jackson
 */
public class JsonHelper
{
    
    
    //private static ObjectMapper mapper = new ObjectMapper();
    
    /**
     * 
     * @描述: 将对象转化为json字串 
     * @作者: hinlin
     * @创建日期: 2017年4月20日 下午4:26:12
     * @param obj
     * @return
     */
    public static String getJSONString(Object obj)
    {
        //        String result = "";
        //        try
        //        {
        //            result = mapper.writeValueAsString(obj);
        //        }
        //        catch (JsonGenerationException e)
        //        {
        //            LoggerUtil.error("", e);
        //        }
        //        catch (JsonMappingException e)
        //        {
        //            LoggerUtil.error("", e);
        //        }
        //        catch (IOException e)
        //        {
        //            LoggerUtil.error("", e);
        //        }
        //        return result;
        
        
        String result = "";
        try
        {
            result = JSON.toJSONString(obj);
        }
        catch (Exception e)
        {
            LoggerUtil.error("", e);
        }
        return result;
    }
    
    /**
     * 
     * @描述: 将字串反序列化为对象 
     * @作者: hinlin
     * @创建日期: 2017年4月20日 下午4:26:27
     * @param jsonStr
     * @return
     */
    public static Object getObjectByJSON(String jsonStr)
    {
        //        Object obj = null;
        //        try
        //        {
        //            obj = mapper.readValue(jsonStr, Object.class);
        //        }
        //        catch (JsonParseException e)
        //        {
        //            LoggerUtil.error("", e);
        //        }
        //        catch (JsonMappingException e)
        //        {
        //            LoggerUtil.error("", e);
        //        }
        //        catch (IOException e)
        //        {
        //            LoggerUtil.error("", e);
        //        }
        //        return obj;
        
        Object obj = null;
        try
        {
            obj = JSON.parseObject(jsonStr,Object.class);
        }
        catch (Exception e)
        {
            LoggerUtil.error("", e);
        }
        return obj;
    }
    
    /**
     * 
     * 描述：json返回指定类型对象
     * @author 王泽宇
     * @created 2017年3月16日 下午5:46:05
     * @since 
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T getObjectByJSON(String jsonStr, Class<T> clazz)
    {
        //        T obj = null;
        //        try
        //        {
        //            obj = mapper.readValue(jsonStr, clazz);
        //        }
        //        catch (JsonParseException e)
        //        {
        //            LoggerUtil.error("", e);
        //        }
        //        catch (JsonMappingException e)
        //        {
        //            LoggerUtil.error("", e);
        //        }
        //        catch (IOException e)
        //        {
        //            LoggerUtil.error("", e);
        //        }
        //        return obj;
        
        T obj = null;
        try
        {
            obj = JSON.parseObject(jsonStr,clazz);
        }
        catch (Exception e)
        {
            LoggerUtil.error("", e);
        }
        return obj;
    }
    
}
