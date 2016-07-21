package org.seu.acetec.mes2Koala.application.impl;

import javax.inject.Named;   

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options; 
import org.apache.axis2.client.ServiceClient; 
import org.seu.acetec.mes2Koala.application.WMSClientApplication;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class WMSClientApplicationImpl implements WMSClientApplication {
	private String namespace="http://ws.wms.com";
	//private String address = "http://127.0.0.1:7001/wms/services/WmsService?wsdl";
	private String  address = "http://192.168.1.58:7001/wms/services/WmsService?wsdl";
	//private String  address = "http://192.168.60.14:7001/wms/services/WmsService?wsdl";
	Long timeout=(long) 20000;//30秒
	/**
	 * 调用下单接口，生成领料信息
	 * @param linetype:测试类型  lotjson 批次信息
	 * @return 是否成功，01 success,02 fail
	 * @author Eva 
	 * @version 20160414
	 */
	@Override
	public void orderLots(String linetype, String lotjson) {
		String methodName="runCard";
		OMElement result = null;  
		try {   
			Options options = new Options();  
			options.setTimeOutInMilliSeconds(timeout);
			// 指定调用WebService的URL    
			EndpointReference targetEPR = new EndpointReference(address);  
			options.setTo(targetEPR);   
			ServiceClient sender = new ServiceClient();  
		
			sender.setOptions(options);  

			OMFactory fac = OMAbstractFactory.getOMFactory();   
			OMNamespace omNs = fac.createOMNamespace(namespace, "");  //名称空间 
			OMElement method = fac.createOMElement(methodName, omNs);  //方法名
			OMElement symbolLineType = fac.createOMElement("storageType", omNs);  //参数名
			OMElement symbolJson = fac.createOMElement("runcardJson", omNs); //参数名

			symbolLineType.addChild(fac.createOMText(symbolLineType,linetype));  
			method.addChild(symbolLineType); 
			symbolJson.addChild(fac.createOMText(symbolJson, lotjson ));  
			method.addChild(symbolJson); 
			method.build();  

			result = sender.sendReceive(method);    
		} catch (AxisFault axisFault) {  
			axisFault.printStackTrace();  
			throw new RuntimeException("调用WMS领料添加接口失败：" + result); 
		}  
	}
	/**
	 * 下单失败，回滚WMS领料
	 * @author Eva
	 * @param linetype 测试类型，testId 领料表的ID,testQTY 下单数量
	 * @version 2016-4-18
	 */
	@Override
	public void deleteOrderLot(String linetype, String testId, Long testQTY) {

		String methodName="r_mfg_in";
		OMElement result = null;  
		try {   
			Options options = new Options();  
			options.setTimeOutInMilliSeconds(timeout);
			// 指定调用WebService的URL    
			EndpointReference targetEPR = new EndpointReference(address);  
			options.setTo(targetEPR);   
			ServiceClient sender = new ServiceClient();  
			sender.setOptions(options);  

			OMFactory fac = OMAbstractFactory.getOMFactory();   
			OMNamespace omNs = fac.createOMNamespace(namespace, "");  //名称空间 
			OMElement method = fac.createOMElement(methodName, omNs);  //方法名
			OMElement symbolLineType = fac.createOMElement("type", omNs);  //参数名
			OMElement symbolId = fac.createOMElement("id", omNs); //参数名
			OMElement symbolQty = fac.createOMElement("quantity", omNs); //参数名
			symbolLineType.addChild(fac.createOMText(symbolLineType,linetype));  
			method.addChild(symbolLineType); 
			symbolId.addChild(fac.createOMText(symbolId, testId));  
			method.addChild(symbolId); 
			symbolQty.addChild(fac.createOMText(symbolQty,String.valueOf(testQTY)));  
			method.addChild(symbolQty); 
			method.build();  

			result = sender.sendReceive(method);   

		} catch (AxisFault axisFault) {  
			axisFault.printStackTrace();  
			throw new RuntimeException("调用WMS领料删除接口失败：" + result); 
		}  
	}
	 
	 /**
	  * 调用入库接口，CP Packing站点出站及FT中转库出库后
	  * @author Eva
	  * @version 2016-4-19
	  * @param linetype 测试类型 ;storagetype 库别，目前仅支持成品库；lotjson 批次的json字符串
	  */
	@Override
	public void mfgInWMS(String linetype, String storagetype, String lotjson) {
		String methodName="mfgIn";
		OMElement result = null;  
		try {   
			Options options = new Options(); 
			options.setTimeOutInMilliSeconds(timeout);
			// 指定调用WebService的URL    
			EndpointReference targetEPR = new EndpointReference(address);  
			options.setTo(targetEPR);   
			ServiceClient sender = new ServiceClient();  
			sender.setOptions(options);  

			OMFactory fac = OMAbstractFactory.getOMFactory();   
			OMNamespace omNs = fac.createOMNamespace(namespace, "");  //名称空间 
			OMElement method = fac.createOMElement(methodName, omNs);  //方法名
			OMElement symbolLineType = fac.createOMElement("type", omNs);  //参数名
			OMElement symbolstoragetype = fac.createOMElement("storageType", omNs); //参数名
			OMElement symboljson = fac.createOMElement("mfgInjson", omNs); //参数名
			symbolLineType.addChild(fac.createOMText(symbolLineType,linetype));  
			method.addChild(symbolLineType); 
			symbolstoragetype.addChild(fac.createOMText(symbolstoragetype, storagetype));  
			method.addChild(symbolstoragetype); 
			symboljson.addChild(fac.createOMText(symboljson,lotjson));  
			method.addChild(symboljson); 
			method.build();  

			result = sender.sendReceive(method);   

		} catch (AxisFault axisFault) {  
			axisFault.printStackTrace();  
			throw new RuntimeException("调用WMS入库接口失败：" + result); 
		}  

	} 
} 

