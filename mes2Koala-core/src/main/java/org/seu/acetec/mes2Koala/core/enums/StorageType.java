package org.seu.acetec.mes2Koala.core.enums;

public enum StorageType {
	IN_STORAGE,  //来料库
	FINISH_STORAGE,  //成品库
	RETURN_STORAGE; //退货品库
	
	public static String getValue(StorageType storageType) {
	    switch (storageType) {
	        case IN_STORAGE:
	            return "01";
	        case FINISH_STORAGE:
	            return "04";
	        case RETURN_STORAGE:
	        	return "05";
	        default:
	            throw new IllegalArgumentException("");
	    }
	}

	public static StorageType fromValue(String value) {
		if ( null == value )
			return null;
	    switch (value) {
	        case "01":
	            return IN_STORAGE;
	        case "04":
	            return FINISH_STORAGE;
	        case "05":
	            return RETURN_STORAGE;
	        default:
	            throw new IllegalArgumentException("");
	    }
	}
}
