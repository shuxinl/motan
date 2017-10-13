package com.hinlin.motan.business;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.hinlin.Constants;
import com.hinlin.base.jdbc.DBPage;
import com.hinlin.base.jdbc.DataRow;
import com.hinlin.base.service.ServiceLocator;
import com.hinlin.base.util.ConvertHelper;
import com.hinlin.base.util.MapHelper;
import com.hinlin.base.util.StringHelper;
import com.hinlin.exception.InvokeException;
import com.hinlin.server.Function;
import com.hinlin.server.IASContext;
import com.hinlin.server.IASRequest;
import com.hinlin.server.IASResponse;
import com.hinlin.server.impl.SimpleContextImpl;
import com.weibo.api.motan.util.LoggerUtil;

/**
 * 描述:
 * 版权:	 Copyright (c) 2007
 * 公司:	 思迪科技
 * 作者:	 易庆锋
 * 版本:	 1.0
 * 创建日期: 2007-11-21
 * 创建时间: 12:49:53
 */
public abstract class BaseFunction implements Function {

	private IASRequest request = null;

	private IASResponse response = null;

	/**
	 * 
	 * 描述：抽象方法，由子类覆写
	 * 作者：李炜
	 * 时间：Jul 9, 2013 8:55:45 PM
	 */
	public abstract ResultVo execute() throws InvokeException;

	/**
	 * 
	 * 描述：
	 * 作者：李炜
	 * 时间：Jul 10, 2013 9:36:24 AM
	 * @param context
	 */
	@Override
	public void invoke(SimpleContextImpl context) {
		this.request = context.getRequest();
		this.response = context.getResponse();

		try {
			before();
			ResultVo result = execute();
			after();
			packet(result);
		} catch (InvokeException e) {
			LoggerUtil.error("", e);
			response.clear();
			response.setErrorNo(e.getErrorCode());
			response.setErrorInfo("调用业务功能[" + request.getFuncNo() + "]失败，失败原因[" + e.getMessage() + "]");
		} catch (Exception e) {
			LoggerUtil.error("", e);
			response.clear();
			response.setErrorNo(Constants.SYSTEM_EXCEPTION);
			response.setErrorInfo("调用业务功能[" + request.getFuncNo() + "]失败，失败原因[" + e.getMessage() + "]");
		}

	}

	protected void setRequest(IASRequest request) {
		this.request = request;
	}

	protected IASRequest getRequest() {
		return request;
	}

	protected void setResponse(IASResponse response) {
		this.response = response;
	}

	protected IASResponse getResponse() {
		return response;
	}

	/**
	 * 
	 * 描述：返回字串Parameter,若不存在，则返回空字串
	 * 作者：李炜
	 * 时间：Jul 10, 2013 1:55:34 PM
	 * @param fieldName
	 * @return
	 */
	public String getStrParameter(String fieldName) {
		String value = request.getFieldValue(fieldName);
		return (StringHelper.isEmpty(value)) ? "" : value;
	}

	/**
	 * 
	 * 描述：返回字串Parameter,若不存在，则返回缺省值
	 * 作者：李炜
	 * 时间：Jul 10, 2013 1:57:54 PM
	 * @param fieldName
	 * @param defaultValue
	 * @return
	 */
	public String getStrParameter(String fieldName, String defaultValue) {
		String value = request.getFieldValue(fieldName);
		return (StringHelper.isEmpty(value)) ? defaultValue : value;
	}

	/**
	 * 
	 * 描述：
	 * 作者：李炜
	 * 时间：Jul 13, 2013 1:33:39 PM
	 * @param fieldName
	 * @return
	 */
	public String[] getStrArrayParameter(String fieldName) {
		String value = getStrParameter(fieldName);
		return StringHelper.split(value, ",");

	}

	/**
	 * 返回整数，若不存在或转换失败，则返回0
	 *
	 * @param name
	 * @return
	 */
	public int getIntParameter(String fieldName) {
		String value = request.getFieldValue(fieldName);
		return ConvertHelper.strToInt(value);
	}

