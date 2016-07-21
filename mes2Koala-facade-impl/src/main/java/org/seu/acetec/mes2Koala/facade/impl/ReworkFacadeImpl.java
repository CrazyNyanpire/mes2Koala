package org.seu.acetec.mes2Koala.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.application.SecurityAccessApplication;
import org.openkoala.security.core.domain.User;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.impl.assembler.ReworkAssembler;
import org.seu.acetec.mes2Koala.facade.ReworkFacade;
import org.seu.acetec.mes2Koala.infra.MyDateUtils;
import org.seu.acetec.mes2Koala.application.ReworkApplication;
import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class ReworkFacadeImpl implements ReworkFacade {

	@Inject
	private ReworkApplication application;

	@Inject
	private SecurityAccessApplication UserApplication;

	private QueryChannelService queryChannel;

	private static String UNPASS = "未通过";

	private static String PASSED = "通过";

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	public InvokeResult getRework(Long id) {
		return InvokeResult.success(ReworkAssembler.toDTO(application
				.getRework(id)));
	}

	public InvokeResult creatRework(ReworkDTO reworkDTO) {
		application.creatRework(ReworkAssembler.toEntity(reworkDTO));
		return InvokeResult.success();
	}

	public InvokeResult updateRework(ReworkDTO reworkDTO) {
		application.updateRework(ReworkAssembler.toEntity(reworkDTO));
		return InvokeResult.success();
	}

	public InvokeResult removeRework(Long id) {
		application.removeRework(application.getRework(id));
		return InvokeResult.success();
	}

	public InvokeResult removeReworks(Long[] ids) {
		Set<Rework> reworks = new HashSet<Rework>();
		for (Long id : ids) {
			reworks.add(application.getRework(id));
		}
		application.removeReworks(reworks);
		return InvokeResult.success();
	}

	public List<ReworkDTO> findAllRework() {
		return ReworkAssembler.toDTOs(application.findAllRework());
	}

	public Page<ReworkDTO> pageQueryRework(ReworkDTO queryVo, int currentPage,
			int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _rework from Rework _rework   where 1=1 ");
		if (queryVo.getCreateTimestamp() != null) {
			jpql.append(" and _rework.createTimestamp between ? and ? ");
			conditionVals.add(queryVo.getCreateTimestamp());
			conditionVals.add(queryVo.getCreateTimestampEnd());
		}
		if (queryVo.getLastModifyTimestamp() != null) {
			jpql.append(" and _rework.lastModifyTimestamp between ? and ? ");
			conditionVals.add(queryVo.getLastModifyTimestamp());
			conditionVals.add(queryVo.getLastModifyTimestampEnd());
		}
		if (queryVo.getCreateEmployNo() != null
				&& !"".equals(queryVo.getCreateEmployNo())) {
			jpql.append(" and _rework.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCreateEmployNo()));
		}
		if (queryVo.getLastModifyEmployNo() != null
				&& !"".equals(queryVo.getLastModifyEmployNo())) {
			jpql.append(" and _rework.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLastModifyEmployNo()));
		}
		if (queryVo.getCategory() != null && !"".equals(queryVo.getCategory())) {
			jpql.append(" and _rework.category like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCategory()));
		}
		if (queryVo.getReworkRCNo() != null
				&& !"".equals(queryVo.getReworkRCNo())) {
			jpql.append(" and _rework.reworkRCNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkRCNo()));
		}
		if (queryVo.getReworkCustomer() != null
				&& !"".equals(queryVo.getReworkCustomer())) {
			jpql.append(" and _rework.reworkCustomer like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkCustomer()));
		}
		if (queryVo.getReworkDate() != null) {
			jpql.append(" and _rework.reworkDate between ? and ? ");
			conditionVals.add(queryVo.getReworkDate());
			conditionVals.add(queryVo.getReworkDateEnd());
		}
		if (queryVo.getReworkDepartment() != null
				&& !"".equals(queryVo.getReworkDepartment())) {
			jpql.append(" and _rework.reworkDepartment like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkDepartment()));
		}
		if (queryVo.getReworkEquipment() != null
				&& !"".equals(queryVo.getReworkEquipment())) {
			jpql.append(" and _rework.reworkEquipment like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkEquipment()));
		}
		if (queryVo.getReworkNo() != null && !"".equals(queryVo.getReworkNo())) {
			jpql.append(" and _rework.reworkNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkNo()));
		}
		if (queryVo.getReasonExplanation() != null
				&& !"".equals(queryVo.getReasonExplanation())) {
			jpql.append(" and _rework.reasonExplanation like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReasonExplanation()));
		}
		if (queryVo.getReasonOther() != null
				&& !"".equals(queryVo.getReasonOther())) {
			jpql.append(" and _rework.reasonOther like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReasonOther()));
		}
		if (queryVo.getReasonReasons() != null
				&& !"".equals(queryVo.getReasonReasons())) {
			jpql.append(" and _rework.reasonReasons like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReasonReasons()));
		}
		if (queryVo.getSummary() != null && !"".equals(queryVo.getSummary())) {
			jpql.append(" and _rework.summary like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getSummary()));
		}
		if (queryVo.getApprovePerson() != null
				&& !"".equals(queryVo.getApprovePerson())) {
			jpql.append(" and _rework.approvePerson like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getApprovePerson()));
		}
		if (queryVo.getReworkType() != null
				&& !"".equals(queryVo.getReworkType())) {
			jpql.append(" and _rework.reworkType like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkType()));
		}
		if (queryVo.getProduct() != null && !"".equals(queryVo.getProduct())) {
			jpql.append(" and _rework.product like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getProduct()));
		}
		if (queryVo.getApprove() != null && !"".equals(queryVo.getApprove())) {
			jpql.append(" and _rework.approve like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getApprove()));
		}
		if (queryVo.getApproveDate() != null) {
			jpql.append(" and _rework.approveDate between ? and ? ");
			conditionVals.add(queryVo.getApproveDate());
			conditionVals.add(queryVo.getApproveDateEnd());
		}
		if (queryVo.getApproveRemark() != null
				&& !"".equals(queryVo.getApproveRemark())) {
			jpql.append(" and _rework.approveRemark like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getApproveRemark()));
		}
		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _rework.lotNo like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
		}
		Page<Rework> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();

		return new Page<ReworkDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, ReworkAssembler.toDTOs(pages.getData()));
	}

	@Override
	public InvokeResult approve(ReworkDTO reworkDTO) {
		Rework rework = application.getRework(reworkDTO.getId());
		JSONArray approveArray = JSONArray
				.fromObject(rework.getApprovePerson());
		approveArray.toArray();
		boolean approveAll = true;
		boolean isApproveUser = false;
		JSONArray newArray = new JSONArray();
		for (Object object : approveArray.toArray()) {
			JSONObject jsonObject = JSONObject.fromObject(object);
			Long apprveUserId = jsonObject.getLong("id");
			User user = UserApplication.getUserById(apprveUserId);
			if (reworkDTO.getLastModifyEmployNo().contains(
					user.getUserAccount())) {
				jsonObject.put("status", reworkDTO.getApprove());
				jsonObject.put("approveDate", MyDateUtils.formaterDate(
						reworkDTO.getLastModifyTimestamp(),
						MyDateUtils.formater));
				jsonObject.put("remark", reworkDTO.getApproveRemark());
				isApproveUser = true;
			}
			if (jsonObject.get("status") == null
					|| !"true".equals(jsonObject.getString("status"))) {
				approveAll = false;
			}
			newArray.add(jsonObject);
		}
		if (!isApproveUser) {
			throw new RuntimeException("当前登录用户不能审核！");
		}
		if (approveAll) {
			rework.setApprove(PASSED);
		} else {
			rework.setApprove(UNPASS);
		}
		rework.setApprovePerson(newArray.toString());
		application.updateRework(rework);
		return InvokeResult.success();
	}

}
