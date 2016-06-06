package org.seu.acetec.mes2Koala.application;


import java.util.List;
import java.util.Set;

import  org.seu.acetec.mes2Koala.core.domain.CPSBLTemplate;

public interface CPSBLTemplateApplication extends GenericMES2Application<CPSBLTemplate> {

	public CPSBLTemplate getCPSBLTemplate(Long id);
	
	public void creatCPSBLTemplate(CPSBLTemplate cPSBLTemplate);
	
	public void updateCPSBLTemplate(CPSBLTemplate cPSBLTemplate);
	
	public void removeCPSBLTemplate(CPSBLTemplate cPSBLTemplate);
	
	public void removeCPSBLTemplates(Set<CPSBLTemplate> cPSBLTemplates);
	
	public List<CPSBLTemplate> findAllCPSBLTemplate();
	
    // 合并SBL
    void integrate(Long[] cpSblTemplateIds);

    // 取消合并SBL
    void undoIntegration(Long cpSblTemplate);

	List<CPSBLTemplate> findByInternalProductId(Long productId);
}

