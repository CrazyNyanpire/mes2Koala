package org.seu.acetec.mes2Koala.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * process实体（产品管理层面）。使用字符串content存储流程节点，在下单后生成副本并以另外一种形式存储。
 * content中使用字符串“Test-”作为test站点的占位符。该实体也用于生成Runcard
 * 关联TestingTemplate M:N 其中存储test站点的详细信息
 * @author LHB Howard
 * @version v1.2
 * @lastModifyDate 2015.12.30
 *
 */
@Entity
@Table(name = "E_Process_Template")
@Access(AccessType.PROPERTY)
public class ProcessTemplate extends MES2AbstractEntity {
	
	private String name;
	private String content;
	private String handlerType;
	private String testType;
	private String allowState;
	private String Runcard;
	private Map<String, RuncardNote> note;//用于存储各个站点的注意事项（IQC FT站点除外）
	
	private List<TestingTemplate> testingTemplates = new ArrayList<TestingTemplate>();
	private List<AcetecAuthorization> acetecAuthorizations = new ArrayList<AcetecAuthorization>(); 
	private SpecialForm specialForm;

	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn( name = "note_id", referencedColumnName = "id")
	@MapKey( name = "nodeName" )
	public Map<String, RuncardNote> getNote() {
		return note;
	}

	public void setNote(Map<String, RuncardNote> note) {
		this.note = note;
	}

	@Column(name="name", unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHandlerType() {
		return handlerType;
	}

	public void setHandlerType(String handlerType) {
		this.handlerType = handlerType;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getAllowState() {
		return allowState;
	}

	public void setAllowState(String allowState) {
		this.allowState = allowState;
	}

	public String getRuncard() {
		return Runcard;
	}

	public void setRuncard(String runcard) {
		Runcard = runcard;
	}

	@ManyToMany(cascade={CascadeType.REFRESH},fetch = FetchType.EAGER)
	@JoinTable(name= "e_process_e_testing", joinColumns = {@JoinColumn(name = "process_id") },inverseJoinColumns = { @JoinColumn(name = "test_id") })
	@Fetch(FetchMode.SUBSELECT)
	public List<TestingTemplate> getTestingTemplates() {
		return testingTemplates;
	}

	public void setTestingTemplates(List<TestingTemplate> testingTemplates) {
		this.testingTemplates = testingTemplates;
	}


	@OneToMany(cascade={CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name="ProcessTemplate_ID")
	@Fetch(FetchMode.SUBSELECT)
	public List<AcetecAuthorization> getAcetecAuthorizations() {
		return acetecAuthorizations;
	}

	public void setAcetecAuthorizations(List<AcetecAuthorization> acetecAuthorizations) {
		this.acetecAuthorizations = acetecAuthorizations;
	}
	
	@OneToOne(cascade={CascadeType.REFRESH})
    @JoinColumn(name = "SpecialForm_ID",referencedColumnName="ID")	
	public SpecialForm getSpecialForm() {
		return specialForm;
	}

	public void setSpecialForm(SpecialForm specialForm) {
		this.specialForm = specialForm;
	}


	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}
