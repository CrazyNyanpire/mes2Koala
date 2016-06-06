package org.seu.acetec.mes2Koala.facade.excelvo;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.seu.acetec.mes2Koala.facade.dto.ProcessTemplateDTO;

public class RuncardVoAssembler {
	
	public static RuncardVo toVo(ProcessTemplateDTO processTemplateDTO) {
		if ( processTemplateDTO == null )
			return null;
		RuncardVo runcardVo = new RuncardVo();
		String process = processTemplateDTO.getContent();
/*		if ( processTemplateDTO.getTestingTemplateDTO() != null )
			process = process.replace("test", processTemplateDTO.getTestingTemplateDTO().getContent());*/
		if ( process.contains("IQC")){
			runcardVo.setHasIQC(true);
			runcardVo.setNoteOfIQC(processTemplateDTO.getNoteDTO().get("IQC").getNodeNote());
		}
		if ( process.contains("Baking") ){
			runcardVo.setHasBaking(true);
			runcardVo.setNoteOfBaking(processTemplateDTO.getNoteDTO().get("Baking").getNodeNote());
		}
		if ( process.contains("PeelForceTest") ){
			runcardVo.setHasPeelForceTest(true);
			runcardVo.setNoteOfPeelForceTest(processTemplateDTO.getNoteDTO().get("PeelForceTest").getNodeNote());
		}
		if ( process.contains("LAT") ){
			runcardVo.setHasLAT(true);
			runcardVo.setNoteOfLAT(processTemplateDTO.getNoteDTO().get("LAT").getNodeNote());
		}
		if ( process.contains("FVI") ){
			runcardVo.setHasFVI(true);
			runcardVo.setNoteOfFVI(processTemplateDTO.getNoteDTO().get("FVI").getNodeNote());
		}
		if ( process.contains("Packing") ){
			runcardVo.setHasPacking(true);
			runcardVo.setNoteOfPacking(processTemplateDTO.getNoteDTO().get("Packing").getNodeNote());
		}
		if ( process.contains("FQC") ){
			runcardVo.setHasFQC(true);
			runcardVo.setNoteOfFQC(processTemplateDTO.getNoteDTO().get("FQC").getNodeNote());
		}
		if ( process.contains("OQC") ){
			runcardVo.setHasOQC(true);
			runcardVo.setNoteOfOQC(processTemplateDTO.getNoteDTO().get("OQC").getNodeNote());
		}

		/***** 以下为测试用例，暂无在前端页面修改的途径  *****/
		runcardVo.setNoteOfTesting(new ArrayList<String>(){
			{
				add("Testing注意事项。");
				add("Testing注意事项。");
				add("Testing注意事项。");
				add("Testing注意事项。");
				add("Testing注意事项。");
				add("Testing注意事项。");
			}
		});
		runcardVo.setParamCheckTitle("NX16参数");

		List<FTParamCheckCols> ftParamCheckCols = new ArrayList<FTParamCheckCols>();
		ftParamCheckCols.add(new FTParamCheckCols("vision 5s length", "disable"));
		ftParamCheckCols.add(new FTParamCheckCols("tap leader length", "52"));
		ftParamCheckCols.add(new FTParamCheckCols("vision 5s length", "disable"));
		ftParamCheckCols.add(new FTParamCheckCols("tap leader length", "52"));
		ftParamCheckCols.add(new FTParamCheckCols("vision 5s length", "disable"));
		ftParamCheckCols.add(new FTParamCheckCols("tap leader length", "52"));
		runcardVo.setFtParamCheckCols(ftParamCheckCols);
		
		//以下为针对BGA的测试
		runcardVo.setTesterSetting(new ArrayList<String>(){
			{
				add("testerSetting1");
				add("testerSetting2");
				add("testerSetting3");
				add("testerSetting4");
			}
		});
		runcardVo.setHandlerSetting(new ArrayList<String>(){
			{
				add("handlerSetting1");
				add("handlerSetting2");
				add("handlerSetting3");
				add("handlerSetting4");
			}
		});
		List<BGABinAssignCols> bgaBinAssignCols = new ArrayList<BGABinAssignCols>();
		bgaBinAssignCols.add(new BGABinAssignCols("col11", "col12", "col12"));
		bgaBinAssignCols.add(new BGABinAssignCols("col21", "col22", "col22"));
		bgaBinAssignCols.add(new BGABinAssignCols("col31", "col32", "col32"));
		bgaBinAssignCols.add(new BGABinAssignCols("col41", "col42", "col42"));
		runcardVo.setBgaBinAssignCols(bgaBinAssignCols);

		runcardVo.setHasEQC(true);
		runcardVo.setNoteOfEQC(new ArrayList<String>(){
			{
				add("EQC注意事项");
			}
		});

		runcardVo.setHasOQC1(true);
		runcardVo.setNoteOfOQC1(new ArrayList<String>(){
			{
				add("OQC1注意事项");
			}
		});

		runcardVo.setHasOQC2(true);
		runcardVo.setNoteOfOQC2(new ArrayList<String>(){
			{
				add("OQC2注意事项");
			}
		});
		
		runcardVo.setNoteOfFT(new ArrayList<String>(){
			{
				add("FT注意事项");
			}
		});
		runcardVo.setHoldRules(new ArrayList<String>(){
			{
				add("Hold机制");
			}
		});
		
		/***** 测试用例结束 ************************/
		
/*		List<String> noteOfBaking = new ArrayList<String>();
        noteOfBaking.add("注意事項(Notes):\n");
        noteOfBaking.add("1.产品烘烤后48H内进入铝箔袋真空包装\n");
        noteOfBaking.add("2.产品烘烤时，每个LOT 相对应的烤盘不能混淆\n");
        noteOfBaking.add("3.烘烤完成之后，核对铝箔袋标签、实物与流程单信息，保持三者一致。                                   出烤箱人员(Operator):\n");
        runcardVo.setNoteOfBaking(noteOfBaking);*/
        
        Set<String> specialForms = new HashSet<String>(); 
        specialForms.add("首页");
        specialForms.add("Flow");
        if ( processTemplateDTO.getSpecialFormDTO() != null ){
	        if ( processTemplateDTO.getSpecialFormDTO().getSummaryForm() == true )
	            specialForms.add("Summary");
	        if ( processTemplateDTO.getSpecialFormDTO().getSizeForm() == true )
	            specialForms.add("记录表");
	        if ( processTemplateDTO.getSpecialFormDTO().getReelcodeForm() == true )
	            specialForms.add("ReelCode");
	        if ( processTemplateDTO.getSpecialFormDTO().getLossForm() == true )
	            specialForms.add("机台落料记录表");
	        if ( processTemplateDTO.getSpecialFormDTO().getCheckSelfForm() == true )
	            specialForms.add("自主检查表");
	        if ( processTemplateDTO.getSpecialFormDTO().getCheckBoxForm() == true )
	            specialForms.add("CheckBox");
        }
        runcardVo.setSpecialForms(specialForms);
        
		return runcardVo;
	}

}
