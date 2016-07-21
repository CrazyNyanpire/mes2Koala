package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.TskInfo;

import java.util.List;
import java.util.Map;

/**
 * @author harlow
 * @version 2016/6/20
 */
public interface TskInfoApplication {
	// 使用带参数的HSQL语句检索数据
	List<TskInfo> find(String queryString, List<Object> params);

	// 使用带参数的HSQL语句检索数据
	List<TskInfo> find(String queryString, Object... params);

	// 使用带命名的参数的HSQL语句检索数据
	List<TskInfo> find(String queryString, Map<String, Object> params);

	// 使用HSQL语句检索数据
	TskInfo findOne(String queryString);

	// 使用带参数的HSQL语句检索数据
	TskInfo findOne(String queryString, List<Object> params);

	// 使用带参数的HSQL语句检索数据
	TskInfo findOne(String queryString, Object... params);

	// 使用带命名的参数的HSQL语句检索数据
	TskInfo findOne(String queryString, Map<String, Object> params);
}
