/*
 *hinlin入口接口类 
 */

package com.hinlin.motan.service;

import com.hinlin.client.result.testDao;
import com.weibo.api.motan.transport.async.MotanAsync;

@MotanAsync
public interface MainService {
	
	String execute(String name);
}
