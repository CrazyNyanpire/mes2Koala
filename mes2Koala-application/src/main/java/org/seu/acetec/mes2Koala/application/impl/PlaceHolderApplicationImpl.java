package org.seu.acetec.mes2Koala.application.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.util.HSSFColor.PALE_BLUE;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.FTQDNApplication;
import org.seu.acetec.mes2Koala.application.PlaceHolderApplication;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.infra.MyDateUtils;
import org.springframework.transaction.annotation.Transactional;


@Named
@Transactional
public class PlaceHolderApplicationImpl extends GenericMES2ApplicationImpl<PlaceHolder> implements PlaceHolderApplication {

    @Inject
    FTLotApplication ftLotApplication;
    
    @Inject
    FTQDNApplication ftQDNApplication;

    @Inject
    CPLotApplication cpLotApplication;
    
    @Override
    public PlaceHolder findByFTLot(Long ftLotId) {
        FTLot ftLot = ftLotApplication.get(ftLotId);
        FTProcess ftProcess = ftLot.getFtProcess();

        List<FTNode> ftNodes = ftProcess.getFtNodes();

        PlaceHolder placeHolder = new PlaceHolder();

        String qdn = getQDN(ftLotId);

        for (int index = 0; index < ftNodes.size(); index++) {
            FTNode ftNode = ftNodes.get(index);
            //switch (ftNode.getState()) {
            //	case ENDED:  // 已经出站的跳过
            if (ftNode instanceof FTIQCNode) {
                placeHolder.setWorker(ftNode.getLastModifyEmployNo());
                placeHolder.setIQCMaterialWeight(((FTIQCNode) ftNode).getGrossWeight());
                placeHolder.setActualWeight(((FTIQCNode) ftNode).getNetWeight());
                Double diff = 0.0;
                try {
                    double w1 = Double.parseDouble(((FTIQCNode) ftNode).getGrossWeight());
                    double w2 = Double.parseDouble(((FTIQCNode) ftNode).getNetWeight());
                    diff = w1 - w2;
                } catch (Exception e) {
                }
                placeHolder.setDifferenceValue(diff.toString());
            } else if (ftNode instanceof FTBakingNode) {
                placeHolder.setOvenNumber(((FTBakingNode) ftNode).getOvenNumber());
                placeHolder.setBakingTemp(((FTBakingNode) ftNode).getOvenTemperature());
                placeHolder.setBakingTime(((FTBakingNode) ftNode).getTimeLimit());
                Date timeIn = ((FTBakingNode) ftNode).getTimeIn();
                placeHolder.setCheckInTime(timeIn == null ? "" : timeIn.toString());
                Date timeOut = ((FTBakingNode) ftNode).getTimeOut();
                placeHolder.setCheckOutTime(timeOut == null ? "" : timeOut.toString());
                placeHolder.setEnterBakingOperator(ftNode.getLastModifyEmployNo());
                placeHolder.setOutBakingOperator(ftNode.getLastModifyEmployNo());
            } else if (ftNode instanceof FTComposedTestNode) {

                placeHolder.setEQCLowYieldReport(qdn);
                placeHolder.setTesterType(((FTComposedTestNode) ftNode).getTestProgram().getTestSys());
                placeHolder.setNormalTestProgram(((FTComposedTestNode) ftNode).getTestProgram().getName());
                placeHolder.setProgramVersion(((FTComposedTestNode) ftNode).getTestProgram().getProductVersion());
                int latFlag = 0;
                List<FTTest> fList = ((FTComposedTestNode) ftNode).getTests();
                for (FTTest ftTest : fList) {
                    if (ftTest.getName().contains("LAT"))
                        latFlag = 1;
                }
                if (latFlag == 1) {
                    placeHolder.setRejectReport(qdn);
                    placeHolder.setLATOprator(ftNode.getLastModifyEmployNo());
                }
            } else if (ftNode instanceof FTPassNode) {
                if (ftNode.getName().contains("FVI")) {
                    placeHolder.setFVILowYieldReport(qdn);
                    placeHolder.setFVIOperator(ftNode.getLastModifyEmployNo());
                } else if (ftNode.getName().contains("Pack")) {
                    placeHolder.setPackingRejectReport(qdn);
                } else if (ftNode.getName().contains("FQC")) {
                    placeHolder.setFQCRejectReport(qdn);
                    placeHolder.setFQCOperator(ftNode.getLastModifyEmployNo());
                } else if (ftNode.getName().contains("OQC")) {
                    placeHolder.setOQCRejectReport(qdn);
                    placeHolder.setOQCOperator(ftNode.getLastModifyEmployNo());
                }
            }
            //break;
            //default:
            //	break;
            //}
        }
        
        placeHolder.setCustomerCode(ftLot.getCustomerDirect().getCode());
    	placeHolder.setProductName(ftLot.getCustomerProductNumber());
    	placeHolder.setProductVersion(ftLot.getCustomerProductRevision());
    	placeHolder.setAssyCompanyName(ftLot.getCustomerFTLot().getWaferManufacturer());
    	placeHolder.setAssyLot(ftLot.getCustomerFTLot().getPackageNumber());
    	placeHolder.setCustomerLot(ftLot.getCustomerFTLot().getCustomerLotNumber());
    	placeHolder.setAcetecLot(ftLot.getInternalLotNumber());
    	placeHolder.setDataCode(ftLot.getCustomerFTLot().getDateCode());
    	placeHolder.setPackageSize(ftLot.getPackageSize());//not found
    	placeHolder.setCustomerPPO(ftLot.getCustomerFTLot().getCustomerPPO());
    	placeHolder.setReceiveData(ftLot.getCustomerFTLot().getIncomingDate()==null?"":ftLot.getCustomerFTLot().getIncomingDate().toString());
    	placeHolder.setReceiveQty(ftLot.getCustomerFTLot().getIncomingQuantity()==null?"0":ftLot.getCustomerFTLot().getIncomingQuantity().toString());
    	placeHolder.setTaxType(ftLot.getCustomerFTLot().getTaxType());
    	placeHolder.setPackingType(ftLot.getCustomerFTLot().getIncomingStyle());
    	placeHolder.setShippingType(ftLot.getShipmentProductNumber());
    	placeHolder.setWaferManufacturer(ftLot.getCustomerFTLot().getFtInfo().getWaferFactory());
    	placeHolder.setKeyProductionManagerName(ftLot.getCustomerFTLot().getFtInfo().getKeyProductionManager().getName());
    	placeHolder.setKeyQuantityManagerName(ftLot.getCustomerFTLot().getFtInfo().getKeyQuantityManager().getName());
    	placeHolder.setKeyTDEManagerName(ftLot.getCustomerFTLot().getFtInfo().getKeyTDEManager().getName());
    	
        return convertPlaceHolder(placeHolder);
    }

