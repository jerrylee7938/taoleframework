/**
 * <pre>
 * Copyright:		Copyright(C) 2005-2006, feloo.org
 * Filename:		CategoryManager.java
 * Module:
 * Class:			CategoryManager
 * Date:			2006-12-13
 * Author:
 * Description:
 *
 *
 * ======================================================================
 * Change History Log
 * ----------------------------------------------------------------------
 * Mod. No.	| Date		| Name			| Reason			| Change Req.
 * ----------------------------------------------------------------------
 * 			| 2006-12-13	| 	| Created			|
 *
 * </pre>
 **/

package com.taole.toolkit.dict.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.NodeDao;
import com.taole.framework.dao.hibernate.CachedHibernateDaoSupport;
import com.taole.framework.dao.hibernate.HibernateNodeDao;
import com.taole.framework.events.EventMethod;
import com.taole.framework.exception.BusinessException;
import com.taole.framework.manager.NodeManager;
import com.taole.framework.util.UUID;
import com.taole.toolkit.dict.condition.DictionaryCondition;
import com.taole.toolkit.dict.domain.Dictionary;
import com.taole.toolkit.dict.Constants;

/**
 * Class: CategoryManager Remark:
 *
 * @author:
 */
@SuppressWarnings("ALL")
@DomainEngine(types = Dictionary.class)
@Transactional(readOnly = true)
public class DictionaryManager extends NodeManager<Dictionary> {

    @Resource(name = "tk.dictionaryDao")
    NodeDao<Dictionary> dictionaryDao;

    @Override
    protected NodeDao<Dictionary> getNodeDao() {
        return dictionaryDao;
    }

    @DomainEngine.C
    @DomainEngine.U
    @Transactional
    @EventMethod
    public String saveDictionary(Dictionary object) {
        return saveNode(object);
    }

    @DomainEngine.D
    @Transactional
    @EventMethod
    public void deleteDictionary(Dictionary object) {
        deleteNode(object);
    }

    @Transactional
    @EventMethod
    public void deleteDictionary(String id) throws BusinessException {
        deleteNodeById(id);
    }

    @DomainEngine.R
    public Dictionary getDictionary(String id) {
        return getNodeById(id);
    }

    public Dictionary getDictionaryByFather(String parentId, String value) {
        Dictionary parent = getNodeById(parentId);
        if (parent != null) {
            for (Dictionary child : parent.getChildNodes()) {
                if (child.getValue() != null && child.getValue().equals(value)) {
                    return child;
                }
            }
        }
        return null;
    }

    public Dictionary getDictionaryByType(String type, String value) {
        for (Dictionary dict : getAll()) {
            if (dict != null) {
                if (dict.getType() != null && dict.getValue() != null && dict.getValue().equals(value) && dict.getType().equals(type))
                    return dict;
            }
        }
        return null;
    }

    public List<Dictionary> getRootDictionaries() {
        return getRootNodes();
    }

    public List<Dictionary> getAll() {
        return getAllNodes();
    }

    @Transactional
    @EventMethod
    public void moveDictionary(String id, String parentId, int index) throws BusinessException {
        moveNode(id, parentId, index);
    }

    @Transactional
    @EventMethod
    public void moveDictionary(String id, int offset) {
        moveNode(id, offset);
    }

    @SuppressWarnings("rawtypes")
    public void clearCache() {
        HibernateNodeDao cachedDao = (HibernateNodeDao) dictionaryDao;
        cachedDao.refresh();
    }

    /**
     * 创建一个category对象
     *
     * @param type
     * @param value
     * @param name
     * @param superId
     * @return
     * @throws BusinessException
     */
    @Transactional
    @EventMethod
    public String createDictionary(String value, String name, String parentId, String description) throws BusinessException {
        Dictionary parent = getNodeById(parentId);
        if (parent == null) {
            throw new BusinessException("parent category is not exist.");
        }
        Dictionary obj = new Dictionary();
        obj.setId(UUID.generateUUID());
        obj.setName(name);
        obj.setDescription(description);
        if (StringUtils.isBlank(value)) {
            obj.setValue(obj.getId());
        } else {
            obj.setValue(value);
        }
        if (Constants.DICT_ROOT.equals(parentId)) {
            obj.setType(value);
        } else {
            obj.setType(parent.getType());
        }
        obj.setParentNode(parent);
        parent.addChildNode(obj);
        return saveNode(obj);
    }

