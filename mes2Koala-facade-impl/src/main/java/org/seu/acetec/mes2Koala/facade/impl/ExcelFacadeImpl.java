package org.seu.acetec.mes2Koala.facade.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.koalacommons.resourceloader.util.StringUtils;
import org.seu.acetec.mes2Koala.application.BomTemplateApplication;
import org.seu.acetec.mes2Koala.application.FTInfoApplication;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.FTProcessApplication;
import org.seu.acetec.mes2Koala.application.ReelDiskApplication;
import org.seu.acetec.mes2Koala.core.domain.BomTemplate;
import org.seu.acetec.mes2Koala.core.domain.FTInfo;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTProcess;
import org.seu.acetec.mes2Koala.core.domain.ReelDisk;
import org.seu.acetec.mes2Koala.facade.BomTemplateFacade;
import org.seu.acetec.mes2Koala.facade.CustomerFacade;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.FTInfoFacade;
import org.seu.acetec.mes2Koala.facade.FTLotFacade;
import org.seu.acetec.mes2Koala.facade.LabelFacade;
import org.seu.acetec.mes2Koala.facade.ReworkFacade;
import org.seu.acetec.mes2Koala.facade.dto.BomTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.CustomerDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.InternalProductDTO;
import org.seu.acetec.mes2Koala.facade.dto.LabelDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReworkDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.FTInfoPageVo;
import org.seu.acetec.mes2Koala.facade.excelvo.BomTemplateVo;
import org.seu.acetec.mes2Koala.facade.excelvo.BomTemplateVoAssembler;
import org.seu.acetec.mes2Koala.facade.excelvo.CustomerVoAssembler;
import org.seu.acetec.mes2Koala.facade.excelvo.LabelVoAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTLotAssembler;
import org.seu.acetec.mes2Koala.infra.MyDateUtils;
import org.xml.sax.SAXException;

import help.ExcelTemplateHelper;
import help.FilenameHelper;

/**
 * @author yuxiangque
 * @version 2016/3/27
 */
@Named
public class ExcelFacadeImpl implements ExcelFacade {

	@Inject
	FTLotApplication ftLotApplication;
    @Inject
    BomTemplateApplication bomTemplateApplication;

    @Inject
    FTInfoApplication ftInfoApplication;

    @Inject
    ReelDiskApplication reelDiskApplication;
    
    @Inject
    FTProcessApplication ftProcessApplication;
    
    @Inject
    BomTemplateFacade bomTemplateFacade;

    @Inject
    FTLotFacade ftLotFacade;

    @Inject
    LabelFacade labelFacade;

    @Inject
    CustomerFacade customerFacade;

    @Inject
    ServletContext servletContext;
    
    @Inject
    FTInfoFacade ftInfoFacade;

    // 获取类文件所在的路径，为获取web应用路径做准备
    private String bathPath;

    private String templatePath;
    private String importPath;
    private String exportPath;

    // 导入配置文件
    private String bomTemplateImportConfigPath;
    private String reelCodeImportConfigPath;
    
    // 导出配置文件
    private String customerExportTemplatePath;
    private String bomTemplateExportTemplatePath;
    private String labelExportTemplatePath;
    private String ftInfoExportTemplatePath;
    
    @Inject
    private ReworkFacade reworkFacade;

    public ExcelFacadeImpl() {


    }

