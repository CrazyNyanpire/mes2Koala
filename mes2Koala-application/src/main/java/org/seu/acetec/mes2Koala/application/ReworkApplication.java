package org.seu.acetec.mes2Koala.application;


import java.util.List;
import java.util.Set;
import  org.seu.acetec.mes2Koala.core.domain.Rework;

public interface ReworkApplication {

	public Rework getRework(Long id);
	
	public void creatRework(Rework rework);
	
	public void updateRework(Rework rework);
	
	public void removeRework(Rework rework);
	
	public void removeReworks(Set<Rework> reworks);
	
	public List<Rework> findAllRework();
	
}

