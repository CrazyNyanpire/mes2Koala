package org.seu.acetec.mes2Koala.application.impl;

import com.google.common.base.Strings;

import org.seu.acetec.mes2Koala.application.*;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.domain.Process;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.core.enums.FTNodeState;
import org.seu.acetec.mes2Koala.core.enums.SBLQuality;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.*;

@Named
@Transactional
public class FTProcessApplicationImpl extends GenericMES2ApplicationImpl<FTProcess> implements FTProcessApplication {

    @Inject
    FTInfoApplication ftInfoApplication;

    @Inject
    FTLotApplication ftLotApplication;

    @Inject
    FTNodeApplication ftNodeApplication;

    @Inject
    FTResultApplication ftResultApplication;

    @Inject
    CustomerFTLotApplication customerFTLotApplication;

    @Inject
    TestProgramTemplateApplication testProgramTemplateApplication;

    @Inject
    TestProgramApplication testProgramApplication;

    @Inject
    ProductionScheduleApplication productionScheduleApplication;
    
    @Inject
    FTProductionScheduleApplication ftProductionScheduleApplication;


    @Override
    public FTProcess findFTProcessByFTLotId(Long ftLotId) {
        return findOne("select o from FTProcess o right join o.ftLot e where e.id = ?", ftLotId);
    }

    /**
     * 下单成功后创建测试积点
     *
     * @param ftLotId
     * @return
     * @version 2016/3/6 YuxiangQue
     */
    @Override
    public void createFTProcess(Long ftLotId) {
        createFTProcess(ftLotId, null);
    }

    /**
     * 兼容分批时从母批当前阶段开始生产process
     *
     * @param ftLotId
     * @param parentFTLotId 父批的Id，用来获取startIndex
     * @return
     * @version 2016/3/4 YuxiangQue
     */
    @Override
    public void createFTProcess(Long ftLotId, Long parentFTLotId) {
        this.createFTProcess(ftLotId, parentFTLotId, -1);
    }


