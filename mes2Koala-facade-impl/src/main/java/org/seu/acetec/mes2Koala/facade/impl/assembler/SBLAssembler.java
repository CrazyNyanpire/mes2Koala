package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.SBL;
import org.seu.acetec.mes2Koala.core.enums.SBLQuality;
import org.seu.acetec.mes2Koala.facade.dto.SBLDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Joyce on 2016/3/11.
 */
public class SBLAssembler {
    public static SBLDTO toDTO(SBL sbl) {
        if (sbl == null) {
            return null;
        }
        SBLDTO result = new SBLDTO();
        result.setId(sbl.getId());
        result.setVersion(sbl.getVersion());
        result.setType(sbl.getType());
        result.setLowerLimit(sbl.getLowerLimit());
        result.setUpperLimit(sbl.getUpperLimit());
        result.setQuality(SBLQuality.getStringValue(sbl.getQuality()));
        result.setSite(sbl.getSite());
        result.setNodeName(sbl.getNodeName());
        return result;
    }

    public static List<SBLDTO> toDTOs(Collection<SBL> sbls) {
        if (sbls == null) {
            return null;
        }
        List<SBLDTO> results = new ArrayList<>();
        for (SBL each : sbls) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static SBL toEntity(SBLDTO sblDTO) {
        if (sblDTO == null) {
            return null;
        }
        SBL result = new SBL();
        result.setId(sblDTO.getId());
        result.setVersion(sblDTO.getVersion());
        result.setType(sblDTO.getType());
        result.setLowerLimit(sblDTO.getLowerLimit());
        result.setUpperLimit(sblDTO.getUpperLimit());
        result.setQuality(SBLQuality.fromStringValue(sblDTO.getQuality()));
        result.setSite(sblDTO.getSite());
        result.setNodeName(sblDTO.getNodeName());
        return result;
    }

    public static List<SBL> toEntities(Collection<SBLDTO> sblDTOs) {
        if (sblDTOs == null) {
            return null;
        }
        List<SBL> results = new ArrayList<>();
        for (SBLDTO each : sblDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