    public String getQDN(Long ftLotId) {
        List<FTQDN> qList = ftQDNApplication.findAllDoingByFTLotId(ftLotId);
        if (qList != null && qList.size() > 0 && qList.get(0) != null)
            return qList.get(0).getQdnNo();
        return "";
    }
    
    public static PlaceHolder convertPlaceHolder(PlaceHolder placeHolder){
		//#IQC
		if(placeHolder.getWorker()==null) placeHolder.setWorker("");
		if(placeHolder.getIQCMaterialWeight()==null) placeHolder.setIQCMaterialWeight("");
		if(placeHolder.getActualWeight()==null) placeHolder.setActualWeight("");
		if(placeHolder.getDifferenceValue()==null) placeHolder.setDifferenceValue("");
		//#Baking
		if(placeHolder.getOvenNumber()==null) placeHolder.setOvenNumber("");
		if(placeHolder.getBakingTemp()==null) placeHolder.setBakingTemp("");
		if(placeHolder.getBakingTime()==null) placeHolder.setBakingTime("");
		if(placeHolder.getCheckInTime()==null) placeHolder.setCheckInTime("");
		if(placeHolder.getCheckOutTime()==null) placeHolder.setCheckOutTime("");
		if(placeHolder.getEnterBakingOperator()==null) placeHolder.setEnterBakingOperator("");
		if(placeHolder.getOutBakingOperator()==null) placeHolder.setOutBakingOperator("");
		//#FT+EQC
		if(placeHolder.getEQCLowYieldReport()==null) placeHolder.setEQCLowYieldReport("");
		if(placeHolder.getTesterType()==null) placeHolder.setTesterType("");
		if(placeHolder.getNormalTestProgram()==null) placeHolder.setNormalTestProgram("");
		if(placeHolder.getProgramVersion()==null) placeHolder.setProgramVersion("");
		//#LAT
		if(placeHolder.getRejectReport()==null) placeHolder.setRejectReport("");
		if(placeHolder.getLATOprator()==null) placeHolder.setLATOprator("");
		//#FVI
		if(placeHolder.getFVILowYieldReport()==null) placeHolder.setFVILowYieldReport("");
		if(placeHolder.getFVIOperator()==null) placeHolder.setFVIOperator("");
		//#Packing
		if(placeHolder.getPackingRejectReport()==null) placeHolder.setPackingRejectReport("");
		//#FQC
		if(placeHolder.getFQCRejectReport()==null) placeHolder.setFQCRejectReport("");
		if(placeHolder.getFQCOperator()==null) placeHolder.setFQCOperator("");
		//#OQC
		if(placeHolder.getOQCRejectReport()==null) placeHolder.setOQCRejectReport("");
		if(placeHolder.getOQCOperator()==null) placeHolder.setOQCOperator("");
		//#ADD
		if(placeHolder.getCustomerCode()==null) placeHolder.setCustomerCode("");
		if(placeHolder.getProductName()==null) placeHolder.setProductName("");
		if(placeHolder.getAssyCompanyName()==null) placeHolder.setAssyCompanyName("");
		if(placeHolder.getAssyLot()==null) placeHolder.setAssyLot("");
		if(placeHolder.getCustomerLot()==null) placeHolder.setCustomerLot("");
		if(placeHolder.getAcetecLot()==null) placeHolder.setAcetecLot("");
		if(placeHolder.getDataCode()==null) placeHolder.setDataCode("");
		if(placeHolder.getPackageSize()==null) placeHolder.setPackageSize("");
		if(placeHolder.getCustomerPPO()==null) placeHolder.setCustomerPPO("");
		if(placeHolder.getReceiveData()==null) placeHolder.setReceiveData("");
		if(placeHolder.getReceiveQty()==null) placeHolder.setReceiveQty("");
		if(placeHolder.getTaxType()==null) placeHolder.setTaxType("");
		if(placeHolder.getPackingType()==null) placeHolder.setPackingType("");
		if(placeHolder.getShippingType()==null) placeHolder.setShippingType("");
		if(placeHolder.getWaferManufacturer()==null) placeHolder.setWaferManufacturer("");
		if(placeHolder.getKeyProductionManagerName()==null) placeHolder.setKeyProductionManagerName("");
		if(placeHolder.getKeyTDEManagerName()==null) placeHolder.setKeyTDEManagerName("");
		if(placeHolder.getKeyQuantityManagerName()==null) placeHolder.setKeyQuantityManagerName("");
    	return placeHolder;
    }

