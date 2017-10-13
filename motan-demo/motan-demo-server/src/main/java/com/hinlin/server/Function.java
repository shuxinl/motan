package com.hinlin.server;

import com.hinlin.server.impl.SimpleContextImpl;

public abstract interface Function
{
  public abstract void invoke(SimpleContextImpl context);
}
