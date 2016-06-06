package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import org.seu.acetec.mes2Koala.core.enums.CPNodeState;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
public class CPNodeDTO {
	
	private Long id;
	
	private int version;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTimestamp;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastModifyTimestamp;
	
	private String lastModifyEmployNo;
	
	private String createEmployNo;
	
	private Integer logic;
	
    private int turn;

    private CPNodeState state;

    private String name;
    
    private int cpState;
    
    private String targetNode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public CPNodeState getState() {
		return state;
	}

	public void setState(CPNodeState state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCpState() {
		return cpState;
	}

	public void setCpState(int cpState) {
		this.cpState = cpState;
	}

	public String getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(String targetNode) {
		this.targetNode = targetNode;
	}

	public String getLastModifyEmployNo() {
		return lastModifyEmployNo;
	}

	public void setLastModifyEmployNo(String lastModifyEmployNo) {
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getLastModifyTimestamp() {
		return lastModifyTimestamp;
	}

	public void setLastModifyTimestamp(Date lastModifyTimestamp) {
		this.lastModifyTimestamp = lastModifyTimestamp;
	}

	public String getCreateEmployNo() {
		return createEmployNo;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public Integer getLogic() {
		return logic;
	}

	public void setLogic(Integer logic) {
		this.logic = logic;
	}
}
