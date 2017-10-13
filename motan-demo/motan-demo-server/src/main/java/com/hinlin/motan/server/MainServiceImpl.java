/**
 * hinlin接口实现类
 */
package com.hinlin.motan.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hinlin.base.jdbc.DataRow;
import com.hinlin.base.util.JsonHelper;
import com.hinlin.base.util.ReflectHelper;
import com.hinlin.base.util.StringHelper;
import com.hinlin.client.result.BusResult;
import com.hinlin.client.result.testDao;
import com.hinlin.motan.service.MainService;
import com.hinlin.server.Function;
import com.hinlin.server.function.config.ModuleXMLConfig;
import com.hinlin.server.impl.SimpleContextImpl;
import com.hinlin.server.impl.SimpleRequestImpl;
import com.hinlin.server.impl.SimpleResponseImpl;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.weibo.api.motan.util.LoggerUtil;

@MotanService(export = "demoMotan:8002")
public class MainServiceImpl implements MainService {
	@Override
	public String execute(String paramJson) {

		SimpleContextImpl context = null;
		SimpleRequestImpl request = null;
		SimpleResponseImpl response = null;

		ResultJsonModel resultJson = new ResultJsonModel();
		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// 获取参数
		Map params = getParaMap(paramJson);
		Function function = null;

		String funNo = params.get("function").toString();

		if (StringHelper.isEmpty(funNo)) {
			LoggerUtil.error("入参功能号不能为空");
		}
		context = new SimpleContextImpl();
		request = new SimpleRequestImpl();
		response = new SimpleResponseImpl();

		context.setRequest(request);
		context.setResponse(response);

		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			request.addFieldValue(String.valueOf(key), String.valueOf(value));
		}
		// 获取配置文件对应功能映射类
		String classImpl = ModuleXMLConfig.getClassImpl(params.get("function").toString());
		if (StringHelper.isNotEmpty(classImpl)) {
			function = (Function) ReflectHelper.objectForName(classImpl);
		}

		if (function != null) {
			function.invoke(context);
		} else {
			LoggerUtil.error("功能号【" + funNo + "】实现类不存在");
		}
		// 结果出参封装
		int errNo = response.getErrorNo();
		String errInfo = response.getErrorInfo();

		resultJson.setErrorNo(errNo+"");
		resultJson.setErrorInfo(errInfo);

		if (errNo >= 0) {
			int dataSetCount = response.getDataSetCount();
			if (dataSetCount > 0) {
				List dataSetNameList = response.getDataSetNameList();
				List dataSetFieldList = response.getDataSetFieldList();
				List dataSetValueList = response.getDataSetValueList();
				ArrayList dataList = null;
				for (int m = 0; m < dataSetCount; ++m) {
					String curDSName = (String) dataSetNameList.get(m);
					List curDSFieldList = (List) dataSetFieldList.get(m);
					List curDSValueList = (List) dataSetValueList.get(m);

					int fieldCount = curDSFieldList.size();
					int rowCount = (fieldCount > 0) ? curDSValueList.size() / fieldCount : 0;
					if (rowCount * fieldCount != curDSValueList.size()) {
						fieldCount = 0;
						rowCount = 0;
					}

					dataList = new ArrayList();

					String[] fieldNameArray = new String[fieldCount];
					for (int i = 0; i < curDSFieldList.size(); ++i) {
						String field = curDSFieldList.get(i).toString();
						fieldNameArray[i] = field;
					}

					for (int i = 0; i < rowCount; ++i) {
						DataRow rowMap = new DataRow();
						for (int j = 0; j < fieldNameArray.length; ++j) {
							int index = i * fieldCount + j;
							String field = fieldNameArray[j];
							String value = curDSValueList.get(index).toString();
							rowMap.set(field.trim(), value);
						}
						dataList.add(rowMap);
					}
					resultJson.setResults(curDSName, dataList);
				}
			}
		}
		return JsonHelper.getJSONString(resultJson.toMap());
	}

	/**
	 * 获取参数
	 * 
	 * @param encrypt
	 * @param paramJson
	 *            所有参数组装在json字符串中
	 * @return
	 */
	private Map getParaMap(String paramJson) {
		if (StringHelper.isEmpty(paramJson)) {
			LoggerUtil.error("入参为空");
		}
		// paramJson = Base64Helper.decodeToString(paramJson);

		Map paraMap = (Map) JsonHelper.getObjectByJSON(paramJson);

		return paraMap;
	}
}
