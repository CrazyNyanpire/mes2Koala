package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * @author yuxiangque
 * @version 2016/3/23
 */
@Entity
@Table(name = "E_TEST_PROGRAM")
@Access(AccessType.PROPERTY)
public class TestProgram extends MES2AbstractEntity {

    private String name;
    private String productVersion;
    private String testSys;
    private String site;
    private boolean isYellow;
    private Integer uphReality;
    private Integer uphTheory;
    private String revision;
    private String note;
    private FTComposedTestNode ftComposedTestNode;
    private CPTestingNode cpTestingNode;
    
    /**
     * 根据测试程序模板实例化测试程序
     *
     * @param testProgramTemplate
     * @return
     */
    public static TestProgram instanceTemplate(TestProgramTemplate testProgramTemplate) {
        TestProgram testProgram = new TestProgram();
        testProgram.setName(testProgramTemplate.getName());
        testProgram.setProductVersion(testProgramTemplate.getProductVersion());
        testProgram.setTestSys(testProgramTemplate.getTestSys());
        testProgram.setSite(testProgramTemplate.getSite());
        testProgram.setYellow(testProgramTemplate.isYellow());
        testProgram.setUphReality(testProgramTemplate.getUPHReality());
        testProgram.setUphTheory(testProgramTemplate.getUPHTheory());
        testProgram.setRevision(testProgramTemplate.getRevision());
        testProgram.setNote(testProgramTemplate.getNote());
        return testProgram;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "FT_COMPOSED_TEST_NODE_ID", referencedColumnName = "FT_NODE_ID")
    public FTComposedTestNode getFtComposedTestNode() {
        return ftComposedTestNode;
    }

    public void setFtComposedTestNode(FTComposedTestNode ftComposedTestNode) {
        this.ftComposedTestNode = ftComposedTestNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getTestSys() {
        return testSys;
    }

    public void setTestSys(String testSys) {
        this.testSys = testSys;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public boolean isYellow() {
        return isYellow;
    }

    public void setYellow(boolean yellow) {
        isYellow = yellow;
    }

    public Integer getUphReality() {
        return uphReality;
    }

    public void setUphReality(int uphReality) {
        this.uphReality = uphReality;
    }

    public Integer getUphTheory() {
        return uphTheory;
    }

    public void setUphTheory(int uphTheory) {
        this.uphTheory = uphTheory;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CP_Testing_NODE_ID", referencedColumnName = "CP_NODE_ID")
	public CPTestingNode getCpTestingNode() {
		return cpTestingNode;
	}

	public void setCpTestingNode(CPTestingNode cpTestingNode) {
		this.cpTestingNode = cpTestingNode;
	}

	@Override
    public String[] businessKeys() {
        return new String[0];
    }
}
