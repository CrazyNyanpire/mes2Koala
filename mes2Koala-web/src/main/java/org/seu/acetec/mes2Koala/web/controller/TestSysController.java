package org.seu.acetec.mes2Koala.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.TestSysFacade;
import org.seu.acetec.mes2Koala.facade.dto.TestSysDTO;
import org.seu.acetec.mes2Koala.facade.ganttvo.RowVo;
import org.seu.acetec.mes2Koala.facade.ganttvo.TaskVo;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/TestSys")
public class TestSysController {

	@Inject
	private TestSysFacade testSysFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(TestSysDTO testSysDTO) {
		return testSysFacade.creatTestSys(testSysDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(TestSysDTO testSysDTO) {
		return testSysFacade.updateTestSys(testSysDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(TestSysDTO testSysDTO, @RequestParam int page, @RequestParam int pagesize) {
//		testSysFacade.findAllTestSys();
		Page<TestSysDTO> all = testSysFacade.pageQueryTestSys(testSysDTO, page, pagesize);
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
        return testSysFacade.removeTestSyss(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return testSysFacade.getTestSys(id);
	}
	
	@ResponseBody
	@RequestMapping("/getTestSyses/{testType}")
	public List<TestSysDTO> getAll( @PathVariable String testType ){
		return testSysFacade.findAllTestSys( testType );
	}
	
	@ResponseBody
	@RequestMapping("/getSchedule")
	public InvokeResult getSchedule(){
//		String res = "[{\"name\":\"机台设备1\",\"tasks\":[{\"id\":\"lot1\",\"name\":\"批次 #1\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-14T16:00:00.000Z\",\"to\":\"2013-10-24T16:00:00.000Z\"},{\"name\":\"批次 #2\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-31T16:00:00.000Z\",\"to\":\"2013-11-10T16:00:00.000Z\",\"dependencies\":[{\"from\":\"lot1\"}],\"id\":\"7c4adc27-5e6e-8710-b764-5e8cda2e87ca\"}],\"id\":\"4b847c74-6902-7a75-f69c-d7409402dec6\"},{\"name\":\"机台设备2\",\"tasks\":[{\"name\":\"批次 #1\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-14T16:00:00.000Z\",\"to\":\"2013-10-29T16:00:00.000Z\",\"id\":\"eca29912-3fcc-eafa-7083-382737267f0e\"},{\"name\":\"批次 #2\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-31T16:00:00.000Z\",\"to\":\"2013-11-04T16:00:00.000Z\",\"id\":\"15ae9c5b-8186-695e-22bb-233c076b865e\"}],\"id\":\"b65f98db-dad4-e0f2-96b5-8c4dae320610\"},{\"name\":\"机台设备3\",\"tasks\":[{\"name\":\"批次 #1\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-08T16:00:00.000Z\",\"to\":\"2013-10-17T16:00:00.000Z\",\"id\":\"18273465-e401-b9fc-9c0e-3729710c38ed\"},{\"name\":\"批次 #2\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-21T16:00:00.000Z\",\"to\":\"2013-10-31T16:00:00.000Z\",\"id\":\"6c52d8f1-2884-3281-f52d-6f2186dd83b9\"}],\"id\":\"fdafcdc5-e160-3ebf-fe36-5df48c55d967\"},{\"name\":\"机台设备4\",\"tasks\":[{\"name\":\"批次 #1\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-14T16:00:00.000Z\",\"to\":\"2013-10-29T16:00:00.000Z\",\"id\":\"54ed00fa-cc68-4bad-e08b-b6c4dadffa74\"},{\"name\":\"批次 #2\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-31T16:00:00.000Z\",\"to\":\"2013-11-04T16:00:00.000Z\",\"id\":\"4efa888f-7d34-02c7-6150-10164d78a6f4\"}],\"id\":\"5db34a8d-748d-5936-e7f5-efb4ffe056b5\"},{\"name\":\"机台设备5\",\"tasks\":[{\"name\":\"批次 #1\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-14T16:00:00.000Z\",\"to\":\"2013-10-29T16:00:00.000Z\",\"id\":\"f92f9a1d-b577-ee0f-8f47-3d4710dae9b0\"},{\"name\":\"批次 #2\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-31T16:00:00.000Z\",\"to\":\"2013-11-04T16:00:00.000Z\",\"id\":\"6062f44a-30e9-6b66-0949-e19b7b516f98\"}],\"id\":\"040d527a-175b-2d7a-17bd-764ba6098964\"},{\"name\":\"机台设备6\",\"tasks\":[{\"name\":\"批次 #1\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-14T16:00:00.000Z\",\"to\":\"2013-10-29T16:00:00.000Z\",\"id\":\"3a8694a4-de75-e6c6-990c-91e4ae108cbd\"},{\"name\":\"批次 #2\",\"color\":\"#9FC5F8\",\"from\":\"2013-10-31T16:00:00.000Z\",\"to\":\"2013-11-04T16:00:00.000Z\",\"id\":\"ef250742-50b0-64b6-b975-d469ce7e6af7\"}],\"id\":\"92fa25d7-2072-f3bf-5d6a-0f20b52cdde3\"}]";
//		String res = "[{\"name\":\"TestSys1\",\"tasks\":[{\"name\":\"LotNumber\",\"from\":\"2016-01-21T11:42:36.126Z\",\"to\":\"2016-01-21T23:42:36.126Z\",\"actualFrom\":\"2016-01-21T11:42:36.126Z\",\"actualTo\":\"2016-01-21T23:42:36.126Z\",\"amount\":10000,\"doneQty\":3000,\"site\":\"site\",\"note\":\"note\",\"state\":1}]},{\"name\":\"TestSys1\",\"tasks\":[{\"name\":\"LotNumber\",\"from\":\"2016-01-21T11:42:36.126Z\",\"to\":\"2016-01-21T23:42:36.126Z\",\"id\":1,\"actualFrom\":\"2016-01-21T11:42:36.126Z\",\"actualTo\":\"2016-01-21T23:42:36.126Z\",\"amount\":10000,\"doneQty\":3000,\"site\":\"site\",\"note\":\"note\",\"state\":1}]}]";
/*		final ProductionSchedule productionSchedule = new ProductionSchedule();
		productionSchedule.setId((long) 1);
		productionSchedule.setAmount((long) 10000);
		productionSchedule.setDoneQty((long) 3000); 
		productionSchedule.setLotNumber("LotNumber");
		productionSchedule.setState(1);
		productionSchedule.setNote("note");
		productionSchedule.setNodeName("site");
		

		List<RowVo> schedule = new ArrayList<RowVo>();
		RowVo rowVo = new RowVo();
		rowVo.setId("1");
		rowVo.setName("TestSys1");
		rowVo.setTasks(new ArrayList<TaskVo>(){
			{
				add(TaskVoAssembler.toVo(productionSchedule));
			}
		});
		schedule.add(rowVo);*/
		List<RowVo> schedule = testSysFacade.findAllGanttRows();
//		List<TestSysDTO> schedule = testSysFacade.findAllTestSys();

		return InvokeResult.success(schedule);
//		return InvokeResult.success(res);
	}
	
	@ResponseBody
	@RequestMapping("/updateSchedule")
	public InvokeResult updateSchedule(@RequestBody String json){
		JSONArray jsonArray = JSONArray.fromObject(json);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(RowVo.class);
		
		jsonConfig.setClassMap(new HashMap<String, Class>(){
			{
				put("tasks", TaskVo.class);
			}
		});
		List<RowVo> schedule = (List<RowVo>) JSONArray.toCollection(jsonArray, jsonConfig);

		testSysFacade.updateTestSyss(schedule);

		return InvokeResult.success(json);
	}
	
    @InitBinder    
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
        //CustomDateEditor 可以换成自己定义的编辑器。  
        //注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
	
}
