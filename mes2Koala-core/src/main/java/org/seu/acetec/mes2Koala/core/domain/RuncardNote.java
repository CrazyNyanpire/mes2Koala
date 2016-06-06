package org.seu.acetec.mes2Koala.core.domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.AccessType;;
/**
 * 经验证@embedable不可嵌套加入@elementcollection，因此将本类建立为实体。
 * 本实体用于存储不同产品的runcardNote
 * 
 * @author Howard
 * @version v1.0
 * @lastModifyDate 2015.12.30
 *
 */
@Entity
@Table(name = "e_runcardnote")
@Access(AccessType.PROPERTY)
public class RuncardNote extends MES2AbstractEntity{
	
	//node名称 如IQC\Baking等
	private String nodeName;
	//对应于nodeName的注意事项，使用@ElementCollection在另外的表中存储
	private List<String> nodeNote;
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	public List<String> getNodeNote() {
		return nodeNote;
	}
	public void setNodeNote(List<String> nodeNote) {
		this.nodeNote = nodeNote;
	}
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}
