package org.openkoala.security.org.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.security.org.facade.MesPositionFacade;
import org.openkoala.security.org.facade.OAUserFacade;
import org.openkoala.security.org.facade.dto.MesPositionDTO;
import org.openkoala.security.org.facade.impl.assembler.MesPositionAssembler;
import org.openkoala.security.org.facade.impl.assembler.OAUserAssembler;


@Named
public class MesPositionFacadeImpl implements MesPositionFacade {

	private QueryChannelService queryChannel;
	
	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}
	
	

	@Override
	public List<MesPositionDTO> findAll() {
		return this.findByCondition(new MesPositionDTO());
	}

	@Override
	public List<MesPositionDTO> findByCondition(MesPositionDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("SELECT id,POST_CODE,POST_NAME,POST_DEPART ");
		jpql.append("FROM mes_post ");
		jpql.append("ORDER BY id");
		@SuppressWarnings("unchecked")
		List<Object[]> list = getQueryChannelService()
				.createSqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();
		return MesPositionAssembler.toDTOs(list);
	}
	
}
