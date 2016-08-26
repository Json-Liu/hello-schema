package com.yy.json.schema;

import java.util.HashMap;
import java.util.Map;
/***
 ** @Author JosonLiu
 ** @Date 2016年8月26日
 ** @Version 1.0
 **/
public class JsonSchemaUtilsTest {
	public static void main(String[] args) {
		JsonSchemaUtils newInstance = JsonSchemaUtils.newInstance();
		String jsonSchemaStr = newInstance.getJsonSchemaStr(User.class);
		Map<String, Object> testMap = new HashMap<String, Object>();
		//正确的情况
		testMap.put("name", "hello testName");
		testMap.put("age", 25);
		Map<String, Object> validate = newInstance.validate(jsonSchemaStr, JsonSchemaUtils.ObjToStr(testMap));
		System.out.println(validate.get("result"));
		//有必要字段 但有多余字段的情况
		testMap.put("school", "scnu");
		validate = newInstance.validate(jsonSchemaStr, JsonSchemaUtils.ObjToStr(testMap));
		System.out.println(validate.get("result"));
		//缺少必要字段  情形 1
		testMap.remove("school");
		testMap.remove("age");
		validate = newInstance.validate(jsonSchemaStr, JsonSchemaUtils.ObjToStr(testMap));
		System.out.println(validate.get("result"));
		
		//缺少必要字段 情形2 
		testMap.put("age1",25);
		validate = newInstance.validate(jsonSchemaStr, JsonSchemaUtils.ObjToStr(testMap));
		System.out.println(validate.get("result"));
	}
}

