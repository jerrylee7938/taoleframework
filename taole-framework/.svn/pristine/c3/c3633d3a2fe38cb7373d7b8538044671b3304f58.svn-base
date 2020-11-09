/**
 * Project:taole-heaframework
 * File:Student.java 
 * Copyright 2004-2011  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test.domain;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;

/**
 * @author rory
 * @date Sep 27, 2011
 * @version $Id: Student.java 5 2014-01-15 13:55:28Z yedf $
 */
public class Student implements DomainObject {

	private static final long serialVersionUID = -2590779982638100165L;

	@PrimaryKey
	private String id;

	private String name;

	private Gender gender;

	private Address address;
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Student() {
	}
	
	public static class Address {
		
		private String phone;
		
		private String address;
		
		private Type type;
		
		public Address() {
			
		}
		
		public Address(String phone, String address) {
			this.phone = phone;
			this.address = address;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
		
		public static enum Type {
			Home,Office
		}
	}

	public static enum Gender {
		Male, Female
	}
}
