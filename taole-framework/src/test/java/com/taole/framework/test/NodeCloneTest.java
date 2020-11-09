/**
 * Project:taole-heaframework
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taole.framework.bean.Node;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since Jul 20, 2011 9:59:27 AM
 * @version $Id: NodeCloneTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class NodeCloneTest {

	@Test
	public void testClone() throws Exception {
		M m = new M();
		m.setDescription("description");
		M mc = new M();
		mc.setName("mc0");
		m.addChildNode(mc);
		M m2 = m.clone();
		assertEquals(m2.getDescription(), "description");
		assertEquals("mc0", m2.getChildNodes().get(0).getName());
		
		m.setDescription("new description");
		m.getChildNodes().get(0).setName("mc0new");
		
		assertEquals(m2.getDescription(), "description");
		assertEquals("mc0", m2.getChildNodes().get(0).getName());
		
		m.getChildNodes().clear();
		assertEquals(1, m2.getChildNodes().size());
		
		m2.setName("m2m2");
		assertNull(m.getName());
	}
	
	protected class M extends Node<M> {
		
		private static final long serialVersionUID = -8402968593256520984L;

		private String description;

		private String action;

		private String icon;

		private boolean visible = true;

		private boolean enabled = true;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public boolean isVisible() {
			return visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		
	}
}
