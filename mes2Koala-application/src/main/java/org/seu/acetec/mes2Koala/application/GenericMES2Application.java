package org.seu.acetec.mes2Koala.application;

import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.core.domain.MES2AbstractEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
public interface GenericMES2Application<T extends MES2AbstractEntity> {
    T get(Long id);

    void create(T entity);

    void createAll(Collection<T> entities);

    void update(T entity);

    void updateAll(Collection<T> entities);

    void remove(T entity);

    void removeAll(Collection<T> entities);

    List<T> findAll();

    // 使用HSQL语句检索数据
    List<T> find(String queryString);

    // 使用带参数的HSQL语句检索数据
    List<T> find(String queryString, List<Object> params);

    // 使用带参数的HSQL语句检索数据
    List<T> find(String queryString, Object... params);

    // 使用带命名的参数的HSQL语句检索数据
    List<T> find(String queryString, Map<String, Object> params);

    Page<T> page(String queryString, int currentPage, int pageSize);

    Page<T> page(String queryString, int currentPage, int pageSize, List<Object> params);

    Page<T> page(String queryString, int currentPage, int pageSize, Object... params);

    Page<T> page(String queryString, int currentPage, int pageSize, Map<String, Object> params);

    // 使用HSQL语句检索数据
    T findOne(String queryString);

    // 使用带参数的HSQL语句检索数据
    T findOne(String queryString, List<Object> params);

    // 使用带参数的HSQL语句检索数据
    T findOne(String queryString, Object... params);

    // 使用带命名的参数的HSQL语句检索数据
    T findOne(String queryString, Map<String, Object> params);

}