	@Override
	public PlaceHolderCP findByCPLot(Long cpLotId) {
        CPLot cpLot = cpLotApplication.get(cpLotId);
        PlaceHolderCP placeHolder = new PlaceHolderCP();
        
        placeHolder.setCustomerCode(cpLot.getCustomerCPLot().getCpInfo().getCustomerDirect().getNumber());
    	placeHolder.setProductName(cpLot.getCustomerCPLot().getCpInfo().getCustomerProductNumber());
    	
    	placeHolder.setCustomerLot(cpLot.getCustomerCPLot().getCustomerLotNumber());
    	placeHolder.setAcetecLot(cpLot.getInternalLotNumber());
    	
    	placeHolder.setCustomerPPO(cpLot.getCustomerCPLot().getCustomerPPO());
    	placeHolder.setReceiveQty(cpLot.getCustomerCPLot().getIncomingQuantity()==null?"0":cpLot.getCustomerCPLot().getIncomingQuantity().toString());
    	placeHolder.setReceiveData(cpLot.getCustomerCPLot().getIncomingDate()==null?"":MyDateUtils.formaterDate(cpLot.getCustomerCPLot().getIncomingDate(), MyDateUtils.formater));
    	
    	placeHolder.setPackageSize(cpLot.getCustomerCPLot().getSize());//not found
    	placeHolder.setPackingType(cpLot.getCustomerCPLot().getIncomingStyle());
    	placeHolder.setShippingType(cpLot.getShipmentProductNumber());
    	
    	placeHolder.setKeyQuantityAuthorization(cpLot.getCustomerCPLot().getCpInfo().getKeyQuantityManager().getName());
    	placeHolder.setKeyProductionAuthorization(cpLot.getCustomerCPLot().getCpInfo().getKeyProductionManager().getName());
    	placeHolder.setKeyTDEAuthorization(cpLot.getCustomerCPLot().getCpInfo().getKeyTDEManager().getName());

    	placeHolder.setPrintDate((new Date()).toString());
    	placeHolder.setPrintDate(MyDateUtils.formaterDate(new Date(), MyDateUtils.formater));
    	placeHolder.setWaferSize(cpLot.getCustomerCPLot().getCpInfo().getWaferSize());
    	placeHolder.setMaskName(cpLot.getCustomerCPLot().getMaskName());
    	placeHolder.setGrossDie(Integer.toString(cpLot.getCustomerCPLot().getCpInfo().getGrossDie()));
    	placeHolder.setInternalProductNumber(cpLot.getCustomerCPLot().getCpInfo().getInternalProductNumber());
//    	placeHolder.setWafers(cpLot.getCustomerCPLot().getCpCustomerWafers());
		placeHolder.setWafers(cpLot.getCpWafers());
    	//fill testProgramsMap
    	Map<String, String> testPrograms = new HashMap<>();
    	for ( CPNode cpNode : cpLot.getCpProcess().getCpNodes()){
    		if ( cpNode instanceof CPTestingNode ) {
    			String temp = "";
    			try {
    				temp = ((CPTestingNode) cpNode).getTestProgram().getName();
				} catch (NullPointerException e) {
					//未绑定测试程序
				}
				testPrograms.put(cpNode.getName(), temp);
    		}
    	}
    	placeHolder.setTestPrograms(testPrograms);
    	placeHolder.setProcessContent(cpLot.getCpProcess().getContent());
		
    	return convertPlaceHolderCP(placeHolder);
	}
	