    /**
     * 兼容分批时从母批当前阶段开始生产process
     * 兼容重工建批选择站点
     *
     * @param ftLotId
     * @param parentFTLotId 父批的Id，用来获取startIndex
     * @return
     * @version 2016/3/4 YuxiangQue
     */
    public void createFTProcess(Long ftLotId, Long parentFTLotId, int index) {

        int startIndex = 0;
        FTLot parentFTLot = null;
        FTNode parentNowNode = null;
        if (parentFTLotId != null) {
            // 如果是建立子批的process，获取父批次当前的站点序号
            // 根据父批获取Process创建的起始点
            parentFTLot = ftLotApplication.get(parentFTLotId);
            //2016/7/5 Hongyu add-start
            for (FTNode ftNode : parentFTLot.getFtProcess().getFtNodes()) {
				if (ftNode instanceof FTComposedTestNode) {
					for (FTProductionSchedule ftProductionSchedule : ((FTComposedTestNode) ftNode)
							.getFtProductionSchedules()) {
						ftProductionSchedule.setLogic(1);
						productionScheduleApplication
								.update(ftProductionSchedule);
					}
				}
			}
            //2016/7/5 Hongyu add-end
            parentNowNode = findToStartNode(parentFTLot.getFtProcess().getFtNodes());
            startIndex = parentNowNode == null ? 0 : parentNowNode.getTurn();
        }
        //index 不为-1时开始站点为传入的index
        if (index > -1) {
            startIndex = index;
        }

        FTLot ftLot = ftLotApplication.get(ftLotId);
        FTInfo ftInfo = ftInfoApplication.findByFTLotId(ftLotId);

        // 根据Process模板创建Process和FTNodes
        ProcessTemplate processTemplate = ftInfo.getProcessTemplate();
        List<TestingTemplate> testingTemplates = processTemplate.getTestingTemplates();
        FTProcess process = FTProcess.instanceTemplate(processTemplate);
        process.setFtLot(ftLot);
        String[] nodeNames = extractNodeNamesByProcessTemplateContent(process.getContent());// 测试站点
        Map<String, String[]> testingNodeNameTestNameMap = buildTestingNodeNameTestTemplateMap(testingTemplates);// 测试站点名称与其对应是测试映射
        List<FTNode> ftNodes=null;
        //update by alpha 2016/07/13
        if(parentFTLot!=null)
        {
        	FTInfo parentFTinfo =ftInfoApplication.findByFTLotId(parentFTLotId);
        	Map<String, List<SBLTemplate>> testingNodeNameSBLTemplateMap = buildTestingNameSBLTemplateMap(parentFTinfo.getSblTemplates());// 测试节点名称到测试SBL的映射
            Map<String, List<EQCSetting>> testingNodeNameEQCTemplateMap = buildTestingNameEQCTemplateMap(parentFTinfo.getEqcSettings());// 测试节点名称到测试SBL的映射
            ftNodes = generateFTNodes(process, startIndex, nodeNames, testingNodeNameTestNameMap, testingNodeNameSBLTemplateMap, testingNodeNameEQCTemplateMap);
        }
        else{
        	Map<String, List<SBLTemplate>> testingNodeNameSBLTemplateMap = buildTestingNameSBLTemplateMap(ftInfo.getSblTemplates());// 测试节点名称到测试SBL的映射
            Map<String, List<EQCSetting>> testingNodeNameEQCTemplateMap = buildTestingNameEQCTemplateMap(ftInfo.getEqcSettings());// 测试节点名称到测试SBL的映射
            ftNodes = generateFTNodes(process, startIndex, nodeNames, testingNodeNameTestNameMap, testingNodeNameSBLTemplateMap, testingNodeNameEQCTemplateMap);
        }
        
        process.setFtNodes(ftNodes);

        // 修改第一个节点状态，驱动Process
        
        if(parentFTLot != null){
        	ftLot.setCurrentState(parentFTLot.getCurrentState());
        	//ftNodes.get(startIndex).setState(parentNowNode.getState());
        	ftNodes.get(0).setState(parentNowNode.getState());
        }else{
            ftLot.setCurrentState("待" + ftNodes.get(startIndex).getName());
            //ftNodes.get(startIndex).setState(FTNodeState.TO_START);
            ftNodes.get(0).setState(FTNodeState.TO_START);
        }
        ftLotApplication.update(ftLot);
        create(process);

        // 绑定测试程序
        bindTestProgram(ftInfo, ftNodes);

        // 绑定ProductionSchedule
        bindProductionSchedule(ftNodes);
    }
    
    
    private void bindProductionSchedule(Collection<FTNode> ftNodes) {
        // 绑定ProductionSchedule
        for (FTNode ftNode : ftNodes) {
            if (ftNode instanceof FTComposedTestNode) {
                productionScheduleApplication.createNewFtSchedule(null, ftNode.getId());
//                break;
            }
        }
    }

    /**
     * 绑定测试程序
     *
     * @param ftInfo
     * @param ftNodes
     * @version 2016/3/22 YuxiangQue
     * @version 2016/3/28 YuxiangQue
     */
    private void bindTestProgram(FTInfo ftInfo, Collection<FTNode> ftNodes) {
        // 绑定测试程序
        String expectedProductVersion = ftInfo.getCustomerProductRevision();
        List<TestProgramTemplate> testProgramTemplates = testProgramTemplateApplication.findAuthorizedTestProgramTemplateByProductId(ftInfo.getId());
        Map<String, TestProgramTemplate> nodeNameTestProgramTemplateMap = buildNodeNameTestProgramTemplateMap(testProgramTemplates, expectedProductVersion);

        List<TestProgram> testPrograms = new ArrayList<TestProgram>();
        for (FTNode ftNode : ftNodes) {
            if (ftNode instanceof FTComposedTestNode) {
                FTComposedTestNode temp = (FTComposedTestNode) ftNode;
                String nodeName = temp.getName();
                if (!nodeNameTestProgramTemplateMap.containsKey(nodeName)) {
                	continue;
                    //throw new RuntimeException("创建TestProgram失败," + nodeName + "节点未配置测试程式！");
                }
                TestProgramTemplate testProgramTemplate = nodeNameTestProgramTemplateMap.get(nodeName);
                TestProgram testProgram = TestProgram.instanceTemplate(testProgramTemplate);
                temp.setTestProgram(testProgram);  // 用于级联保存
                testProgram.setFtComposedTestNode(temp);
                testPrograms.add(testProgram);
            }
        }
        testProgramApplication.createTestPrograms(testPrograms);
    }


