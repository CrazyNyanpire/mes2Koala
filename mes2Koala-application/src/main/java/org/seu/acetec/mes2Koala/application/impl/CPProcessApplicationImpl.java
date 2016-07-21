package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.CPInfoApplication;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPNodeApplication;
import org.seu.acetec.mes2Koala.application.CPProcessApplication;
import org.seu.acetec.mes2Koala.application.CPProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.CustomerCPLotApplication;
import org.seu.acetec.mes2Koala.application.CustomerFTLotApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.TestProgramApplication;
import org.seu.acetec.mes2Koala.application.TestProgramTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.CPInfo;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.domain.CPTestingNode;
import org.seu.acetec.mes2Koala.core.domain.FTComposedTestNode;
import org.seu.acetec.mes2Koala.core.domain.FTInfo;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.FTProcess;
import org.seu.acetec.mes2Koala.core.domain.Process;
import org.seu.acetec.mes2Koala.core.domain.ProcessTemplate;
import org.seu.acetec.mes2Koala.core.domain.SBLTemplate;
import org.seu.acetec.mes2Koala.core.domain.TestProgram;
import org.seu.acetec.mes2Koala.core.domain.TestProgramTemplate;
import org.seu.acetec.mes2Koala.core.domain.TestingTemplate;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.core.enums.FTNodeState;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import java.util.*;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Named
@Transactional
public class CPProcessApplicationImpl extends GenericMES2ApplicationImpl<CPProcess> implements CPProcessApplication {

    @Inject
    CPInfoApplication cpInfoApplication;

    @Inject
    CPLotApplication cpLotApplication;

    @Inject
    CPNodeApplication cpNodeApplication;

    @Inject
    CustomerCPLotApplication customerCPLotApplication;
    
    @Inject
    ProductionScheduleApplication productionScheduleApplication;
	
    @Inject
    TestProgramTemplateApplication testProgramTemplateApplication;
    
    @Inject
    TestProgramApplication testProgramApplication;
    
    @Inject
    CPProductionScheduleApplication cpProductionScheduleApplication;
    
    @Override
    public CPProcess findByCPLotId(Long cpLotId) {
        return findOne("select o from CPProcess o right join o.cpLot e where e.id = ?", cpLotId);
    }

    /**
     * 下单成功后创建测试积点
     *
     * @param cpLotId
     * @return
     * @version 2016/4/11 LHB
     */
    @Override
    public void createCPProcess(Long cpLotId) {
    	createCPProcess(cpLotId, null);
    }

    /**
     * 兼容分批时从母批当前阶段开始生产process
     *
     * @param cpLotId
     * @param parentCPLotId 父批的Id，用来获取startIndex
     * @return
     * @version 2016/4/11 LHB copy from FTProcessApplicationImpl
     */
    @Override
    public void createCPProcess(Long cpLotId, Long parentCPLotId) {
    	int startIndex = 0;
    	/*
    	 if (parentFTLotId != null) {
             // 如果是建立子批的process，获取父批次当前的站点序号
             // 根据父批获取Process创建的起始点
             FTLot parentFTLot = ftLotApplication.get(parentFTLotId);
             FTNode node = findToStartNode(parentFTLot.getFtProcess().getFtNodes());
             startIndex = node == null ? 0 : node.getTurn();
         }
    	*/
        CPLot cpLot = cpLotApplication.get(cpLotId);
        
        // 方法没用，替代为下一句
        //CPInfo cpInfo = cpInfoApplication.findByCPLotId(cpLotId);
        //CPInfo 没有值
        CPInfo cpInfo = cpLot.getCustomerCPLot().getCpInfo();
        
        // 根据Process模板创建Process和FTNodes
        ProcessTemplate processTemplate = cpInfo.getProcessTemplate();
        //List<TestingTemplate> testingTemplates = processTemplate.getTestingTemplates();
        CPProcess process = CPProcess.instanceTemplate(processTemplate);
        process.setCpLot(cpLot);
        String[] nodeNames = extractNodeNamesByProcessTemplateContent(process.getContent());// 测试站点
        List<CPNode> cpNodes = generateCPNodes(process, startIndex, nodeNames);
        process.setCpNodes(cpNodes);

        // 修改第一个节点状态，驱动Process
        cpNodes.get(0).setState(CPNodeState.TO_START);
        cpLot.setCurrentState("待" + cpNodes.get(0).getName());

        //cpLotApplication.update(cpLot);
        create(process);
        
        // 绑定测试程序
        bindTestProgram(cpInfo, cpNodes);

        // 绑定ProductionSchedule
        bindProductionSchedule(cpNodes);
        
    }
    
    /**
     * 根据测试模板的内容提出去流程模板
     *
     * @param processContent
     * @return
     */
    public String[] extractNodeNamesByProcessTemplateContent(String processContent) {
        if (Strings.isNullOrEmpty(processContent))
            return new String[0];
        return processContent.split("\\|");
    }
    
    public List<CPNode> generateCPNodes(CPProcess process,
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
			cpNode.setCpProcess(process);
			cpNodes.add(cpNode);			
		}
		return cpNodes;
	}
    
    public void bindProductionSchedule(Collection<CPNode> cpNodes) {
        // 绑定ProductionSchedule
        for (CPNode cpNode : cpNodes) {
            if (cpNode instanceof CPTestingNode) {
                productionScheduleApplication.createNewCpSchedule(null, (CPTestingNode)cpNode);
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
    private void bindTestProgram(CPInfo cpInfo, Collection<CPNode> cpNodes) {
        // 绑定测试程序
        String expectedProductVersion = cpInfo.getCustomerProductRevision();
        List<TestProgramTemplate> testProgramTemplates = testProgramTemplateApplication.findAuthorizedTestProgramTemplateByProductId(cpInfo.getId());
        Map<String, TestProgramTemplate> nodeNameTestProgramTemplateMap = buildNodeNameTestProgramTemplateMap(testProgramTemplates, expectedProductVersion);

        List<TestProgram> testPrograms = new ArrayList<TestProgram>();
        for (CPNode cpNode : cpNodes) {
            if (cpNode instanceof CPTestingNode) {
            	CPTestingNode temp = (CPTestingNode) cpNode;
                String nodeName = temp.getName();
                if (!nodeNameTestProgramTemplateMap.containsKey(nodeName)) {
                    throw new RuntimeException("创建TestProgram失败," + nodeName + "节点未配置测试程式！");
                }
                TestProgramTemplate testProgramTemplate = nodeNameTestProgramTemplateMap.get(nodeName);
                TestProgram testProgram = TestProgram.instanceTemplate(testProgramTemplate);
                temp.setTestProgram(testProgram);  // 用于级联保存
                testProgram.setCpTestingNode(temp);
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
                nodeNameTestProgramTemplateMap.put(testProgramTemplate.getSite(), testProgramTemplate);
            }
        }
        return nodeNameTestProgramTemplateMap;
    }

	@Override
	public void deleteCPProcess(Long ftLotId) {
		CPLot cpLot = cpLotApplication.get(ftLotId);
		
		//当状态不为待Incoming时不允许删除
		if(!"待Incoming".equals(cpLot.getCurrentState())){
			throw new RuntimeException("当前站点不为-待Incoming，不允许删除！");
		}
		
		CPProcess process = cpLot.getCpProcess();
		for(CPNode cpNode : process.getCpNodes()){
			cpNode.setCpProcess(null);
			cpProductionScheduleApplication.deleteProductionScheduleByNodeId(cpNode.getId());
			cpNodeApplication.remove(cpNode);
		}
		process.setCpNodes(null);
		this.remove(process);
	}
}
