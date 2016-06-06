package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Strings;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FTBakingNodeAssembler {

    public static FT_BakingDTO toDTO(FTBakingNode fT_Baking) {
        if (fT_Baking == null) {
            return null;
        }
        FT_BakingDTO result = new FT_BakingDTO();
        result.setId(fT_Baking.getId());
        result.setVersion(fT_Baking.getVersion());
        result.setOvenNumber(fT_Baking.getOvenNumber());
        result.setOvenTemperature(fT_Baking.getOvenTemperature());
//     	    	result.setTimeIn (fT_Baking.getTimeIn());
//     	    	result.setTimeOut (fT_Baking.getTimeOut());
        result.setTimeLimit(fT_Baking.getTimeLimit());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.setTimeIn(fT_Baking.getTimeIn() == null ? "null" : formatter.format(fT_Baking.getTimeIn()));
        result.setTimeOut(fT_Baking.getTimeOut() == null ? "null" : formatter.format(fT_Baking.getTimeOut()));

        result.setFtResultDTO(FTResultAssembler.toDTO(fT_Baking.getResult()));
        return result;
    }

    public static List<FT_BakingDTO> toDTOs(Collection<FTBakingNode> fT_Bakings) {
        if (fT_Bakings == null) {
            return null;
        }
        List<FT_BakingDTO> results = new ArrayList<FT_BakingDTO>();
        for (FTBakingNode each : fT_Bakings) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static FTBakingNode toEntity(FT_BakingDTO fT_BakingDTO) {
        if (fT_BakingDTO == null) {
            return null;
        }
        FTBakingNode result = new FTBakingNode();
        result.setId(fT_BakingDTO.getId());
        result.setVersion(fT_BakingDTO.getVersion());

        result.setOvenNumber(fT_BakingDTO.getOvenNumber());
        result.setOvenTemperature(fT_BakingDTO.getOvenTemperature());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (!Strings.isNullOrEmpty(fT_BakingDTO.getTimeIn()))
                result.setTimeIn(formatter.parse(fT_BakingDTO.getTimeIn()));
            if (!Strings.isNullOrEmpty(fT_BakingDTO.getTimeOut()))
                result.setTimeOut(formatter.parse(fT_BakingDTO.getTimeOut()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result.setTimeLimit(fT_BakingDTO.getTimeLimit());
        result.setResult(FTResultAssembler.toEntity(fT_BakingDTO.getFtResultDTO()));

        return result;
    }

    public static List<FTBakingNode> toEntities(Collection<FT_BakingDTO> fT_BakingDTOs) {
        if (fT_BakingDTOs == null) {
            return null;
        }

        List<FTBakingNode> results = new ArrayList<FTBakingNode>();
        for (FT_BakingDTO each : fT_BakingDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