    private Map<String, TestProgramTemplate> buildNodeNameTestProgramTemplateMap(List<TestProgramTemplate> testProgramTemplates, String expectedProductVersion) {
        // 升序排列，时间大的在后面，这样装进map时最终结果会是最新的
        Collections.sort(testProgramTemplates, new Comparator<TestProgramTemplate>() {
            @Override
            public int compare(TestProgramTemplate o1, TestProgramTemplate o2) {
                Date t1 = o1.getAcetecAuthorizations().get(0).getLastModifyTimestamp();
                Date t2 = o2.getAcetecAuthorizations().get(0).getLastModifyTimestamp();
                return t1.getTime() - t2.getTime() > 0 ? 1 : -1;
            }
        });
        Map<String, TestProgramTemplate> nodeNameTestProgramTemplateMap = new HashMap<>();
        for (TestProgramTemplate testProgramTemplate : testProgramTemplates) {
            if (Objects.equals(testProgramTemplate.getProductVersion(), expectedProductVersion)) {
                // FT产品信息管理 测试程序维护 自动捞取测试程序管理中授权时间最新的测试程序 作为该产品当前使用的测试程序
                nodeNameTestProgramTemplateMap.put(testProgramTemplate.getSite(), testProgramTemplate);
            }
        }
        return nodeNameTestProgramTemplateMap;
    }


    /**
     * 根据SBL的模板的绑定节点名称，创建绑定节点到对应其若干模板的映射
     *
     * @param sblTemplates
     * @return
     */
    private Map<String, List<SBLTemplate>> buildTestingNameSBLTemplateMap(List<SBLTemplate> sblTemplates) {
        Map<String, List<SBLTemplate>> nameSBLTemplateMap = new HashMap<>();
        for (SBLTemplate sblTemplate : sblTemplates) {
            String nodeName = sblTemplate.getNodeName();
            if (Strings.isNullOrEmpty(nodeName))
                continue;
            if (!nameSBLTemplateMap.containsKey(nodeName))
                nameSBLTemplateMap.put(nodeName, new ArrayList<SBLTemplate>());
            nameSBLTemplateMap.get(nodeName).add(sblTemplate);
        }
        return nameSBLTemplateMap;
    }

    /**
     * 根据EQC的模板的绑定节点名称，创建绑定节点到对应其若干模板的映射
     *
     * @param eqcTemplates
     * @return
     */
    private Map<String, List<EQCSetting>> buildTestingNameEQCTemplateMap(List<EQCSetting> eqcTemplates) {
        Map<String, List<EQCSetting>> nameEQCTemplateMap = new HashMap<>();
        for (EQCSetting eqcTemplate : eqcTemplates) {
            String nodeName = eqcTemplate.getNodeName();
            if (Strings.isNullOrEmpty(nodeName))
                continue;
            if (!nameEQCTemplateMap.containsKey(nodeName))
                nameEQCTemplateMap.put(nodeName, new ArrayList<EQCSetting>());
            nameEQCTemplateMap.get(nodeName).add(eqcTemplate);
        }
        return nameEQCTemplateMap;
    }

    /**
     * 根据测试模板的内容提出去流程模板
     *
     * @param processContent
     * @return
     */
    private String[] extractNodeNamesByProcessTemplateContent(String processContent) {
        if (Strings.isNullOrEmpty(processContent))
            return new String[0];
        return processContent.split("\\|");
    }

    /**
     * 找到出现正准备进站的节点，没找到返回null
     *
     * @param ftNodes
     * @return
     */
    public FTNode findToStartNode(List<FTNode> ftNodes) {
        Collections.sort(ftNodes);  // 根据turn排序
        for (int index = 0; index < ftNodes.size(); ++index) {
            FTNode ftNode = ftNodes.get(index);
            if (ftNode.getState() == FTNodeState.TO_START
					|| ftNode.getState() == FTNodeState.STARTED) {
				return ftNode;
			}
        }
        return null;
    }

