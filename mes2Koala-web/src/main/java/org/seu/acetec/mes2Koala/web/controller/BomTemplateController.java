package org.seu.acetec.mes2Koala.web.controller;

import help.ExcelTemplateHelper;
import help.FilenameHelper;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.BomTemplateFacade;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.dto.BomTemplateDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/BomTemplate")
public class BomTemplateController {

    @Inject
    ExcelFacade excelFacade;

    @Inject
    BomTemplateFacade bomTemplateFacade;

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

    /**
     * 从Excel文件
     *
     * @param multipartFile
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/importExcel")
    public InvokeResult importExcel(@RequestParam(value = "excel", required = false) MultipartFile multipartFile) {

        //获取模板路径与导入文件的路径
        String importPath = ExcelTemplateHelper.importPath(this.getClass());
        String filename = FilenameHelper.generateXlsxFilename(
                FilenameHelper.extractFilenameNoExtensionByFileName(multipartFile.getOriginalFilename()));
        if (!uploadFile(multipartFile, importPath, filename))
            return InvokeResult.failure("上传Excel失败");
        return excelFacade.importBomTemplate(this.getClass(), filename);
    }

    /**
     * 导出成Excel
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/exportExcel")
    public InvokeResult exportExcel(@RequestParam String ids) {
        Long[] idArray = ExcelTemplateHelper.extractIdArray(ids, ",");
        return excelFacade.exportBomTemplate(this.getClass(), idArray);
    }

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(BomTemplateDTO bomTemplateDTO) {
        return bomTemplateFacade.createBomTemplate(bomTemplateDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(BomTemplateDTO bomTemplateDTO) {
        return bomTemplateFacade.updateBomTemplate(bomTemplateDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(BomTemplateDTO bomTemplateDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<BomTemplateDTO> all = bomTemplateFacade.pageQueryBomTemplate(bomTemplateDTO, page, pagesize);
        return all;
    }

    @ResponseBody
    @RequestMapping("/findAllBom")
    public List<BomTemplateDTO> findAllBom() {
        return bomTemplateFacade.findAllBomTemplate();
    }

    @ResponseBody
    @RequestMapping("/delete")
    public InvokeResult remove(@RequestParam String ids) {
        String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            idArrs[i] = Long.parseLong(value[i]);
        }
        return bomTemplateFacade.removeBomTemplates(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return bomTemplateFacade.getBomTemplate(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        // CustomDateEditor 可以换成自己定义的编辑器。
        // 注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
}