    /**
     * 获取指定的code字典下所有子项，以map（字典value作为KEY，字典做值）形式返回
     *
     * @param id
     * @return
     */
    public Map<String, DictNode> getDictNods(String id) {
        Map<String, DictNode> dataMap = new HashMap();
        try {
            Dictionary dict = getDictionary(id);
            if (dict == null) return null;
            List<Dictionary> subDictList = dict.getChildNodes();
            for (Dictionary subDict : subDictList) {
                DictNode dictNode = new DictNode();
                dataMap.put(subDict.getValue(), dictNode.setId(subDict.getValue()).setName(subDict.getName()).setValue(subDict.getValue()));
            }
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    @EventMethod
    public String createDictionary(String value, String name, String parentId) throws BusinessException {
        Dictionary parent = getNodeById(parentId);
        if (parent == null) {
            throw new BusinessException("parent category is not exist.");
        }
        Dictionary obj = new Dictionary();
        obj.setId(UUID.generateUUID());
        obj.setName(name);

        if (StringUtils.isBlank(value)) {
            obj.setValue(obj.getId());
        } else {
            obj.setValue(value);
        }
        if (Constants.DICT_ROOT.equals(parentId)) {
            obj.setType(value);
        } else {
            obj.setType(parent.getType());
        }
        obj.setParentNode(parent);
        parent.addChildNode(obj);
        return saveNode(obj);
    }

    @Transactional
    @EventMethod
    public String createDictionary(Dictionary dictionary) throws BusinessException {
        Dictionary parent = getNodeById(dictionary.getParentId());
        if (parent == null) {
            throw new BusinessException("parent category is not exist.");
        }

        if (StringUtils.isBlank(dictionary.getId())) {
            dictionary.setId(UUID.generateUUID());
        }

        if (StringUtils.isBlank(dictionary.getValue())) {
            dictionary.setValue(dictionary.getId());
        }

        if (Constants.DICT_ROOT.equals(dictionary.getParentId())) {
            dictionary.setType(dictionary.getValue());
        } else {
            dictionary.setType(parent.getType());
        }
        dictionary.setParentNode(parent);
        parent.addChildNode(dictionary);
        return saveNode(dictionary);
    }

    /**
     * 根据条件过滤字典表数据
     *
     * @param dictionary
     * @param condition
     * @return
     */
    public Dictionary filter(Dictionary dictionary, DictionaryCondition condition) {
        if (!condition.accept(dictionary)) {
            return null;
        }
        if (dictionary.hasChild()) {
            List<Dictionary> children = new ArrayList<Dictionary>();
            for (Dictionary child : dictionary.getChildNodes()) {
                Dictionary filteredChild = filter(child, condition);
                if (filteredChild != null) {
                    children.add(filteredChild);
                }
            }
            dictionary.setChildNodes(children);
        }
        return dictionary;
    }

    /**
     * 根据条件过滤分类列表
     *
     * @param dicts
     * @param condition
     * @return
     */
    public List<Dictionary> filter(List<Dictionary> dicts, DictionaryCondition condition) {
        List<Dictionary> filteredList = new ArrayList<Dictionary>();
        for (Dictionary dict : dicts) {
            Dictionary filteredCategory = filter(dict, condition);
            if (filteredCategory != null) {
                filteredList.add(filteredCategory);
            }
        }
        return filteredList;
    }

    public String getDictName(String dictKey, String dictValue) {
        if (StringUtils.isBlank(dictValue)) {
            return "未设置";
        } else {
            Dictionary dict = getDictionaryByFather(dictKey, dictValue);
            return dict == null ? "未知" : dict.getName();
        }

    }

    public class DictNode {
        //对应字典value
        private String id;
        //对应字典name
        private String name;
        //对应字典value
        private String value;

        public String getId() {
            return id;
        }

        public DictNode setId(String id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public DictNode setName(String name) {
            this.name = name;
            return this;
        }

        public String getValue() {
            return value;
        }

        public DictNode setValue(String value) {
            this.value = value;
            return this;
        }
    }

}
