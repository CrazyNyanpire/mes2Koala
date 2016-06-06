package org.seu.acetec.mes2Koala.facade.excelvo;

import org.seu.acetec.mes2Koala.core.common.Mes2DateUtils;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.facade.dto.BomTemplateDTO;

import java.text.DecimalFormat;
import java.util.List;

public class WorkOrderVoAssembler {

    // TODO: 工单编号修改为客户编号
    public static WorkOrderVo toVo(String workOrderNumber, List<FTLot> ftLots, List<BomTemplateDTO> bomTemplateDTOs) {
        if (ftLots == null)
            return null;
        //获取列表中的第一个作为模板
        FTLot ftLot0 = ftLots.get(0);
        //获取批次的数量总和以及各个批次的批号
        Long sum = 0L;
        StringBuilder internalLotNumbers = new StringBuilder();
        for ( FTLot ftLot : ftLots ) {
        	sum += ftLot.getQty();
        	internalLotNumbers.append(ftLot.getInternalLotNumber());
        	internalLotNumbers.append('\n');
        }
        
        WorkOrderVo workOrderVo = new WorkOrderVo();
        workOrderVo.setCustomerNumber(ftLot0.getCustomerFTLot().getCustomerNumber());
        workOrderVo.setAmount( sum.toString() );
        workOrderVo.setStartTime(Mes2DateUtils.getToday());
        workOrderVo.setWorkOrderNumber(workOrderNumber);
        workOrderVo.setCustomerProductNumber( ftLot0.getCustomerProductNumber() );
        workOrderVo.setInternalLotNumber( internalLotNumbers.toString() );
        workOrderVo.setBomTemplateVos(BomTemplateVoAssembler.toVos(bomTemplateDTOs));

        for (BomTemplateVo bomTemplateVo : workOrderVo.getBomTemplateVos()) {

            // 理论需求:BOM单Theory Qty乘以数量
            if (bomTemplateVo.getTheoryQuantity() != null) {
                double theoryQuantity = sum * bomTemplateVo.getTheoryQuantity();
                bomTemplateVo.setTheoryQuantity(Double.valueOf(new DecimalFormat("#.######").format(theoryQuantity)));
            }

            // 最小用量：BOM单Qty值乘以数量
            if (bomTemplateVo.getQuantity() != null) {
                double minRequire = sum * bomTemplateVo.getQuantity();
                bomTemplateVo.setMinRequire(new DecimalFormat("#.######").format(minRequire));
            }

            // 实际用量：空值，不需要计算
            bomTemplateVo.setQuantity(0.0);
        }
        return workOrderVo;
    }
}
