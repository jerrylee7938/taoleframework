package com.taole.framework.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.taole.framework.bean.Node;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.rest.ResultSet.Fetcher;
import com.taole.framework.rest.ResultSet.Field;
import com.taole.framework.rest.ResultSet.Row;

public class ExtjsDataConvertor {

	private Boolean checkedInLeaf;
	private Boolean checkedInParent;
	private Boolean expandAll;
	private String defaultIconCls;

	public String getDefaultIconCls() {
		return defaultIconCls;
	}

	public void setDefaultIconCls(String defaultIconCls) {
		this.defaultIconCls = defaultIconCls;
	}

	private List<String> expandedIds = new ArrayList<String>();
	private List<String> checkedIds = new ArrayList<String>();

	public Boolean getCheckedInLeaf() {
		return checkedInLeaf;
	}

	public void setCheckedInLeaf(Boolean checkedInLeaf) {
		this.checkedInLeaf = checkedInLeaf;
	}

	public Boolean getCheckedInParent() {
		return checkedInParent;
	}

	public void setCheckedInParent(Boolean checkedInParent) {
		this.checkedInParent = checkedInParent;
	}

	public Boolean getExpandAll() {
		return expandAll;
	}

	public void setExpandAll(Boolean expandAll) {
		this.expandAll = expandAll;
	}

	public List<String> getExpandedIds() {
		if (expandedIds == null) {
			expandedIds = new ArrayList<String>();
		}
		return expandedIds;
	}

	public void setExpandedIds(List<String> expandedIds) {
		this.expandedIds = expandedIds;
	}

	public List<String> getCheckedIds() {
		if (checkedIds == null) {
			checkedIds = new ArrayList<String>();
		}
		return checkedIds;
	}

	public void setCheckedIds(List<String> checkedIds) {
		this.checkedIds = checkedIds;
	}

	public ExtjsDataConvertor() {
	}
	
	private Set<String> getCheckedIdSet() {
		return new HashSet<String>(getCheckedIds());
	}

