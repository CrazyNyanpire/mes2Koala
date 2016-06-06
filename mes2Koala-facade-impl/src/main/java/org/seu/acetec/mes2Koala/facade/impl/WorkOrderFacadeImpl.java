package org.seu.acetec.mes2Koala.facade.impl;

import org.apache.commons.lang.Validate;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.BomTemplateApplication;
import org.seu.acetec.mes2Koala.application.CustomerFTLotApplication;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.core.domain.Customer;
import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.domain.FTInfo;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.facade.BomTemplateFacade;
import org.seu.acetec.mes2Koala.facade.WorkOrderFacade;
import org.seu.acetec.mes2Koala.facade.dto.BomTemplateDTO;
import org.seu.acetec.mes2Koala.facade.excelvo.WorkOrderVo;
import org.seu.acetec.mes2Koala.facade.excelvo.WorkOrderVoAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Named
public class WorkOrderFacadeImpl implements WorkOrderFacade {

    @Inject
    FTLotApplication ftLotApplication;

    @Inject
    IncrementNumberApplication incrementNumberApplication;

    @Inject
    BomTemplateApplication bomTemplateApplication;

    @Inject
    BomTemplateFacade bomTemplateFacade;


    // 检查FTLot是否都存在，并且是否是属于同一个CustomerFTLot
    private InvokeResult checkCustomerFTLot(Long[] ftLotIds) {
        Validate.notNull(ftLotIds);
        Validate.noNullElements(ftLotIds);
        // 查找internalLot实体
        if (ftLotIds.length == 0)
            return InvokeResult.failure("请选择批次");
        FTLot ftLot0 = ftLotApplication.get(ftLotIds[0]);
        if (ftLot0 == null) {
            return InvokeResult.failure("批次不存在");
        }

        /*
        CustomerFTLot customerFTLot0 = ftLot0.getCustomerFTLot();
        if (customerFTLot0 == null) {
            return InvokeResult.failure("批次对应的客户批次不存在");
        }
        */
        FTInfo ftInfo0 = ftLot0.getFtInfo();
        if ( ftInfo0 == null ) {
        	return InvokeResult.failure("批次对应的产品型号不存在");
        }

        for (Long lotId : ftLotIds) {
            FTLot ftLot = ftLotApplication.get(lotId);
            if (ftLot == null) {
                return InvokeResult.failure("批次不存在");
            }
            /*
            CustomerFTLot customerFTLot = ftLot.getCustomerFTLot();
            if (customerFTLot == null || !Objects.equals(customerFTLot.getId(), customerFTLot0.getId())) {
                return InvokeResult.failure("批次对应的客户批次不一致");
            }
            */
            FTInfo ftInfo = ftLot.getFtInfo();
            if ( ftInfo == null || !ftInfo.getId().equals(ftInfo0.getId()) ){
            	return InvokeResult.failure("批次对应的产品型号不一致");
            }
        }
        return InvokeResult.success();
    }

    @Override
    public InvokeResult create(String workOrderNumber, Long[] ftLotIds, Long[] bomIds) {
        Validate.notNull(bomIds);
        Validate.noNullElements(bomIds);
        if (bomIds.length == 0)
            return InvokeResult.failure("请选择BOM单");
        InvokeResult checkResult = checkCustomerFTLot(ftLotIds);
        if (!checkResult.isSuccess())
            return checkResult;

        List<FTLot> ftLots = ftLotApplication.findByIds(ftLotIds);
        Customer customer = ftLots.get(0).getCustomerDirect();

        //查找bom实体（迫不得已使用dto）
        List<BomTemplateDTO> bomTemplateDTOs = new ArrayList<BomTemplateDTO>();
        for (Long id : bomIds) {
            bomTemplateDTOs.add((BomTemplateDTO) bomTemplateFacade.getBomTemplate(id).getData());
        }
        String peek = incrementNumberApplication.peekWorkOrderNumber(customer); // next
        if (!Objects.equals(workOrderNumber, peek)) {
            return InvokeResult.failure("工单编号错误");
        }

        //拼装工单的excel vo
        WorkOrderVo workOrderVo = WorkOrderVoAssembler.toVo(workOrderNumber, ftLots, bomTemplateDTOs);
        incrementNumberApplication.nextWorkOrderNumber(customer);
        return exportOrderExcel(workOrderVo);
    }

    @Override
    public InvokeResult getWorkOrderVo(String workOrderNumber, Long[] ftLotIds, Long[] bomIds) {
        Validate.notNull(bomIds);
        Validate.noNullElements(bomIds);
        if (bomIds.length == 0)
            return InvokeResult.failure("请选择BOM单");
        InvokeResult checkResult = checkCustomerFTLot(ftLotIds);
        if (!checkResult.isSuccess())
            return checkResult;

        List<FTLot> ftLots = ftLotApplication.findByIds(ftLotIds);
        Customer customer = ftLots.get(0).getCustomerDirect();

        //查找bom实体（迫不得已使用dto）
        List<BomTemplateDTO> bomTemplateDTOs = new ArrayList<BomTemplateDTO>();
        for (Long id : bomIds) {
            bomTemplateDTOs.add((BomTemplateDTO) bomTemplateFacade.getBomTemplate(id).getData());
        }

        String peek = incrementNumberApplication.peekWorkOrderNumber(customer); // next
        if (!Objects.equals(workOrderNumber, peek)) {
            return InvokeResult.failure("工单编号错误");
        }
//        incrementNumberApplication.nextWorkOrderNumber(internalLot.getCustomerFTLot()); // next++

        //拼装工单的excel vo
        WorkOrderVo workOrderVo = WorkOrderVoAssembler.toVo(workOrderNumber, ftLots, bomTemplateDTOs);

        return InvokeResult.success(workOrderVo);
    }

    private InvokeResult exportOrderExcel(WorkOrderVo workOrderVo) {
        /**********导出工单excel************/
        //获取类文件所在路径
        String classPath = this.getClass().getClassLoader().getResource("").getPath();
        String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
        String exportPath = templatePath + "export/";
        String fileName = workOrderVo.getWorkOrderNumber() + ".xlsx";

        try (InputStream is = new FileInputStream(templatePath + '/' + "WorkOrder-Template.xlsx")) {
            try (OutputStream os = new FileOutputStream(exportPath + '/' + fileName)) {
                Context context = new Context();
                context.putVar("workOrder", workOrderVo);
                JxlsHelper.getInstance().processTemplateAtCell(is, os, context, "Sheet1!A1");
                return InvokeResult.success("excel/export/" + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return InvokeResult.failure(e.getMessage());
        }

    }

    @Override
    public InvokeResult getExpectedWorkOrderNumber(Long[] ftLotIds) {
        InvokeResult checkResult = checkCustomerFTLot(ftLotIds);
        if (!checkResult.isSuccess())
            return checkResult;
        FTLot ftLot = ftLotApplication.get(ftLotIds[0]);
        Customer customer = ftLot.getCustomerDirect();
        return InvokeResult.success(incrementNumberApplication.peekWorkOrderNumber(customer));
    }
}
