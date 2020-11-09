package com.taole.framework.dao.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.bean.Node;
import com.taole.framework.cache.Cache;
import com.taole.framework.cache.CacheFactoryFactory;
import com.taole.framework.dao.NodeDao;
import com.taole.framework.exception.BusinessException;
import com.taole.framework.util.UUID;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: HibernateNodeDao.java 9642 2017-05-08 03:30:39Z tonytang $
 */
public class HibernateNodeDao<T extends Node<T>> extends BaseHibernateDaoSupport<T> implements NodeDao<T> {

    private String allCachedKey;

    @Autowired
    protected CacheFactoryFactory cacheFactoryFactory;

    @SuppressWarnings("rawtypes")
    protected Cache<String, List> getEntityCache() {
        Cache<String, List> cache = cacheFactoryFactory.getCacheFactory().getCache("__" + getEntityName() + "__", String.class, List.class);
        return cache;
    }

    protected String getAllCachedKey() {
        if (allCachedKey == null) {
            allCachedKey = "__" + getEntityName() + "___all___";
        }
        return allCachedKey;
    }

    /**
     * @param entityClass
     */
    public HibernateNodeDao(Class<T> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveNode(T node) {
        List<T> allChildren = node.getAllChildNodes();
        for (T child : allChildren) {
            saveOrUpdate(child);
        }
        if (StringUtils.isBlank(node.getId())) {
            node.setId(UUID.generateUUID());
        }
        saveOrUpdate(node);
         refresh();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNode(T node) {
        List<T> all = node.getAllChildNodes();
        all.add(node);
        getHibernateTemplate().deleteAll(all);
         refresh();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() {
        super.deleteAll();
         refresh();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllNodes() {
        this.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getRootNodes() {
        List<T> rootNotes = new ArrayList<T>();
        for (T node : findAll()) {
            if (node.getParentNode() == null) {
                rootNotes.add(node);
            }
        }
        return rootNotes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        List<T> cached = getEntityCache().get(getAllCachedKey());
        if (cached == null) {
            DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
            criteria.addOrder(Order.asc("index"));
            List<T> all = (List<T>) getHibernateTemplate().findByCriteria(criteria);

            List<T> roots = new ArrayList<T>();
            Set<String> addedNodeIds = new HashSet<String>();
            for (T node : all) {
                if (StringUtils.isBlank(node.getParentId())) {
                    if (addedNodeIds.contains(node.getId())) {
                        continue;
                    }
                    fillChildren(node, all);
                    addedNodeIds.addAll(node.getAllNodeIds());
                    roots.add(node);
                }
            }
            getEntityCache().put(getAllCachedKey(), all);
            return all;
        } else {
            return cached;
        }
    }

    private void fillChildren(T parentNode, List<T> nodes) {
        List<T> children = new ArrayList<T>();
        for (T child : nodes) {
            if (StringUtils.isNotBlank(child.getParentId()) && StringUtils.equals(parentNode.getId(), child.getParentId())) {
                fillChildren(child, nodes);
                child.setParentNode(parentNode);
                children.add(child);
            }
        }
        if (!children.isEmpty()) {
            parentNode.setChildNodes(children);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getNode(String id) {
        List<T> all = findAll();
        for (T t : all) {
            if (StringUtils.equals(id, t.getId())) {
                return t;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNodeById(String id) throws BusinessException {
        T node = getNode(id);
        if (node == null) {
            throw new BusinessException("Node[" + id + "] not exist.");
        }
        deleteNode(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveNode(String id, String parentId, int index) throws BusinessException {
        T node = getNode(id);
        if (node == null) {
            throw new BusinessException("Node[" + id + "] not exist");
        }
        if (node.getParentNode() != null && StringUtils.equals(node.getParentNode().getId(), parentId) && node.getIndex() == index) {
            return;
        }
        T oldParentNode = node.getParentNode();
        // 如果在不同父节点间移动，从原父节点删除并更新原父节点中子节点的index值
        if (oldParentNode != null && !StringUtils.equals(oldParentNode.getId(), parentId)) {
            oldParentNode.removeChildNode(node);
            for (T child : oldParentNode.getChildNodes()) {
                child.setIndex(oldParentNode.getChildNodes().indexOf(child));
                updateObject(child);
            }
            updateObject(oldParentNode);
        }

        T parentNode = getNode(parentId);
        if (parentNode != null) {
            T exist = null;
            if (!parentNode.getChildNodes().isEmpty()) {
                for (T child : parentNode.getChildNodes()) {
                    if (StringUtils.equals(child.getId(), node.getId())) {
                        exist = child;
                        break;
                    }
                }
                if (exist != null) {
                    parentNode.removeChildNode(exist);
                }
            }
            parentNode.addChildNode(node, index);
            for (T child : parentNode.getChildNodes()) {
                child.setIndex(parentNode.getChildNodes().indexOf(child));
                updateObject(child);
            }
            updateObject(parentNode);
        }
         refresh();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveNode(String id, int offset) {
        if (offset == 0) {
            return;
        }
        T object = getNode(id);
        if (object == null) {
            return;
        }
        T parentNode = object.parentNode();
        if (parentNode == null) {
            return;
        }
        List<T> brothers = parentNode.getChildNodes();
        int newIndex = object.getIndex() + offset;
        if (newIndex < 0) {
            newIndex = 0;
        } else if (newIndex >= brothers.size()) {
            newIndex = brothers.size() - 1;
        }
        if (object.getIndex() == newIndex) {
            return;
        }
        brothers.remove(object);
        brothers.add(newIndex, object);
        for (T child : brothers) {
            child.setIndex(brothers.indexOf(child));
			if (StringUtils.isBlank(child.getParentId())) {
				throw new RuntimeException("节点[" + child.getName() + "]错误，父Id不应为空！");
			}
            updateObject(child);
        }
        parentNode.setChildNodes(brothers);
        updateObject(parentNode);
        refresh();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T copyNode(String id) throws BusinessException {
        T node = getNode(id);
        if (node == null) {
            return null;
        }
        T copy = node.copy();
        saveNode(copy);
        return copy;
    }

    public void refresh() {
        getEntityCache().clear();
        getEntityCache().remove("___cache.entity.__tk.Dictionary__.__tk.Dictionary___all___");
        getEntityCache().remove("__tk.Dictionary__");
        findAll();
    }

}
