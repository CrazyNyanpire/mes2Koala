package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTInfoApplication;
import org.seu.acetec.mes2Koala.application.FTRuncardTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.FTInfo;
import org.seu.acetec.mes2Koala.core.domain.FTRuncardTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by LCN on 2016/3/10.
 */
@Named
@Transactional
public class FTRuncardTemplateApplicationImpl extends GenericMES2ApplicationImpl<FTRuncardTemplate> implements FTRuncardTemplateApplication {

    @Inject
    private FTInfoApplication ftInfoApplication;


    @Override
    public FTRuncardTemplate findByInternalProductId(Long id) {
        return findOne("select a from FTRuncardTemplate a right join a.internalProduct b where b.id = ?", id);
    }

    @Override
    public void deleteByInternalProductId(Long id) {

    }

    @Override
    public Boolean isRuncardSigned(Long id) {
        boolean isFinished = true;
        FTInfo ftInfo = ftInfoApplication.get(id);
        if (ftInfo == null) {
            return false;
        }
        FTRuncardTemplate FTRuncardTemplate = findByInternalProductId(id);
        if (FTRuncardTemplate == null) {
            return false;
        }
        //对应三个部门
        String keyMangerOpinion = "";
        String assistManagerOpinion = "";
        //质量部门
        keyMangerOpinion = FTRuncardTemplate.getKeyQuantityAuthorization().getOpinion();
        assistManagerOpinion = FTRuncardTemplate.getAssistQuantityAuthorization().getOpinion();
        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
            return false;

        //生产部门
        keyMangerOpinion = FTRuncardTemplate.getKeyProductionAuthorization().getOpinion();
        assistManagerOpinion = FTRuncardTemplate.getAssistProductionAuthorization().getOpinion();
        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
            return false;

        //TDE部门
        keyMangerOpinion = FTRuncardTemplate.getKeyTDEAuthorization().getOpinion();
        assistManagerOpinion = FTRuncardTemplate.getAssistTDEAuthorization().getOpinion();

        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
            return false;
        return true;
    }
    
    public String isRuncardSignedMsg(Long id) {
        boolean isFinished = true;
        String msg = "";
        FTRuncardTemplate FTRuncardTemplate = findByInternalProductId(id);
        if (FTRuncardTemplate == null) {
        	return "未绑定流程";
        }
        //对应三个部门
        String keyMangerOpinion = "";
        String assistManagerOpinion = "";
        //质量部门
        keyMangerOpinion = FTRuncardTemplate.getKeyQuantityAuthorization().getOpinion();
        assistManagerOpinion = FTRuncardTemplate.getAssistQuantityAuthorization().getOpinion();
        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
        	msg += "QRE未签核;";
        //生产部门
        keyMangerOpinion = FTRuncardTemplate.getKeyProductionAuthorization().getOpinion();
        assistManagerOpinion = FTRuncardTemplate.getAssistProductionAuthorization().getOpinion();
        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
        	msg += "PD未签核;";

        //TDE部门
        keyMangerOpinion = FTRuncardTemplate.getKeyTDEAuthorization().getOpinion();
        assistManagerOpinion = FTRuncardTemplate.getAssistTDEAuthorization().getOpinion();

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
