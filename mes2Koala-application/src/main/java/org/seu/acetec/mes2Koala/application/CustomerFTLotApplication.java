package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CustomerFTLotApplication extends GenericMES2Application<CustomerFTLot> {

	/**
	 *
	 * @param wmsId
	 * @return
	 * @version 2016/3/27 YuxiangQue
	 * @version 2016/3/28 YuxiangQue
	 */
	CustomerFTLot findByWmsId(String wmsId);

	List<CustomerFTLot> findByParentSeparationId(Long parentSeparationId);

	CustomerFTLot findByParentIntegrationId(String parentIntegrationId);

	/**
	 * 手动下单
	 *
	 * @param customerFTLotId
	 * @param ftLot
	 */
	void  order(Long customerFTLotId, FTLot ftLot,Long ftInfoId);

	/**
	 * 批量下单：循环遍历尝试自动合批后下单
	 *
	 * @param customerFTLotIds 需要批量下单的批次ids
	 * @param messages
	 * @return 返回批量下单结果（新创建的FTLot的id List）
	 * @version 2016/1/17 Howad
	 * @version 2016/3/27 YuxiangQue
	 */
	List<Long> orderInBatches(Long[] customerFTLotIds, Map<String, Integer> messages,Long ftInfoId);


	/**
	 * 客批分批：将一批分为多批，原批次数量减少，新批次数量参照separationQties，并与母批关联
	 *
	 * @param customerLotId                 母批id
	 * @param separateQuantities 子批数量List
	 * @return 成功返回成功标记，失败返回错误信息
	 * @version 2016/1/15 Howad
	 * @version 2016/3/27 YuxiangQue
	 */
	void separateMany(Long customerLotId, List<Long> separateQuantities);

	/**
	 * 分批分批还原：首先判断是否来自于同一母批，若来自于同一母批则恢复母批数量删除子批
	 *
	 * @param customerFTLotIds 子批ids
	 * @return 返回成功或错误信息
	 * @version 2016/1/9 Howad
	 * @version 2016/3/27 YuxiangQue
	 */
	void undoSeparate(Long[] customerFTLotIds);

	/**
	 * 合批还原，针对一个合批生成的批次进行合批还原
	 *
	 * @param customerFTLotId 合批生成的批次
	 * @return 返回成功或错误信息
	 * @version 2016/1/11 Howad
	 * @version 2016/3/27 YuxiangQue
	 */
	void undoIntegration(Long customerFTLotId);

	/**
	 * 手动合批：当产品型号、客户ppo、版本型号一致时可合批，其余字段“/”相连
	 *
	 * @param customerFTLotIds 逗号相隔的source lots' customerFTLotId
	 * @return 调用结果：成功时成功标记，失败时包含错误信息
	 * @version 2016/1/15 Howad
	 * @version 2016/3/27 YuxiangQue
	 */
	void integrateManual(Long[] customerFTLotIds);

	/**
	 * 获取内部产品批号
	 *
	 * @param customerFTLotId 需要下单的客户批号
	 * @return 成功时返回批号，失败时返回错误信息
	 * @version 2016/1/16 Howad
	 * @version 2016/3/27 YuxiangQue
	 */
	String getExpectedLotNumber(Long customerFTLotId);

	/**
	 * 获取RC编号
	 *
	 * @param customerFTLotId 需要下单的客户批号
	 * @return 成功是返回批号，失败时返回错误信息
	 * @version 2016/1/13 Howad
	 * @version 2016/3/27 YuxiangQue
	 */
	String getExpectedRCNumber(Long customerFTLotId);

	/**
	 * 下单失败回滚WMS领料记录
	 * @param ft 测试类型
	 * @param testId  T_FTTEST表的ID
	 * @param testQTY 下单的数量
	 */
	void rollbackWMSTestInfo(String ft,String testId,Long testQTY);
	
	/**
	 * 删除已经下单的lot
	 * @param ftLot
	 */
	public void deleteOrder(FTLot ftLot);
}

