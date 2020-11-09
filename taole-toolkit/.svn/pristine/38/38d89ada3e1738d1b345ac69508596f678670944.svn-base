package com.taole.toolkit.dict.service.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.patterns.ParserException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.taole.framework.exception.BusinessException;
import com.taole.framework.rest.ActionMethod;
import com.taole.framework.rest.BaseDomainObjectServiceSupport;
import com.taole.framework.rest.RequestParameters;
import com.taole.framework.rest.RestContext;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.rest.bind.annotation.ParameterObject;
import com.taole.framework.rest.bind.annotation.ParameterVariable;
import com.taole.framework.rest.bind.annotation.ResourceVariable;
import com.taole.framework.rest.processor.ExtTreeGridResponseProcessor;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.BeanUpdater;
import com.taole.framework.util.CharacterDetectorUtils;
import com.taole.framework.util.ExtjsDataConvertor;
import com.taole.framework.util.IOUtils;
import com.taole.framework.util.JSONTransformer;
import com.taole.framework.util.UUID;
import com.taole.toolkit.dict.Constants;
import com.taole.toolkit.dict.condition.DictionaryCondition;
import com.taole.toolkit.dict.domain.Dictionary;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.filesystem.FileSystemPath;
import com.taole.toolkit.filesystem.manager.FileSystemManager;

@RestService
public class DictionaryService extends BaseDomainObjectServiceSupport<Dictionary> {

	

	@Autowired
	private DictionaryManager dictionaryManager;

	@Autowired
	FileSystemPath fileSystemPath;

	@Autowired
	FileSystemManager fileSystemManager;

