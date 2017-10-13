package com.hinlin.server.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hinlin.server.IASResponse;

public class SimpleResponseImpl implements IASResponse {
	private List dataSetNameList;
	private List dataSetFieldList;
	private List dataSetValueList;
	private int dataSetCount;
	private List curDSFieldList;
	private List curDSValueList;
	private int errorNo;
	private String errorInfo;

	public SimpleResponseImpl() {
		this.dataSetNameList = new ArrayList();
		this.dataSetFieldList = new ArrayList();
		this.dataSetValueList = new ArrayList();
		this.dataSetCount = 0;

		this.curDSFieldList = null;
		this.curDSValueList = null;

		this.errorNo = 0;
		this.errorInfo = "";
	}

	public void setErrorNo(int errNo) {
		this.errorNo = errNo;
	}

	public void setErrorInfo(String errInfo) {
		this.errorInfo = ((errInfo == null) ? "" : errInfo);
	}

	public void newDataSet() {
		this.curDSFieldList = new ArrayList();
		this.curDSValueList = new ArrayList();
		this.dataSetFieldList.add(this.curDSFieldList);
		this.dataSetValueList.add(this.curDSValueList);
		this.dataSetCount += 1;
		this.dataSetNameList.add("DataSet" + this.dataSetCount);
	}

	public void setDataSetName(String name) {
		if ((name == null) || (name.length() <= 0) || (this.dataSetCount <= 0))
			return;
		this.dataSetNameList.set(this.dataSetCount - 1, name);
	}

	public void addField(String field) {
		if (this.curDSFieldList == null)
			return;
		field = (field == null) ? "" : field;
		this.curDSFieldList.add(field);
	}

	public void addValue(String value) {
		if (this.curDSValueList == null)
			return;
		value = (value == null) ? "" : value;
		this.curDSValueList.add(value);
	}

	public int getErrorNo() {
		return this.errorNo;
	}

	public String getErrorInfo() {
		return this.errorInfo;
	}

	public int getDataSetCount() {
		return this.dataSetCount;
	}

	public List getDataSetNameList() {
		return this.dataSetNameList;
	}

	public boolean isDataSetNameExist(String name) {
		for (Iterator iter = this.dataSetNameList.iterator(); iter.hasNext();) {
			String dsName = (String) iter.next();
			if (dsName.equals(name)) {
				return true;
			}
		}

		return false;
	}

	public List getDataSetFieldList() {
		return this.dataSetFieldList;
	}

	public List getDataSetValueList() {
		return this.dataSetValueList;
	}

	public void clear() {
		for (int i = 0; i < this.dataSetCount; ++i) {
			this.curDSFieldList = ((List) this.dataSetFieldList.get(i));
			this.curDSValueList = ((List) this.dataSetFieldList.get(i));
			this.curDSFieldList.clear();
			this.curDSValueList.clear();
		}
		this.dataSetFieldList.clear();
		this.dataSetValueList.clear();
		this.dataSetNameList.clear();
		this.dataSetCount = 0;

		this.errorNo = 0;
		this.errorInfo = "";
	}

	public String getData() {
		StringBuilder builder = new StringBuilder();

		builder.append(getErrorNo());
		builder.append("|");
		builder.append(getErrorInfo());
		builder.append("|").append("\n\n");

		if (getErrorNo() == 0) {
			int dataSetCount = getDataSetCount();
			if (dataSetCount > 0) {
				List dataSetNameList = getDataSetNameList();
				List dataSetFieldList = getDataSetFieldList();
				List dataSetValueList = getDataSetValueList();
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

					builder.append(curDSName + "|" + fieldCount + "|" + rowCount + "|" + "\n");

					if (fieldCount > 0) {
						for (int i = 0; i < curDSFieldList.size(); ++i) {
							String field = curDSFieldList.get(i).toString();
							builder.append(field + "|");
						}
						builder.append("\n");

						for (int i = 0; i < rowCount; ++i) {
							for (int j = 0; j < fieldCount; ++j) {
								int index = i * fieldCount + j;
								String value = curDSValueList.get(index).toString();
								builder.append(value + "|");
							}
							builder.append("\n");
						}
					}
					builder.append("\n");
				}
			}
		}

		return builder.toString();
	}
}