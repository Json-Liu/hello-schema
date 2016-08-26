package com.yy.json.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eel.kitchen.jsonschema.main.JsonSchema;
import org.eel.kitchen.jsonschema.main.JsonSchemaFactory;
import org.eel.kitchen.jsonschema.report.ValidationReport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
/***
 ** @Author JosonLiu
 ** @Date 2016年8月26日
 ** @Version 1.0
 **/
public class JsonSchemaUtils {
	private static final ObjectMapper mapper = new ObjectMapper();
	private JsonSchemaUtils(){}
	public static JsonSchemaUtils newInstance(){
		return new JsonSchemaUtils();
	}
	/**
	 * Json 字符串校验 
	 * @param jsonSchema Json格式的  jsonSchema 字符串
	 * @param json Json格式的字符串
	 * @return Map<String, Object> 包含两个KEY ：result 为 Boolean 表示校验结果  ，<br />
	 * message 为 List<String> 表示 错误消息 当 result 为 true 时 message为空
	 */
	public Map<String, Object> validate(String jsonSchema,String json){
		Map<String, Object> result = new HashMap<String, Object>();
		JsonSchema schema = JsonSchemaFactory.defaultFactory().fromSchema(strToJsonNode(jsonSchema));
		JsonNode jsonNode = strToJsonNode(json);
		ValidationReport report = schema.validate(jsonNode);
		List<String> messages = report.getMessages();
		if(messages.size() == 0 ){
			result.put("result", true);
			result.put("message", "");
		}else{
			result.put("result", false);
			result.put("message", messages);
		}
		return result;
	}
	/**
	 * 根据  Class 得到 JsonSchema 的 Json 格式字符串  
	 * @param clazz
	 * @return Json 格式字符串
	 */
	public<T> String getJsonSchemaStr( Class<T> clazz) {
        JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().build();
        JsonNode schemaNode = v4generator.generateSchema( clazz );
        return jsonNodeToStr(schemaNode);
	}
	/**
	 * 将 JsonNode 转换为 Json格式的字符串
	 * @param schema
	 * @return Json 格式字符串 
	 */
	private  String jsonNodeToStr( JsonNode jsonNode ){
		 try {
			return mapper.writeValueAsString(jsonNode);
		} catch (JsonProcessingException e) {
			String msg = String.format("Failed to parse JsonNode to jsonStr %s", jsonNode);
			throw new RuntimeException(msg, e);
		}
	}
	/**
	 * 将 Json 格式的字符串转换为 JsonNode 
	 * @param josonStr
	 * @return JsonNode 对象
	 */
	private JsonNode strToJsonNode( String jsonStr ){
		 try {
			return mapper.readTree(jsonStr);
		} catch (Throwable e) {
			String msg = String.format("Failed to parse jsonStr to JsonNode %s", jsonStr);
			throw new RuntimeException(msg, e);
		} 
	}
	
	public static <T> T strToObj(String jsonStr, Class<T> type) {
		try {
			return mapper.readValue(jsonStr, type);
		} catch (Exception e) {
			String msg = String.format("Failed to parse json %s", jsonStr);
			throw new RuntimeException(msg, e);
		}
	}
	
	/**
	 * 生成对象对应的JSON字符串.
	 * 
	 * @param obj
	 *            对象实例
	 * @exception e
	 *                对象为空时，底层抛出异常时，均会封装成RuntimeException抛出
	 * @return 返回生成的字符串
	 */
	public static String ObjToStr(Object obj) {
		if (obj == null) {
			throw new RuntimeException("Failed to map object, which is null");
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			String msg = String.format("Failed to map object {}", obj);
			throw new RuntimeException(msg, e);
		}
	}
}