	public static PlaceHolderCP convertPlaceHolderCP(PlaceHolderCP placeHolder){
		if(placeHolder.getCustomerCode()==null) placeHolder.setCustomerCode("");
		if(placeHolder.getProductName()==null) placeHolder.setProductName("");
		
		if(placeHolder.getCustomerLot()==null) placeHolder.setCustomerLot("");
		if(placeHolder.getAcetecLot()==null) placeHolder.setAcetecLot("");
		
		if(placeHolder.getCustomerPPO()==null) placeHolder.setCustomerPPO("");
		if(placeHolder.getReceiveQty()==null) placeHolder.setReceiveQty("");
		if(placeHolder.getReceiveData()==null) placeHolder.setReceiveData("");
			
		if(placeHolder.getPackageSize()==null) placeHolder.setPackageSize("");
		if(placeHolder.getPackingType()==null) placeHolder.setPackingType("");
		if(placeHolder.getShippingType()==null) placeHolder.setShippingType("");

		if(placeHolder.getKeyQuantityAuthorization()==null) placeHolder.setKeyQuantityAuthorization("");
		if(placeHolder.getKeyProductionAuthorization()==null) placeHolder.setKeyProductionAuthorization("");
		if(placeHolder.getKeyTDEAuthorization()==null) placeHolder.setKeyTDEAuthorization("");
		
		if(placeHolder.getPrintDate()==null) placeHolder.setPrintDate("");
		if(placeHolder.getWaferSize()==null) placeHolder.setWaferSize("");
		if(placeHolder.getMaskName()==null) placeHolder.setMaskName("");
		if(placeHolder.getGrossDie()==null) placeHolder.setGrossDie("");

		return placeHolder;	
	}
}
