package org.seu.acetec.mes2Koala.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.ReworkApplication;
import org.seu.acetec.mes2Koala.core.domain.Rework;

@Named
@Transactional
public class ReworkApplicationImpl implements ReworkApplication {

	public Rework getRework(Long id) {
		return Rework.get(Rework.class, id);
	}
	
	public void creatRework(Rework rework) {
		rework.save();
	}
	
	public void updateRework(Rework rework) {
		rework .save();
	}
	
	public void removeRework(Rework rework) {
		if(rework != null){
			rework.remove();
		}
	}
	
	public void removeReworks(Set<Rework> reworks) {
		for (Rework rework : reworks) {
			rework.remove();
		}
	}
	
	public List<Rework> findAllRework() {
		return Rework.findAll(Rework.class);
	}
	
}
