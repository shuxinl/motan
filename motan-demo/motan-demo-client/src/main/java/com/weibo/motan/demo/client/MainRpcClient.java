/*
 *  Copyright 2009-2016 Weibo, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.weibo.motan.demo.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hinlin.base.jdbc.DataRow;
import com.hinlin.base.util.JsonHelper;
import com.hinlin.client.result.BusResult;
import com.hinlin.motan.service.MainService;
import com.weibo.api.motan.util.LoggerUtil;

public class MainRpcClient {

	public static void main(String[] args) throws InterruptedException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("function", "1");
		MainRpcClient client = new MainRpcClient();
		BusResult busResult = client.execute(map);
		System.err.println(busResult.getPage().getData());
		System.err.println(busResult.getErr_no());
		System.err.println(busResult.getErr_info());
		System.err.println(busResult.getDataSetNum());
		
	}

	/**
	 * 
	 * execute:(这里用一句话描述这个方法的作用)调用执行方法执行完成会返回一个busresult对象 <br/>
	 *
	 * @author hinlin
	 * @param map
	 * @return BusResult
	 * @since JDK 1.6
	 */
	public BusResult execute(Map<String, Object> map) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "classpath:motan_demo_client.xml" });

		long start = System.currentTimeMillis();

		MainService service = (MainService) ctx.getBean("MainReferer");
		long end1 = System.currentTimeMillis() - start;
		LoggerUtil.info("使用时间：" + end1);
		// 参数拼装
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("function", "1");
		
		//调用
		String resultStr = service.execute(JSON.toJSONString(map));
		
		LoggerUtil.info(resultStr);
		
		BusResult result = getResultToString(resultStr);

		long end = System.currentTimeMillis() - start;
		
		LoggerUtil.info("使用时间：" + end);
		
		// System.exit(0);
		return result;
		
	}
	/**
	 * 
	 * getResultToString: 客户端请求json结果转换成对象 <br/>
	 * TODO(这里描述这个方法适用条件 – 可选) motand的返回结果都使用这个进行json字符串处理 <br/>
	 *
	 * @author hinlin
	 * @param result
	 * @return
	 * @since JDK 1.6
	 */
	public static BusResult getResultToString(String resultStr) {
		
		// 存储结果对象
		BusResult busresult = null;
		try {
			// 返回的结果集转成json对象
			Map<String, Object> result = (Map<String, Object>) JsonHelper.getObjectByJSON(resultStr);
			
			busresult = new BusResult();
			
			// 错误码
			int errNo = Integer.parseInt(result.get("error_no").toString());
			String errInfo = (String) result.get("error_info");

			// 错误码、错误信息处理
			busresult.setErr_no(errNo);
			busresult.setErr_info(errInfo);

			// 结果集处理
			JSONArray ds = (JSONArray) result.get("dsName");

			// 设置结果集数量 分页有两个数据集/普通有一个数据集合 + 自定义新增的数据集合
			busresult.setDataSetNum(ds.size());

			// 设置第一个数据集的名称
			busresult.setFirstDataSetName(ds.getString(0));

			// 数据集合的设置
			for (Object str : ds) {
				String subDsName = str.toString();
				JSONArray arr = (JSONArray) result.get(str.toString());
				// 把JSONArray装换成ArrayList<DataRow> 
				ArrayList<DataRow> subData = (ArrayList<DataRow>) arr.parseArray(arr.toJSONString(), DataRow.class);
				//设置值
				busresult.setResult(subDsName, subData);
			}
		} catch (Exception e) {
			LoggerUtil.error("client结果转换异常：" + e.getStackTrace());
			busresult.setErr_no(-999);
			busresult.setErr_info("client结果转换异常");
		}
		// //dbpage 测试
		// DBPage db = busresult.getPage();
		// System.err.println(db.getData());
		// ArrayList<DataRow> datas = (ArrayList<DataRow>) db.getData();
		// DataRow datarow = datas.get(0);
		// System.err.println(datarow.getObject("data1"));
		// System.err.println(db.getLastIndex());
		// System.err.println(db.getTotalPages());
		// System.err.println(db.getTotalRows());
		// System.err.println(db.getStartIndex());
		// System.err.println(busresult.getPage().getData());
		return busresult;
	}
	
/*	public static void main(String[] args) throws InterruptedException {

		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "classpath:motan_demo_client.xml" });

		long start = System.currentTimeMillis();

		MainService service = (MainService) ctx.getBean("MainReferer");
		long end1 = System.currentTimeMillis() - start;
		System.err.println("使用时间：" + end1);
		// 参数拼装
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("function", "1");

		// 测试调用 --- 测试打印点
		System.out.println(service.execute(JSON.toJSONString(map)));

		String resultStr = service.execute(JSON.toJSONString(map));
		
		System.err.println(resultStr);
		
		BusResult result = getResultToString(resultStr);

		long end = System.currentTimeMillis() - start;
		
		System.err.println("使用时间：" + end);
		// System.exit(0);
	}*/
}