    private void setupPaths(Class<?> clazz) {
        bathPath = clazz.getClassLoader().getResource("/").getPath();
        bathPath = bathPath.substring(0, bathPath.indexOf("WEB-INF"));
        // bathPath = "D:/Projects/mes2_3_1/mes2Koala-web/target/mes2Koala-web-1.0.0-SNAPSHOT/";
        templatePath = bathPath + "/excel";
        importPath = templatePath + "/import";
        exportPath = templatePath + "/export";

        // 导入配置文件
        bomTemplateImportConfigPath = templatePath + '/' + "bomXmlConfig.xml";
        reelCodeImportConfigPath = templatePath + '/' + "reelCodeXmlConfig.xml";
        
        // 导出配置文件
        customerExportTemplatePath = templatePath + '/' + "Customer-Template.xlsx";
        bomTemplateExportTemplatePath = templatePath + '/' + "Bom-Template.xlsx";
        labelExportTemplatePath = templatePath + '/' + "Label-Template.xlsx";
        ftInfoExportTemplatePath = templatePath + '/' + "FTInfo-Template.xlsx";

        // 创建目录
        File file = new File(templatePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(importPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(exportPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public InvokeResult importBomTemplate(Class<?> clazz, String filename) {
        setupPaths(clazz);
        try (InputStream inputXmlConfig = new BufferedInputStream(
                new FileInputStream(bomTemplateImportConfigPath))) {
            try (InputStream inputXlsxStream = new BufferedInputStream(
					new FileInputStream(importPath + '/' + filename))) {
				List<Object> bomVoList = new ArrayList<>();
				HashMap<String, Set<String>> bomNumberMap = new HashMap<>(); // 用于去除重复料号的记录
				if (ExcelTemplateHelper.simpleTemplateImportExcel(
						inputXmlConfig, "bomTemplates", inputXlsxStream,
						bomVoList)) {
					int bomID = 1;// 每次导入序号从1开始递增
					for (Object bomVo : bomVoList) {
						BomTemplateDTO bomTemplateDTO = BomTemplateVoAssembler
								.toDTO((BomTemplateVo) bomVo);
						if (!bomTemplateFacade
								.fillBomDTOWithInternalProductID(bomTemplateDTO))
							return InvokeResult.failure("找不到对应的产品！");
						if (!isDuplicated(bomNumberMap, bomTemplateDTO)) {
							bomTemplateDTO.setBomId(Integer.toString(bomID++));
							InvokeResult result = bomTemplateFacade
									.createBomTemplate(bomTemplateDTO);
							if (result.isHasErrors())
								return result;
						}
					}
					return InvokeResult.success();
				}
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
		return InvokeResult.failure("");
	}

	/**
	 * 用于在Bom导入时判断是否料号重复
	 * 
	 * @param bomNumberMap
	 * @param bomTemplateDTO
	 * @return
	 */
	private boolean isDuplicated(HashMap<String, Set<String>> bomNumberMap,
			BomTemplateDTO bomTemplateDTO) {
		String tempKey = bomTemplateDTO.getModelNumber()
				+ bomTemplateDTO.getCustomerCode();
		if (!bomNumberMap.containsKey(tempKey)) {
			// 该产品未出现过，则添加并返回false
			Set<String> tempValue = new HashSet<>();
			tempValue.add(bomTemplateDTO.getNumber());
			bomNumberMap.put(tempKey, tempValue);
			return false;
		} else {
			// 该产品出现过，则判断该料号是否出现过
			Set<String> tempValue = bomNumberMap.get(tempKey);
			if (!tempValue.contains(bomTemplateDTO.getNumber())) {
				// 改料号未出现过，则添加返回false
				tempValue.add(bomTemplateDTO.getNumber());
				bomNumberMap.put(tempKey, tempValue);
				return false;
			} else {
				// 料号出现过，返回true
				return true;
			}
		}
	}

	@Override
	public InvokeResult exportBomTemplate(Class<?> clazz, Long[] bomTemplateIds) {
		setupPaths(clazz);
		// 获取类文件所在的路径，为获取web应用路径做准备
		// 获取模板路径与导出文件的路径

		String fileName = FilenameHelper.generateXlsxFilename("bom_export");

		List<BomTemplateVo> bomVoArray = new ArrayList<BomTemplateVo>();
		for (Long bomTemplateId : bomTemplateIds) { // TODO 优化性能
			BomTemplate bomTemplate = bomTemplateApplication.get(bomTemplateId);
			bomVoArray.add(BomTemplateVoAssembler.toVo(bomTemplate));
		}
		try (InputStream is = new FileInputStream(bomTemplateExportTemplatePath)) {
			try (OutputStream os = new FileOutputStream(exportPath + '/'
					+ fileName)) {
				ExcelTemplateHelper.simpleTemplateExportExcel(is, "boms",
						(List) bomVoArray, "Sheet1!A1", os);
				return InvokeResult.success("excel/export/" + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}

	@Override
	public InvokeResult exportLabel(Class<?> clazz, Long[] idArray) {
		setupPaths(clazz);

		// 获取模板路径与导出文件的路径
		String fileName = FilenameHelper.generateXlsxFilename("label_export");

		List<Object> labelVoList = new ArrayList<Object>();
		for (int i = 0; i < idArray.length; i++) { // TODO 优化性能
			LabelDTO labelDTO = (LabelDTO) labelFacade.getLabel(idArray[i])
					.getData();
			labelVoList.add(LabelVoAssembler.toVo(labelDTO));
		}
		try (InputStream is = new FileInputStream(labelExportTemplatePath)) {
			try (OutputStream os = new FileOutputStream(exportPath + '/'
					+ fileName)) {
				ExcelTemplateHelper.simpleTemplateExportExcel(is, "labels",
						labelVoList, "Sheet1!A1", os);
				return InvokeResult.success("excel/export/" + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}

	@Override
	public InvokeResult exportFTLot(Class<?> clazz, Long[] idArray) {
		setupPaths(clazz);

		// 获取模板路径与导出文件的路径
		String fileName = FilenameHelper.generateXlsxFilename("ftTest_export");

		List ftPageDTOs;
		try {
			ftPageDTOs = ftLotFacade.getFTPageDTOs(idArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return InvokeResult.failure("无法获取批次信息。" + e.getMessage());
		}
		try (InputStream is = new FileInputStream(templatePath + '/'
				+ "FTPage-Template.xlsx")) {
			try (OutputStream os = new FileOutputStream(exportPath + '/'
					+ fileName)) {
				ExcelTemplateHelper.simpleTemplateExportExcel(is, "ftLots",
						ftPageDTOs, "Sheet1!A1", os);
				return InvokeResult.success("excel/export/" + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}

	@Override
	public InvokeResult exportCustomer(Class<?> clazz, Long[] idArray) {
		setupPaths(clazz);
		// 获取类文件所在的路径，为获取web应用路径做准备
		// 获取模板路径与导出文件的路径
		String fileName = FilenameHelper
				.generateXlsxFilename("customer_export");

		List<Object> customerVoList = new ArrayList<Object>();
		for (int i = 0; i < idArray.length; i++) { // TODO 优化性能
			CustomerDTO customerDTO = (CustomerDTO) customerFacade.getCustomer(
					idArray[i]).getData();
			customerVoList.add(CustomerVoAssembler.toVo(customerDTO));
		}
		try (InputStream is = new FileInputStream(customerExportTemplatePath)) {
			try (OutputStream os = new FileOutputStream(exportPath + '/'
					+ fileName)) {
				ExcelTemplateHelper.simpleTemplateExportExcel(is, "customers",
						customerVoList, "Sheet1!A1", os);
				return InvokeResult.success("excel/export/" + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}

	@Override
	public InvokeResult exportCPLot(Class<?> clazz, Long[] idArray) {
		setupPaths(clazz);
		throw new UnsupportedOperationException("");
	}

	@Override
	public List<ReelDiskDTO> importReelCode(Long ftLotId, String filename) {
		if (ftLotId == null || filename == null)
			return null;

		return (List<ReelDiskDTO>) importReelCodeImpl(this.getClass(),
				filename, ftLotId).getData();
	}

	@Override
	public InvokeResult importReelCodeImpl(Class<?> clazz, String filename,
			Long ftLotId) {
		FTLot ftLot = ftLotApplication.get(ftLotId);
		FTLotDTO ftLotDTO = FTLotAssembler.toDTO(ftLot);

		// 用于校验导入的Reelcode是否正确
		String reelcodePrefix = ftLot.getFtInfo().getReelFixCode();
		String partNumber = ftLot.getCustomerProductNumber();
		String datecode = ftLot.getCustomerFTLot().getDateCode();
		String internalLotNumber = ftLot.getInternalLotNumber();

		setupPaths(clazz);
		try (InputStream inputXmlConfig = new BufferedInputStream(
				new FileInputStream(reelCodeImportConfigPath))) {
			try (InputStream inputXlsxStream = new BufferedInputStream(
					new FileInputStream(importPath + '/' + filename))) {
				List reelDiskDTOs = new ArrayList<>();
				if (ExcelTemplateHelper.simpleTemplateImportExcel(
						inputXmlConfig, "reelDisks", inputXlsxStream,
						reelDiskDTOs)) {
					for (Object reelDiskDTO : reelDiskDTOs) {
						ReelDiskDTO dto = (ReelDiskDTO) reelDiskDTO;
						if (!dto.getReelCode().startsWith(reelcodePrefix))
							return InvokeResult.failure("导入的Reelcode前缀错误");
						if (!dto.getPartNumber().equals(partNumber))
							return InvokeResult
									.failure("导入的ReelcodePartNumer错误");
						if (!dto.getDateCode().equals(datecode))
							return InvokeResult
									.failure("导入的ReelcodeDateCode错误");
						if (!dto.getImportedLotNumber().equals(
								internalLotNumber))
							return InvokeResult
									.failure("导入的Reelcode艾科批号与目标批次艾科批号不符");
						dto.setFtLotDTO(ftLotDTO);
						dto.setLogic(0);
					}
					return InvokeResult.success(reelDiskDTOs);
				}
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
		return InvokeResult.failure("");
	}

	@Override
	public InvokeResult exportFTInfo(Class<?> clazz, Long[] idArray) {
		setupPaths(clazz);
		// 获取类文件所在的路径，为获取web应用路径做准备
		// 获取模板路径与导出文件的路径
		String fileName = FilenameHelper.generateXlsxFilename("ftInfo_export");

		List<Object> ftInfoList = new ArrayList<Object>();
		for (int i = 0; i < idArray.length; i++) { // TODO 优化性能

			FTInfoPageVo ftInfoPageVo = (FTInfoPageVo) ftInfoFacade
					.getFTInfoPageVo(idArray[i]).getData();
			ftInfoList.add(ftInfoPageVo);
		}
		try (InputStream is = new FileInputStream(ftInfoExportTemplatePath)) {
			try (OutputStream os = new FileOutputStream(exportPath + '/'
					+ fileName)) {
				ExcelTemplateHelper.simpleTemplateExportExcel(is, "ftInfos",
						ftInfoList, "Sheet1!A1", os);
				return InvokeResult.success("excel/export/" + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}

	public InvokeResult exportReworkLot(ReworkDTO reworkDTO) {
		this.setupPaths(this.getClass());
		// 获取模板路径与导出文件的路径
		String fileName = FilenameHelper.generateXlsxFilename("Rework_export");

		Page<ReworkDTO> reworkPageDTOs;
		try {
			reworkPageDTOs = reworkFacade.pageQueryRework(reworkDTO, 0, 10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return InvokeResult.failure("无法获取重工信息。" + e.getMessage());
		}
		List<Object> reworkList = new ArrayList<Object>();
		for (ReworkDTO reworkTempDTO : reworkPageDTOs.getData()) {
			JSONArray jsonArray = JSONArray.fromObject(reworkTempDTO
					.getApprovePerson());
			List<String> statusList = new ArrayList<String>();
			for (Object object : jsonArray.toArray()) {
				JSONObject jsonObject = JSONObject.fromObject(object);
				reworkTempDTO.setStatus(jsonObject.getString("name")
						.concat(":"));
				if (jsonObject.get("status") != null
						&& "true".equalsIgnoreCase(jsonObject
								.getString("status"))) {
					reworkTempDTO.setStatus(reworkTempDTO.getStatus().concat(
							"通过"));
				} else {
					reworkTempDTO.setStatus(reworkTempDTO.getStatus().concat(
							"未通过"));
				}
				reworkTempDTO.setReworkDateStr(MyDateUtils.formaterDate(
						reworkTempDTO.getReworkDate(),
						MyDateUtils.DefFormatString));
				statusList.add(reworkTempDTO.getStatus());
			}
			reworkTempDTO.setStatus(StringUtils.collectionToDelimitedString(
					statusList, ","));
			reworkList.add(reworkTempDTO);
		}

		try (InputStream is = new FileInputStream(templatePath + '/'
				+ "Rework-Template.xlsx")) {
			try (OutputStream os = new FileOutputStream(exportPath + '/'
					+ fileName)) {
				ExcelTemplateHelper.simpleTemplateExportExcel(is, "reworkDTOs",
						reworkList, "Sheet1!A1", os);
				return InvokeResult.success("excel/export/" + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}
}
