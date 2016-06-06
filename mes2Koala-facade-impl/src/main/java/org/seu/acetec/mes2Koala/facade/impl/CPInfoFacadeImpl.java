package org.seu.acetec.mes2Koala.facade.impl;

import org.apache.poi.hssf.usermodel.*;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.*;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.facade.CPInfoFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.LabelDTO;
import org.seu.acetec.mes2Koala.facade.dto.RawDataDTO;
import org.seu.acetec.mes2Koala.facade.dto.SBLTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.CPInfoPageVo;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPInfoAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.LabelAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.RawDataAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.SBLTemplateAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 阙宇翔
 * @version 2016/2/13
 */
@Named
public class CPInfoFacadeImpl implements CPInfoFacade {

    @Inject
    SBLTemplateApplication sblTemplateApplication;
    @Inject
    private CPInfoApplication cpInfoApplication;
    @Inject
    private ProcessTemplateApplication processTemplateApplication;
    @Inject
    private LabelApplication labelApplication;
    @Inject
    private CPRuncardTemplateApplication cpRuncardTemplateApplication;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    @Override
    public InvokeResult getCPInfo(Long id) {
        return InvokeResult.success(CPInfoAssembler.toDTO(cpInfoApplication.get(id)));
    }

    @Override
    public InvokeResult createCPInfo(CPInfoDTO cpInfoDTO) {
        cpInfoApplication.create(CPInfoAssembler.toEntity(cpInfoDTO));
        return InvokeResult.success();
    }

    @Override
    public InvokeResult updateCPInfo(CPInfoDTO cpInfoDTO) {
        cpInfoApplication.update(CPInfoAssembler.toEntity(cpInfoDTO));
        return InvokeResult.success();
    }

    @Override
    public InvokeResult removeCPInfo(Long id) {
        cpInfoApplication.remove(cpInfoApplication.get(id));
        return InvokeResult.success();
    }

    @Override
    public InvokeResult removeCPInfos(Long[] ids) {
        Set<CPInfo> cpInfos = new HashSet<>();
        for (Long id : ids) {
            cpInfos.add(cpInfoApplication.get(id));
        }
        cpInfoApplication.removeAll(cpInfos);
        return InvokeResult.success();
    }

    @Override
    public List<CPInfoDTO> findAllCPInfo() {
        return CPInfoAssembler.toDTOs(cpInfoApplication.findAll());
    }

