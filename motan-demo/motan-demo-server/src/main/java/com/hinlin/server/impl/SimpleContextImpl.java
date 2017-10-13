package com.hinlin.server.impl;

import com.hinlin.server.IASContext;
import com.hinlin.server.IASRequest;
import com.hinlin.server.IASResponse;

public class SimpleContextImpl
  implements IASContext
{
  private IASRequest request;
  private IASResponse response;

  public SimpleContextImpl()
  {
    this.request = null;
    this.response = null;
  }

  public void setRequest(IASRequest request)
  {
    this.request = request;
  }

  public IASRequest getRequest()
  {
    return this.request;
  }

  public void setResponse(IASResponse response)
  {
    this.response = response;
  }

  public IASResponse getResponse()
  {
    return this.response;
  }

  public void clear()
  {
    this.request = null;
    this.response = null;
  }
}