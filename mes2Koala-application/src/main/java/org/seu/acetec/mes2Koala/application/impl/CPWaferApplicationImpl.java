package org.seu.acetec.mes2Koala.application.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.CPLotOptionLogApplication;
import org.seu.acetec.mes2Koala.application.CPWaferApplication;
import org.seu.acetec.mes2Koala.application.CPWaferLogApplication;
import org.seu.acetec.mes2Koala.application.bean.SaveBaseBean;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPWafer;
import org.seu.acetec.mes2Koala.core.domain.CPWaferLog;
import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;

import com.sun.star.uno.RuntimeException;

@Named
@Transactional
public class CPWaferApplicationImpl extends GenericMES2ApplicationImpl<CPWafer>
		implements CPWaferApplication {

	@Inject
	CPWaferLogApplication CPWaferLogApplication;
	
	@Inject
	CPLotOptionLogApplication cpLotOptionLogApplication;

	public CPWafer getCPWafer(Long id) {
		return CPWafer.get(CPWafer.class, id);
	}

	public void creatCPWafer(CPWafer cPWafer) {
		cPWafer.save();
	}

	public void updateCPWafer(CPWafer cPWafer) {
		cPWafer.save();
	}

	public void removeCPWafer(CPWafer cPWafer) {
		if (cPWafer != null) {
			cPWafer.remove();
		}
	}

	public void removeCPWafers(Set<CPWafer> cPWafers) {
		for (CPWafer cPWafer : cPWafers) {
			cPWafer.remove();
		}
	}

	public List<CPWafer> findAllCPWafer() {
		return CPWafer.findAll(CPWafer.class);
	}

	@Override
	public void changeStatus(Long waferId, CPWaferState passed) {
		Date nowDate = new Date();
		CPWafer cpWafer = CPWafer.get(CPWafer.class, waferId);
		cpWafer.setState(passed);
		cpWafer.save();
		if (cpWafer.getCpLot().getCpProcess().getNowNode() != null) {
			CPWaferLog cpWaferLog = this.createCPWaferLog(cpWafer.getId(),
					cpWafer.getCpLot().getCpProcess().getNowNode().getId());
			cpWaferLog.setCpWafer(cpWafer);
			cpWaferLog.setCpLot(cpWafer.getCpLot());
			cpWaferLog.setNode(cpWafer.getCpLot().getCpProcess().getNowNode());
			cpWaferLog.setLastModifyTimestamp(nowDate);
			cpWaferLog.setState(passed);
			cpWaferLog.setRemark("直接通过");
			cpWaferLog.save();
		}
	}

	@Override
	public void saveCheck(String waferIds, CPWaferCheck cpWaferCheck, SaveBaseBean sbb) {
		Long cpLotId = null;
		String[] ids = waferIds.split(",");
		if(ids[0] == null){
			throw new RuntimeException("请选择抽检的片号！");
		}
		CPWafer wafer = CPWafer.get(CPWafer.class, Long.valueOf(ids[0]));
		cpLotId = wafer.getCpLot().getId();
		if (cpLotId != null) {
			List<CPWafer> cpWafers = this
					.find("select e from CPWafer e where e.cpLot.id = "
							+ cpLotId);
			CPWaferLog cpWaferLog;
			for (CPWafer cpWafer : cpWafers) {
				if(!this.hasId(cpWafer.getId(), ids)){
					cpWafer.setCpWaferCheck(CPWaferCheck.UNCHECKED);
				}else{
					cpWafer.setCpWaferCheck(cpWaferCheck);
				}
				cpWafer.setState(CPWaferState.PASSED);
				BeanUtils.copyProperties(sbb, cpWafer);
				cpWafer.save();
				if (cpWafer.getCpLot().getCpProcess().getNowNode() != null) {
					cpWaferLog = this.createCPWaferLog(cpWafer.getId(), cpWafer
							.getCpLot().getCpProcess().getNowNode().getId());
					cpWaferLog.setCpWafer(cpWafer);
					cpWaferLog.setCpLot(cpWafer.getCpLot());
					cpWaferLog.setNode(cpWafer.getCpLot().getCpProcess()
							.getNowNode());
					BeanUtils.copyProperties(sbb, cpWaferLog);
					cpWaferLog.setCreateEmployNo(sbb.getCreateEmployNo());
					cpWaferLog.setCreateTimestamp(sbb.getCreateTimestamp());
					cpWaferLog.setState(CPWaferState.PASSED);
					cpWaferLog.setRemark("抽检通过");
					cpWaferLog.save();
				}
			}
		}
		CPLot cpLot = wafer.getCpLot();
		BeanUtils.copyProperties(sbb, cpLot);
		cpLotOptionLogApplication.createCPNode(cpLot, cpLot.getCpProcess().getNowNode(),cpLot.getCurrentState(),"抽检");
	}
	
	private boolean hasId(Long id,String [] ids){
		for(String idStr : ids){
			if(id.longValue() == Long.valueOf(idStr).longValue()){
				return true;
			}
		}
		return false;
	}

	private CPWaferLog createCPWaferLog(Long waferId, Long nodeId) {
		CPWaferLog cpWaferLog = this.CPWaferLogApplication
				.findOne("select e from CPWaferLog e where e.node.id = "
						+ nodeId + " and e.cpWafer.id = " + waferId);
		if (cpWaferLog == null || cpWaferLog.getId() == null) {
			return new CPWaferLog();
		} else {
			return cpWaferLog;
		}
	}
	
	public void changeStatusSBL(JSONArray cpWaferArray, Long cpLotId) {
		List<CPWafer> cpWafers = this
				.find("select e from CPWafer e where e.cpLot.id = "
						+ cpLotId);
		@SuppressWarnings("unchecked")
		List<JSONObject> cpWaferList = (List<JSONObject>) JSONArray
				.toCollection(cpWaferArray, JSONObject.class);
		Date nowDate = new Date();
		for(JSONObject cpWaferJson : cpWaferList){
			CPWafer cpWafer = this.getCPWaferByWaferCode(cpWafers, cpWaferJson.getString("WAFERID"));
			cpWafer.setPass(cpWaferJson.getString("WAFERID"));
			cpWafer.setFail(cpWaferJson.getString("FAIL"));
			cpWafer.setTotal(cpWaferJson.getString("TOTAL"));
			cpWafer.setSblResult(cpWaferJson.getString("SBLRESULT"));
			if("PASS".equalsIgnoreCase(cpWafer.getSblResult()))
				cpWafer.setState(CPWaferState.PASSED);
			cpWafer.setLastModifyTimestamp(nowDate);
			cpWafer.save();
			CPWaferLog cpWaferLog = this.createCPWaferLog(cpWafer.getId(),
					cpWafer.getCpLot().getCpProcess().getNowNode().getId());
			cpWaferLog.setCpWafer(cpWafer);
			cpWaferLog.setCpLot(cpWafer.getCpLot());
			cpWaferLog.setNode(cpWafer.getCpLot().getCpProcess().getNowNode());
			cpWaferLog.setLastModifyTimestamp(nowDate);
			cpWaferLog.setPass(cpWafer.getPass());
			cpWaferLog.setFail(cpWafer.getFail());
			cpWaferLog.setTotal(cpWafer.getTotal());
			cpWaferLog.setSblResult(cpWafer.getSblResult());
			cpWafer.setLastModifyTimestamp(nowDate);
			cpWaferLog.setState(cpWafer.getState());
			cpWaferLog.setRemark("SBL通过");
			cpWaferLog.save();
		}
	}
	
	private CPWafer getCPWaferByWaferCode(List<CPWafer> cpWafers,String cpWaferCode){
		for(CPWafer cpWafer : cpWafers){
			if(cpWafer.getInternalWaferCode().indexOf(cpWaferCode) > -1){
				return cpWafer;
			}
		}
		return null;
	}
}
