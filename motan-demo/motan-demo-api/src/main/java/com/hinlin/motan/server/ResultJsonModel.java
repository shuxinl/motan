package com.hinlin.motan.server;

import com.hinlin.base.domain.DynaModel;
import com.hinlin.base.jdbc.DataRow;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ResultJsonModel extends DynaModel
{
  public static final String SUCCESS_CODE = "0";
  public static final String SYSTEM_ERROR_CODE = "-1000";
  public static final String NO_LOGIN_ERROR_CODE = "-999";
  private static final String ERROR_NO = "error_no";
  private static final String ERROR_INFO = "error_info";
  private static final String RESULTS = "results";
  private static final String DS_NAME = "dsName";
  private static final String PAGEINFO = "pageinfo";

  public void setErrorNo(String errorNo)
  {
    set("error_no", errorNo);
  }

  public String getErrorNo()
  {
    return getString("error_no");
  }

  public void setErrorInfo(String errorInfo)
  {
    set("error_info", errorInfo);
  }

  public String getErrorInfo()
  {
    return getString("error_info");
  }

  public void setDsName(String dsName)
  {
    HashSet result = (HashSet)getObject("dsName");
    if (result == null)
    {
      result = new HashSet();
      set("dsName", result);
    }
    result.add(dsName);
  }

  public void setResults(Map result)
  {
    List dataList = new ArrayList();
    dataList.add(result);

    setDsName("results");
    set("results", dataList);
  }

  public void setResults(List results)
  {
    setDsName("results");
    set("results", results);
  }

  public void setResults(String name, List results)
  {
    setDsName(name);
    set(name, results);
  }

  public List getResults()
  {
    Object value = getObject("results");
    if (value instanceof List)
    {
      return ((List)value);
    }

    return null;
  }

  public DataRow getPageInfo()
  {
    Object value = getObject("pageinfo");
    if (value instanceof DataRow)
    {
      return ((DataRow)value);
    }

    return null;
  }
}