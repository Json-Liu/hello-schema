package com.yy.json.schema;

import com.github.reinert.jjschema.Attributes;

/***
 ** @Author JosonLiu
 ** @Date 2016年8月25日
 ** @Version 1.0
 **/
@Attributes(title = "User",description = " Schema for an User")
public class User {
	@Attributes(required = true,description = "name of user")
	String name ;
	@Attributes(required = true, maxItems = 100,minItems = 1,description = "age of user")
	Integer age ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}

