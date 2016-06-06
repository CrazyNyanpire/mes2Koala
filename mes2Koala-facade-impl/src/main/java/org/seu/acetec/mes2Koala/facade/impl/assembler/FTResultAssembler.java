package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.FTResult;
import org.seu.acetec.mes2Koala.facade.dto.FT_ResultDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FTResultAssembler {

    public static FT_ResultDTO toDTO(FTResult fT_Result) {
        if (fT_Result == null) {
            return null;
        }
        FT_ResultDTO result = new FT_ResultDTO();
        result.setId(fT_Result.getId());
        result.setVersion(fT_Result.getVersion());
        result.setYield(fT_Result.getYield());
        result.setLoss(fT_Result.getLoss());
        result.setBackUp(fT_Result.getBackUp());
        result.setOther(fT_Result.getOther());
        result.setSum(fT_Result.getResultSum());
        result.setMarkF(fT_Result.getMarkF());
        result.setFail(fT_Result.getFail());
        result.setLat(fT_Result.getLat());

        List<String> aList = new ArrayList<String>();
        aList.add(fT_Result.getBin1());
        aList.add(fT_Result.getBin2());
        aList.add(fT_Result.getBin3());
        aList.add(fT_Result.getBin4());
        aList.add(fT_Result.getBin5());
        aList.add(fT_Result.getBin6());
        aList.add(fT_Result.getBin7());
        aList.add(fT_Result.getBin8());
        aList.add(fT_Result.getBin9());
        aList.add(fT_Result.getBin10());
        aList.add(fT_Result.getBin11());
        aList.add(fT_Result.getBin12());
        aList.add(fT_Result.getBin13());
        aList.add(fT_Result.getBin14());
        aList.add(fT_Result.getBin15());
        aList.add(fT_Result.getBin16());
        aList.add(fT_Result.getBin17());
        aList.add(fT_Result.getBin18());
        aList.add(fT_Result.getBin19());
        aList.add(fT_Result.getBin20());

        result.setBin(aList);
                 /*
                 result.setBin1 (fT_Result.getBin1());
     	    	result.setBin2 (fT_Result.getBin2());
     	    	result.setBin3 (fT_Result.getBin3());
     	    	result.setBin4 (fT_Result.getBin4());
     	    	result.setBin5 (fT_Result.getBin5());
     	    	result.setBin6 (fT_Result.getBin6());
     	    	result.setBin7 (fT_Result.getBin7());
     	    	result.setBin8 (fT_Result.getBin8());
     	    	result.setBin9 (fT_Result.getBin9());
     	    	result.setBin10 (fT_Result.getBin10());
     	    	result.setBin11 (fT_Result.getBin11());
     	    	result.setBin12 (fT_Result.getBin12());
     	    	result.setBin13 (fT_Result.getBin13());
     	    	result.setBin14 (fT_Result.getBin14());
     	    	result.setBin15 (fT_Result.getBin15());
     	    	result.setBin16 (fT_Result.getBin16());
     	    	result.setBin17 (fT_Result.getBin17());
     	    	result.setBin18 (fT_Result.getBin18());
     	    	result.setBin19 (fT_Result.getBin19());
     	    	result.setBin20 (fT_Result.getBin20());
     	    	*/
        return result;
    }

    public static List<FT_ResultDTO> toDTOs(Collection<FTResult> fT_Results) {
        if (fT_Results == null) {
            return null;
        }
        List<FT_ResultDTO> results = new ArrayList<FT_ResultDTO>();
        for (FTResult each : fT_Results) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static FTResult toEntity(FT_ResultDTO fT_ResultDTO) {
        if (fT_ResultDTO == null) {
            return null;
        }
        FTResult result = new FTResult();
        result.setId(fT_ResultDTO.getId());
        result.setVersion(fT_ResultDTO.getVersion());
        //   result.setInTape (fT_ResultDTO.getInTape()==null?"0":fT_ResultDTO.getInTape());
        result.setYield(fT_ResultDTO.getYield() == null ? "0" : fT_ResultDTO.getYield());
        result.setLoss(fT_ResultDTO.getLoss() == null ? "0" : fT_ResultDTO.getLoss());
        result.setBackUp(fT_ResultDTO.getBackUp() == null ? "0" : fT_ResultDTO.getBackUp());
        result.setOther(fT_ResultDTO.getOther() == null ? "0" : fT_ResultDTO.getOther());
        result.setResultSum(fT_ResultDTO.getSum() == null ? "0" : fT_ResultDTO.getSum());

        result.setMarkF(fT_ResultDTO.getMarkF() == null ? "0" : fT_ResultDTO.getMarkF());
        //  result.setPass(fT_ResultDTO.getPass()==null?"0":fT_ResultDTO.getPass());
        result.setFail(fT_ResultDTO.getFail() == null ? "0" : fT_ResultDTO.getFail());
        result.setLat(fT_ResultDTO.getLat() == null ? "0" : fT_ResultDTO.getLat());

        List<String> aList = fT_ResultDTO.getBin();
        if (aList == null || aList.size() == 0 || aList.get(0) == null) {
            aList = new ArrayList<String>();
            for (int i = 0; i < 20; i++) {
                aList.add("0");
            }
        }
         /*    	 
         result.setBin1 (fT_ResultDTO.getBin1()==null?0:fT_ResultDTO.getBin1());
         result.setBin2 (fT_ResultDTO.getBin2()==null?0:fT_ResultDTO.getBin2());
         result.setBin3 (fT_ResultDTO.getBin3()==null?0:fT_ResultDTO.getBin3());
         result.setBin4 (fT_ResultDTO.getBin4()==null?0:fT_ResultDTO.getBin4());
         result.setBin5 (fT_ResultDTO.getBin5()==null?0:fT_ResultDTO.getBin5());
         result.setBin6 (fT_ResultDTO.getBin6()==null?0:fT_ResultDTO.getBin6());
         result.setBin7 (fT_ResultDTO.getBin7()==null?0:fT_ResultDTO.getBin7());
         result.setBin8 (fT_ResultDTO.getBin8()==null?0:fT_ResultDTO.getBin8());
         result.setBin9 (fT_ResultDTO.getBin9()==null?0:fT_ResultDTO.getBin9());
         result.setBin10 (fT_ResultDTO.getBin10()==null?0:fT_ResultDTO.getBin10());
         result.setBin11 (fT_ResultDTO.getBin11()==null?0:fT_ResultDTO.getBin11());
         result.setBin12 (fT_ResultDTO.getBin12()==null?0:fT_ResultDTO.getBin12());
         result.setBin13 (fT_ResultDTO.getBin13()==null?0:fT_ResultDTO.getBin13());
         result.setBin14 (fT_ResultDTO.getBin14()==null?0:fT_ResultDTO.getBin14());
         result.setBin15 (fT_ResultDTO.getBin15()==null?0:fT_ResultDTO.getBin15());
         result.setBin16 (fT_ResultDTO.getBin16()==null?0:fT_ResultDTO.getBin16());
         result.setBin17 (fT_ResultDTO.getBin17()==null?0:fT_ResultDTO.getBin17());
         result.setBin18 (fT_ResultDTO.getBin18()==null?0:fT_ResultDTO.getBin18());
         result.setBin19 (fT_ResultDTO.getBin19()==null?0:fT_ResultDTO.getBin19());
         result.setBin20 (fT_ResultDTO.getBin20()==null?0:fT_ResultDTO.getBin20());
         */
        result.setBin1(aList.get(0));
        result.setBin2(aList.get(1));
        result.setBin3(aList.get(2));
        result.setBin4(aList.get(3));
        result.setBin5(aList.get(4));
        result.setBin6(aList.get(5));
        result.setBin7(aList.get(6));
        result.setBin8(aList.get(7));
        result.setBin9(aList.get(8));
        result.setBin10(aList.get(9));
        result.setBin11(aList.get(10));
        result.setBin12(aList.get(11));
        result.setBin13(aList.get(12));
        result.setBin14(aList.get(13));
        result.setBin15(aList.get(14));
        result.setBin16(aList.get(15));
        result.setBin17(aList.get(16));
        result.setBin18(aList.get(17));
        result.setBin19(aList.get(18));
        result.setBin20(aList.get(19));
        return result;
    }

    public static List<FTResult> toEntities(Collection<FT_ResultDTO> fT_ResultDTOs) {
        if (fT_ResultDTOs == null) {
            return null;
        }

        List<FTResult> results = new ArrayList<FTResult>();
        for (FT_ResultDTO each : fT_ResultDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
