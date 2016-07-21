package org.seu.acetec.mes2Koala.application;

import java.util.Collection;
import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;

import com.google.common.base.Strings;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
public interface CPProcessApplication extends GenericMES2Application<CPProcess> {

	/**
	 * @param cpLotId
	 * @return
	 */
	CPProcess findByCPLotId(Long cpLotId);

	/**
	 * 下单后创建测试站点
	 *
	 * @param cpLotId
	 */
	void createCPProcess(Long cpLotId);

	/**
	 * @param cpLotId
	 * @param parentCPLotId
	 */
	void createCPProcess(Long cpLotId, Long parentCPLotId);

	void deleteCPProcess(Long id);

	public String[] extractNodeNamesByProcessTemplateContent(
			String processContent);

	public List<CPNode> generateCPNodes(CPProcess process, int startIndex,
			String[] nodeNames);
	
	  public void bindProductionSchedule(Collection<CPNode> cpNodes);
}
