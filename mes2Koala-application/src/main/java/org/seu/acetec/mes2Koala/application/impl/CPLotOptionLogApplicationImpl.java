package org.seu.acetec.mes2Koala.application.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.CPLotOptionLogApplication;
import org.seu.acetec.mes2Koala.application.GenericMES2Application;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPLotOptionLog;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.FTLotOptionLog;

@Named
@Transactional
public class CPLotOptionLogApplicationImpl extends GenericMES2ApplicationImpl<CPLotOptionLog> implements CPLotOptionLogApplication {

	public CPLotOptionLog getCPLotOptionLog(Long id) {
		return CPLotOptionLog.get(CPLotOptionLog.class, id);
	}
	
	public void creatCPLotOptionLog(CPLotOptionLog cPLotOptionLog) {
		cPLotOptionLog.save();
	}
	
	public void updateCPLotOptionLog(CPLotOptionLog cPLotOptionLog) {
		cPLotOptionLog .save();
	}
	
	public void removeCPLotOptionLog(CPLotOptionLog cPLotOptionLog) {
		if(cPLotOptionLog != null){
			cPLotOptionLog.remove();
		}
	}
	
	public void removeCPLotOptionLogs(Set<CPLotOptionLog> cPLotOptionLogs) {
		for (CPLotOptionLog cPLotOptionLog : cPLotOptionLogs) {
			cPLotOptionLog.remove();
		}
	}
	
	public List<CPLotOptionLog> findAllCPLotOptionLog() {
		return CPLotOptionLog.findAll(CPLotOptionLog.class);
	}

	@Override
	public void createStartCPNode(CPLot cpLot, CPNode cpNode) {
		this.createCPNode(cpLot, cpNode, cpLot.getCurrentState(), "进站");
	}
	
	@Override
	public void createEndCPNode(CPLot cpLot, CPNode cpNode) {
		//操作日志
		this.createCPNode(cpLot, cpNode, cpLot.getCurrentState(),"出站" );
	}
	
	@Override
	public void createCPNode(CPLot cpLot, CPNode cpNode,String optType, String remark) {
		//操作日志
		CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();
		BeanUtils.copyProperties(cpLot, cpLotOptionLog,"id");
		cpLotOptionLog.setCreateEmployNo(cpLot.getLastModifyEmployNo());
		cpLotOptionLog.setCreateTimestamp(cpLotOptionLog.getLastModifyTimestamp());
		cpLotOptionLog.setCpLot(cpLot);
		cpLotOptionLog.setOptType(optType);
		cpLotOptionLog.setRemark(remark);
		cpLotOptionLog.setFlownow(cpNode.getName());
		CPLotOptionLog lastCpLotOptionLog = this.findLastOptionLog(cpLot);
		if(lastCpLotOptionLog != null){
			lastCpLotOptionLog.setLastModifyEmployNo(cpLot.getLastModifyEmployNo());
			lastCpLotOptionLog.setLastModifyTimestamp(cpLotOptionLog.getLastModifyTimestamp());
			this.update(lastCpLotOptionLog);
		}
		this.creatCPLotOptionLog(cpLotOptionLog);
	}
	
	public CPLotOptionLog findLastOptionLog(CPLot cpLot){
		return this.findOne("select o from CPLotOptionLog o where o.cpLot.id = ? order by o.id desc",cpLot.getId());
	}

	@Override
	public CPLotOptionLog get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(CPLotOptionLog entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createAll(Collection<CPLotOptionLog> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CPLotOptionLog entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAll(Collection<CPLotOptionLog> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(CPLotOptionLog entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAll(Collection<CPLotOptionLog> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CPLotOptionLog> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CPLotOptionLog> find(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CPLotOptionLog> find(String queryString, List<Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CPLotOptionLog> find(String queryString, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CPLotOptionLog> find(String queryString,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CPLotOptionLog> page(String queryString, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CPLotOptionLog> page(String queryString, int currentPage,
			int pageSize, List<Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CPLotOptionLog> page(String queryString, int currentPage,
			int pageSize, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CPLotOptionLog> page(String queryString, int currentPage,
			int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CPLotOptionLog findOne(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CPLotOptionLog findOne(String queryString, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCPLotOptionLogByLotId(Long lotId) {
		List<CPLotOptionLog> list = this.find(
				"select a from CPLotOptionLog a where a.cpLot.id = ?", lotId);
		this.removeAll(list);
	}
	
}