    /**
     * 创建 组合节点名与其对应的测试模板的Map
     *
     * @param testingTemplates
     * @return
     */
    private Map<String, String[]> buildTestingNodeNameTestTemplateMap(List<TestingTemplate> testingTemplates) {
        Map<String, String[]> testNodeNameTestTemplateMap = new HashMap<>();
        for (TestingTemplate testTemplate : testingTemplates) {
            testNodeNameTestTemplateMap.put("Test-" + testTemplate.getName(), testTemplate.getContent().split("\\|"));
        }
        return testNodeNameTestTemplateMap;
    }

    /**
     * 根据process，起始点，等参数创建FTNode
     *
     * @param process                         流程
     * @param startIndex                      起始下标
     * @param nodeNames                       流程模板
     * @param composedTestNameTestTemplateMap 测试模板
     * @param nameSBLTemplateMap              bin模板
     * @return
     */
    private List<FTNode> generateFTNodes(FTProcess process,
                                         int startIndex,
                                         String[] nodeNames,
                                         Map<String, String[]> composedTestNameTestTemplateMap,
                                         Map<String, List<SBLTemplate>> nameSBLTemplateMap,
                                         Map<String, List<EQCSetting>> nameEQCTemplateMap) {
        List<FTNode> ftNodes = new ArrayList<FTNode>();

        int nodeTurn = startIndex;
        for (int index = startIndex; index < nodeNames.length; index++) {
            String nodeName = nodeNames[index];
            FTNode ftNode = FTNode.instanceFTNode(nodeName);
            // setup common properties
            FTResult ftResult = new FTResult();
            ftNode.setName(nodeName);
            ftNode.setTurn(nodeTurn++);
            ftNode.setState(FTNodeState.UNREACHED);
            ftNode.setResult(ftResult);
            ftNode.setFtProcess(process);
            ftNodes.add(ftNode);

            // setup for FTComposedTestNode
            if (ftNode instanceof FTComposedTestNode) {
                FTComposedTestNode ftNode1 = (FTComposedTestNode) ftNode;
                List<FTTest> tests = new ArrayList<>();
                ftNode1.setTests(tests);
                // 节点需要SBL，binFilter构造需要bin过滤
                List<Integer> binFilter = new ArrayList<>();
                if (nameSBLTemplateMap.containsKey(nodeName)) {
                    // 根据SBL模板创建SBL实体与对应FTTest绑定
                    List<SBLTemplate> templates = nameSBLTemplateMap.get(nodeName);
                    List<SBL> sbls = new ArrayList<>();
                    for (SBLTemplate template : templates) {
                        SBL sbl = new SBL();
                        sbl.setQuality(template.getBinQuality());
                        sbl.setSite(template.getSite());
                        sbl.setLowerLimit(template.getLowerLimit());
                        sbl.setUpperLimit(template.getUpperLimit());
                        sbl.setFtNode(ftNode);
                        sbl.setNodeName(nodeName);
                        // 处理bin
                        String binType = template.getBinType();
                        binType = binType.startsWith("Bin") ? binType.substring(3) : binType;
                        sbl.setType(binType);
                        int binIndex = Integer.valueOf(binType) - 1;
                        binFilter.add(binIndex);
                        sbls.add(sbl);
                    }
                    ftNode.setSbls(sbls);
                }
                if (nameEQCTemplateMap.containsKey(nodeName)) {
                    // 根据SBL模板创建SBL实体与对应FTTest绑定
                    List<EQCSetting> templates = nameEQCTemplateMap.get(nodeName);
                    List<EQC> eqcs = new ArrayList<>();
                    for (EQCSetting template : templates) {
                        EQC eqc = new EQC();
                        eqc.setQty(template.getQty());
                        eqc.setLowerLimit(template.getLowerLimit());
                        eqc.setUpperLimit(template.getUpperLimit());
                        eqc.setFtNode(ftNode);
                        eqcs.add(eqc);
                    }
                    ftNode.setEqcs(eqcs);
                }

                int testTurn = 0;
                String[] testTemplate = composedTestNameTestTemplateMap.get(nodeName);
                if(testTemplate == null){
                	throw new RuntimeException("站点" + nodeName +"没有配置测试节点！");
                }
                for (String testNodeName : testTemplate) {
                    if (testNodeName.startsWith("FT") || testNodeName.startsWith("RT") ||
                            testNodeName.startsWith("EQC") || testNodeName.startsWith("LAT")) {
                        FTTest test = new FTTest();
                        FTResult testResult = new FTResult();
                        test.setName(testNodeName);
                        test.setTurn(testTurn);
                        test.setResult(testResult);
                        test.setFtComposedTestNode(ftNode1);
                        tests.add(test);
                        ++testTurn;
                    }
                }
                int[] emptyBins = FTResult.emptyBins();
                // 设置每个组合测试的finalYield的bin
                for (Integer binIndex : binFilter) {
                    emptyBins[binIndex] = 0;
                }
                FTResult.setBins(ftResult, emptyBins);
                // 设置每个子测试的bin
                for (FTTest test : tests) {
                    FTResult.setBins(test.getResult(), emptyBins);
                }
            } else if (ftNode instanceof FTFinishNode) {
                // 找到Finish站点
                FTFinishNode ftFinishNode = null;
                for (FTNode ftNode2 : ftNodes) {
                    if (ftNode2 instanceof FTFinishNode) {
                        ftFinishNode = (FTFinishNode) ftNode;
                        break;
                    }
                }
                //update 2016/07/13/Alpha
                //ftFinishNode = (FTFinishNode) ftNode;
                if (ftFinishNode != null) {
                    //  Finish站点继承所有测试站点的bin别设定
                    List<SBL> sbls = new ArrayList<>();
                    for (FTNode ftComposedTestNode : ftNodes) {
                        if (!(ftComposedTestNode instanceof FTComposedTestNode)) {
                            continue;
                        }
                        List<SBL> sbls1 = ftComposedTestNode.getSbls();
                        if (sbls1 == null) {
                            continue;
                        }
                        for (SBL sbl : sbls1) {
                            SBL cloneSBL = new SBL();
                            BeanUtils.copyProperties(sbl, cloneSBL);
                            cloneSBL.setFtNode(ftFinishNode);
                            sbls.add(cloneSBL);
                        }
                    }
                    // 更新FTResult
                    ftFinishNode.setSbls(sbls);
                    int[] bins = FTResult.getBins(ftResult);
                    for (SBL sbl : sbls) {
                        bins[Integer.parseInt(sbl.getType()) - 1] = 0;
                    }
                    FTResult.setBins(ftResult, bins);

                    FTStatistics statistics = new FTStatistics();
                    for (SBL sbl : sbls) {
                        String quality = sbl.getQuality().toString();
                        String site = sbl.getSite();
                        String nodesName = sbl.getNodeName();

                        String flag = "";
                        if (quality.equals("PASS"))
                            flag = "PASS" + site;
                        else
                            flag = nodesName + site;

                        if (statistics.getSite1Quality().equals(flag)) {
                            String sName = statistics.getSite1Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite1Name(sName);
                        } else if (statistics.getSite1Quality().equals("")) {
                            statistics.setSite1Quality(flag);
                            statistics.setSite1Name(sbl.getType());
                            statistics.setSite1Num("0");
                        } else if (statistics.getSite2Quality().equals(flag)) {
                            String sName = statistics.getSite2Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite2Name(sName);
                        } else if (statistics.getSite2Quality().equals("")) {
                            statistics.setSite2Quality(flag);
                            statistics.setSite2Name(sbl.getType());
                            statistics.setSite2Num("0");
                        } else if (statistics.getSite3Quality().equals(flag)) {
                            String sName = statistics.getSite3Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite3Name(sName);
                        } else if (statistics.getSite3Quality().equals("")) {
                            statistics.setSite3Quality(flag);
                            statistics.setSite3Name(sbl.getType());
                            statistics.setSite3Num("0");
                        } else if (statistics.getSite4Quality().equals(flag)) {
                            String sName = statistics.getSite4Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite4Name(sName);
                        } else if (statistics.getSite4Quality().equals("")) {
                            statistics.setSite4Quality(flag);
                            statistics.setSite4Name(sbl.getType());
                            statistics.setSite4Num("0");
                        } else if (statistics.getSite5Quality().equals(flag)) {
                            String sName = statistics.getSite5Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite5Name(sName);
                        } else if (statistics.getSite5Quality().equals("")) {
                            statistics.setSite5Quality(flag);
                            statistics.setSite5Name(sbl.getType());
                            statistics.setSite5Num("0");
                        } else if (statistics.getSite6Quality().equals(flag)) {
                            String sName = statistics.getSite6Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite6Name(sName);
                        } else if (statistics.getSite6Quality().equals("")) {
                            statistics.setSite6Quality(flag);
                            statistics.setSite6Name(sbl.getType());
                            statistics.setSite6Num("0");
                        } else if (statistics.getSite7Quality().equals(flag)) {
                            String sName = statistics.getSite7Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite7Name(sName);
                        } else if (statistics.getSite7Quality().equals("")) {
                            statistics.setSite7Quality(flag);
                            statistics.setSite7Name(sbl.getType());
                            statistics.setSite7Num("0");
                        } else if (statistics.getSite8Quality().equals(flag)) {
                            String sName = statistics.getSite8Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite8Name(sName);
                        } else if (statistics.getSite8Quality().equals("")) {
                            statistics.setSite8Quality(flag);
                            statistics.setSite8Name(sbl.getType());
                            statistics.setSite8Num("0");
                        } else if (statistics.getSite9Quality().equals(flag)) {
                            String sName = statistics.getSite9Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite9Name(sName);
                        } else if (statistics.getSite9Quality().equals("")) {
                            statistics.setSite9Quality(flag);
                            statistics.setSite9Name(sbl.getType());
                            statistics.setSite9Num("0");
                        } else if (statistics.getSite10Quality().equals(flag)) {
                            String sName = statistics.getSite10Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite10Name(sName);
                        } else if (statistics.getSite10Quality().equals("")) {
                            statistics.setSite10Quality(flag);
                            statistics.setSite10Name(sbl.getType());
                            statistics.setSite10Num("0");
                        } else if (statistics.getSite11Quality().equals(flag)) {
                            String sName = statistics.getSite11Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite11Name(sName);
                        } else if (statistics.getSite11Quality().equals("")) {
                            statistics.setSite11Quality(flag);
                            statistics.setSite11Name(sbl.getType());
                            statistics.setSite11Num("0");
                        } else if (statistics.getSite12Quality().equals(flag)) {
                            String sName = statistics.getSite12Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite12Name(sName);
                        } else if (statistics.getSite12Quality().equals("")) {
                            statistics.setSite12Quality(flag);
                            statistics.setSite12Name(sbl.getType());
                            statistics.setSite12Num("0");
                        } else if (statistics.getSite13Quality().equals(flag)) {
                            String sName = statistics.getSite13Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite13Name(sName);
                        } else if (statistics.getSite13Quality().equals("")) {
                            statistics.setSite13Quality(flag);
                            statistics.setSite13Name(sbl.getType());
                            statistics.setSite13Num("0");
                        } else if (statistics.getSite14Quality().equals(flag)) {
                            String sName = statistics.getSite14Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite14Name(sName);
                        } else if (statistics.getSite14Quality().equals("")) {
                            statistics.setSite14Quality(flag);
                            statistics.setSite14Name(sbl.getType());
                            statistics.setSite14Num("0");
                        } else if (statistics.getSite15Quality().equals(flag)) {
                            String sName = statistics.getSite15Name();
                            if (!quality.equals("PASS")) {
                                sName += " " + sbl.getType();
                            }
                            statistics.setSite15Name(sName);
                        } else if (statistics.getSite15Quality().equals("")) {
                            statistics.setSite15Quality(flag);
                            statistics.setSite15Name(sbl.getType());
                            statistics.setSite15Num("0");
                        }

                    	/*

                    	int site = Integer.parseInt(sbl.getSite());
                    	
                    	String name ;
                    	switch(site){
	                    	case 1:
	                    		statistics.setSite1Quality(SBLQuality.getStringValue(sbl.getQuality()));
	                    		name = statistics.getSite1Name();
	                    		if(name.equals("")) 
	                    			name = name + sbl.getType();
	                    		else
	                    			name = name + " " + sbl.getType();
	                    		statistics.setSite1Name(name);
	                    		break;
	                    	case 2:
	                    		statistics.setSite2Quality(SBLQuality.getStringValue(sbl.getQuality()));
	                    		name = statistics.getSite2Name();
	                    		if(name.equals("")) 
	                    			name = name + sbl.getType();
	                    		else
	                    			name = name + " " + sbl.getType();
	                    		statistics.setSite2Name(name);
	                    		break;
	                    	case 3:
	                    		statistics.setSite3Quality(SBLQuality.getStringValue(sbl.getQuality()));
	                    		name = statistics.getSite3Name();
	                    		if(name.equals("")) 
	                    			name = name + sbl.getType();
	                    		else
	                    			name = name + " " + sbl.getType();
	                    		statistics.setSite3Name(name);
	                    		break;
	                    	case 4:
	                    		statistics.setSite4Quality(SBLQuality.getStringValue(sbl.getQuality()));
	                    		name = statistics.getSite4Name();
	                    		if(name.equals("")) 
	                    			name = name + sbl.getType();
	                    		else
	                    			name = name + " " + sbl.getType();
	                    		statistics.setSite4Name(name);
	                    		break;
	                    	case 5:
	                    		statistics.setSite5Quality(SBLQuality.getStringValue(sbl.getQuality()));
	                    		name = statistics.getSite5Name();
	                    		if(name.equals("")) 
	                    			name = name + sbl.getType();
	                    		else
	                    			name = name + " " + sbl.getType();
	                    		statistics.setSite5Name(name);
	                    		break;
	                    	default:
	                    		break;
                    	}
                    	*/
                    }

                    ftNode.setStatistics(statistics);
                }
            } else {
                List<SBL> sbls = new ArrayList<>();
                int[] bins = FTResult.getBins(ftResult);
                for (int binIndex = 0; binIndex < 8; ++binIndex) {
                    SBL sbl = new SBL();
                    sbl.setQuality(binIndex == 0 ? SBLQuality.PASS : SBLQuality.FAIL);
                    sbl.setLowerLimit(0);
                    sbl.setUpperLimit(100);
                    sbl.setFtNode(ftNode);
                    // 处理bin
                    sbl.setType(String.valueOf(binIndex + 1));
                    bins[binIndex] = 0;
                    sbls.add(sbl);
                }
                FTResult.setBins(ftResult, bins);
                ftNode.setSbls(sbls);
            }
        }
        return ftNodes;
    }


