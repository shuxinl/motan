/*
 * Copyright (c) 2006 Your Corporation. All Rights Reserved.
 */

package com.hinlin.base.jdbc;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.weibo.api.motan.util.LoggerUtil;

/**
 * 描述:	 数据行，继承自HashMap方便对数据存取
 * 版权:	 Copyright (c) 2005
 * 公司:	 hinlin
 * 作者:	 hinlin
 * 版本:	 1.0
 * 创建日期: 2006-10-4
 * 创建时间: 23:14:36
 */
public class DataRowLinked extends LinkedHashMap {
	
    public void set(String name, String value) {
        if (name == null || name.equals(""))
            return;

        if (value == null)
            put(name, "");
        else
            put(name, value);
    }

    public void set(String name, int value) {
        put(name, new Integer(value));
    }

    public void set(String name, boolean value) {
        put(name, new Boolean(value));
    }

    public void set(String name, long value) {
        put(name, new Long(value));
    }

    public void set(String name, float value) {
        put(name, new Float(value));
    }

    public void set(String name, double value) {
        put(name, new Double(value));
    }

    public void set(String name, Object value) {
        put(name, value);
    }


    public String getString(String name) {
        if (name == null || name.equals(""))
            return "";

        Object obj = this.get(name);
        return (obj == null) ? "" : obj.toString();
    }


    public int getInt(String name) {
        if (name == null || name.equals(""))
            return 0;

        int value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Integer)) {
            try {
                value = Integer.parseInt(obj.toString());
            }
            catch (Exception ex) {
    			LoggerUtil.debug(name+"对应的值不是int类型，return 0", ex);
                value = 0;
            }
        } else {
            value = ((Integer) obj).intValue();
            obj = null;
        }

        return value;
    }


    public long getLong(String name) {
        if (name == null || name.equals(""))
            return 0;

        long value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Long)) {
            try {
                value = Long.parseLong(obj.toString());
            }
            catch (Exception ex) {
    			LoggerUtil.error(name+"对应的值不是long类型，return 0", ex);
                value = 0;
            }
        } else {
            value = ((Long) obj).longValue();
            obj = null;
        }

        return value;
    }

    public float getFloat(String name) {
        if (name == null || name.equals(""))
            return 0;

        float value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Float)) {
            try {
                value = Float.parseFloat(obj.toString());
            }
            catch (Exception ex) {
    			LoggerUtil.error(name+"对应的值不是float类型，return 0", ex);
                value = 0;
            }
        } else {
            value = ((Float) obj).floatValue();
            obj = null;
        }

        return value;
    }

    public double getDouble(String name) {
        if (name == null || name.equals(""))
            return 0;

        double value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Double)) {
            try {
                value = Double.parseDouble(obj.toString());
            }
            catch (Exception ex) {
    			LoggerUtil.error(name+"对应的值不是double类型，return 0", ex);
                value = 0;
            }
        } else {
            value = ((Double) obj).doubleValue();
            obj = null;
        }

        return value;
    }

    public boolean getBoolean(String name) {
        if (name == null || name.equals(""))
            return false;

        boolean value = false;
        if (containsKey(name) == false)
            return false;
        Object obj = this.get(name);
        if (obj == null)
            return false;

        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }

        value = Boolean.valueOf(obj.toString()).booleanValue();
        obj = null;
        return value;
    }

    public Object getObject(String name) {
        if (name == null || name.equals(""))
            return null;
        //如果该键不存在，则返回
        if (containsKey(name) == false)
            return null;
        return this.get(name);
    }
}
