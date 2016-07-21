package org.seu.acetec.mes2Koala.web.controller;

import help.ExcelTemplateHelper;
import help.FilenameHelper;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.CPLotFacade;
import org.seu.acetec.mes2Koala.facade.CPLotNodeOperationFacade;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPQDNDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPSBLTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTQDNDTO;
import org.seu.acetec.mes2Koala.infra.MyDateUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 阙宇翔
 * @version 2016/2/14
 */
@Controller
@RequestMapping("/CPLot")
public class CPLotController extends BaseController {

    @Inject
    private CPLotFacade cpLotFacade;

    @Inject
    private ExcelFacade excelFacade;
    
    @Inject
    private CPLotNodeOperationFacade cpLotNodeOperationFacade;
    
    private static boolean uploadFile(MultipartFile multipartFile, String path, String filename) {
        File targetFile = new File(path, filename);
        if (!targetFile.exists()) {
            if (targetFile.mkdirs()) {
                try {
                    multipartFile.transferTo(targetFile);
                    return true;
                } catch (IOException e) {
                }
            }
        }
        return false;
    }

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(CPLotDTO fTLotDTO) {
        return cpLotFacade.createCPLot(fTLotDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(CPLotDTO fTLotDTO) {
        return cpLotFacade.updateCPLot(fTLotDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(CPLotDTO cpLotDTO,
                         @RequestParam int page,
                         @RequestParam int pagesize) {
        return cpLotFacade.pageQueryCPLot(cpLotDTO, page, pagesize);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public InvokeResult remove(@RequestParam String ids) {
        String[] value = ids.split(",");
        Long[] idArr = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            idArr[i] = Long.parseLong(value[i]);
        }
        return cpLotFacade.removeCPLots(idArr);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return cpLotFacade.getCPLot(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        //CustomDateEditor 可以换成自己定义的编辑器。
        //注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
    
    @ResponseBody
    @RequestMapping("/hold")
    public InvokeResult hold(CPQDNDTO cpQDNDTO) {
    	cpQDNDTO = this.createBase(cpQDNDTO);
        return cpLotNodeOperationFacade.hold(cpQDNDTO);
    }
    
    @ResponseBody
    @RequestMapping("/unhold")
    public InvokeResult unhold(CPLotDTO cpLotDTO) {
    	cpLotDTO = this.createBase(cpLotDTO);
        return cpLotNodeOperationFacade.unhold(cpLotDTO);
    }
    
    @ResponseBody
    @RequestMapping("/futureHold")
    public InvokeResult futureHold(CPQDNDTO cpQDNDTO) {
    	cpQDNDTO = this.createBase(cpQDNDTO);
        return cpLotNodeOperationFacade.futureHold(cpQDNDTO);
    }
    
    @ResponseBody
    @RequestMapping("/exportExcel")
    public InvokeResult exportExcel(HttpServletResponse response, @RequestParam String ids) {
    	Long[] idArray = ExcelTemplateHelper.extractIdArray(ids, ",");
		List cpLotArray = new ArrayList();
		for (int i = 0; i < idArray.length; i++) {
			CPLotDTO cpLotDTO = (CPLotDTO) this.get(idArray[i]).getData();
			cpLotArray.add(cpLotDTO);
		}
		String fileName = FilenameHelper.generateXlsFilename("CPLot");
        try {
        	cpLotNodeOperationFacade.exportExcel(cpLotArray,fileName);
        	return InvokeResult.success("excel/export/" + fileName);
        } catch (Exception e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
    }
    
    /**
     * 入站
     *
     * @param processId
     * @return
     */
    @ResponseBody
    @RequestMapping("/startCPNode/{processId}")
    public InvokeResult startNode(@PathVariable Long processId) {
    	CPLotDTO  cpLotDTO  = new CPLotDTO();
    	cpLotDTO = this.createBase(cpLotDTO);
        return cpLotFacade.startCPNode(processId,cpLotDTO);
    }

    /**
     * 出站
     *
     * @param processId
     * @return
     */
    @ResponseBody
    @RequestMapping("/endCPNode/{processId}")
    public InvokeResult endFTNode(@PathVariable Long processId) {
    	CPLotDTO  cpLotDTO  = new CPLotDTO();
    	cpLotDTO = this.lastModifyBase(cpLotDTO);
        return cpLotFacade.endCPNode(processId,cpLotDTO);
    }
    
    /**
     * 出站
     *
     * @param processId
     * @return
     */
    @ResponseBody
    @RequestMapping("/endCPNodeIncoming/{processId}")
    public InvokeResult endCPNodeIncoming(@PathVariable Long processId,@RequestParam String data) {
    	JSONObject jsonObjet = JSONObject.fromObject(data);
    	JSONArray waferCodes = jsonObjet.getJSONArray("waferCode");
    	CPLotDTO  cpLotDTO  = new CPLotDTO();
    	cpLotDTO = this.lastModifyBase(cpLotDTO);
        return cpLotFacade.endCPNodeIncoming(processId, waferCodes,cpLotDTO);
    }

    /**
     * 良率放行
     *
     * @param processId
     * @return
     */
    @ResponseBody
    @RequestMapping("/endFailTestNode")
    public InvokeResult endFailTestNode(@RequestParam Long processId,CPNodeDTO cpNodeDTO) {
    	cpNodeDTO = this.createBase(cpNodeDTO);
        return cpLotFacade.endFailTestNode(processId,cpNodeDTO);
    }

    /**
     * 保存站点信息
     *
     * @param processId
     * @param ftNodeDTO
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveCPNode")
    public InvokeResult saveFTNode(@RequestParam Long processId, FTNodeDTO ftNodeDTO) {
        return cpLotFacade.saveCPNode(processId, ftNodeDTO);
    }
    
    /**
     * 跳站
     *
     * @param processId
     * @param cpNodeDTO
     * @return
     */
    @ResponseBody
    @RequestMapping("/jumpCPNode")
    public InvokeResult jumpCPNode(@RequestParam Long processId,CPNodeDTO cpNodeDTO) {
    	cpNodeDTO = this.createBase(cpNodeDTO);
        return cpLotNodeOperationFacade.jumpCPNode(processId,cpNodeDTO);
    }
    
    @ResponseBody
    @RequestMapping("/splitLot")
    public InvokeResult splitLot(@RequestParam Long id,@RequestParam("childsInfo") String childsInfo) {
    	List<CPLotDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(childsInfo), CPLotDTO.class);
    	this.createBase(list.get(0));
    	CPLotDTO  cpLotDTO  = new CPLotDTO();
    	cpLotDTO = this.lastModifyBase(cpLotDTO);
        return cpLotNodeOperationFacade.separateCPLot(id, list,cpLotDTO);
    }

    @ResponseBody
    @RequestMapping("/mergeLot")
    public InvokeResult mergeLot(@RequestParam("ids") String ids,@RequestParam("motherName") String motherName) {
    	String[] id=ids.split(",");
    	CPLotDTO  cpLotDTO=new CPLotDTO();
    	cpLotDTO.setInternalLotNumber(motherName);
    	this.createBase(cpLotDTO);
        return cpLotNodeOperationFacade.mergeLot(id,cpLotDTO);
    }
    
    @ResponseBody
    @RequestMapping("/getLabelInfo/{id}")
    public InvokeResult getLabelInfo(@PathVariable Long id) {
        return cpLotNodeOperationFacade.getLabelInfo(id);
    }
    
    /**
     * childLot
     *合批时取可用(showFlag !=1)全部子批信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getChildsLot/{id}")
    public List<CPLotDTO> getChildsLot(@PathVariable Long id) {
        return cpLotFacade.getChildsLot(id);
    }
    
    @ResponseBody
    @RequestMapping("/dataCompensationChk/{id}")
    public InvokeResult dataCompensationChk(@PathVariable Long id) {
        return cpLotNodeOperationFacade.dataCompensationChk(id);
    }
    
    @ResponseBody
    @RequestMapping("/dataCompensation")
    public InvokeResult dataCompensation(@RequestParam String fileName) {
    	String classPath = this.getClass().getClassLoader().getResource("").getPath();
    	String path = classPath.substring(0, classPath.indexOf("WEB-INF")) + "upload/cpDataCompensationFile/";
        return cpLotNodeOperationFacade.dataCompensation(path + fileName);
    }
    
	@ResponseBody
	@RequestMapping("/uploadCPDataCompensationFile")
	public InvokeResult uploadLogo(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {
		//获取类文件所在的路径，为获取web应用路径做准备
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		//获取上传路径
		String path = classPath.substring(0, classPath.indexOf("WEB-INF")) + "upload/cpDataCompensationFile";
		//String fileName = new Date().getTime() + "";// 使用时间值作为上传文件的命名
		String suffix = file.getOriginalFilename();
		System.out.println(path);
		if (!uploadFile(file, path, suffix))
			return InvokeResult.failure("upload file failed.");

		return InvokeResult.success("upload/cpDataCompensationFile/" + suffix);
	}
	
    @ResponseBody
    @RequestMapping("/changePid")
    public InvokeResult dataCompensation( CPLotDTO cpLotDTO,@RequestParam String ids) {
    	cpLotDTO = this.lastModifyBase(cpLotDTO);
    	return cpLotFacade.changePid(cpLotDTO,ids);
    }
}
