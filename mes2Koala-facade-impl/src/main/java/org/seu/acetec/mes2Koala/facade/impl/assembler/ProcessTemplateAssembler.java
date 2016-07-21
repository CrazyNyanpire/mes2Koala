package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class ProcessTemplateAssembler {

	public static ProcessTemplateDTO toDTO(ProcessTemplate processTemplate) {
		if (processTemplate == null) {
			return null;
		}
		ProcessTemplateDTO result = new ProcessTemplateDTO();
		result.setId(processTemplate.getId());
		result.setVersion(processTemplate.getVersion());
		result.setCreateEmployNo(processTemplate.getCreateEmployNo());
		result.setName(processTemplate.getName());
		result.setContent(processTemplate.getContent());
		result.setHandlerType(processTemplate.getHandlerType());
		result.setTestType(processTemplate.getTestType());
		result.setAllowState(processTemplate.getAllowState());
		result.setRuncard(processTemplate.getRuncard());
		result.setTestingTemplateDTOs(TestingTemplateAssembler
				.toDTOs(processTemplate.getTestingTemplates()));
		result.setAcetecAuthorizationDTOs(AcetecAuthorizationAssembler
				.toDTOs(processTemplate.getAcetecAuthorizations()));
		if (!"已签核".equals(result.getAllowState())) {
			String status = "";
			for (AcetecAuthorizationDTO acetecAuthorizationDTO : result
					.getAcetecAuthorizationDTOs()) {
				status += acetecAuthorizationDTO.getEmployeeDTO().getName()
						.concat(":");
				if (acetecAuthorizationDTO.getOpinion() == null) {
					status += "未签核".concat(";");
				} else {
					status += acetecAuthorizationDTO.getOpinion().concat(";");
				}
			}
			result.setAllowState(status);
		}

		result.setSpecialFormDTO(SpecialFormAssembler.toDTO(processTemplate
				.getSpecialForm()));

		if (processTemplate.getNote() != null) {
			Map<String, RuncardNote> note = processTemplate.getNote();
			Map<String, RuncardNoteDTO> noteDTO = new HashMap<String, RuncardNoteDTO>();
			for (Map.Entry<String, RuncardNote> entry : note.entrySet()) {
				noteDTO.put(entry.getKey(),
						RuncardNoteAssembler.toDTO(entry.getValue()));
			}
			result.setNoteDTO(noteDTO);
		}

		return result;
	}

	public static List<ProcessTemplateDTO> toDTOs(
			Collection<ProcessTemplate> processTemplates) {
		if (processTemplates == null) {
			return null;
		}
		List<ProcessTemplateDTO> results = new ArrayList<ProcessTemplateDTO>();
		for (ProcessTemplate each : processTemplates) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static ProcessTemplate toEntity(ProcessTemplateDTO processTemplateDTO) {
		if (processTemplateDTO == null) {
			return null;
		}
		ProcessTemplate result = new ProcessTemplate();
		result.setId(processTemplateDTO.getId());
		result.setVersion(processTemplateDTO.getVersion());
		result.setCreateEmployNo(processTemplateDTO.getCreateEmployNo());
		result.setName(processTemplateDTO.getName());
		result.setContent(processTemplateDTO.getContent());
		result.setHandlerType(processTemplateDTO.getHandlerType());
		result.setTestType(processTemplateDTO.getTestType());
		result.setAllowState(processTemplateDTO.getAllowState());
		result.setRuncard(processTemplateDTO.getRuncard());

		result.setAcetecAuthorizations(AcetecAuthorizationAssembler
				.toEntities(processTemplateDTO.getAcetecAuthorizationDTOs()));

		/*
		 * // for test Howard Map<String, RuncardNote> note = new
		 * HashMap<String, RuncardNote>();
		 * 
		 * RuncardNote runcardNoteIQC = new RuncardNote(); List<String>
		 * noteOfIQC = new ArrayList<String>(); noteOfIQC.add("注意事項(Notes):");
		 * noteOfIQC.add("1.产品烘烤后48H内进入铝箔袋真空包装"); noteOfIQC.add(
		 * "3.烘烤完成之后，核对铝箔袋标签、实物与流程单信息，保持三者一致。                                   出烤箱人员(Operator):"
		 * ); runcardNoteIQC.setNodeName("IQC");
		 * runcardNoteIQC.setNodeNote(noteOfIQC);
		 * note.put(runcardNoteIQC.getNodeName(),
		 * runcardNoteIQC);//RuncardNote中的nodeName虽然可以主动更新，但是为了防止覆盖需要赋值key
		 * 
		 * RuncardNote runcardNoteBaking = new RuncardNote(); List<String>
		 * noteOfBaking = new ArrayList<String>();
		 * noteOfBaking.add("注意事項(Notes):");
		 * noteOfBaking.add("1.产品烘烤后48H内进入铝箔袋真空包装");
		 * noteOfBaking.add("2.产品烘烤时，每个LOT 相对应的烤盘不能混淆"); noteOfBaking.add(
		 * "3.烘烤完成之后，核对铝箔袋标签、实物与流程单信息，保持三者一致。                                   出烤箱人员(Operator):"
		 * ); runcardNoteBaking.setNodeName("Baking");
		 * runcardNoteBaking.setNodeNote(noteOfBaking);
		 * note.put(runcardNoteBaking.getNodeName(), runcardNoteBaking);
		 * 
		 * result.setNote(note); //test end
		 */
		// LHB
		/*
		 * TestingTemplate testingTemplate = new TestingTemplate(); Long testId
		 * = processTemplateDTO.getTestId();
		 * if((processTemplateDTO.getTestingTemplateDTO()==null)&&(testId!=null)
		 * ){ testingTemplate.setId(processTemplateDTO.getTestId());
		 * result.setTestingTemplate(testingTemplate); }
		 */
		if (processTemplateDTO.getTestIds() != null
				&& processTemplateDTO.getTestIds().size() > 0
				&& processTemplateDTO.getTestIds().get(0) != null) {
			List<TestingTemplateDTO> tList = new ArrayList<TestingTemplateDTO>();
			for (int i = 0; i < processTemplateDTO.getTestIds().size(); i++) {
				TestingTemplateDTO tDTO = new TestingTemplateDTO();
				tDTO.setId(processTemplateDTO.getTestIds().get(i));
				tList.add(tDTO);
				result.setTestingTemplates(TestingTemplateAssembler
						.toEntities(tList));
			}
		}
		// LHB
		List<AcetecAuthorization> acetecAuthorizations = new ArrayList<AcetecAuthorization>();
		List<Long> authorizationIds = processTemplateDTO
				.getAcetecAuthorizationIds();
		if (authorizationIds.size() > 0) {
			// 新增
			for (Long each : authorizationIds) {
				AcetecAuthorization authorization = new AcetecAuthorization();
				authorization.setId(each);
				acetecAuthorizations.add(authorization);
			}
			result.setAcetecAuthorizations(acetecAuthorizations);
		}

		//
		result.setSpecialForm(SpecialFormAssembler.toEntity(processTemplateDTO
				.getSpecialFormDTO()));

		/*
		 * //设置note Howard //若processTemplateDTO中没有noteDTO则新建一实例 Map<String,
		 * RuncardNoteDTO> noteDTO = processTemplateDTO.getNoteDTO() == null ?
		 * new HashMap<String, RuncardNoteDTO>() :
		 * processTemplateDTO.getNoteDTO();
		 * 
		 * //拼装完整的process流程，用以检测是否要建立对应的RuncardNote String process =
		 * processTemplateDTO.getContent(); if (
		 * processTemplateDTO.getTestingTemplateDTO() != null ) process =
		 * process.replace("test", result.getTestingTemplate().getContent());
		 * 
		 * //新建可持久化的对象用以盛放新的note Map<String, RuncardNote> note = new
		 * HashMap<String, RuncardNote>();
		 * 
		 * List<String> flowOfFT = new ArrayList<String>( Arrays.asList("IQC",
		 * "Baking", "PeelForceTest", "FT", "LAT", "FVI", "Packing", "FQC",
		 * "OQC")); //寻找每个模板中Flow节点 for ( String flow : flowOfFT ){
		 * //如果该节点包括在process中，则需要存储 if ( process.contains(flow) ){
		 * //如果需要保存的节点已经存在，则复制到新建的note当中，否则新建 if ( noteDTO.containsKey(flow) ){
		 * note.put(flow, RuncardNoteAssembler.toEntity(noteDTO.get(flow))); }
		 * else { RuncardNote runcardNote = new RuncardNote();
		 * runcardNote.setNodeName(flow); runcardNote.setNodeNote(new
		 * ArrayList<String>()); note.put(flow, runcardNote); } } }
		 * result.setNote(note);
		 */

		return result;
	}

	public static List<ProcessTemplate> toEntities(
			Collection<ProcessTemplateDTO> processTemplateDTOs) {
		if (processTemplateDTOs == null) {
			return null;
		}

		List<ProcessTemplate> results = new ArrayList<ProcessTemplate>();
		for (ProcessTemplateDTO each : processTemplateDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
