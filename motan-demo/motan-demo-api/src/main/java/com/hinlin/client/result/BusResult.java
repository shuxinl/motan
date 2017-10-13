package com.hinlin.client.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hinlin.base.jdbc.DBPage;
import com.hinlin.base.jdbc.DataRow;



/**
 * 描述: 
 * 版权: Copyright (c) 2012 
 * 公司: hinlin
 * 作者: hinlin
 * 版本: 1.0 
 * 创建日期: May 21, 2013 
 * 创建时间: 6:53:42 PM
 */
public class BusResult
{
	
	private int err_no = 0;//0成功
	
	private String err_info = "";//错误消息
	
	private int dataSetNum = 0;//结果集数量
	
	private String firstDataSetName = "";//第一个结果集名称
	
	private Map<String, ArrayList<DataRow>> results = new HashMap<String, ArrayList<DataRow>>();
	
	public int getErr_no()
	{
		return err_no;
	}
	
	public void setErr_no(int errNo)
	{
		err_no = errNo;
	}
	
	public String getErr_info()
	{
		return err_info;
	}
	
	public void setErr_info(String errInfo)
	{
		err_info = errInfo;
	}
	
	public int getDataSetNum()
	{
		return dataSetNum;
	}
	
	public void setDataSetNum(int dataSetNum)
	{
		this.dataSetNum = dataSetNum;
	}
	
	public void setFirstDataSetName(String firstDataSetName)
	{
		this.firstDataSetName = firstDataSetName;
	}
	
	public void setResult(String name, ArrayList<DataRow> hashMap)
	{
		this.results.put(name, hashMap);
	}
	
	/**
	 * 
	 * 描述：获取缺省结果集第一行内容(只有一个结果集时使用)
	 * 作者：hinlin
	 * 时间：Jul 11, 2013 3:12:21 PM
	 * @return
	 */
	public DataRow getDataRow()
	{
		return getDataRow(this.firstDataSetName);
	}
	
	/**
	 * 
	 * 描述：获取指定名称结果集的第一行内容
	 * 作者：hinlin
	 * 时间：Jul 11, 2013 3:12:42 PM
	 * @param dsName
	 * @return
	 */
	public DataRow getDataRow(String dsName)
	{
		List dataList = getDataList(dsName);
		if (dataList != null && !dataList.isEmpty())
		{
			return (DataRow) dataList.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * 描述：获取缺省结果集(只有一个结果集时使用)
	 * 作者：hinlin
	 * 时间：May 22, 2013 10:20:53 AM
	 * @return
	 */
	public List getDataList()
	{
		return getDataList(this.firstDataSetName);
	}
	
	/**
	 * 
	 * 描述：根据结果集名称返回结果集
	 * 作者：hinlin
	 * 时间：May 22, 2013 10:22:45 AM
	 * @param index
	 * @return
	 */
	public List getDataList(String dsName)
	{
		if (this.results.containsKey(dsName))
		{
			return this.results.get(dsName);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 
	 * 描述：获取缺省的颁页数据
	 * 作者：hinlin
	 * 时间：Jul 11, 2013 3:21:34 PM
	 * @return
	 */
	public DBPage getPage()
	{
		if (this.firstDataSetName.length() > 0)
		{
			/**
			 * 分页数据是分两个结果集返回的,第一个结果集名称和第二个结果名称分别是：结果集名称+0、结果集名称加1
			 * 这里先删掉末尾的0和1，再下面方法中追加
			 */
			
			return getPage(this.firstDataSetName.substring(0, this.firstDataSetName.length() - 1));
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 
	 * 描述：获取分页数据
	 * 作者：hinlin
	 * 时间：Jul 9, 2013 7:36:14 PM
	 * @return
	 */
	public DBPage getPage(String dsName)
	{
		DBPage dbPage = null;
		if (this.dataSetNum < 2)
		{
			return null;
		}
		List dataList = getDataList(dsName + "0");//获取第一个结果集
		Map pageData = getDataRow(dsName + "1");//获取第二个结果集的第一行数据
		int curr_page = Integer.parseInt(pageData.get("curr_page").toString()) ;//当前页
		int page_count = Integer.parseInt(pageData.get("page_count").toString());//一页显示记录数
		int total_rows = Integer.parseInt(pageData.get("total_rows").toString());//总记录数
		
		if (curr_page <= 0 || page_count <= 0)
		{
			return null;
		}
		
		dbPage = new DBPage(curr_page, page_count);
		dbPage.setData(dataList);
		dbPage.setTotalRows(total_rows);
		return dbPage;
	}
}