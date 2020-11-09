/**
 * Project:taole-heaframework
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import static org.junit.Assert.*;

import com.taole.framework.json.PojoJsonMapper;
import com.taole.framework.util.UUID;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since Jul 25, 2011 3:31:23 PM
 * @version $Id: PojoJsonMapperTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class PojoJsonMapperTest {

	@Test
	public void testMapping() throws Exception {
		Group g1 = new Group("G1", "Group1");
		g1.setGroupId(UUID.generateUUID());
		g1.setParentId(UUID.generateUUID());
		g1.setStatus(2);
		
		Group g2 = new Group("G2", "Group2");
		g2.setGroupId(UUID.generateUUID());
		g2.setParentId(g1.getGroupId());
		g2.setStatus(1);
		g2.setDomainId(UUID.generateUUID());
		List<Group> groups = Arrays.asList(g1, g2);
		String json = PojoJsonMapper.toJson(groups, true);
		System.out.println(json);
		List<Group> groupList = PojoJsonMapper.fromJson(json, new TypeReference<List<Group>>() {
		});
		assertEquals(2, groupList.size());
		assertEquals("G2", groupList.get(1).getName());
	}

	protected static class Group {
		private String groupId;

		private String domainId;

		private String name;

		private String description;

		private String parentId;

		private Integer status;

		public Group() {
		}
		
		public Group(String name, String description) {
			this.name = name;
			this.description = description;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getDomainId() {
			return domainId;
		}

		public void setDomainId(String domainId) {
			this.domainId = domainId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

	}
}
