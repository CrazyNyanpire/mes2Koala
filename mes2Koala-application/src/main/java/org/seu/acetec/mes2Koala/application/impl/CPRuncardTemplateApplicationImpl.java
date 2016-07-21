package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.CPRuncardTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.CPRuncardTemplate;
import org.seu.acetec.mes2Koala.core.domain.FTRuncardTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

/**
 * Created by LCN on 2016/4/27.
 */
@Named
@Transactional
public class CPRuncardTemplateApplicationImpl extends GenericMES2ApplicationImpl<CPRuncardTemplate> implements CPRuncardTemplateApplication {
    @Override
    public CPRuncardTemplate findByInternalProductId(Long id) {
        return findOne("select a from CPRuncardTemplate a right join a.internalProduct b where b.id = ?", id);
    }
    
    public String isRuncardSignedMsg(Long id) {
        boolean isFinished = true;
        String msg = "";
        CPRuncardTemplate cpRuncardTemplate = findByInternalProductId(id);
        if (cpRuncardTemplate == null) {
        	return "未绑定流程";
        }
        //对应三个部门
        String keyMangerOpinion = "";
        String assistManagerOpinion = "";
        //质量部门
        keyMangerOpinion = cpRuncardTemplate.getKeyQuantityAuthorization().getOpinion();
        assistManagerOpinion = cpRuncardTemplate.getAssistQuantityAuthorization().getOpinion();
        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
        	msg += "QRE未签核;";
        //生产部门
        keyMangerOpinion = cpRuncardTemplate.getKeyProductionAuthorization().getOpinion();
        assistManagerOpinion = cpRuncardTemplate.getAssistProductionAuthorization().getOpinion();
        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
        	msg += "PD未签核;";

        //TDE部门
        keyMangerOpinion = cpRuncardTemplate.getKeyTDEAuthorization().getOpinion();
        assistManagerOpinion = cpRuncardTemplate.getAssistTDEAuthorization().getOpinion();

        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
        	msg += "TDE未签核;";
        if("".equals(msg)){
        	msg = "已签核";
        }
        return msg;
    }


    /**
     * 辅助类   传入两个string  验证是否这个部门签核通过
     *
     * @param keyMangerOpinion
     * @param assistManagerOpinion
     * @return
     */
    private boolean checkRuncardFinished(String keyMangerOpinion, String assistManagerOpinion) {
        
        return (keyMangerOpinion != null && keyMangerOpinion.equals("同意")) ||
        		( ( keyMangerOpinion == null || "".equals(keyMangerOpinion) ) && 
        				assistManagerOpinion != null && assistManagerOpinion.equals("同意") );
    }
}
