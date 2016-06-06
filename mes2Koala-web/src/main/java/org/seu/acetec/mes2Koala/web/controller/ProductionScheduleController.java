package org.seu.acetec.mes2Koala.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.ProductionScheduleFacade;
import org.seu.acetec.mes2Koala.facade.dto.ProductionScheduleDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ProductionSchedule")
public class ProductionScheduleController {
		
	@Inject
	private ProductionScheduleFacade productionScheduleFacade;
	
	@ResponseBody
	@RequestMapping("/basicScheduling")
	public InvokeResult basicScheduling( @RequestParam Long id, @RequestParam Integer version, @RequestParam Long testSysId )
	{
		return productionScheduleFacade.basicScheduling( id, version, testSysId );
	}
	
	@ResponseBody
	@RequestMapping("/separate")
	public InvokeResult separate( @RequestParam Long id, @RequestParam String newLotNumber, @RequestParam Double percent, @RequestParam Long targetTestSysId )
	{
		if ( percent == null || percent > 100 || percent < 0 )
			return InvokeResult.failure("百分比超出范围");
		return productionScheduleFacade.separate( id, newLotNumber, percent, targetTestSysId );
	}
	
	@ResponseBody
	@RequestMapping("/updateState/{id}")
	public InvokeResult updateState( @PathVariable Long id, @RequestParam String state, String note ) {
		return productionScheduleFacade.updateState( id, state, note );
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(ProductionScheduleDTO productionScheduleDTO) {
		return productionScheduleFacade.creatProductionSchedule(productionScheduleDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(ProductionScheduleDTO productionScheduleDTO) {
		return productionScheduleFacade.updateProductionSchedule(productionScheduleDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ProductionScheduleDTO productionScheduleDTO, @RequestParam int page, @RequestParam int pagesize, String sortname, String sortorder) {
		productionScheduleFacade.updateAllTestingProduction();
		Page<ProductionScheduleDTO> all = productionScheduleFacade.pageQueryProductionSchedule(productionScheduleDTO, page, pagesize, sortname, sortorder);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson/{testType}")
	public Page pageJson(ProductionScheduleDTO productionScheduleDTO, @RequestParam int page, @RequestParam int pagesize, @PathVariable String testType ) {
		productionScheduleDTO.setTestType(testType);
		Page<ProductionScheduleDTO> all = productionScheduleFacade.pageQueryProductionSchedule(productionScheduleDTO, page, pagesize, null, null);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/pageJsonProductionsToBeScheduled")
	public Page pageJsonProductionsToBeScheduled( ProductionScheduleDTO productionScheduleDTO, @RequestParam int page, @RequestParam int pagesize ){
		Page<ProductionScheduleDTO> all = productionScheduleFacade.pageQueryProductionsWaitToBeScheduled( productionScheduleDTO, page, pagesize );
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String ids) {
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	        					idArrs[i] = Long.parseLong(value[i]);
						        }
        return productionScheduleFacade.removeProductionSchedules(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return productionScheduleFacade.getProductionSchedule(id);
	}
	
	@ResponseBody
	@RequestMapping("/arrangeProductionsInATestSys")
	public InvokeResult arrangeProductionsInATestSys( @RequestParam Long[] productionIds, @RequestParam Long testSysId ) {
		return productionScheduleFacade.arrangeProductionsInATestSys( productionIds, testSysId );
	}
	
	@ResponseBody
	@RequestMapping("/revokeProductionSchedules")
	public InvokeResult revokeProductionSchedules( @RequestParam Long[] ids ){
		return productionScheduleFacade.revokeProductionSchedules( ids );
	}
	
	@ResponseBody
	@RequestMapping("/reorderUp")
	public InvokeResult reorderUpper( @RequestParam Long id ){
		return productionScheduleFacade.reorderUp(id);
	}
	
	@ResponseBody
	@RequestMapping("/reorderDown")
	public InvokeResult reorderDown( @RequestParam Long id ) {
		return productionScheduleFacade.reorderDown(id);
	}
	
	@ResponseBody
	@RequestMapping("/moveToTop")
	public InvokeResult moveToTop( @RequestParam Long[] ids ) {
		return productionScheduleFacade.moveToTop(ids);
	}
	
	@ResponseBody
	@RequestMapping("/startTesting")
	public InvokeResult startTesting( @RequestParam Long id ) {
		return productionScheduleFacade.startTesting(id);
	}

	@ResponseBody
	@RequestMapping("/endTesting")
	public InvokeResult endTesting( @RequestParam Long id ) {
		return productionScheduleFacade.endTesting(id);
	}

    @InitBinder    
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
        //CustomDateEditor 可以换成自己定义的编辑器。  
        //注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
	
}
