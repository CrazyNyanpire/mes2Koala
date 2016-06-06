package org.seu.acetec.mes2Koala.facade.impl.assembler;

import com.google.common.base.Strings;
import org.seu.acetec.mes2Koala.core.domain.FTComposedTestNode;
import org.seu.acetec.mes2Koala.core.domain.FTResult;
import org.seu.acetec.mes2Koala.core.domain.FTTest;
import org.seu.acetec.mes2Koala.facade.dto.FT_ResultDTO;
import org.seu.acetec.mes2Koala.facade.dto.FT_TestDTO;

import java.util.*;

public class FT_TestAssembler {

    public static FT_TestDTO toDTO(FTComposedTestNode fT_Test) {
        if (fT_Test == null) {
            return null;
        }
        FT_TestDTO result = new FT_TestDTO();
        result.setId(fT_Test.getId());
        result.setVersion(fT_Test.getVersion());

        // 保持接口不变
        List<FTTest> tests = fT_Test.getTests();
        result.setFtState(-2);
        result.setRtState(-2);
        result.setEqcState(-2);
        result.setLatState(-2);
        Set<String> orderInfo = new LinkedHashSet<>();
        List<FTResult> ftTests = new ArrayList<>();
        List<FTResult> rtTests = new ArrayList<>();
        List<FTResult> eqcTests = new ArrayList<>();
        List<FTResult> latTests = new ArrayList<>();
        for (FTTest test : tests) {
            String name = test.getName();
            if (Strings.isNullOrEmpty(name)) {
                continue;
            }
            if (name.startsWith("FT")) {
                result.setFtState(0);
                orderInfo.add("FT");
                ftTests.add(test.getResult());
            } else if (name.startsWith("RT")) {
                result.setRtState(0);
                orderInfo.add("RT");
                rtTests.add(test.getResult());
            } else if (name.startsWith("EQC")) {
                result.setEqcState(0);
                orderInfo.add("EQC");
                eqcTests.add(test.getResult());
            } else if (name.startsWith("LAT")) {
                result.setLatState(0);
                orderInfo.add("LAT");
                latTests.add(test.getResult());
            }
        }
        StringBuilder sb = new StringBuilder();
        Iterator iterator = orderInfo.iterator();
        if (iterator.hasNext()) {
            sb.append(iterator.next());
        }
        while (iterator.hasNext()) {
            sb.append("|");
            sb.append(iterator.next());
        }
        result.setOrderInfo(sb.toString());
        result.setFtList(FTResultAssembler.toDTOs(ftTests));
        result.setRtList(FTResultAssembler.toDTOs(rtTests));
        result.setEqcList(FTResultAssembler.toDTOs(eqcTests));
        result.setLatList(FTResultAssembler.toDTOs(latTests));
        result.setFinalYield(FTResultAssembler.toDTO(fT_Test.getResult()));
        return result;
    }

    public static List<FT_TestDTO> toDTOs(Collection<FTComposedTestNode> fT_Tests) {
        if (fT_Tests == null) {
            return null;
        }
        List<FT_TestDTO> results = new ArrayList<FT_TestDTO>();
        for (FTComposedTestNode each : fT_Tests) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static FTComposedTestNode toEntity(FT_TestDTO fT_TestDTO) {
        if (fT_TestDTO == null) {
            return null;
        }
        FTComposedTestNode result = new FTComposedTestNode();
        result.setId(fT_TestDTO.getId());
        result.setVersion(fT_TestDTO.getVersion());
        // TODO 保持接口不变
        List<FT_ResultDTO> ftTests = fT_TestDTO.getFtList();
        List<FT_ResultDTO> rtTests = fT_TestDTO.getRtList();
        List<FT_ResultDTO> eqcTests = fT_TestDTO.getEqcList();
        List<FT_ResultDTO> latTests = fT_TestDTO.getLatList();
        String orderInfo = fT_TestDTO.getOrderInfo();
        result.setResult(FTResultAssembler.toEntity(fT_TestDTO.getFinalYield()));
        
//        result.setFtState(fT_TestDTO.getFtState() != null ? fT_TestDTO.getFtState() : 0);
//        result.setRtState(fT_TestDTO.getRtState() != null ? fT_TestDTO.getRtState() : 0);
//        result.setEqcState(fT_TestDTO.getEqcState() != null ? fT_TestDTO.getEqcState() : 0);
//        result.setLatState(fT_TestDTO.getLatState() != null ? fT_TestDTO.getLatState() : 0);
//
//        result.setOrderInfo(fT_TestDTO.getOrderInfo());
//
//        result.setFtNote(fT_TestDTO.getFtNote());
//        result.setRtNote(fT_TestDTO.getRtNote());
//        result.setEqcNote(fT_TestDTO.getEqcNote());
//        result.setLatNote(fT_TestDTO.getLatNote());
//
//        result.setFtList(FTResultAssembler.toEntities(fT_TestDTO.getFtList()));
//        result.setRtList(FTResultAssembler.toEntities(fT_TestDTO.getRtList()));
//        result.setEqcList(FTResultAssembler.toEntities(fT_TestDTO.getEqcList()));
//        result.setLatList(FTResultAssembler.toEntities(fT_TestDTO.getLatList()));

        return result;
    }

    public static List<FTComposedTestNode> toEntities(Collection<FT_TestDTO> fT_TestDTOs) {
        if (fT_TestDTOs == null) {
            return null;
        }

        List<FTComposedTestNode> results = new ArrayList<FTComposedTestNode>();
        for (FT_TestDTO each : fT_TestDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
