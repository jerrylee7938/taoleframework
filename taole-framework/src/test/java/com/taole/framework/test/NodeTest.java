package com.taole.framework.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.taole.framework.bean.Node;

public class NodeTest {
	
	
	@Test
	public void testGetAncestors() throws Exception {
		M parent0 = new M("parent0");
		M parent1 = new M("parent1");
		M parent2 = new M("parent2");
		M child = new M("child");
		
		parent2.addChildNode(child);
		parent1.addChildNode(parent2);
		parent0.addChildNode(parent1);
		
		assertEquals(3, child.getAncestors().size());
		
		assertEquals("parent2", child.getAncestors().get(0).getName());
		assertEquals("parent0", child.getAncestors().get(2).getName());
		
	}
	
	protected class M extends Node<M> {
		
		private static final long serialVersionUID = -8402968593256520984L;

		private String description;

		private String action;

		private String icon;

		private boolean visible = true;

		private boolean enabled = true;
		
		public M (String name) {
			setName(name);
		}
		
		public M () {
		}

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
		
		@Override
		public String toString() {
			return getName();
		}
	}
}
