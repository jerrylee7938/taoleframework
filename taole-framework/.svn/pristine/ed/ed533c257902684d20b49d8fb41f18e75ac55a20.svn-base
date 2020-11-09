/**
 * Project:taole-heaframework
 * File:ExtjsDataConvertorTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.taole.framework.bean.Node;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.rest.ResultSet.Fetcher;
import com.taole.framework.rest.ResultSet.Row;
import com.taole.framework.rest.processor.ExtTreeGridResponseProcessor;
import com.taole.framework.util.ExtjsDataConvertor;
import com.taole.framework.util.UUID;

/**
 * @author Rory
 * @date May 10, 2012
 * @version $Id: ExtjsDataConvertorTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class ExtjsDataConvertorTest extends AbstractRestTest {

	/**
	 * 测试给Node添加额外属性。在ext-treegrid里需要用到。
	 * @throws Exception
	 */
	@Test
	public void testConvertResultSetWithNode() throws Exception {
		
		Tree tree = createTree();
		ExtjsDataConvertor convertor = ExtTreeGridResponseProcessor.createExtjsDataConvertorFromContext();
		ResultSet rs = new ResultSet(tree);
		rs.addField("treeHeight", new Fetcher<String>() {
			@Override
			public String fetch(Row row, Object propName) {
				String name = row.getValue("name", String.class);
				return name += "'s Height";
			}
		});
		JSONArray jsonArray = convertor.convertTreeGridStoreData(rs);
		System.out.println(jsonArray);
		assertNotNull(jsonArray);
		JSONObject rootJson = (JSONObject)jsonArray.get(0);
		assertEquals("Root's Height", rootJson.get("treeHeight"));
		assertEquals("Child001's Height", rootJson.getJSONArray("children").getJSONObject(0).get("treeHeight"));
		assertEquals("Child002's Height", rootJson.getJSONArray("children").getJSONObject(1).get("treeHeight"));
		assertEquals("CC一's Height", rootJson.getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).get("treeHeight"));
		
	}
	
	private Tree createTree() {
		Tree tree = new Tree();
		tree.setName("Root");
		tree.setId(UUID.generateUUID());
		
		Tree c1 = new Tree();
		c1.setName("Child001");
		c1.setId(UUID.generateUUID());
		
		Tree cc1 = new Tree();
		cc1.setId(UUID.generateUUID());
		cc1.setName("CC一");
		c1.addChildNode(cc1);
		
		Tree c2 = new Tree();
		c2.setName("Child002");
		c2.setId(UUID.generateUUID());
		
		tree.addChildNode(c1);
		tree.addChildNode(c2);
		
		
		return tree;
	}
	
	public static class Tree extends Node<Tree> {

		private static final long serialVersionUID = -3209072504728717267L;
	}
}
