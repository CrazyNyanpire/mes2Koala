package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class RuncardNoteAssembler {
	
	public static RuncardNoteDTO  toDTO(RuncardNote  runcardNote){
		if (runcardNote == null) {
			return null;
		}
    	RuncardNoteDTO result  = new RuncardNoteDTO();
	    	result.setId (runcardNote.getId());
     	    	result.setVersion (runcardNote.getVersion());
     	    	result.setCreateTimestamp (runcardNote.getCreateTimestamp());
     	    	result.setLastModifyTimestamp (runcardNote.getLastModifyTimestamp());
     	    	result.setCreateEmployNo (runcardNote.getCreateEmployNo());
     	    	result.setLastModifyEmployNo (runcardNote.getLastModifyEmployNo());
     	    	result.setLogic (runcardNote.getLogic());
     	    	result.setNodeName (runcardNote.getNodeName());
     	    	result.setNodeNote (runcardNote.getNodeNote());
     	    return result;
	 }
	
	public static List<RuncardNoteDTO>  toDTOs(Collection<RuncardNote>  runcardNotes){
		if (runcardNotes == null) {
			return null;
		}
		List<RuncardNoteDTO> results = new ArrayList<RuncardNoteDTO>();
		for (RuncardNote each : runcardNotes) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static RuncardNote  toEntity(RuncardNoteDTO  runcardNoteDTO){
	 	if (runcardNoteDTO == null) {
			return null;
		}
	 	RuncardNote result  = new RuncardNote();
        result.setId (runcardNoteDTO.getId());
         result.setVersion (runcardNoteDTO.getVersion());
         result.setCreateTimestamp (runcardNoteDTO.getCreateTimestamp());
         result.setLastModifyTimestamp (runcardNoteDTO.getLastModifyTimestamp());
         result.setCreateEmployNo (runcardNoteDTO.getCreateEmployNo());
         result.setLastModifyEmployNo (runcardNoteDTO.getLastModifyEmployNo());
         result.setLogic (runcardNoteDTO.getLogic());
         result.setNodeName (runcardNoteDTO.getNodeName());
         result.setNodeNote (runcardNoteDTO.getNodeNote());
 	  	return result;
	 }
	
	public static List<RuncardNote> toEntities(Collection<RuncardNoteDTO> runcardNoteDTOs) {
		if (runcardNoteDTOs == null) {
			return null;
		}
		
		List<RuncardNote> results = new ArrayList<RuncardNote>();
		for (RuncardNoteDTO each : runcardNoteDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
