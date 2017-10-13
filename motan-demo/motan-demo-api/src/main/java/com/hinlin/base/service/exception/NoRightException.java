package com.hinlin.base.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 描述:
 * 版权:	 Copyright (c) 2005
 * 公司:	 hinlin
 * 作者:	 hinlin
 * 版本:	 1.0
 * 创建日期: 2006-10-30
 * 创建时间: 13:48:28
 */
public class NoRightException extends RuntimeException
{
    public NoRightException()
    {
        super();
    }


    public NoRightException(String message)
    {
        super(message);
    }


    public NoRightException(String message, Throwable cause)
    {
        super(message, cause);
    }


    public NoRightException(Throwable cause)
    {
        super(cause);
    }

    public void printStackTrace()
    {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream outStream)
    {
        printStackTrace(new PrintWriter(outStream));
    }

    public void printStackTrace(PrintWriter writer)
    {
        super.printStackTrace(writer);

        if (getCause() != null)
        {
            getCause().printStackTrace(writer);
        }
        writer.flush();
    }
}