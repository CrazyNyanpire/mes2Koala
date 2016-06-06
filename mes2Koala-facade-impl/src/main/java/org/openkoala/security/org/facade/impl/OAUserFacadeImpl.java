package org.openkoala.security.org.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.security.org.facade.OAUserFacade;
import org.openkoala.security.org.facade.dto.OAUserDTO;
import org.openkoala.security.org.facade.impl.assembler.OAUserAssembler;


@Named
public class OAUserFacadeImpl implements OAUserFacade {

	private QueryChannelService queryChannel;
	
	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}
	
	

	@Override
	public List<OAUserDTO> findAll() {
		return this.findByCondition(new OAUserDTO());
	}

	@Override
	public List<OAUserDTO> findByCondition(OAUserDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("SELECT USER_ID, USER_NAME, USER_ACCOUNTS, b.DEPT_ID, b.DEPT_NAME, a.EMAIL, a.MOBIL_NO,a.SEX,a.USER_PRIV ");
		jpql.append("FROM oa_user a INNER JOIN oa_department b ON a.DEPT_ID = b.DEPT_ID ");
		jpql.append("WHERE a.USER_ACCOUNTS <> '' and a.USER_ACCOUNTS <> 'admin' ");
		if (queryVo.getAccounts() != null && !"".equals(queryVo.getAccounts())) {
			jpql.append(" and a.USER_ACCOUNTS in (").append(queryVo.getAccounts()).append(") ");
		}
		if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
			jpql.append(" and a.USER_NAME = ? ");
			conditionVals.add(queryVo.getName());
		}
		if (queryVo.getDeptId() != null && !"".equals(queryVo.getDeptId())) {
			jpql.append(" and b.DEPT_ID = ? ");
			conditionVals.add(queryVo.getDeptId());
		}
		if (queryVo.getDeptIds() != null && !"".equals(queryVo.getDeptIds())) {
			jpql.append(" and b.DEPT_ID in ( ").append(queryVo.getDeptIds()).append(") ");
		}
		if (queryVo.getDeptName() != null && !"".equals(queryVo.getDeptName())) {
			jpql.append(" and b.DEPT_NAME = ? ");
			conditionVals.add(queryVo.getDeptName());
		}
		if (queryVo.getUserPriv() != null && !"".equals(queryVo.getUserPriv())) {
			jpql.append(" and a.USER_PRIV = ? ");
			conditionVals.add(queryVo.getUserPriv());
		}
		jpql.append("ORDER BY a.USER_ACCOUNTS");
		@SuppressWarnings("unchecked")
		List<Object[]> list = getQueryChannelService()
				.createSqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();

		return OAUserAssembler.toDTOs(list);
	}
	
}
