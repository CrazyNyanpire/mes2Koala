package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.mail.search.SentDateTerm;

import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.TestSysApplication;
import org.seu.acetec.mes2Koala.core.domain.CPProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.ProductionSchedule;
import org.seu.acetec.mes2Koala.facade.dto.ProductionScheduleDTO;
import org.seu.acetec.mes2Koala.infra.MyDateUtils;

/**
 * @author Administrator
 *
 */
public class ProductionScheduleAssembler {
	
	@Inject
	private static TestSysApplication testSysApplication;
	
	@Inject
	private static ProductionScheduleApplication productionScheduleApplication;
	
	public static ProductionScheduleDTO  toDTO(ProductionSchedule  productionSchedule){
		if (productionSchedule == null) {
			return null;
		}
    	ProductionScheduleDTO result  = new ProductionScheduleDTO();
	    	result.setId (productionSchedule.getId());
     	    	result.setVersion (productionSchedule.getVersion());
/*     	    	result.setCreateTimestamp (productionSchedule.getCreateTimestamp());
     	    	result.setLastModifyTimestamp (productionSchedule.getLastModifyTimestamp());
     	    	result.setCreateEmployNo (productionSchedule.getCreateEmployNo());
     	    	result.setLastModifyEmployNo (productionSchedule.getLastModifyEmployNo());
     	    	result.setLogic (productionSchedule.getLogic());*/

     	    	result.setPlanedStartTimestamp (productionSchedule.getPlanedStartTimestamp() == null ? "" : getScheduleDate( productionSchedule.getPlanedStartTimestamp()) );
     	    	result.setPlanedEndTimestamp (productionSchedule.getPlanedEndTimestamp() == null ? "" : getScheduleDate( productionSchedule.getPlanedEndTimestamp() ));
     	    	result.setActualStartTimestamp (productionSchedule.getActualStartTimestamp() == null ? "" : getScheduleDate( productionSchedule.getActualStartTimestamp() ) );
     	    	result.setActualEndTimestamp (productionSchedule.getActualEndTimestamp() == null ? "" : getScheduleDate( productionSchedule.getActualEndTimestamp() ) );

     	    	result.setState (productionSchedule.getState());
     	    	result.setSubState(productionSchedule.getSubState());
     	    	result.setAmount (productionSchedule.getAmount());
     	    	result.setDoneQty (productionSchedule.getDoneQty());
     	    	result.setNote (productionSchedule.getNote());
     	    	result.setLotNumber (productionSchedule.getLotNumber());
     	    	result.setNodeName(productionSchedule.getNodeName());
     	    	if ( productionSchedule instanceof FTProductionSchedule ) {
     	    		result.setTestType("FT");
     	    		try {
						result.setLotCurrentState(
								((FTProductionSchedule) productionSchedule).getFtLot().getCurrentState());
						result.setLotHoldState(
								((FTProductionSchedule) productionSchedule).getFtLot().getHoldState());
					} catch (NullPointerException e) {
						result.setLotCurrentState("无法获取");
						result.setLotHoldState("无法获取");
					}
     	    	} else if ( productionSchedule instanceof CPProductionSchedule ){
     	    		result.setTestType("CP");
     	    		try {
						result.setLotCurrentState(
								((CPProductionSchedule) productionSchedule).getCpLot().getCurrentState());
						result.setLotHoldState(
								((CPProductionSchedule) productionSchedule).getCpLot().getHoldState());
					} catch (NullPointerException e) {
						result.setLotCurrentState("无法获取");
						result.setLotHoldState("无法获取");
					}
     	    	} else {
     	    		result.setTestType(null);
     	    	}

     	    	try {
					result.setTestSysId(productionSchedule.getTestSys().getId());
					result.setTestSysName(productionSchedule.getTestSys().getPlatformNumber());
				} catch (NullPointerException e) {
					result.setTestSysId(null);
					result.setTestSysName(null);
				}
				result.setpPO(productionSchedule.getpPO() );
     	    	result.setCustomerProductNumber(productionSchedule.getCustomerProductNumber());
     	    	result.setPackageNumber(productionSchedule.getPackageNumber());
     	    	result.setCustomerLotNumber(productionSchedule.getCustomerLotNumber());

     	    	//预计需要时间、实际需要时间，保留两位小数
     	    	DecimalFormat df = new DecimalFormat("0.00");
     	    	result.setPlannedTimeTakes(df.format(productionSchedule.getPlannedTimeTakes()));
     	    	result.setActualTimeTakes(productionSchedule.getActualTimeTakes() == null ? "0" : df.format( productionSchedule.getActualTimeTakes() ));
     	    	
     	    	
     	    return result;
	 }
	
