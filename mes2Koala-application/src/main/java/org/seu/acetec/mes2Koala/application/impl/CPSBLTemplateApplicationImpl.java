package org.seu.acetec.mes2Koala.application.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Named;

import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.CPSBLTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.CPSBLTemplate;
import org.seu.acetec.mes2Koala.core.domain.SBLTemplate;

@Named
@Transactional
public class CPSBLTemplateApplicationImpl extends GenericMES2ApplicationImpl<CPSBLTemplate>  implements CPSBLTemplateApplication {

	public CPSBLTemplate getCPSBLTemplate(Long id) {
		return CPSBLTemplate.get(CPSBLTemplate.class, id);
	}
	
	public void creatCPSBLTemplate(CPSBLTemplate cPSBLTemplate) {
		cPSBLTemplate.save();
	}
	
	public void updateCPSBLTemplate(CPSBLTemplate cPSBLTemplate) {
		cPSBLTemplate .save();
	}
	
	public void removeCPSBLTemplate(CPSBLTemplate cPSBLTemplate) {
		if(cPSBLTemplate != null){
			cPSBLTemplate.remove();
		}
	}
	
	public void removeCPSBLTemplates(Set<CPSBLTemplate> cPSBLTemplates) {
		for (CPSBLTemplate cPSBLTemplate : cPSBLTemplates) {
			cPSBLTemplate.remove();
		}
	}
	
	public List<CPSBLTemplate> findAllCPSBLTemplate() {
		return CPSBLTemplate.findAll(CPSBLTemplate.class);
	}
	
    @Override
    public void integrate(Long[] cpSblTemplateIds) {
        if (cpSblTemplateIds.length == 0)
            return;
        CPSBLTemplate cpsblTemplate = get(cpSblTemplateIds[0]);
        CPSBLTemplate integrated = new CPSBLTemplate();
        CPSBLTemplate.Quality quality = cpsblTemplate.getQuality();
        CPSBLTemplate.Type type = cpsblTemplate.getType();
        for (int index = 1; index < cpSblTemplateIds.length; ++index) {
            CPSBLTemplate temp = get(cpSblTemplateIds[index]);
            if (!Objects.equals(temp.getQuality(), quality)) {
                throw new RuntimeException("bin的品质不一致");
            }
            if (!Objects.equals(temp.getType(), type)) {
                throw new RuntimeException("bin的类型不一致");
            }
        }
        // TODO
        create(integrated);
    }

	@Override
    public void undoIntegration(Long cpSblTemplate) {

    }
	
    @Override
    public List<CPSBLTemplate> findByInternalProductId(Long productId) {
        return find("select o from CPSBLTemplate o right join o.internalProduct e where e.id = ?", productId);
    }
	
}
