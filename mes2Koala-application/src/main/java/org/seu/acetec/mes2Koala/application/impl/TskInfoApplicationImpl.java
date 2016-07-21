package org.seu.acetec.mes2Koala.application.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.seu.acetec.mes2Koala.application.TskInfoApplication;
import org.seu.acetec.mes2Koala.core.domain.TskInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

import java.util.List;
import java.util.Map;

/**
 * @author harlow
 * @version 2016/6/20 harlow
 */

@Named
@Transactional
public class TskInfoApplicationImpl implements TskInfoApplication {

	private QueryChannelService queryChannel;

	protected QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Override
	public List<TskInfo> find(String queryString, List<Object> params) {
		@SuppressWarnings("unchecked")
		List<TskInfo> list = (List<TskInfo>) getQueryChannelService()
				.createJpqlQuery(queryString).setParameters(params).list();
		return list;
	}

	@Override
	public List<TskInfo> find(String queryString, Object... params) {
		@SuppressWarnings("unchecked")
		List<TskInfo> list = (List<TskInfo>) getQueryChannelService()
				.createJpqlQuery(queryString).setParameters(params).list();
		return list;
	}

	@Override
	public List<TskInfo> find(String queryString, Map<String, Object> params) {
		@SuppressWarnings("unchecked")
		List<TskInfo> list = (List<TskInfo>) getQueryChannelService()
				.createJpqlQuery(queryString).setParameters(params).list();
		return list;
	}

	@Override
	public TskInfo findOne(String queryString) {
		TskInfo t = (TskInfo) getQueryChannelService().createJpqlQuery(
				queryString).singleResult();
		return t;
	}

	@Override
	public TskInfo findOne(String queryString, List<Object> params) {
		@SuppressWarnings("unchecked")
		TskInfo t = (TskInfo) getQueryChannelService()
				.createJpqlQuery(queryString).setParameters(params)
				.singleResult();
		return t;
	}

	@Override
	public TskInfo findOne(String queryString, Object... params) {
		TskInfo t = (TskInfo) getQueryChannelService()
				.createJpqlQuery(queryString).setParameters(params)
				.singleResult();
		return t;
	}

	@Override
	public TskInfo findOne(String queryString, Map<String, Object> params) {
		@SuppressWarnings("unchecked")
		TskInfo t = (TskInfo) getQueryChannelService()
				.createJpqlQuery(queryString).setParameters(params)
				.singleResult();
		return t;
	}

}
