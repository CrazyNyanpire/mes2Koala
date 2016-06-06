package org.seu.acetec.mes2Koala.infra;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.seu.acetec.mes2Koala.infra.shiro.ShiroMain;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public final class EmsFetcher {
	
	//EMS地址
	private final static String EMS_ADDRESS = "http://192.168.1.44:8280";
	
	//按照EquipmentCategory查询所有Tester设备
	private final static String QUERY_ALL_TESTER = EMS_ADDRESS + "/Equipment/pageJson.koala?page=0&pagesize=10000000&equipmentCategory=tester";
	//查询所有的FTplatform
	private final static String QUERY_ALL_FT_PLATFORM = EMS_ADDRESS + "/ChangeStatus/pageJson.koala?page=0&pagesize=10000&category=FT";
	//查询所有的CPPlatform
	private final static String QUERY_ALL_CP_PLATFORM = EMS_ADDRESS + "/ChangeStatus/pageJson.koala?page=0&pagesize=10000&category=CP";
	//根据proberOrHandler找platform
	private final static String QUERY_PLATFORM_BY_PROBERORHANDLER = EMS_ADDRESS + "/ChangeStatus/pageJson.koala?page=0&pagesize=10000&hanlderOrProberNo=";
	//根据platformId获取FT测试数据
	private final static String QUERY_FT_TESTDATA_BY_PLATFORMID = EMS_ADDRESS + "/TestData/ft.koala?id=";
	//根据platformId获取CP测试数据
	private final static String QUERY_CP_TESTDATA_BY_PLATFORMID = EMS_ADDRESS + "/TestData/cp.koala?id=";
	//根据platformId获取平台信息
	private final static String QUERY_PLATFORM_BY_ID = EMS_ADDRESS + "/Platform/get/";
	//查找platform操作日志
	private final static String QUERY_PLATFORM_OPERATION_LOG = EMS_ADDRESS + "/PlatformOptionLog/pageJson.koala?page=0&pagesize=10";
	//查找platform的set up时间
	private final static String QUERY_PLATFORM_SETUP_TIME = QUERY_PLATFORM_OPERATION_LOG + "&status=SET_UP";

	private final static String server = EMS_ADDRESS + "/login.koala";
	private final static String username = "koala";
	private final static String password = "qwerty";
	
	//单例：已取消
//	private static EmsFetcher instance = new EmsFetcher();
//	public static EmsFetcher getInstance(){
//		return instance;
//	}
	//构造函数私有，该类不可实例
	private EmsFetcher() {}
	
	public static String fetch( String service ){
		ShiroMain sso = ShiroMain.getInstance();

		String result = null;
		try {
			result = sso.getTicket(server, username, password, service);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result; 

	}
	
	//查询所有的Tester
	public static String fetchAllTester() {
		return fetch(QUERY_ALL_TESTER);
	}
	
	//获取所有tester
	public static List<String> getTesterList() {
		List<String> testers = new ArrayList<>(); 
		try
		{
			JSONObject jsonObject = JSONObject.fromObject(fetchAllTester());
			JSONArray data = jsonObject.getJSONArray("data");
			List<JSONObject> jsonObjects = (List<JSONObject>) JSONArray.toCollection(data, JSONObject.class);
			for ( JSONObject object : jsonObjects ) {
				testers.add(object.getString("equipmentNo"));
			}
		} catch ( JSONException e ) {
			e.printStackTrace();
			throw new RuntimeException("JSON读取或转换错误");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("未知错误");
		}
		
		return testers;
		
	}
	
	//查询所有的FT平台
	public static String fetchAllFTPlatform() {
		return fetch(QUERY_ALL_FT_PLATFORM);
	}

	//查询所有的CP平台
	public static String fetchAllCPPlatform() {
		return fetch(QUERY_ALL_CP_PLATFORM);
	}
	
	//根据proberOrHandler查找platform
	public static String fetchPlatformByProberOrHandler( String proberOrHandlerNo ) {
		return fetch( QUERY_PLATFORM_BY_PROBERORHANDLER + proberOrHandlerNo );
	}
	
	//根据platformId和当前批次获取setup时间列表
	public static String fetchSetupTimes( Long platformId, String nowLot ) {
		return fetch( QUERY_PLATFORM_SETUP_TIME + "&platformId=" + platformId + "&nowLot=" + nowLot );
	}
	
	public static Date getSetupTime( Long platformId, String nowLot ){
		if ( null == platformId || null == nowLot ) return null;
		Date result = null;
		try {
			//取出setup记录的第一次setup时间
			JSONObject jsonObject = JSONObject.fromObject(fetchSetupTimes(platformId, nowLot));
			JSONArray data = jsonObject.getJSONArray("data");
			JSONObject firstSetupLog = data.getJSONObject(data.size() - 1);
			String temp = firstSetupLog.getString("startTime");
			result = MyDateUtils.str2Date(temp, MyDateUtils.formater);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	//根据ID查找平台信息
	public static String fetchPlatformInfoById( Long id ) {
		return fetch( QUERY_PLATFORM_BY_ID + id.toString() + ".koala" );
	}
	
	public static String getPlatformState( Long id ) {
		if ( null == id ) return null;
		String result = null;
		try {
			JSONObject jsonObject = JSONObject.fromObject( fetchPlatformInfoById(id));
			JSONObject data = jsonObject.getJSONObject("data");
			result = data.getString("status");
		} catch ( JSONException e ) {
			e.printStackTrace();
		}
		return result;
	}
	
	//根据平台id获取FT测试数据
	public static String fetchFtTestData( Long id ) {
		return fetch( QUERY_FT_TESTDATA_BY_PLATFORMID + id.toString() );
	}
	
	//根据平台id获取CP测试数据
	public static String fetchCpTestData( Long id ) {
		return fetch( QUERY_CP_TESTDATA_BY_PLATFORMID + id.toString() );
	}
	
	public static Long getFtDoneQty( Long id ) {
		if ( null == id ) return null;
		Long result = null;
		try {
			JSONObject jsonObject = JSONObject.fromObject(fetchFtTestData(id));
			JSONObject data = jsonObject.getJSONObject("data");
			//data有可能为空，即无法通过ems获取已测数量
			result = data.getLong("tested");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Long getCpDoneQty(Long id ) {
		if ( null == id ) return null;
		Long result = null;
		try {
			JSONObject jsonObject = JSONObject.fromObject(fetchCpTestData(id));
			JSONObject data = jsonObject.getJSONObject("data");
			//data有可能为空，即无法通过ems获取已测数量
			result = data.getLong("tested");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

}