    @Override
    public Page<CPInfoPageVo> pageQueryCPInfo(CPInfoDTO queryVo, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _cpInfo from CPInfo _cpInfo  where 1=1 ");
        if (queryVo.getCustomerDirectDTO() != null && queryVo.getCustomerDirectDTO().getNumber() != null && !"".equals(queryVo.getCustomerDirectDTO().getNumber())) {
            jpql.append(" and _cpInfo.customerDirect.number like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerDirectDTO().getNumber()));
        }
        if (queryVo.getCustomerIndirectDTO() != null && queryVo.getCustomerIndirectDTO().getNumber() != null && !"".equals(queryVo.getCustomerDirectDTO().getNumber())) {
            jpql.append(" and _cpInfo.customerIndirect.number like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerIndirectDTO().getNumber()));
        }
        if (queryVo.getCustomerProductNumber() != null && !"".equals(queryVo.getCustomerProductNumber())) {
            jpql.append(" and _cpInfo.customerProductNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerProductNumber()));
        }
        if (queryVo.getCustomerProductRevision() != null && !"".equals(queryVo.getCustomerProductRevision())) {
            jpql.append(" and _cpInfo.customerProductRevision like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerProductRevision()));
        }
        if (queryVo.getInternalProductNumber() != null && !"".equals(queryVo.getInternalProductNumber())) {
            jpql.append(" and _cpInfo.internalProductNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalProductNumber()));
        }
        if (queryVo.getInternalProductRevision() != null && !"".equals(queryVo.getInternalProductRevision())) {
            jpql.append(" and _cpInfo.internalProductRevision like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalProductRevision()));
        }
        if (queryVo.getShipmentProductNumber() != null && !"".equals(queryVo.getShipmentProductNumber())) {
            jpql.append(" and _cpInfo.shipmentProductNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getShipmentProductNumber()));
        }
        if (queryVo.getWaferSize() != null && !"".equals(queryVo.getWaferSize())) {
            jpql.append(" and _cpInfo.waferSize like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getWaferSize()));
        }
        if (queryVo.getGrossDie() != null) {
            jpql.append(" and _cpInfo.grossDie like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getGrossDie()));
        }
        if (queryVo.getWarningQty() != null) {
            jpql.append(" and _cpInfo.warningQty like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getWarningQty()));
        }
        if (queryVo.getWarningType() != null && !"".equals(queryVo.getWarningType())) {
            jpql.append(" and _cpInfo.warningType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getWarningType()));
        }
        if (queryVo.getTestTime() != null) {
            jpql.append(" and _cpInfo.testTime like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestTime()));
        }
        if (queryVo.getTouchQty() != null) {
            jpql.append(" and _cpInfo.touchQty like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTouchQty()));
        }
        Page pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();
        return new Page<>(pages.getStart(), pages.getResultCount(), pageSize, CPInfoAssembler.toPageVos(pages.getData()));
    }

    @Override
    public List<SBLTemplateDTO> getSBLTemplatesByProduct(Long id) {
        List<SBLTemplate> sblTemplates = sblTemplateApplication.findByInternalProductId(id);
        return SBLTemplateAssembler.toDTOs(sblTemplates);
    }

    @Override
    public List<LabelDTO> getLabelsByPackageType(String packageType) {
        List<Label> labels = labelApplication.findCPLabelsByPackageType(packageType);
        return LabelAssembler.toDTOs(labels);
    }

    @Override
    public InvokeResult updateProcessTemplate(Long id, Long processTemplateId) {
        CPInfo cpInfo = cpInfoApplication.get(id);
        cpInfo.setProcessTemplate(processTemplateApplication.get(processTemplateId));


        //将runcardtemplace删除
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);
        if (cpRuncardTemplate != null) {
            cpRuncardTemplateApplication.remove(cpRuncardTemplate);
        }


        cpInfoApplication.update(cpInfo);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult updateLabels(Long id, List<Long> labelIds) {
        CPInfo cpInfo = cpInfoApplication.get(id);
        List<Label> labels = new ArrayList<>();
        for (Long labelId : labelIds) {
            labels.add(labelApplication.get(labelId));
        }
        cpInfo.setLabels(labels);
        cpInfoApplication.update(cpInfo);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult clearLabels(Long id) {
        CPInfo cpInfo = cpInfoApplication.get(id);
        cpInfo.setLabels(null);
        cpInfoApplication.update(cpInfo);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult clearProcessTemplate(Long id) {
        CPInfo cpInfo = cpInfoApplication.get(id);
        cpInfo.setProcessTemplate(null);
        cpInfoApplication.update(cpInfo);
        return InvokeResult.success();
    }

    @Override
    //public void exportExcel(List<Object> cpInfoDTOs , OutputStream OutputStream)
    public void exportExcel(List<Object> cpInfoDTOs, String fileName) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("CP产品信息情报");
        sheet.setDefaultColumnWidth(16);
        sheet.setDefaultRowHeightInPoints(20);
        // 第三步，在sheet中添加表头第0行t
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        HSSFCell  cell = row.createCell(0);
        cell.setCellValue("客户产品型号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("客户产品版本");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("所属直接客户");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("所属间接客户");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("晶圆尺寸");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("GROSS DIE");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("最低PASS报警");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("报警指标分类");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("测试时间/片");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("产品制程要求");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("每片接触次数");
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("质量部主要负责人");
        cell.setCellStyle(style);
        cell = row.createCell(12);
        cell.setCellValue("质量部协助负责人");
        cell.setCellStyle(style);
        cell = row.createCell(13);
        cell.setCellValue("产品部主要负责人");
        cell.setCellStyle(style);
        cell = row.createCell(14);
        cell.setCellValue("产品部协助负责人");
        cell.setCellStyle(style);
        cell = row.createCell(15);
        cell.setCellValue("TDE主要负责人");
        cell.setCellStyle(style);
        cell = row.createCell(16);
        cell.setCellValue("TDE协助负责人");
        cell.setCellStyle(style);

	     // 第五步，写入实体数据 实际应用中这些数据从数据库得到，   
	     
	     for (int i = 0; i < cpInfoDTOs.size(); i++)  
	     {  
	    	 CPInfoDTO cpInfo = (CPInfoDTO) cpInfoDTOs.get(i);
	         row = sheet.createRow((int) i + 1);   
	         HSSFCell datacell = row.createCell(0);
	         datacell.setCellValue(cpInfo.getCustomerProductNumber());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(1);
	         datacell.setCellValue(cpInfo.getCustomerProductRevision()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(2);
	         datacell.setCellValue(cpInfo.getCustomerDirectDTO().getChineseName()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(3);
	         datacell.setCellValue(cpInfo.getCustomerIndirectDTO().getChineseName());  
	         datacell.setCellStyle(style);
	         datacell = row.createCell(4);
	         datacell.setCellValue(cpInfo.getWaferSize());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(5);
	         datacell.setCellValue(cpInfo.getGrossDie()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(6);
	         datacell.setCellValue(cpInfo.getWarningQty());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(7);
	         datacell.setCellValue(cpInfo.getWarningType());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(8);
	         datacell.setCellValue(cpInfo.getTestTime());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(9);
	         datacell.setCellValue(cpInfo.getProductRequire()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(10);
	         datacell.setCellValue(cpInfo.getTouchQty());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(11);
	         datacell.setCellValue(cpInfo.getKeyQuantityManagerDTO().getName());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(12);
	         datacell.setCellValue(cpInfo.getAssistQuantityManagerDTO().getName());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(13);
	         datacell.setCellValue(cpInfo.getKeyProductionManagerDTO().getName());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(14);
	         datacell.setCellValue(cpInfo.getAssistProductionManagerDTO().getName());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(15);
	         datacell.setCellValue(cpInfo.getKeyTDEManagerDTO().getName());  
	         datacell.setCellStyle(style);
	         datacell = row.createCell(16);
	         datacell.setCellValue(cpInfo.getAssistTDEManagerDTO().getName());  
	         datacell.setCellStyle(style);
	     }  
	     // 第六步，将文件存到指定位置   
	     try  
	     {  
	 		 //获取类文件所在的路径，为获取web应用路径做准备
	 		 String classPath = this.getClass().getClassLoader().getResource("").getPath();
	 		 //获取模板路径与导出文件的路径
	 		 String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
	 		 String exportPath = templatePath + "export/";
	         FileOutputStream fout = new FileOutputStream(exportPath + fileName);  
	         wb.write(fout);
	         fout.close();
	         //wb.write(OutputStream);
	         //File fileLoad = new File(exportPath,fileName);
	         //FileInputStream in = new FileInputStream(fileLoad);
	         //byte b[] = new byte[1024];
	         //int n = 0;
	         //while ((n = in.read(b)) != -1) {
	         //OutputStream.write(b, 0, n);
	         //}
	     }  
	     catch (IOException e)  
	     {  
	         e.printStackTrace();  
	     }  
	}
}
