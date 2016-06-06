package org.openkoala.security.org.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.security.org.facade.OAUserPrivFacade;
import org.openkoala.security.org.facade.dto.OAUserPrivDTO;
import org.openkoala.security.org.facade.impl.assembler.OAUserPrivAssembler;


@Named
public class OAUserPrivFacadeImpl implements OAUserPrivFacade {

	private QueryChannelService queryChannel;
	
	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}
	
	

	@Override
	public List<OAUserPrivDTO> findAll() {
		return this.findByCondition(new OAUserPrivDTO());
	}

	@Override
	public List<OAUserPrivDTO> findByCondition(OAUserPrivDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("SELECT USER_PRIV, PRIV_NAME ");
		jpql.append("FROM oa_user_priv ");
		jpql.append("WHERE 1 = 1 ");
		jpql.append("ORDER BY USER_PRIV");
		@SuppressWarnings("unchecked")
		List<Object[]> list = getQueryChannelService()
				.createSqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();

		return OAUserPrivAssembler.toDTOs(list);
	}
	
}