	/**
	 * 
	 * 描述：返回整数，若不存在或转换失败，则返回缺省值
	 * 作者：李炜
	 * 时间：Jul 10, 2013 2:01:18 PM
	 * @param fieldName
	 * @param defaultValue
	 * @return
	 */
	public int getIntParameter(String fieldName, int defaultValue) {
		String value = request.getFieldValue(fieldName);
		if (StringHelper.isEmpty(value)) {
			return defaultValue;
		} else {
			return ConvertHelper.strToInt(value);
		}
	}
	
	/**
	 * 
	 * 描述：
	 * 作者：李炜
	 * 时间：Jul 10, 2013 2:10:34 PM
	 * @return
	 */
	public DataRow getDataRowParameter() {
		DataRow dataRow = new DataRow();
		int fieldCount = request.getFieldCount();
		for (int i = 0; i < fieldCount; ++i) {
			String name = request.getFieldName(i);
			String value = request.getFieldValue(name);
			dataRow.set(name, value);
		}
		return dataRow;
	}

	/**
	 * 
	 * 描述：
	 * 作者：李炜
	 * 时间：Jul 10, 2013 2:10:34 PM
	 * @return
	 */
	public Map getArrayParameter() {
		Map map = new HashMap();
		int fieldCount = request.getFieldCount();
		for (int i = 0; i < fieldCount; ++i) {
			String name = request.getFieldName(i);
			String value = request.getFieldValue(name);
			map.put(name, value);
		}
		return map;
	}

	/**
	 * 
	 * 描述：返回错误号，格式 - 功能号 错误号
	 * 作者：李炜
	 * 时间：Jul 10, 2013 10:11:56 AM
	 * @param errorNo 取值范围 0-99
	 * @return
	 */
	protected int getErrorNo(int errorNo) {
		if (errorNo > 100) {
			errorNo = 99;
		}
		if (errorNo < 0) {
			errorNo = 0;
		}
		if (errorNo < 10) {
			return ConvertHelper.strToInt("-" + request.getBranchNo() + "0" + errorNo);
		} else {
			return ConvertHelper.strToInt("-" + request.getBranchNo() + errorNo);
		}
	}

	/**
	 * 
	 * 描述：打包结果集
	 * 作者：李炜
	 * 时间：Jul 10, 2013 9:36:11 AM
	 * @param object
	 * @throws Exception
	 */
	protected void packet(ResultVo result) throws Exception {
		
		if (result == null) {
			response.setErrorNo(Constants.INVOKE_FUNCTION_SUCCESS);
			response.setErrorInfo("调用成功");
		} else {
			response.setErrorNo(result.getErrorNo());
			response.setErrorInfo(result.getErrorMsg());
			Map results = result.getResults();
			if (results != null && !results.isEmpty()) {
				for (Iterator iter = results.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					Object object = results.get(name);
					if (object instanceof DataRow) {
						response.newDataSet();
						response.setDataSetName(name);
						packetRightResultData((DataRow) object);
					} else if (object instanceof Map) {
						response.newDataSet();
						response.setDataSetName(name);
						packetRightResultData((Map) object);
					} else if (object instanceof List) {
						response.newDataSet();
						response.setDataSetName(name);
						packetRightResultList((List) object);
					} else if (object instanceof DBPage) {
						packetRightResultPage(name, (DBPage) object);
					} else {
						throw new Exception("不支持的打包类型");
					}
				}
			}
		}
	}

	/**
	 * 在调用相应的function方法前调用,子类可以覆盖,在进入方法前做一些处理
	 *
	 * @param function 业务功能参数
	 */
	public void before() {
	}

	/**
	 * 在调用相应的function之后调用，子类可以覆盖,在离开方法后再进行一些相应的处理
	 *
	 * @param function 业务功能参数
	 */
	public void after() {
	}