	public static List<ProductionScheduleDTO>  toDTOs(Collection<ProductionSchedule>  productionSchedules){
		if (productionSchedules == null) {
			return null;
		}
		List<ProductionScheduleDTO> results = new ArrayList<ProductionScheduleDTO>();
		for (ProductionSchedule each : productionSchedules) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	/**
	 * 调用者只应在修改时调用此方法
	 * 调用者需要保证testSys的ID不为空
	 * @param productionScheduleDTO
	 * @return
	 * @deprecated
	 */
	 public static ProductionSchedule  toEntity(ProductionScheduleDTO  productionScheduleDTO){
	 	if (productionScheduleDTO == null) {
			return null;
		}
	 	ProductionSchedule result  = new ProductionSchedule();
	 	if ( productionScheduleDTO.getId() != null )
			result = productionScheduleApplication.get(productionScheduleDTO.getId());
		 result.setVersion (productionScheduleDTO.getVersion());
/*         result.setCreateTimestamp (productionScheduleDTO.getCreateTimestamp());
         result.setLastModifyTimestamp (productionScheduleDTO.getLastModifyTimestamp());
         result.setCreateEmployNo (productionScheduleDTO.getCreateEmployNo());
         result.setLastModifyEmployNo (productionScheduleDTO.getLastModifyEmployNo());
         result.setLogic (productionScheduleDTO.getLogic());*/
         
         result.setPlanedStartTimestamp( setScheduleDate(productionScheduleDTO.getPlanedStartTimestamp()) );
//         result.setPlanedEndTimestamp( MyDateUtils.addHours(setScheduleDate(productionScheduleDTO.getPlanedStartTimestamp()), 10));
/*         result.setActualStartTimestamp ( setScheduleDate(productionScheduleDTO.getActualStartTimestamp()));
         result.setActualEndTimestamp ( setScheduleDate(productionScheduleDTO.getActualEndTimestamp()));*/
         
         result.setState (productionScheduleDTO.getState());
         result.setAmount (productionScheduleDTO.getAmount());
         result.setDoneQty (productionScheduleDTO.getDoneQty());
         result.setNote (productionScheduleDTO.getNote());
         result.setLotNumber (productionScheduleDTO.getLotNumber());
         
         if ( productionScheduleDTO.getTestSysId() != null )
			 result.setTestSys(testSysApplication.get(productionScheduleDTO.getTestSysId()));

		 return result;
	 }
	
	public static List<ProductionSchedule> toEntities(Collection<ProductionScheduleDTO> productionScheduleDTOs) {
		if (productionScheduleDTOs == null) {
			return null;
		}
		
		List<ProductionSchedule> results = new ArrayList<ProductionSchedule>();
		for (ProductionScheduleDTO each : productionScheduleDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
	
	private static Date setScheduleDate( String dateString ) {
		if ( dateString == null )
			return null;
		else
			return MyDateUtils.str2Date(dateString, MyDateUtils.DefFormatProductionScheduleString);
	}
	
	private static String getScheduleDate( Date date ) {
		if ( date == null )
			return null;
		else
			return MyDateUtils.formaterDate(date, MyDateUtils.DefFormatProductionScheduleString);
	}
}
