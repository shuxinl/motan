package com.hinlin.exception;

/**
 * 
 * @author hinlin
 *
 */
public class InvokeException extends Exception
{
	
	private int errorCode;
	
	public InvokeException(int errorCode, String message)
	{
		super(message);
		this.errorCode = errorCode;
	}
	
	public int getErrorCode()
	{
		return errorCode;
	}
	
}
