package com.taole.framework.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.common.collect.Maps;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.util.StringUtils;

public class ResultSet {

	private List<Field> fields = new ArrayList<Field>();
	private List<Row> rows = new ArrayList<Row>();
	private int total = 0;

	public ResultSet() {
	}

	public ResultSet(int total, Object... rows) {
		this.addFields("*");
		addRows(rows);
		this.total = total;
	}

	public ResultSet(Object... rows) {
		this.addFields("*");
		addRows(rows);
	}

	public ResultSet(List<?> objs) {
		this.addFields("*");
		addRows(objs.toArray(new Object[0]));
	}

	public ResultSet(PaginationSupport<?> ps) {
		this.addFields("*");
		addRows(ps.getItems().toArray(new Object[0]));
		this.total = ps.getTotalCount();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Field removeField(String name) {
		for (Field field : fields) {
			if (StringUtils.compare(name, field.getName())) {
				fields.remove(field);
				return field;
			}
		}
		return null;
	}

	public Field getField(String name) {
		for (Field field : fields) {
			if (StringUtils.compare(name, field.getName())) {
				return field;
			}
		}
		return null;
	}

	/**
	 * 取带有Fetcher的Fields.
	 * 
	 * @return
	 */
	public List<Field> getFetcherFields() {
		List<Field> fetcherFields = new ArrayList<ResultSet.Field>();
		for (Field field : fields) {
			if (field.getFetcher() != null) {
				fetcherFields.add(field);
			}
		}
		return fetcherFields;
	}

	public void clearFields() {
		if (fields != null) {
			fields.clear();
		}
	}

	public void setFields(String... fieldNames) {
		if (fields != null) {
			fields.clear();
		}
		addFields(fieldNames);
	}

	public void addFields(String... fieldNames) {
		for (String fieldName : fieldNames) {
			fields.add(new Field(fieldName));
		}
	}

	public void addField(String fieldName, Fetcher<?> fetcher) {
		fields.add(new Field(fieldName, fetcher));
	}

	public List<Row> getRows() {
		List<Row> list = new ArrayList<Row>();
		for (Row row : rows) {
			list.add(ProxyRow.class.isInstance(row) ? row : new ProxyRow(row));
		}
		return list;
	}

	public int getRowCount() {
		return rows.size();
	}

	public int getFieldCount() {
		return fields.size();
	}

	public Row getRowAt(int index) {
		Row row = rows.get(index);
		return ProxyRow.class.isInstance(row) ? row : new ProxyRow(row);
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	public void addRows(Row... rows) {
		for (Row row : rows) {
			this.rows.add(row);
		}
		total += rows.length;
	}

	public void addRows(Map<String, Object>... rows) {
		for (Map<String, Object> row : rows) {
			this.rows.add(new MapRow(row));
		}
		total += rows.length;
	}

	public void addRows(Object... rows) {
		for (Object row : rows) {
			this.rows.add(new BeanRow(row));
		}
		total += rows.length;
	}

	public void addRows(List<Object> rows) {
		for (Object row : rows) {
			this.rows.add(new BeanRow(row));
		}
		total += rows.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("fields", getFields()).append("rows", getRows()).toString();
	}

	class ProxyRow implements Row {

		private Row delegate;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getOriginal() {
			return delegate.getOriginal();
		}

		public ProxyRow(Row delegate) {
			this.delegate = delegate;
		}

		@SuppressWarnings({ "rawtypes" })
		public Object getValue(String name) {
			Field field = getField(name);
			if (field != null && field.getFetcher() != null) {
				Fetcher t = field.getFetcher();
				return t.fetch(delegate, name);
			} else {
				return delegate.getValue(name);
			}
		}

		@Override
		public <T> T getValue(String field, Class<T> clz) {
			return clz.cast(getValue(field));
		}

		@Override
		public List<String> getFields() {
			Set<String> names = new HashSet<String>();
			boolean hasAll = false;
			for (Field field : fields) {
				if (StringUtils.compare("*", field.getName())) {
					hasAll = true;
				} else {
					names.add(field.getName());
				}
			}
			if (hasAll) {
				for (String name : delegate.getFields()) {
					names.add(name);
				}
			}
			return new ArrayList<String>(names);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			Map<String, Object> rowMap = Maps.newHashMap();
			for (String fieldName : getFields()) {
				rowMap.put(fieldName, getValue(fieldName));
			}
			return rowMap.toString();
		}
	}

	public static interface Row {

		public Object getValue(String field);

		public <T> T getValue(String field, Class<T> clz);

		public List<String> getFields();

		/**
		 * 得到原来row的对象。
		 * 
		 * @return
		 */
		public Object getOriginal();
	}

	public static class MapRow implements Row {

		Map<String, Object> map;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getOriginal() {
			return map;
		}

		public MapRow() {
			this.map = new HashMap<String, Object>();
		}

		public MapRow(Map<String, Object> map) {
			this.map = map;
		}

		public Object getValue(String field) {
			return map.get(field);
		}

		public List<String> getFields() {
			return new ArrayList<String>(map.keySet());
		}

		@Override
		public <T> T getValue(String field, Class<T> clz) {
			return clz.cast(getValue(field));
		}
	}

	public static class BeanRow implements Row {

		private BeanMap beanMap;
		private Object delegate;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getOriginal() {
			return delegate;
		}

		public BeanRow(Object delegate) {
			beanMap = BeanMap.create(delegate);
			this.delegate = delegate;
		}

		public Object getDelegate() {
			return delegate;
		}

		public Object getValue(String field) {
			return beanMap.get(field);
		}

		@Override
		public <T> T getValue(String field, Class<T> clz) {
			return clz.cast(getValue(field));
		}

		@SuppressWarnings("unchecked")
		public List<String> getFields() {
			return new ArrayList<String>(beanMap.keySet());
		}
	}

	public static class Field {

		String name;

		Fetcher<?> fetcher;

		public Field() {
		}

		public Field(String name) {
			this.name = name;
		}

		public Field(String name, Fetcher<?> fetcher) {
			this.name = name;
			this.fetcher = fetcher;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Fetcher<?> getFetcher() {
			return fetcher;
		}

		public void setFetcher(Fetcher<?> fetcher) {
			this.fetcher = fetcher;
		}
	}

	public static interface Fetcher<K> {
		public K fetch(Row row, Object propName);
	}

}
