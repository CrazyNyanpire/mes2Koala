package org.openkoala.security.org.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.security.org.facade.MesStaffsFacade;
import org.openkoala.security.org.facade.OAUserFacade;
import org.openkoala.security.org.facade.dto.MesStaffsDTO;
import org.openkoala.security.org.facade.dto.OAUserDTO;
import org.openkoala.security.org.facade.impl.assembler.MesStaffsAssembler;
import org.openkoala.security.org.facade.impl.assembler.OAUserAssembler;

@Named
public class MesStaffsFacadeImpl implements MesStaffsFacade {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Override
	public List<MesStaffsDTO> findAll() {
		return this.findByCondition(new MesStaffsDTO());
	}

	@Override
	public List<MesStaffsDTO> findAllDepartnent() {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select DISTINCT Staffs_department ");
		jpql.append("from mes_staffs a ");
		@SuppressWarnings("unchecked")
		List<String> list = getQueryChannelService()
				.createSqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();

		return MesStaffsAssembler.toDepartmentDTOs(list);
	}

	@Override
	public List<MesStaffsDTO> findAllPost() {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select DISTINCT Staffs_position ");
		jpql.append("from mes_staffs a ");
		@SuppressWarnings("unchecked")
		List<Object[]> list = getQueryChannelService()
				.createSqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();
		return MesStaffsAssembler.toPostDTOs(list);
	}

	@Override
	public List<MesStaffsDTO> findAllPostAndDepartment() {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select DISTINCT Staffs_department,Staffs_position ");
		jpql.append("from mes_staffs a ");
		@SuppressWarnings("unchecked")
		List<Object[]> list = getQueryChannelService()
				.createSqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();

		return MesStaffsAssembler.toPostAndDepartmentDTOs(list);
	}

	@Override
	public List<MesStaffsDTO> findByCondition(MesStaffsDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select a.id,a.Staffs_Name,a.Staffs_Number,a.Staffs_department,a.Staffs_position,a.Staffs_email,a.Staffs_LianXiDianHua,a.Staffs_Sex ");
		jpql.append("from mes_staffs a ");
		if (queryVo.getAccounts() != null && !"".equals(queryVo.getAccounts())) {
			jpql.append(" and a.Staffs_Number in (")
					.append(queryVo.getAccounts()).append(") ");
		}
		if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
			jpql.append(" and a.Staffs_Name = ? ");
			conditionVals.add(queryVo.getName());
		}
		if (queryVo.getDeptName() != null && !"".equals(queryVo.getDeptName())) {
			jpql.append(" and a.Staffs_department = ? ");
			conditionVals.add(queryVo.getDeptName());
		}
		if (queryVo.getUserPriv() != null && !"".equals(queryVo.getUserPriv())) {
			jpql.append(" and a.Staffs_position = ? ");
			conditionVals.add(queryVo.getUserPriv());
		}
		jpql.append("ORDER BY a.Staffs_Number");
		@SuppressWarnings("unchecked")
		List<Object[]> list = getQueryChannelService()
				.createSqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();

		return MesStaffsAssembler.toDTOs(list);
	}

}