	/**
	 * 格式化
	 * 
	 * @return
	 */
	public JSONObject convertGridStoreData(PaginationSupport<?> ps) {
		JSONObject json = new JSONObject();
		try {
			json.put("total", ps.getTotalCount());
			JSONArray items = new JSONArray();
			json.put("items", items);
			for (Object obj : ps.getItems()) {
				items.put(convertJSONObject(obj));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 格式化
	 * 
	 * @return
	 */
	public JSONObject convertGridStoreData(ResultSet rs) {
		JSONObject json = new JSONObject();
		try {
			json.put("total", rs.getTotal());
			JSONArray items = new JSONArray();
			json.put("items", items);
			for (Row row : rs.getRows()) {
				JSONObject item = new JSONObject();
				for (String name : row.getFields()) {
					Object obj = row.getValue(name);
					item.put(name, convertJSONObject(obj));
				}
				items.put(item);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}

	// ------------------ Tree Grid Begin ------------------

	/**
	 * 格式化
	 * 
	 * @return
	 */
	public JSONArray convertTreeGridStoreData(Node<?>... nodes) {
		JSONArray children = new JSONArray();
		for (Node<?> node : nodes) {
			children.put(convertTreeGridStoreData(node));
		}
		return children;
	}

	public JSONArray convertTreeGridStoreData(List<Field> fetcherFields, Node<?>... nodes) {
		JSONArray children = new JSONArray();
		for (Node<?> node : nodes) {
			children.put(convertTreeGridStoreData(node, fetcherFields));
		}
		return children;
	}

	public JSONObject convertTreeGridStoreData(Node<?> node, List<Field> fetcherFields) {
		JSONObject json = null;
		try {
			json = (JSONObject) JSONTransformer.transformPojo2Jso(node);
			json.remove("childNodes");
			json.put("leaf", !node.hasChild());
			if (defaultIconCls != null) {
				json.put("iconCls", defaultIconCls);
			}
			if (expandAll != null && expandAll && getExpandedIds().isEmpty()) {
				getExpandedIds().addAll(node.getAllNodeIds());
			}
			Set<String> checkedIdSet = getCheckedIdSet();
			if (checkedIdSet.isEmpty() && getExpandedIds().isEmpty()) {
				getExpandedIds().add(node.getId());
				@SuppressWarnings("unchecked")
				List<Node<?>> allChildren = (List<Node<?>>) node.getAllChildNodes();
				if (expandAll == null || !expandAll) {
					for (Node<?> child : allChildren) {
						if (!child.hasChild() && checkedIdSet.contains(child.getId())) {
							Node<?> parent = child.parentNode();
							while (parent != null) {
								getExpandedIds().add(parent.getId());
								parent = parent.parentNode();
							}
						}
					}
				}
			}
			boolean checked = false;
			if (checkedIdSet.contains(node.getId())) {
				checked = true;
			}
			if (checkedInLeaf != null && checkedInLeaf && !node.hasChild()) {
				json.put("checked", checked);
			}
			if (checkedInParent != null && checkedInParent && node.hasChild()) {
				json.put("checked", checked);
			}
			if (json.has("icon")) {
				Object iconcls = json.remove("icon");
				json.put("iconCls", iconcls);
			}
			if ((expandAll != null && expandAll) || (node.hasChild() && getExpandedIds().contains(node.getId()))) {
				json.put("expanded", true);
			}
			if (fetcherFields != null && !fetcherFields.isEmpty()) {
				for (Field field : fetcherFields) {
					Fetcher<?> fetcher = field.getFetcher();
					if (fetcher == null) {
						continue;
					}
					json.put(field.getName(), fetcher.fetch(new ResultSet.BeanRow(node), field.getName()));
				}
				json.put("children", convertTreeGridStoreData(fetcherFields, (Node<?>[]) node.getChildNodes().toArray(new Node<?>[0])));
			} else {
				json.put("children", convertTreeGridStoreData((Node<?>[]) node.getChildNodes().toArray(new Node<?>[0])));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 格式化
	 * 
	 * @return
	 * @throws JSONException
	 */
	public JSONObject convertTreeGridStoreData(Node<?> node) {
		return convertTreeGridStoreData(node, null);
	}

	/**
	 * 格式化
	 * 
	 * @return
	 */
	public JSONArray convertTreeGridStoreData(Object... objs) {
		JSONArray children = new JSONArray();
		for (Object obj : objs) {
			children.put(convertTreeGridStoreData(obj));
		}
		return children;
	}

	public JSONArray convertTreeGridStoreData(ResultSet rs) {
		JSONArray children = new JSONArray();
		for (Row row : rs.getRows()) {
			JSONObject item = new JSONObject();
			if (row.getOriginal() instanceof Node) {
				item = convertTreeGridStoreData((Node<?>) row.getOriginal(), rs.getFetcherFields());
			} else {
				try {
					item.put("leaf", true);
					for (String name : row.getFields()) {
						Object obj = row.getValue(name);
						item.put(name, convertRow2Json(obj));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			children.put(item);
		}
		return children;
	}

	public Object convertRow2Json(Object obj) {
		if (obj instanceof Row) {
			Row row = (Row) obj;
			JSONObject json = new JSONObject();
			for (String name : row.getFields()) {
				Object value = row.getValue(name);
				try {
					json.put(name, JSONTransformer.transformPojo2Jso(value));
				} catch (JSONException e) {
				}
			}
			return json;
		} else {
			try {
				return JSONTransformer.transformPojo2Jso(obj);
			} catch (JSONException e) {
				return null;
			}
		}
	}

	public JSONObject convertTreeGridStoreData(Object obj) {
		if (obj instanceof Node) {
			return convertTreeGridStoreData((Node<?>) obj);
		} else {
			return (JSONObject) convertJSONObject(obj);
		}
	}

	// ------------------ Tree Grid End ------------------

	// ------------------ Tree Begin ------------------

	/**
	 * 格式化
	 * 
	 * @return
	 */
	public JSONArray convertTreeStoreData(Node<?>... nodes) {
		JSONArray children = new JSONArray();
		for (Node<?> node : nodes) {
			children.put(convertTreeStoreData(node));
		}
		return children;
	}

	/**
	 * 格式化
	 * 
	 * @return
	 * @throws JSONException
	 */
	public JSONObject convertTreeStoreData(Node<?> node) {
		JSONObject json = new JSONObject();
		try {
			json.put("id", node.getId());
			json.put("text", node.getName());
			json.put("children", convertTreeStoreData((Node<?>[]) node.getChildNodes().toArray(new Node<?>[0])));
			if (defaultIconCls != null) {
				json.put("iconCls", defaultIconCls);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public Object convertJSONObject(Object obj) {
		if (obj instanceof Row) {
			Row row = (Row) obj;
			JSONObject json = new JSONObject();
			if (defaultIconCls != null) {
				try {
					json.put("iconCls", defaultIconCls);
				} catch (JSONException e) {
				}
			}
			for (String name : row.getFields()) {
				Object value = row.getValue(name);
				try {
					json.put(name, JSONTransformer.transformPojo2Jso(value));
				} catch (JSONException e) {
				}
			}
			return json;
		} else {
			try {
				return JSONTransformer.transformPojo2Jso(obj);
			} catch (JSONException e) {
				return null;
			}
		}
	}

	/**
	 * 格式化
	 * 
	 * @return
	 */
	public JSONArray convertTreeStoreData(Object... objs) {
		JSONArray children = new JSONArray();
		for (Object obj : objs) {
			children.put(convertTreeStoreData(obj));
		}
		return children;
	}

	public JSONObject convertTreeStoreData(Object obj) {
		return (JSONObject) convertJSONObject(obj);
	}

	public JSONArray convertTreeStoreData(List<?> list) {
		JSONArray array = new JSONArray();
		for (Object obj : list) {
			array.put(convertTreeStoreData(obj));
		}
		return array;
	}

	// ------------------ Tree End ------------------

	public static Sorter convertSorter(String sortString) {
		try {
			Sorter sorter = new Sorter();
			JSONArray array = new JSONArray(sortString);
			for (int i = 0, l = array.length(); i < l; i++) {
				JSONObject json = (JSONObject) array.get(i);
				String property = json.getString("property");
				String direction = json.getString("direction");
				if (StringUtils.equalsIgnoreCase("desc", direction)) {
					sorter.desc(property);
				} else {
					sorter.asc(property);
				}
			}
			return sorter;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

}
