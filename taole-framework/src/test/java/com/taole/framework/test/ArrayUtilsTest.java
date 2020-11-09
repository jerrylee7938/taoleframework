package com.taole.framework.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.taole.framework.util.ArrayUtils;

public class ArrayUtilsTest {
	
	static class Obj{
		
		public Obj(String name, Date birthday) {
			this.name = name;
			this.birthday = birthday;
		}
		
		String name;
		Date birthday;

		/**
		 * @return the birthday
		 */
		public Date getBirthday() {
			return birthday;
		}

		/**
		 * @param birthday the birthday to set
		 */
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		
	}
	
	public static void main(String[] args) {

		long dateMax = System.currentTimeMillis();
		List<Obj> list = new ArrayList<Obj>();
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			list.add(new Obj(String.valueOf(Math.random()), new Date(Math.round(Math.random() * dateMax))));
		}
		long t2 = System.currentTimeMillis();
		ArrayUtils.sort(list, "name", true);
		long t3 = System.currentTimeMillis();
		ArrayUtils.sort(list, new ArrayUtils.PropertyFetcher<Obj>() {
			public Object getProperty(Obj obj) {
				return obj.getBirthday();
			}
		}, true);
		long t4 = System.currentTimeMillis();
		System.out.println(t2 - t1);
		System.out.println(t3 - t2);
		System.out.println(t4 - t3);
		for (int i = 0; i < 20; i++) {
			System.out.println(list.get(i).getBirthday());
		}
		
	}

}