	/**
	 * 
	 * @描述：打包map
	 * @作者：李炜
	 * @时间：2011-11-3 下午01:09:39
	 * @param dataRow
	 * @param response
	 * @throws Exception
	 */
	protected void packetRightResultData(Map rowMap) throws Exception {
		ArrayList arrayList = new ArrayList();
		arrayList.add(rowMap);
		packetRightResultList(arrayList);
	}

	/**
	 * 
	 * @描述：打包list
	 * @作者：李炜
	 * @时间：2011-11-3 下午01:08:06
	 * @param valueList
	 * @param response
	 * @throws Exception
	 */
	protected void packetRightResultList(List<Map> valueList) throws Exception {
		if (valueList != null && valueList.size() > 0) {
			String fieldName = "";
			for (int i = 0; i < valueList.size(); i++) {
				Map rowMap = valueList.get(i);
				Object[] itemNameArray = rowMap.keySet().toArray();
				if (i == 0) {
					for (int j = 0; j < itemNameArray.length; j++) {
						fieldName = (String) itemNameArray[j];
						response.addField(fieldName);
					}
				}

				for (int j = 0; j < itemNameArray.length; j++) {
					fieldName = (String) itemNameArray[j];
					response.addValue(MapHelper.getString(rowMap, fieldName));
				}
			}
		}
	}

	/**
	 * 
	 * 描述：打包分页数据，分两个结果信返回
	 * 作者：李炜
	 * 时间：May 13, 2013 3:20:22 PM
	 * @param page
	 * @param response
	 * @throws Exception
	 */
	protected void packetRightResultPage(String name, DBPage page) throws Exception {
		if (page != null) {
			// 打包第一个结果集
			response.newDataSet();
			response.setDataSetName(name + "0");
			packetRightResultList(page.getData());

			// 打包第二个结果集：分页信息
			DataRow pageInfo = new DataRow();
			pageInfo.set("page_count", page.getNumPerPage());// 一页显示记录数
			pageInfo.set("curr_page", page.getCurrentPage());// 当前页
			pageInfo.set("total_rows", page.getTotalRows());// 总记录数
			pageInfo.set("total_pages", page.getTotalPages());// 总页数
			response.newDataSet();
			response.setDataSetName(name + "1");
			packetRightResultData(pageInfo);

		}
	}

	/**
	 * 把结果集写入Response
	 *
	 * @param rs       结果集
	 * @param response
	 * @throws SQLException
	 */
	protected void packetRightResultSet(ResultSet rs, IASResponse response) throws SQLException {
		response.setErrorNo(0);
		response.setErrorInfo("调用成功");

		ResultSetMetaData metaData = rs.getMetaData();
		int colNameCount = metaData.getColumnCount();
		if (colNameCount <= 0) // 没有值需要返回
		{
			return;
		}

		// 得到列名数组
		String[] colNameArray = new String[colNameCount];
		for (int i = 0; i < colNameCount; i++) {
			String fieldName = metaData.getColumnName(i + 1);
			colNameArray[i] = fieldName;
		}

		// 得到值列表
		ArrayList valueList = new ArrayList();
		while (rs.next()) {
			DataRow dataRow = new DataRow();
			for (int i = 0; i < colNameArray.length; i++) {
				String fieldName = colNameArray[i];
				Object value = rs.getObject(fieldName);
				if (value instanceof Clob) {
					value = rs.getString(fieldName);
				} else if (value instanceof Blob) {
					value = rs.getBytes(fieldName);
				}
				dataRow.set(fieldName, value);
			}
			valueList.add(dataRow);
		}

		// 若有值则才需要打包
		if (valueList.size() > 0) {
			// 添加一个新结果集
			response.newDataSet();

			for (int i = 0; i < colNameArray.length; i++) {
				String fieldName = colNameArray[i];
				// 把字段转换成小写
				response.addField(fieldName.toLowerCase());
			}

			// 添加值
			for (Iterator iter = valueList.iterator(); iter.hasNext();) {
				DataRow data = (DataRow) iter.next();
				for (int i = 0; i < colNameArray.length; i++) {
					String fieldName = colNameArray[i];
					response.addValue(data.getString(fieldName));
				}
			}
		}
	}

