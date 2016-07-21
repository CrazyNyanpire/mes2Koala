package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;


/**
 * @author HongYu
 * @version 2016/6/6
 */
public class TSKInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String device_Name;

	private int index_X;

    private int index_Y;

    private String operator_Name;

    private String wafer_ID;

    private String lot_No;

    private String start_Time;

    private String end_Time;

    private String load_Time;

    private String unLoad_Time;
    
    private int total_Dice;
    
    private int total_Pass_Dice;
    
    private int total_Fail_Dice;
    
    private String total_Yield;
    
    private String passbin2;

    private String passbin3;

    private String passbin4;

    // 内部补偿
    private String acetec_Vi_Fail;

    // 外部补偿
    private String client_Vi_Fail;

    // 标签Good Die=Total pass Dice-内部补偿
    private int good_Die;

    // Pass Dice结果
    private String client_Final_Good_Die;
    
    // 内部补偿结果
    private String pass_Dice_Result;
    
    // 外部补偿结果
    private String client_Vi_Fail_Result;
    
    // 最终Pass Dice结果
    private String acetec_Vi_Fail_Result;
    
    // 客户Pass Dice
    private String acetec_Final_Good_Die_Result;
    
    // 客户内部补偿
    private String client_Acetec_Vi_Fail;
    
    // 客户外部补偿
    private String client_Client_Vi_Fail;
    
    // 客户最终Pass Dice
    private String client_Pass_Dice;
    
    // 文件名称
    private String fileName;
    
    private int map[][][];
    
    private int waferSize;
    
    private double standard_Orientation_Flat_Direction;
    
    private long rowSize;
    
    private long columnsize;
    
    private int tested_dice;
    
    private int tested_pass_dice;
    
    private int tested_fail_dice;
    
    private int machine_No;
    
    private int x;
    
    private int y;

	public int getIndex_X() {
		return index_X;
	}

	public void setIndex_X(int index_X) {
		this.index_X = index_X;
	}

	public int getIndex_Y() {
		return index_Y;
	}

	public void setIndex_Y(int index_Y) {
		this.index_Y = index_Y;
	}

	public String getOperator_Name() {
		return operator_Name;
	}

	public void setOperator_Name(String operator_Name) {
		this.operator_Name = operator_Name;
	}

	public String getWafer_ID() {
		return wafer_ID;
	}

	public void setWafer_ID(String wafer_ID) {
		this.wafer_ID = wafer_ID;
	}

	public String getLot_No() {
		return lot_No;
	}

	public void setLot_No(String lot_No) {
		this.lot_No = lot_No;
	}

	public String getStart_Time() {
		return start_Time;
	}

	public void setStart_Time(String start_Time) {
		this.start_Time = start_Time;
	}

	public String getEnd_Time() {
		return end_Time;
	}

	public void setEnd_Time(String end_Time) {
		this.end_Time = end_Time;
	}

	public String getLoad_Time() {
		return load_Time;
	}

	public void setLoad_Time(String load_Time) {
		this.load_Time = load_Time;
	}

	public String getUnLoad_Time() {
		return unLoad_Time;
	}

	public void setUnLoad_Time(String unLoad_Time) {
		this.unLoad_Time = unLoad_Time;
	}

	public String getTotal_Yield() {
		return total_Yield;
	}

	public void setTotal_Yield(String total_Yield) {
		this.total_Yield = total_Yield;
	}

	public String getPassbin2() {
		return passbin2;
	}

	public void setPassbin2(String passbin2) {
		this.passbin2 = passbin2;
	}

	public String getPassbin3() {
		return passbin3;
	}

	public void setPassbin3(String passbin3) {
		this.passbin3 = passbin3;
	}

	public String getPassbin4() {
		return passbin4;
	}

	public void setPassbin4(String passbin4) {
		this.passbin4 = passbin4;
	}

	public String getAcetec_Vi_Fail() {
		return acetec_Vi_Fail;
	}

	public void setAcetec_Vi_Fail(String acetec_Vi_Fail) {
		this.acetec_Vi_Fail = acetec_Vi_Fail;
	}

	public String getClient_Vi_Fail() {
		return client_Vi_Fail;
	}

	public void setClient_Vi_Fail(String client_Vi_Fail) {
		this.client_Vi_Fail = client_Vi_Fail;
	}

	public String getClient_Final_Good_Die() {
		return client_Final_Good_Die;
	}

	public void setClient_Final_Good_Die(String client_Final_Good_Die) {
		this.client_Final_Good_Die = client_Final_Good_Die;
	}

	public String getPass_Dice_Result() {
		return pass_Dice_Result;
	}

	public void setPass_Dice_Result(String pass_Dice_Result) {
		this.pass_Dice_Result = pass_Dice_Result;
	}

	public String getClient_Vi_Fail_Result() {
		return client_Vi_Fail_Result;
	}

	public void setClient_Vi_Fail_Result(String client_Vi_Fail_Result) {
		this.client_Vi_Fail_Result = client_Vi_Fail_Result;
	}

	public String getAcetec_Vi_Fail_Result() {
		return acetec_Vi_Fail_Result;
	}

	public void setAcetec_Vi_Fail_Result(String acetec_Vi_Fail_Result) {
		this.acetec_Vi_Fail_Result = acetec_Vi_Fail_Result;
	}

	public String getAcetec_Final_Good_Die_Result() {
		return acetec_Final_Good_Die_Result;
	}

	public void setAcetec_Final_Good_Die_Result(
			String acetec_Final_Good_Die_Result) {
		this.acetec_Final_Good_Die_Result = acetec_Final_Good_Die_Result;
	}

	public String getClient_Pass_Dice() {
		return client_Pass_Dice;
	}

	public void setClient_Pass_Dice(String client_Pass_Dice) {
		this.client_Pass_Dice = client_Pass_Dice;
	}

	public String getClient_Acetec_Vi_Fail() {
		return client_Acetec_Vi_Fail;
	}

	public void setClient_Acetec_Vi_Fail(String client_Acetec_Vi_Fail) {
		this.client_Acetec_Vi_Fail = client_Acetec_Vi_Fail;
	}

	public String getClient_Client_Vi_Fail() {
		return client_Client_Vi_Fail;
	}

	public void setClient_Client_Vi_Fail(String client_Client_Vi_Fail) {
		this.client_Client_Vi_Fail = client_Client_Vi_Fail;
	}

	public String getDevice_Name() {
		return device_Name;
	}

	public void setDevice_Name(String device_Name) {
		this.device_Name = device_Name;
	}

	public int getTotal_Dice() {
		return total_Dice;
	}

	public void setTotal_Dice(int total_Dice) {
		this.total_Dice = total_Dice;
	}

	public int getTotal_Pass_Dice() {
		return total_Pass_Dice;
	}

	public void setTotal_Pass_Dice(int total_Pass_Dice) {
		this.total_Pass_Dice = total_Pass_Dice;
	}

	public int getTotal_Fail_Dice() {
		return total_Fail_Dice;
	}

	public void setTotal_Fail_Dice(int total_Fail_Dice) {
		this.total_Fail_Dice = total_Fail_Dice;
	}

	public int getGood_Die() {
		return good_Die;
	}

	public void setGood_Die(int good_Die) {
		this.good_Die = good_Die;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int[][][] getMap() {
		return map;
	}

	public void setMap(int map[][][]) {
		this.map = map;
	}

	public double getStandard_Orientation_Flat_Direction() {
		return standard_Orientation_Flat_Direction;
	}

	public void setStandard_Orientation_Flat_Direction(
			double standard_Orientation_Flat_Direction) {
		this.standard_Orientation_Flat_Direction = standard_Orientation_Flat_Direction;
	}

	public long getRowSize() {
		return rowSize;
	}

	public void setRowSize(long rowSize) {
		this.rowSize = rowSize;
	}

	public long getColumnsize() {
		return columnsize;
	}

	public void setColumnsize(long columnsize) {
		this.columnsize = columnsize;
	}

	public int getTested_dice() {
		return tested_dice;
	}

	public void setTested_dice(int tested_dice) {
		this.tested_dice = tested_dice;
	}

	public int getTested_pass_dice() {
		return tested_pass_dice;
	}

	public void setTested_pass_dice(int tested_pass_dice) {
		this.tested_pass_dice = tested_pass_dice;
	}

	public int getTested_fail_dice() {
		return tested_fail_dice;
	}

	public void setTested_fail_dice(int tested_fail_dice) {
		this.tested_fail_dice = tested_fail_dice;
	}

	public int getMachine_No() {
		return machine_No;
	}

	public void setMachine_No(int machine_No) {
		this.machine_No = machine_No;
	}

	public int getWaferSize() {
		return waferSize;
	}

	public void setWaferSize(int waferSize) {
		this.waferSize = waferSize;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
