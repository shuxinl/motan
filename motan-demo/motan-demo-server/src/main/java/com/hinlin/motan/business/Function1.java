/**
 * hinlin接口实现类
 */
package com.hinlin.motan.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hinlin.base.jdbc.DBPage;
import com.hinlin.base.jdbc.DataRow;
import com.hinlin.exception.InvokeException;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

@MotanService(export = "demoMotan:8002")
public class Function1 extends BaseFunction {


	@Override
	public ResultVo execute() throws InvokeException {
		ResultVo result = new ResultVo();
		String str = this.getStrParameter("function");
		System.err.println("function:"+str);
		result.setErrorMsg("执行成功");
		result.setErrorNo(1);
		Map data = new HashMap();
		data.put("name", "lsx");
		List<DataRow> list = new ArrayList<DataRow>() {};
		DataRow da = new DataRow();
		da.put("data1", "data1");
		da.put("data1", "data1");
		list.add(da);
		DataRow da2 = new DataRow();
		da2.put("data1", "data1");
		da2.put("data1", "data1");
		list.add(da2);
		DBPage page = new DBPage(1, 10);
		page.setData(list);
		result.setResult(page);
		return result;
	}

//	@Override
//	public void invoke(SimpleContextImpl context) {
//	    String function = context.getRequest().getFieldValue("function");
//		System.err.println("function参数输出："+function);
//		LoggerUtil.error("参数："+function);
//		
//	}
	
}
