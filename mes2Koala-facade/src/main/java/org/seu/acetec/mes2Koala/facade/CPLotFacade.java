package org.seu.acetec.mes2Koala.facade;

import net.sf.json.JSONArray;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTNodeDTO;

import java.util.List;

/**
 * @author 阙宇翔
 * @version 2016/2/14
 */
public interface CPLotFacade {

	InvokeResult getCPLot(Long id);

	InvokeResult createCPLot(CPLotDTO cpLotDTO);

	InvokeResult updateCPLot(CPLotDTO cpLotDTO);

	InvokeResult removeCPLot(Long id);

	InvokeResult removeCPLots(Long[] ids);

	List<CPLotDTO> findAllCPLot();

	Page<CPLotDTO> pageQueryCPLot(CPLotDTO cpLotDTO, int currentPage,
			int pageSize);

	/**
	 * 切换等待入站（1）状态变为已入站（2） 入站，会顺序查找整个Process上的所有站点，
	 * 跳过已经出站的站点，找到第一个没进站的站点，进站，对于其他情况作为异常状态处理
	 *
	 * @param processId
	 * @return
	 * @version 2016/4/12 harlow
	 */
	InvokeResult startCPNode(Long processId,CPLotDTO cpLotDTO);

	/**
	 * 出站 1. 首先检查外部的DTO中的state状态，保证不会又客户端修改状态 2. 然后检查状态必须是进站而没出战 3.
	 * 接着对Test站点进行良率卡控 4. 最后更新出站信息，更新出站信息也是顺序查找，找到一个没出站的站点，对于其他状态作为异常处理
	 *
	 * @param processId
	 * @param currentNode
	 *            当前所在站点
	 * @return
	 * @version 2016/4/12 harlow
	 */
	InvokeResult endCPNode(Long processId,CPLotDTO cpLotDTO);

	/**
	 * 良率放行
	 *
	 * @param processId
	 * @return
	 * @version 2016/4/12 harlow
	 */
	InvokeResult endFailTestNode(Long processId,CPNodeDTO cpNodeDTO);

	/**
	 * 更新站点信息
	 *
	 * @param currentNode
	 * @param processId
	 * @version 2016/4/12 harlow
	 */
	InvokeResult saveCPNode(Long processId, FTNodeDTO currentNode);

	InvokeResult endCPNodeIncoming(Long processId, JSONArray wafers,CPLotDTO cpLotDTO);

	List<CPLotDTO> getChildsLot(Long id);
	
	public InvokeResult changePid(CPLotDTO cpLotDTO,String ids);
}
