package org.seu.acetec.mes2Koala.infra;

import java.util.Date;
import java.util.List;


public interface EmsFetcher {
	
	public String fetch( String service );
	
	//查询所有的Tester
	public String fetchAllTester();
	//获取所有tester
	public List<String> getTesterList();
	
	//查询所有的FT平台
	public String fetchAllFTPlatform();

	//查询所有的CP平台
	public String fetchAllCPPlatform();
	//根据proberOrHandler查找platform
	public String fetchPlatformByProberOrHandler( String proberOrHandlerNo ) ;
	
	//根据platformId和当前批次获取setup时间列表
	public String fetchSetupTimes( Long platformId, String nowLot ) ;
	
	public Date getSetupTime( Long platformId, String nowLot );
	
	//根据ID查找平台信息
	public String fetchPlatformInfoById( Long id );
	
	public String getPlatformState( Long id ) ;
	
	//根据平台id获取FT测试数据
	public String fetchFtTestData( Long id ) ;
	
	//根据平台id获取CP测试数据
	public String fetchCpTestData( Long id ) ;
	
	public Long getFtDoneQty( Long id ) ;

	public Long getCpDoneQty(Long id ) ;
	
	public String getEmsAddress();

}
