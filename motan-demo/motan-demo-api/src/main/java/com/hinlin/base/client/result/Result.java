package com.hinlin.client.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hinlin.base.jdbc.DBPage;
import com.hinlin.base.jdbc.DataRow;

public class Result
{
  private Map<String, ArrayList<DataRow>> results;
  private String err_info;
  private int dataSetNum;
  private String firstDataSetName;
  private int err_no;

  public Result()
  {
    this.results = new HashMap();

    this.err_info = "";

    this.dataSetNum = 0;

    this.firstDataSetName = "";

    this.err_no = 0;
  }

  public int getErr_no() {
    return this.err_no;
  }

  public void setErr_no(int errNo)
  {
    this.err_no = errNo;
  }

  public String getErr_info()
  {
    return this.err_info;
  }

  public void setErr_info(String errInfo)
  {
    this.err_info = errInfo;
  }

  public void setFirstDataSetName(String firstDataSetName)
  {
    this.firstDataSetName = firstDataSetName;
  }

  public int getDataSetNum()
  {
    return this.dataSetNum;
  }

  public void setDataSetNum(int dataSetNum)
  {
    this.dataSetNum = dataSetNum;
  }

  public DataRow getData()
  {
    return getData(this.firstDataSetName);
  }

  public DataRow getData(String dsName)
  {
    List dataList = getList(dsName);
    if ((dataList != null) && (!(dataList.isEmpty())))
    {
      return ((DataRow)dataList.get(0));
    }
    return new DataRow();
  }

  public List<DataRow> getList()
  {
    return getList(this.firstDataSetName);
  }

  public List getList(String dsName)
  {
    if (this.results.containsKey(dsName))
    {
      return ((List)this.results.get(dsName));
    }

    return new ArrayList();
  }

  public String getString(String name)
  {
    return getData().getString(name);
  }

  public DBPage getPage()
  {
    if (this.firstDataSetName.length() > 0)
    {
      return getPage(this.firstDataSetName.substring(0, this.firstDataSetName.length() - 1));
    }

    return null;
  }

  public DBPage getPage(String dsName)
  {
    DBPage dbPage = null;
    if (this.dataSetNum < 2)
    {
      return null;
    }
    List dataList = getList(dsName + "0");
    DataRow pageData = getData(dsName + "1");
    int curr_page = pageData.getInt("curr_page");
    int page_count = pageData.getInt("page_count");
    int total_rows = pageData.getInt("total_rows");

    if ((curr_page <= 0) || (page_count <= 0))
    {
      return null;
    }

    dbPage = new DBPage(curr_page, page_count);
    dbPage.setData(dataList);
    dbPage.setTotalRows(total_rows);
    return dbPage;
  }

  public void setResult(String name, ArrayList dataList)
  {
    this.results.put(name, dataList);
  }

  public Set<String> dsNameSet()
  {
    return this.results.keySet();
  }
}