package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface RuncardNoteFacade {

	public InvokeResult getRuncardNote(Long id);
	
	public InvokeResult creatRuncardNote(RuncardNoteDTO runcardNote);
	
	public InvokeResult updateRuncardNote(RuncardNoteDTO runcardNote);
	
	public InvokeResult removeRuncardNote(Long id);
	
	public InvokeResult removeRuncardNotes(Long[] ids);
	
	public List<RuncardNoteDTO> findAllRuncardNote();
	
	public Page<RuncardNoteDTO> pageQueryRuncardNote(RuncardNoteDTO runcardNote, int currentPage, int pageSize);
	

}

