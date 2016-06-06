package org.openkoala.security.org.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.security.org.facade.OADepartmentFacade;
import org.openkoala.security.org.facade.dto.OADepartmentDTO;
import org.openkoala.security.org.facade.impl.assembler.OADepartmentAssembler;

@Named
public class OADepartmentFacadeImpl implements OADepartmentFacade {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Override
	public List<OADepartmentDTO> findAll() {
		return this.findByCondition(new OADepartmentDTO());
	}

	@Override
	public List<OADepartmentDTO> findByCondition(OADepartmentDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"SELECT a.DEPT_ID, a.DEPT_NAME, a.DEPT_NO, a.DEPT_PARENT, b.DEPT_NO PARENTNO ");
		jpql.append("FROM oa_department a left join oa_department b on a.DEPT_PARENT = b.DEPT_ID ");
		if (queryVo.getId() != null) {
			jpql.append(" and a.DEPT_ID = ? ");
			conditionVals.add(queryVo.getId());
		}
		if (queryVo.getDeptParentId() != null) {
			jpql.append(" and a.DEPT_PARENT = ? ");
			conditionVals.add(queryVo.getDeptParentId());
		}
		if (queryVo.getDeptName() != null && !"".equals(queryVo.getDeptName())) {
			jpql.append(" and a.DEPT_NAME = ? ");
			conditionVals.add(queryVo.getDeptName());
		}
		if (queryVo.getDeptNo() != null && !"".equals(queryVo.getDeptNo())) {
			jpql.append(" and a.DEPT_NO = ? ");
			conditionVals.add(queryVo.getDeptNo());
		}
		jpql.append("ORDER BY a.DEPT_ID");
		@SuppressWarnings("unchecked")
		List<Object[]> list = getQueryChannelService()
				.createSqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();

		return OADepartmentAssembler.toDTOs(list);
	}

}
