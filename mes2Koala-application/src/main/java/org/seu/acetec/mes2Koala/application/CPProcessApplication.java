package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.CPProcess;

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
}
