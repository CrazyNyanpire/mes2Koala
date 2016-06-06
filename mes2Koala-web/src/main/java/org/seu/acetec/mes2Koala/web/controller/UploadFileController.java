package org.seu.acetec.mes2Koala.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.openkoala.koala.commons.InvokeResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/FileUpload")
public class UploadFileController {

	@ResponseBody
	@RequestMapping(value = "/uploadLogo")
	public InvokeResult uploadLogo(@RequestParam(value = "logo", required = false) MultipartFile file,
			HttpServletRequest request) {
		//获取类文件所在的路径，为获取web应用路径做准备
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		//获取上传路径
		String path = classPath.substring(0, classPath.indexOf("WEB-INF")) + "upload/logo";

		String fileName = new Date().getTime() + "";// 使用时间值作为上传文件的命名
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
		System.out.println(path);
		if (!uploadFile(file, path, fileName + suffix))
			return InvokeResult.failure("upload Logo failed.");

		return InvokeResult.success("upload/logo/" + fileName + suffix);
	}
	
	@ResponseBody
	@RequestMapping(value = "/uploadLabel")
	public InvokeResult uploadLabel(@RequestParam(value = "label", required = false) MultipartFile file, HttpServletRequest request){
		//获取类文件所在的路径，为获取web应用路径做准备
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		//获取上传路径
		String path = classPath.substring(0, classPath.indexOf("WEB-INF")) + "upload/label";

		String fileName = file.getOriginalFilename();
		System.out.println(path);
		
		if (!uploadFile(file, path, fileName))
			return InvokeResult.failure("upload Label failed.n");

		return InvokeResult.success("upload/label/" + fileName);
	}
	
	@ResponseBody
	@RequestMapping(value = "/uploadReelCode")
	public InvokeResult uploadReelCode(@RequestParam(value = "reelCode", required = false) MultipartFile file, HttpServletRequest request){
		//获取类文件所在的路径，为获取web应用路径做准备
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		//获取上传路径
		String path = classPath.substring(0, classPath.indexOf("WEB-INF")) + "upload/reelCode";

		String fileName = file.getOriginalFilename();
		System.out.println(path);
		
		if (!uploadFile(file, path, fileName))
			return InvokeResult.failure("upload ReelCode failed.");

		return InvokeResult.success("upload/reelCode/" + fileName);
	}
	
    private static boolean uploadFile(MultipartFile multipartFile, String path, String filename) {
        File targetFile = new File(path, filename);
        // 保存
        if (!targetFile.exists() && targetFile.mkdirs()) {
            try {
                multipartFile.transferTo(targetFile);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}