    private List<CPNode> generateCPNodes(Process process,
                                         int startIndex,
                                         String[] nodeNames) {
        List<CPNode> cpNodes = new ArrayList<CPNode>();
        int nodeTurn = startIndex;
        for (int index = startIndex; index < nodeNames.length; index++) {
            String nodeName = nodeNames[index];
            CPNode cpNode = CPNode.instanceCPNode(nodeName);
            cpNode.setName(nodeName);
            cpNode.setTurn(++nodeTurn);
            cpNode.setState(CPNodeState.UNREACHED);
            cpNodes.add(cpNode);
        }
        return cpNodes;
    }

	@Override
	public void deleteFTProcess(Long ftLotId) {
		FTLot ftLot = ftLotApplication.get(ftLotId);
		
		//当状态不为待IQC时不允许删除
		if(!"待IQC".equals(ftLot.getCurrentState())){
			throw new RuntimeException("当前站点不为-待IQC，不允许删除！");
		}
		
		FTProcess process = ftLot.getFtProcess();
		for(FTNode ftNode : process.getFtNodes()){
			ftNode.setFtProcess(null);
			ftProductionScheduleApplication.deleteProductionScheduleByNodeId(ftNode.getId());
			ftNodeApplication.remove(ftNode);
		}
		process.setFtNodes(null);
		this.remove(process);
	}

}
