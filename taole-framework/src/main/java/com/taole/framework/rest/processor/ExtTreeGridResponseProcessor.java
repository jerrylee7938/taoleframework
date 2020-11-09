package com.taole.framework.rest.processor;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.rest.RestContext;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.util.ExtjsDataConvertor;

@Component
public class ExtTreeGridResponseProcessor extends AbstractResponseProcessor {
	
	/**
	 * 表示在叶节点显示checked 属性
	 */
	public static final String SHOW_CHECKED_IN_LEAF = "_exttree_config_show_checked_in_leaf";
	
	public static final String SHOW_CHECKED_IN_PARENT = "_exttree_config_show_checked_in_parent";
	
	public static final String EXPAND_ALL = "_exttree_config_expand_all";
	
	public static final String CHECKED_NODE_IDS = "_exttree_config_checked_node_ids";
	
	@Override
	public String getName() {
		return "ext-treegrid";
	}

	@SuppressWarnings("unchecked")
	public Object process(Object input) {
		
		Object json = null;
		ExtjsDataConvertor convertor = getExtjsDataConvertor();
		if (input == null) {
			json = convertor.convertTreeGridStoreData();
		} else if (ResultSet.class.isInstance(input)) {
			json = convertor.convertTreeGridStoreData(ResultSet.class.cast(input));
		} else if (PaginationSupport.class.isInstance(input)) {
			json = convertor.convertTreeGridStoreData((Object[])((PaginationSupport<?>)input).getItems().toArray(new Object[0]));
		} else  if (input instanceof List) {
			json = convertor.convertTreeGridStoreData((Object[])((List<Object>)input).toArray(new Object[0]));
		} else  if (input.getClass().isArray()) {
			json = convertor.convertTreeGridStoreData((Object[])input);
		} else {
			json = convertor.convertTreeGridStoreData(input);
		}
		String jsonStr = json.toString();
		String jsonp = RestSessionFactory.getCurrentContext().getHttpServletRequest().getParameter("jsonp");
		if (StringUtils.isNotBlank(jsonp)) {
			jsonStr = jsonp + "(" + jsonStr + ");";
		}
		HttpHeaders headers = new HttpHeaders();
		try {
			byte[] data = jsonStr.getBytes("utf-8");
			headers.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
			headers.setContentLength(data.length);
			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (UnsupportedEncodingException e) {
			return new ResponseEntity<String>(e.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	private ExtjsDataConvertor convertor;

	public void setExtjsDataConvertor(ExtjsDataConvertor convertor) {
		this.convertor = convertor;
	}

	private ExtjsDataConvertor getExtjsDataConvertor() {
		if (RestSessionFactory.getCurrentContext().getResponseProcessor() == null) {
			convertor = new ExtjsDataConvertor();
		}
		if (convertor == null) {
			convertor = createExtjsDataConvertorFromContext();
		}
		return convertor;
	}
	
	@SuppressWarnings("unchecked")
	public static ExtjsDataConvertor createExtjsDataConvertorFromContext() {
		ExtjsDataConvertor convertor = new ExtjsDataConvertor();
		RestContext ctx = RestSessionFactory.getCurrentContext();
		Boolean checkedInLeaf = ctx.getAttribute(SHOW_CHECKED_IN_LEAF, Boolean.class);
		Boolean checkedInParent = ctx.getAttribute(SHOW_CHECKED_IN_PARENT, Boolean.class);
		Boolean expandAll = ctx.getAttribute(EXPAND_ALL, Boolean.class);
		Object obj = ctx.getAttribute(CHECKED_NODE_IDS);
		List<String> checkedIds = new ArrayList<String>();
		if (obj != null) {
			if (obj instanceof List) {
				checkedIds.addAll((List<String>) obj);
			} else if (obj.getClass().isArray()) {
				checkedIds.addAll(Arrays.asList((String[]) obj));
			} else {
				checkedIds.add(String.valueOf(obj));
			}
		}
		convertor.setCheckedIds(checkedIds);
		convertor.setExpandAll(expandAll);
		convertor.setCheckedInLeaf(checkedInLeaf);
		convertor.setCheckedInParent(checkedInParent);
		return convertor;
	}
	
	public static void initContextInstance (boolean expandAll) {	
		initContextInstance(expandAll, false, false, new ArrayList<String>());
	}
	
	public static void initContextInstance (boolean expandAll, boolean checkedInLeaf, boolean checkedInParent) {	
		initContextInstance(expandAll, checkedInLeaf, checkedInParent, new ArrayList<String>());
	}

	public static void initContextInstance (boolean expandAll, boolean checkedInLeaf, boolean checkedInParent, List<String> checkedIds) {	
		ExtjsDataConvertor convertor = new ExtjsDataConvertor();
		convertor.setExpandAll(expandAll);
		convertor.setCheckedInLeaf(checkedInLeaf);
		convertor.setCheckedInParent(checkedInParent);
		convertor.setCheckedIds(checkedIds);
		ExtTreeGridResponseProcessor processor = new ExtTreeGridResponseProcessor();
		processor.setExtjsDataConvertor(convertor);
		RestSessionFactory.getCurrentContext().setResponseProcessor(processor);
	}
}
