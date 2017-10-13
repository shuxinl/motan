package com.hinlin.motan.business;


import java.util.HashMap;
import java.util.Map;

import com.hinlin.Constants;

/**
 * 描述: 
 * 版权: Copyright (c) 2012 
 * 公司: hinlin 
 * 作者: hinlin
 * 版本: 1.0 
 * 创建日期: Jul 10, 2013 
 * 创建时间: 2:43:45 PM
 */
public class ResultVo
{
	
	private int errorNo;
	
	private String errorMsg;
	
	/**
	 * 保存结果集信息
	 * 结果集可能有多个，key是结果集名称，值可以是map、list、DBPage
	 */
	private Map<String, Object> results = null;
	
	public ResultVo()
	{
		this.errorNo = Constants.INVOKE_FUNCTION_SUCCESS;
		results = new HashMap<String, Object>();
	}
	
	public int getErrorNo()
	{
		return errorNo;
	}
	
	public void setErrorNo(int errorNo)
	{
		this.errorNo = errorNo;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}
	
	public Map<String, Object> getResults()
	{
		return results;
	}
	
	/**
	 * 
	 * 描述：当一个 接口只返回一个结果集时，可调用此方法
	 * 作者：hinlin
	 * 时间：Jul 11, 2013 10:25:12 AM
	 * @param object
	 */
	public void setResult(Object object)
	{
		this.results.put("DataSet", object);
	}
	
	/**
	 * 
	 * 描述：多个结果集需使用此方法，前台根据结果集名称获取不同的数据
	 * 作者：hinlin
	 * 时间：Jul 11, 2013 10:25:50 AM
	 * @param name
	 * @param object
	 */
	public void setResult(String name, Object object)
	{
		this.results.put(name, object);
	}
}