	/**
	 * @param rs
	 * @param response
	 * @throws SQLException
	 */
	protected void packetErrorResultSet(ResultSet rs, IASResponse response) throws SQLException {
		response.setErrorNo(-10000);
		response.setErrorInfo("调用失败");
		if (rs.next()) {
			int errorno = rs.getInt("error_no");
			String errorinfo = rs.getString("error_info");
			response.setErrorNo(errorno);
			response.setErrorInfo(errorinfo);
		}
	}

	/**
	 * 把记录集转换位DataRow
	 *
	 * @param rs
	 * @param metaData
	 * @return
	 * @throws SQLException
	 */
	private DataRow toDataRow(ResultSet rs, ResultSetMetaData metaData) throws SQLException {
		DataRow dataRow = new DataRow();
		int count = metaData.getColumnCount();
		for (int i = 0; i < count; i++) {
			String fieldName = metaData.getColumnName(i + 1);
			Object value = rs.getObject(fieldName);
			if (value instanceof Clob) {
				value = rs.getString(fieldName);
			} else if (value instanceof Blob) {
				value = rs.getBytes(fieldName);
			}
			// 把字段名转换为小写
			dataRow.set(fieldName.toLowerCase(), value);
		}
		return dataRow;
	}

	/**
	 * 关闭ResultSet
	 *
	 * @param
	 */
	protected void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 关闭statement
	 *
	 * @param stmt
	 */
	protected void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 关闭数据库连接
	 *
	 * @param conn
	 */
	protected void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 获得相应的Service实现类
	 *
	 * @param interfaceClass
	 * @return
	 */
	public Object getService(Class interfaceClass) {
		return ServiceLocator.getService(interfaceClass);
	}

	/**
	 * 
	 * 描述：当前页数，默认是第一页
	 * 作者：李炜
	 * 时间：May 13, 2013 3:05:32 PM
	 * @param request
	 * @return
	 */
	public int getCurrPage() {
		int result = 0;
		String currPage = request.getFieldValue("page");// 当前页数 默认为第一页
		result = ConvertHelper.strToInt(currPage);
		return (result == 0) ? 1 : result;
	}

	/**
	 * 
	 * 描述：一页显示记录数，默认是50条，大于1000条按系统指定值(1000条)处理
	 * 作者：李炜
	 * 时间：May 13, 2013 2:59:45 PM
	 * @param request
	 * @return
	 */
	public int getPageCount() {
		int result = 0;
		String pageCount = request.getFieldValue("numPerPage");
		result = ConvertHelper.strToInt(pageCount);
		result = (result == 0) ? Constants.ROW_OF_PAGE : result;
		result = (result >= 1000) ? 1000 : result;
		return result;
	}

	/**
	 * 
		 * 描述：验证字段集合个字段是否为空
		 * 作者：李标 libiao@thinkive.com
		 * 时间：2013-7-4 下午05:29:41
		 * @param  输入参数
		 * @return 输出参数
	 * @throws InvokeException 
	 */
	public void validateNotNull(Map dataRow, String[] validateKeys) throws InvokeException {

		if (validateKeys != null && validateKeys.length > 0) {

			for (int i = 0; i < validateKeys.length; i++) {
System.out.println("validateKeys"+validateKeys[i]);
				String[] str = StringHelper.split(validateKeys[i], "|");

				int errorNo = new Integer(str[0]);// 错误号
				String validateKey = str[1];// 验证关键字。
				String msgKey = str[2];// 消息消息关键字
				if (!dataRow.containsKey(validateKey)) {

					throw new InvokeException(getErrorNo(errorNo), msgKey + "不能为空");

				}
				if (StringHelper.isEmpty(dataRow.get(validateKey).toString())) {
					throw new InvokeException(getErrorNo(errorNo), msgKey + "不能为空");
				}

			}
		}
	}
}
