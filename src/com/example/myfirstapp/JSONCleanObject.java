package com.example.myfirstapp;

import org.json.JSONObject;

public class JSONCleanObject {
	JSONObject obj;
	public JSONCleanObject(JSONObject _obj) {
		this.obj = _obj;
	}
	public int getInt(String key) {
		try {
			return obj.getInt(key);
		} catch (Exception e) {
			return -1;
		}
	}
	public String getString(String key) {
		try {
			String s = obj.getString(key);
			s = s.replace("<p>", "");
			s = s.replace("</p>", "");
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "EEXCEPTION";
	}
}
