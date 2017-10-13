package com.hinlin.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hinlin.server.IASRequest;

public class SimpleRequestImpl
  implements IASRequest
{
  private Map fieldValueMap;
  private List fieldNameList;
  private int branchNo;
  private int funcNo;

  public SimpleRequestImpl()
  {
    this.fieldValueMap = new HashMap();

    this.fieldNameList = new ArrayList();

    this.branchNo = 0;

    this.funcNo = 0;
  }

  public int getFieldCount()
  {
    return this.fieldNameList.size();
  }

  public String getFieldName(int index)
  {
    if ((this.fieldNameList.size() > 0) && 
      (index >= 0) && (index < this.fieldNameList.size()))
    {
      return ((String)this.fieldNameList.get(index));
    }

    return "";
  }

  public String getFieldValue(String name)
  {
    Object value = this.fieldValueMap.get(name);
    return ((value == null) ? "" : value.toString());
  }

  public void addFieldValue(String name, String value)
  {
    this.fieldValueMap.put(name, value);
    this.fieldNameList.add(name);
  }

  public void setFuncNo(int funcNo)
  {
    this.funcNo = funcNo;
  }

  public int getFuncNo()
  {
    return this.funcNo;
  }

  public void setBranchNo(int branchNo)
  {
    this.branchNo = branchNo;
  }

  public int getBranchNo()
  {
    return this.branchNo;
  }

  public void clear()
  {
    this.fieldValueMap.clear();
    this.fieldNameList.clear();
  }
}