package com.taole.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.dao.util.Sorter.Sort;

public abstract class PageUtils {

	@SuppressWarnings({"rawtypes" })
	public static PaginationSupport getPaginationSupport(List all, Predicate predicate, Range range, Sorter sorter) {
		return getPaginationSupport(all, predicate, range, sorter);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PaginationSupport getPaginationSupport(Collection all, Predicate predicate, Range range, Sorter sorter) {

		List list = new ArrayList(predicate == null ? all : CollectionUtils.select(all, predicate));

		int fromIndex = range.getStart();
		int toIndex = range.getStart() + range.getLimit();
		if (toIndex > list.size()) {
			toIndex = list.size();
		}

		// if out of range then return empty page
		if (list.size() <= fromIndex) {
			return new PaginationSupport();
		}
		if (sorter != null) {
			Sort[] sorts = (Sort[]) sorter.getSorts().toArray(new Sort[0]);
			for (int i = sorts.length - 1; i >= 0; i--) {
				ArrayUtils.sort(list, sorts[i].property, sorts[i].isAscending());
			}
		}
		if (range.getLimit() <= 0) {
			return new PaginationSupport(list);
		} else {
			return new PaginationSupport(list.subList(fromIndex, toIndex), list.size(), range.getStart(), range.getLimit());
		}

	}

	public static <T> List<T> getList(List<T> all, Predicate predicate, Sorter sorter) {
		List<T> list = getList(all, predicate);
		if (sorter != null) {
			Sort[] sorts = (Sort[]) sorter.getSorts().toArray(new Sort[0]);
			for (int i = sorts.length - 1; i >= 0; i--) {
				ArrayUtils.sort(list, sorts[i].property, sorts[i].isAscending());
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(List<T> all, Predicate predicate) {
		return new ArrayList<T>(CollectionUtils.select(all, predicate));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(Collection<T> all, Predicate predicate) {
		return new ArrayList<T>(CollectionUtils.select(all, predicate));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(Collection<T> all, Predicate predicate, Sorter sorter) {
		ArrayList<T> list = new ArrayList<T>(CollectionUtils.select(all, predicate));
		if (sorter != null) {
			Sort[] sorts = (Sort[]) sorter.getSorts().toArray(new Sort[0]);
			for (int i = sorts.length - 1; i >= 0; i--) {
				ArrayUtils.sort(list, sorts[i].property, sorts[i].isAscending());
			}
		}
		return list;
	}
}
