package org.seu.acetec.mes2Koala.application.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.application.GenericMES2Application;
import org.seu.acetec.mes2Koala.core.domain.MES2AbstractEntity;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Named
@Transactional
public abstract class GenericMES2ApplicationImpl<T extends MES2AbstractEntity> implements GenericMES2Application<T> {

    private Class<T> entityClass;
    private QueryChannelService queryChannel;

    private EntityManager entityManager;


    public GenericMES2ApplicationImpl() {
        entityClass = null;
        Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            entityClass = (Class<T>) p[0];
        }
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = (EntityManager) InstanceFactory.getInstance(SharedEntityManagerBean.class, "defaultEntityManager");
        }
        return entityManager;
    }

    @Override
    public T get(Long id) {
        return T.get(entityClass, id);
    }

    @Override
    public void create(T entity) {
        entity.save();
    }

    @Override
    public void createAll(Collection<T> entities) {
        for (T entity : entities) {
            entity.save();
        }
    }

    @Override
    public void update(T entity) {
    	if ( entity != null )
    		entity.save();
    }

    @Override
    public void updateAll(Collection<T> entities) {
        for (T entity : entities) {
        	if ( entity != null )
        		entity.save();
        }
    }

    @Override
    public void remove(T entity) {
        if (entity != null) {
            entity.remove();
        }
    }

    @Override
    public void removeAll(Collection<T> entities) {
        for (T entity : entities) {
            remove(entity);
        }
    }

    @Override
    public List<T> findAll() {
        return T.findAll(entityClass);
    }

    @Override
    public List<T> find(String queryString) {
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) getQueryChannelService()
                .createJpqlQuery(queryString)
                .list();
        return list;
    }

    @Override
    public List<T> find(String queryString, List<Object> params) {
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setParameters(params)
                .list();
        return list;
    }

    @Override
    public List<T> find(String queryString, Object... params) {
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setParameters(params)
                .list();
        return list;
    }

    @Override
    public List<T> find(String queryString, Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setParameters(params)
                .list();
        return list;
    }

    @Override
    public Page<T> page(String queryString, int currentPage, int pageSize) {
        @SuppressWarnings("unchecked")
        Page<T> page = (Page<T>) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setPage(currentPage, pageSize)
                .pagedList();
        return page;
    }

    @Override
    public Page<T> page(String queryString, int currentPage, int pageSize, List<Object> params) {
        @SuppressWarnings("unchecked")
        Page<T> page = (Page<T>) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setParameters(params)
                .setPage(currentPage, pageSize)
                .pagedList();
        return page;
    }

    @Override
    public Page<T> page(String queryString, int currentPage, int pageSize, Object... params) {
        @SuppressWarnings("unchecked")
        Page<T> page = (Page<T>) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setParameters(params)
                .setPage(currentPage, pageSize)
                .pagedList();
        return page;
    }

    @Override
    public Page<T> page(String queryString, int currentPage, int pageSize, Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        Page<T> page = (Page<T>) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setParameters(params)
                .setPage(currentPage, pageSize)
                .pagedList();
        return page;
    }

    @Override
    public T findOne(String queryString) {
        @SuppressWarnings("unchecked")
        T t = (T) getQueryChannelService()
                .createJpqlQuery(queryString)
                .singleResult();
        return t;
    }

    @Override
    public T findOne(String queryString, List<Object> params) {
        @SuppressWarnings("unchecked")
        T t = (T) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setParameters(params)
                .singleResult();
        return t;
    }

    @Override
    public T findOne(String queryString, Object... params) {
        @SuppressWarnings("unchecked")
        T t = (T) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setParameters(params)
                .singleResult();
        return t;
    }

    @Override
    public T findOne(String queryString, Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        T t = (T) getQueryChannelService()
                .createJpqlQuery(queryString)
                .setParameters(params)
                .singleResult();
        return t;
    }

}