	@ActionMethod(response = "json")
	public Object childNodes(@ResourceVariable String id) {
		Dictionary dictionary = dictionaryManager.getDictionary(id);
		if (dictionary == null) {
			return null;
		}
		JSONArray array = new JSONArray();
		try {
			for (Dictionary child : dictionary.getChildNodes()) {
				JSONObject json = JSONTransformer.transformPojo2Jso(child, JSONObject.class);
				json.remove("childNodes");
				array.put(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	
	@ActionMethod(response = "json")
	public Object getDictionary(@ResourceVariable String id) {
		Dictionary dictionary = dictionaryManager.getDictionary(id);
		if (dictionary == null) {
			return null;
		}
		
		
		
		JSONArray array = new JSONArray();
		try {
			
			JSONObject json = JSONTransformer.transformPojo2Jso(dictionary, JSONObject.class);
			json.remove("childNodes");
			array.put(json);
			
			for (Dictionary child : dictionary.getChildNodes()) {
				json = JSONTransformer.transformPojo2Jso(child, JSONObject.class);
				json.remove("childNodes");
				array.put(json);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		

		return array;
	}
	
	
	@ActionMethod(response = "json")
	public Object childAllNodes(@ResourceVariable String id) {
		Dictionary dictionary = dictionaryManager.getDictionary(id);
		if (dictionary == null) {
			return null;
		}
		return dictionary.getChildNodes();
	}

	@ActionMethod(response = "json")
	public Object settree() {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Dictionary dictionary : dictionaryManager.getRootDictionaries()) {
			TreeNode node = convertSetCategory(dictionary);
			if (node != null) {
				nodes.add(node);
			}
		}
		return nodes;
	}

	@ActionMethod(response = "json")
	public Object properties(@ResourceVariable String id) {
		Dictionary dictionary = dictionaryManager.getDictionary(id);
		if (dictionary == null) {
			return null;
		}
		JSONObject json = new JSONObject();
		try {
			json.put("id", dictionary.getId());
			json.put("name", dictionary.getName());
			json.put("value", dictionary.getValue());
			json.put("system", dictionary.isSystem());
			json.put("default", dictionary.getIsDefault());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	@ActionMethod(response = "json")
	public Object retrieve(@ResourceVariable String id) {
		Dictionary entity = dictionaryManager.getDictionary(id);
		/*
		JSONObject json = null;
	
		try {
			json = JSONTransformer.transformPojo2Jso(entity, JSONObject.class);
			if (json.has("isDefault")) {
				json.put("default", json.remove("isDefault"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		*/
		return entity;
	}

	@ActionMethod(response = "ext-treegrid")
	public Object alltree() {
		return dictionaryManager.getRootDictionaries();
	}

	@ActionMethod(response = "ext-treegrid")
	public Object onetree(@ResourceVariable String id) {
		ExtjsDataConvertor convertor = new ExtjsDataConvertor();
		convertor.setDefaultIconCls("icon-tk-dictionary-element");
		ExtTreeGridResponseProcessor processor = new ExtTreeGridResponseProcessor();
		processor.setExtjsDataConvertor(convertor);
		RestSessionFactory.getCurrentContext().setResponseProcessor(processor);
		return dictionaryManager.getDictionary(id);
	}
	
	@ActionMethod(response = "ext-tree")
	public Object tree(@ResourceVariable String id) {
		Dictionary dict = dictionaryManager.getDictionary(id);
		if (dict != null) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(convertTreeMap(dict));
			return list;
		}
		return new ArrayList<Map<String, Object>>();
	}

	
	private Map<String, Object> convertTreeMap(Dictionary dict) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", dict.getId());
		map.put("text", dict.getName());
		map.put("value", dict.getValue());
		if(StringUtils.isNotBlank(dict.getDescription())){
			map.put("description", dict.getDescription());
		}
//		if (dict.getDepth()==0)
//		{
//			map.put("expanded", true);
//		}
		

		
		List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
		for (Dictionary child : dict.getChildNodes()) {
			children.add(convertTreeMap(child));
		}
		if (children.isEmpty()) {
			map.put("leaf", true);
			map.put("expanded", false);
		} else {
			map.put("leaf", false);
			map.put("children", children);
			map.put("expanded", true);
		}
		return map;
	}
	/*
	 * private TreeNode getCategoryTreeNode(Category dictionary){ TreeNode node = new TreeNode(); node.setId(dictionary.getId()); if(dictionary.hasChild()){
	 * 
	 * } }
	 */

	@ActionMethod(response = "ext-treegrid")
	public Object asynctree(@ParameterVariable("node") String node) {
		List<Dictionary> categories = new ArrayList<Dictionary>();
		if ("root".equals(node) || StringUtils.isBlank(node)) {
			categories = dictionaryManager.getRootDictionaries();
		} else {
			Dictionary parent = dictionaryManager.getDictionary(node);
			if (parent != null) {
				categories = parent.getChildNodes();
			}
		}
		return getChildNodes(categories);
	}

	private List<Map<String, Object>> getChildNodes(List<Dictionary> dictionaryList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Dictionary dictionary : dictionaryList) {
			Map<String, Object> item = new HashMap<String, Object>();
			String iconCls = "icon-tk-dictionary-element";
			item.put("id", dictionary.getId());
			item.put("name", dictionary.getName());
			item.put("iconCls", iconCls);
			item.put("value", dictionary.getValue());
			item.put("default", dictionary.getIsDefault());
			item.put("system", dictionary.isSystem());
			item.put("decription", dictionary.getDescription());
			if (dictionary.hasChild()) {
				item.put("children", getChildNodes(dictionary.getChildNodes()));
			}
			item.put("leaf", !dictionary.hasChild());
			list.add(item);
		}
		return list;
	}

	@ActionMethod(response = "ext-treegrid")
	public Object list(@ParameterVariable("parentId") String parentId, RestContext restContext, RequestParameters request) {
		Boolean checked = request.getParameter("checkType", Boolean.class);
		Boolean checkInLeaf = request.getParameter("checkInLeaf", Boolean.class);
		Boolean checkInParent = request.getParameter("checkInParent", Boolean.class);
		String[] objs = (String[]) request.getParameterValues("values");
		if (checkInLeaf == null) {
			checkInLeaf = false;
		}
		if (checkInParent == null) {
			checkInParent = false;
		}
		if (checked != null && checked) {
			if (objs == null) {
				objs = new String[] {};
			}
			ExtTreeGridResponseProcessor.initContextInstance(true, true, true, Arrays.asList(objs));
		} else {
			ExtTreeGridResponseProcessor.initContextInstance(true, checkInLeaf, checkInParent);
		}
		DictionaryCondition condition = new DictionaryCondition();
		List<Dictionary> dictionaryList = new ArrayList<Dictionary>();
		if (StringUtils.isBlank(parentId)) {
			List<Dictionary> roots = dictionaryManager.getRootDictionaries();
			dictionaryList = dictionaryManager.filter(roots, condition);
		} else {
			Dictionary dictionary = dictionaryManager.getDictionary(parentId);
			if (dictionary != null) {
				dictionaryList = dictionaryManager.filter(dictionary.getChildNodes(), condition);
			}
		}
		return dictionaryList;
	}

	@ActionMethod(request = "json", response = "json")
	public Object update(@ResourceVariable String id, @ParameterObject JSONObject updateObj) {
		Dictionary entity = dictionaryManager.getDictionary(id);
		if (entity == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		Dictionary parent = null;
		if (updateObj.has("parentNode")) {
			parent = dictionaryManager.getDictionary((String) updateObj.remove("parentNode"));
		}
		
		BeanUpdater.updateObject(entity, updateObj);
		entity.setName(com.taole.framework.util.StringUtils.strTrim(entity.getName()));
		
		Dictionary oldParent = entity.getParentNode();
		if ((parent != null && oldParent == null) || (parent != null && !StringUtils.equals(parent.getId(), oldParent.getId()))) {
			if (oldParent != null) {
				oldParent.removeChildNode(entity);
				dictionaryManager.saveDictionary(oldParent);
			}
			//处理重复的判断
			for (Dictionary brother : parent.getChildNodes()) {
				if (StringUtils.equals(brother.getName(), entity.getName()) && !StringUtils.equals(entity.getId(), brother.getId())) {
					return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下名称不能重复!");
				}
//				if (StringUtils.isNotBlank(entity.getValue()) && StringUtils.equals(brother.getValue(), entity.getValue()) && !StringUtils.equals(entity.getId(), brother.getId())) {
//					return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下值不能重复!");
//				}
			}
			parent.addChildNode(entity);
			dictionaryManager.saveDictionary(parent);
		} else {
			if (parent != null) {
				//处理重复的判断
				for (Dictionary brother : parent.getChildNodes()) {
					if (StringUtils.equals(brother.getName(), entity.getName()) && !StringUtils.equals(entity.getId(), brother.getId())) {
						return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下名称不能重复!");
					}
//					if (StringUtils.isNotBlank(entity.getValue()) && StringUtils.equals(brother.getValue(), entity.getValue()) && !StringUtils.equals(entity.getId(), brother.getId())) {
//						return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下值不能重复!");
//					}
				}
			}
			dictionaryManager.saveDictionary(entity);
		}
		return new ServiceResult(ReturnResult.SUCCESS, entity);
	}

	@ActionMethod(response = "json")
	public Object delete(@ResourceVariable String id) {
		Dictionary entity = dictionaryManager.getDictionary(id);
		if (entity == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		dictionaryManager.deleteDictionary(entity);
		return new ServiceResult(ReturnResult.SUCCESS);
	}

	@ActionMethod(request = "json", response = "json")
	public Object create(@ParameterObject JSONObject json) {
		Dictionary parent = null;
		
		String father=null;
		if (json.has("parentNode")) {
			//father=(String) json.remove("parentNode");
			parent = dictionaryManager.getDictionary((String) json.remove("parentNode"));
		}
		try {
/*
			if (json.has("default")) {
				json.put("isDefault", json.remove("default"));
			}
	*/		
			String id= UUID.generateUUID();
			String value=null;
			if (!json.has("value") || StringUtils.isBlank(json.getString("value")) ) {
				value=id;
				json.put("value",id);
			}
			else{
				value=json.getString("value");
			}
			if (!json.has("id") || StringUtils.isBlank(json.getString("id"))) {
				json.put("id",id);
			}
			
			if (parent==null){
				json.put("type",value);//没有父节点，就是根节点
			}
			else if (Constants.DICT_ROOT.equals(parent.getId())){
				json.put("type",value);//父节点是DICT_ROOT数据字典
			}
			else{
				json.put("type",parent.getType());//父节点是非DICT_ROOT数据字典
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		Dictionary dict = JSONTransformer.transformJso2Pojo(json, Dictionary.class);
		if (StringUtils.isBlank(dict.getName())) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "名称为必填!");
		}else{
			dict.setName(com.taole.framework.util.StringUtils.strTrim(dict.getName()));
		}

		if (parent != null) {
			//处理重复的判断
			for (Dictionary brother : parent.getChildNodes()) {
				if (StringUtils.equals(brother.getName(), dict.getName())) {
					return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下名称不能重复!");
				}
//				if (StringUtils.isNotBlank(dict.getValue()) && StringUtils.equals(brother.getValue(), dict.getValue())) {
//					return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下值不能重复!");
//				}
			}
			parent.addChildNode(dict);
			dictionaryManager.saveDictionary(parent);
		} else {
			dictionaryManager.saveDictionary(dict);
		}
		return new ServiceResult(ReturnResult.SUCCESS, dict.getId());
	}

	/**
	 * @return
	 */


	@ActionMethod(request = "pojo", response = "json")
	public Object addChild(@ResourceVariable String id, @ParameterObject Dictionary entity) {
		if (StringUtils.isBlank(entity.getName())) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "名称为必填!");
		}
		Dictionary parent = dictionaryManager.getDictionary(id);
		if (parent == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		//处理重复的判断
		for (Dictionary brother : parent.getChildNodes()) {
			if (StringUtils.equals(brother.getName(), entity.getName())) {
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下名称不能重复!");
			}
			if (StringUtils.isNotBlank(entity.getValue()) && StringUtils.equals(brother.getValue(), entity.getValue())) {
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下值不能重复!");
			}
		}
		
		if (StringUtils.isBlank(entity.getValue()))
		{
			entity.setValue(entity.getId());
		}
		if (Constants.DICT_ROOT.equals(id))
		{
			entity.setType(entity.getValue());
		}
		else
		{
			entity.setType(parent.getType());
		}
		
		parent.addChildNode(entity);
		dictionaryManager.saveDictionary(parent);
		return new ServiceResult(ReturnResult.SUCCESS, entity);
	}

	@ActionMethod(response = "json")
	public Object updown(@ResourceVariable String id, @ParameterVariable("offset") int offset) {
		Dictionary entity = dictionaryManager.getDictionary(id);
		if (entity == null) {
			return new ServiceResult(ReturnResult.FAILURE, "Category not found! id:" + id);
		}
		dictionaryManager.moveDictionary(entity.getId(), offset);
		return new ServiceResult(ReturnResult.SUCCESS);
	}

	@ActionMethod(response = "json")
	public Object move(@ResourceVariable String id, @ParameterVariable("parent") String parent, @ParameterVariable("index") int index) {
		Dictionary entity = dictionaryManager.getDictionary(id);
		if (entity == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		if ("root".equals(parent)) {
			parent = null;
		}
		try {
			Dictionary p = dictionaryManager.getDictionary(parent);
			if (p != null && !StringUtils.equals(p.getId(), entity.getParentNode().getId())) {
				//处理重复的判断
				for (Dictionary brother : p.getChildNodes()) {
					if (StringUtils.equals(brother.getName(), entity.getName())) {
						return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下名称不能重复!");
					}
					if (StringUtils.isNotBlank(entity.getValue()) && StringUtils.equals(brother.getValue(), entity.getValue())) {
						return new ServiceResult(ReturnResult.VALIDATION_ERROR, "同一级下值不能重复!");
					}
				}
			}
			
			dictionaryManager.moveDictionary(entity.getId(), parent, index);
			return new ServiceResult(ReturnResult.SUCCESS);
		} catch (BusinessException e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE);
		}
	}

	@ActionMethod(response = "json")
	public Object getNamesbyIds(RequestParameters request) {

		String[] ids = request.getParameter("ids", String[].class);
		List<String> list = new ArrayList<String>();
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				Dictionary dictionary = dictionaryManager.getDictionary(ids[i]);
				if (dictionary != null) {
					list.add(dictionary.getName());
				}
			}
		}
		return list;
	}

	@ActionMethod(response = "json")
	public void exportCategory(RequestParameters reqParameters, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String dictionaryId = reqParameters.getParameter("dictionaryId", String.class);
		if (StringUtils.isNotBlank(dictionaryId)) {
			Dictionary dictionary = dictionaryManager.getDictionary(dictionaryId);
			jsonArray.put(JSONTransformer.transformPojo2Jso(dictionary));
		} else {
			List<Dictionary> categories = dictionaryManager.getRootDictionaries();
			for (Dictionary dictionary : categories) {
				jsonArray.put(JSONTransformer.transformPojo2Jso(dictionary));
			}
		}
		json.put("roots", jsonArray);
		String fileName = "Category" + RandomStringUtils.randomNumeric(4);
		StringWriter stringWriter = new StringWriter();
		stringWriter.append(json.toString());
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fileName + ".json", "utf-8") + "\"");
		StringReader stringReader = new StringReader(stringWriter.toString());
		IOUtils.copyAndCloseIOStream(new OutputStreamWriter(response.getOutputStream(), "utf-8"), stringReader);

	}

	@ActionMethod(response = "json")
	public Object importCategory(RequestParameters reqParameters) throws ParserException, Exception {
		String fileId = reqParameters.getParameter("fileId", String.class);
		String pId = reqParameters.getParameter("dictionaryId", String.class);
		if (StringUtils.isBlank(fileId)) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		String content = getFileStream(fileId);
		if (StringUtils.isBlank(content)) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		JSONObject json = new JSONObject(content);
		JSONArray dictionaryArray = (JSONArray) json.get("roots");
		if (dictionaryArray == null || dictionaryArray.length() == 0) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		for (int i = 0; i < dictionaryArray.length(); i++) {
			JSONObject dictionaryObject = dictionaryArray.getJSONObject(i);
			Dictionary dictionary = JSONTransformer.transformJso2Pojo(dictionaryObject, Dictionary.class);
			if (isExistImport(null, dictionary)) {
				if (isExistImport(pId, dictionary)) {
					dictionaryManager.deleteDictionary(dictionary.getId());
				} else {
					changeCategoryId(dictionary);
				}
			}
			importCreate(pId, dictionary);
		}
		return new ServiceResult(ReturnResult.SUCCESS);
	}

	private void importCreate(String parentId, Dictionary entity) {
		if (StringUtils.isNotBlank(parentId)) {
			Dictionary dictionary = dictionaryManager.getDictionary(parentId);
			dictionary.addChildNode(entity);
			dictionaryManager.saveDictionary(dictionary);
		} else {
			dictionaryManager.saveDictionary(entity);
		}
	}

	private Boolean isExistImport(String parentId, Dictionary dictionary) {
		List<Dictionary> brothers = null;
		if (StringUtils.isNotBlank(parentId)) {
			Dictionary parent = dictionaryManager.getDictionary(parentId);
			brothers = parent.getAllChildNodes();
		} else {
			brothers = dictionaryManager.getAll();
		}
		for (Dictionary brother : brothers) {
			List<String> brotherNodeIds = brother.getAllNodeIds();
			for (String nodeId : brotherNodeIds) {
				if (dictionary.getAllNodeIds().contains(nodeId)) {
					return true;
				}
			}
		}
		return false;
	}

	private Dictionary changeCategoryId(Dictionary dictionary) {
		dictionary.setId(UUID.generateUUID());
		List<Dictionary> lists = dictionary.getChildNodes();
		for (Dictionary c : lists) {
			changeCategoryId(c);
		}
		return dictionary;
	}

	private String getFileStream(String fileId) throws ParserException, Exception {
		com.taole.toolkit.filesystem.domain.File file = fileSystemManager.getFile(fileId);
		File f = new File(fileSystemPath.getRealPath(file.getPath()));
		String encoding = CharacterDetectorUtils.detectEncodingAndClose(new BufferedInputStream(new FileInputStream(f)));
		String content = org.apache.commons.io.IOUtils.toString(new FileInputStream(f), encoding);
		return content;
	}

	private TreeNode convertSetCategory(Dictionary dictionary) {
		TreeNode node = new TreeNode();
		node.setId(dictionary.getId());
		node.setName(dictionary.getName());
		for (Dictionary child : dictionary.getChildNodes()) {
			TreeNode childNode = convertSetCategory(child);
			if (childNode != null) {
				node.addChild(childNode);
			}
		}
		node.setLeaf(node.getChildren() == null || node.getChildren().isEmpty());
		return node;
	}

	@ActionMethod(response = "json")
	public Object listByIds(RequestParameters params) {
		String[] ids = params.getParameter("ids", String[].class);
		List<Object> list = new ArrayList<Object>();
		if (ids != null) {
			for (String id : ids) {
				Dictionary dictionary = dictionaryManager.getDictionary(id);
				if (dictionary != null) {
					list.add(dictionary);
				}
			}
		}
		return list;
	}
	
	@ActionMethod(response = "json")
	public Object retrieveByValue(@ResourceVariable String id,@ParameterVariable("parent") String  value) {
		Dictionary entity = dictionaryManager.getDictionaryByFather(value,id);

		return entity;
	}
	
	

	
}
