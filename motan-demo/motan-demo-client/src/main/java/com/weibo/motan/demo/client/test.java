package com.weibo.motan.demo.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hinlin.client.result.BusResult;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        test aa = new test();
        aa.getResultToString("");
	}

	 public BusResult getResultToString( String jsonStr) {
		 	BusResult busresult = new BusResult();
	    	String str = "{'DataSet0':[{'data1':'data1'},{'data1':'data1'}],'DataSet1':[{'curr_page':'1','page_count':'10','total_pages':'0',"
	    			+ "'total_rows':'0'}],'dsName':['DataSet0','DataSet1'],'error_info':'执行成功','error_no':'1'}";
	    	ObjectMapper mapper = new ObjectMapper();
	        Map<String,Object> resultMap = null;
	        try {
	        	resultMap = mapper.readValue(str, Map.class);
			} catch (JsonParseException e) {
				//LoggerUtil.error("JsonPars转换错误,转换错误的原始数据是:" + resultStr);
				e.printStackTrace();
			} catch (JsonMappingException e) {
				//LoggerUtil.error("JsonMapping映射错误,映射错误的原始数据是:" + resultStr);
				e.printStackTrace();
			} catch (IOException e) {
				//LoggerUtil.error("系统出小差");
				e.printStackTrace();
			}
	        
	        int errNo = (Integer) resultMap.get("error_no");
	        String errInfo =  (String) resultMap.get("error_info");
	        //错误码、错误信息处理
	        busresult.setErr_no(errNo);
	        busresult.setErr_info(errInfo);
	        
	        //结果集处理
	        ArrayList data =  (ArrayList) resultMap.get("dsName");
	        Map map3 = (Map) data.get(0);
	        System.err.println(map3);
	        
	    	//	    	Object aa = JsonHelper.getObjectByJSON(str);
//	    	Map resultmap =  (Map) aa;
//	    	JSONArray o = (JSONArray) resultmap.get("dsName");
//	    	List ds = (List) o;
//	    	System.err.println(ds);
////	    	System.err.println(results.size());
////	    	BusResult su = new BusResult();
////	    	su.setResult(results);
//	    	//su.setDataSetNum();
////	    	System.err.println(su.getResult());
			return null;
		}
}
