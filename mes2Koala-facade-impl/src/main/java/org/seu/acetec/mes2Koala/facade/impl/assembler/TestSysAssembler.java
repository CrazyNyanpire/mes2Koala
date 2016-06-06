package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.TestSys;
import org.seu.acetec.mes2Koala.facade.dto.TestSysDTO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestSysAssembler {

	public static TestSysDTO toDTO(TestSys testSys) {
		if (testSys == null) {
			return null;
		}
		TestSysDTO result = new TestSysDTO();
		result.setId(testSys.getId());
		result.setVersion(testSys.getVersion());
		result.setCreateTimestamp(testSys.getCreateTimestamp());
		result.setLastModifyTimestamp(testSys.getLastModifyTimestamp());
		result.setCreateEmployNo(testSys.getCreateEmployNo());
		result.setLastModifyEmployNo(testSys.getLastModifyEmployNo());
		result.setLogic(testSys.getLogic());
		result.setState(testSys.getState());
		result.setPlatformNumber(testSys.getPlatformNumber());
		result.setTesterNumber(testSys.getTesterNumber());
		result.setProberOrHandlerNumber(testSys.getProberOrHandlerNumber());
		result.setTestType(testSys.getTestType());
		return result;
	}

	public static List<TestSysDTO> toDTOs(Collection<TestSys> testSyss) {
		if (testSyss == null) {
			return null;
		}
		List<TestSysDTO> results = new ArrayList<TestSysDTO>();
		for (TestSys each : testSyss) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static TestSys toEntity(TestSysDTO testSysDTO) {
		if (testSysDTO == null) {
			return null;
		}
		TestSys result = new TestSys();
		result.setId(testSysDTO.getId());
		result.setVersion(testSysDTO.getVersion());
		result.setCreateTimestamp(testSysDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(testSysDTO.getLastModifyTimestamp());
		result.setCreateEmployNo(testSysDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(testSysDTO.getLastModifyEmployNo());
		result.setLogic(testSysDTO.getLogic());
		result.setState(testSysDTO.getState());
		result.setPlatformNumber(testSysDTO.getPlatformNumber());
		result.setTesterNumber(testSysDTO.getTesterNumber());
		result.setProberOrHandlerNumber(testSysDTO.getProberOrHandlerNumber());
		result.setTestType(testSysDTO.getTestType());
		return result;
	}

	public static List<TestSys> toEntities(Collection<TestSysDTO> testSysDTOs) {
		if (testSysDTOs == null) {
			return null;
		}

		List<TestSys> results = new ArrayList<TestSys>();
		for (TestSysDTO each : testSysDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}

	@Deprecated
	public static List<TestSysDTO> emsTesterJsonToDTOs(String testerEmsJson) {
		if (null == testerEmsJson)
			return null;
		List<TestSysDTO> testSysDTOs = new ArrayList<>();
		JSONObject pageJsonObject = JSONObject.fromObject(testerEmsJson);
		JSONArray jsonArray = JSONArray.fromObject(pageJsonObject.getString("data"));
		List<JSONObject> jsonObjects = (List<JSONObject>) JSONArray.toCollection(jsonArray, JSONObject.class);
		for (JSONObject object : jsonObjects) {
			testSysDTOs.add(emsTesterJsonToDTO(object));
		}
		return testSysDTOs;
	}

	@Deprecated
	public static TestSysDTO emsTesterJsonToDTO(JSONObject object) {
		TestSysDTO testSysDTO = new TestSysDTO();

//		testSysDTO.setNumber(object.getString("equipmentNo"));
		testSysDTO.setState(object.getString("status"));
		// testSysDTO.setEmsId(object.getLong("id"));
		return testSysDTO;
	}

	public static List<TestSys> emsTestSysJsonToEntities(String testerEmsJson) {
		if (null == testerEmsJson)
			return null;
		List<TestSys> testSyses = new ArrayList<>();
		JSONObject pageJsonObject = JSONObject.fromObject(testerEmsJson);
		JSONArray jsonArray = JSONArray.fromObject(pageJsonObject.getString("data"));
		List<JSONObject> jsonObjects = (List<JSONObject>) JSONArray.toCollection(jsonArray, JSONObject.class);
		for (JSONObject object : jsonObjects) {
			testSyses.add(emsTestSysJsonToEntity(object));
		}
		return testSyses;
	}

	public static TestSys emsTestSysJsonToEntity(JSONObject object) {
		TestSys testSys = new TestSys();

		testSys.setEmsPlatformId(object.getLong("id"));
		testSys.setTesterNumber(object.getString("no"));
		testSys.setProberOrHandlerNumber(object.getString("equipmentNo"));
		testSys.setPlatformNumber(testSys.getTesterNumber() + '&' + testSys.getProberOrHandlerNumber());
		testSys.setTestType(object.getString("category"));
		testSys.setState(object.getString("status"));
		return testSys;
	}
}
