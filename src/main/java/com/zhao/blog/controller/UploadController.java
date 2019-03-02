package com.zhao.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;

@Controller
@PropertySource(value = "classpath:application.properties")
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${web.upload-path}")
    private String path;

    @PostMapping(value = "/uploadFile", produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public HashMap<String,Object> uploadfile (@RequestParam(value = "file", required = false) MultipartFile file,
                                             HttpServletResponse response){
        HashMap <String,Object> map = new HashMap <>();
        try {

            /**
             * 文件路径不存在则需要创建文件路径
             */
            File filePath=new File(path);
            if(!filePath.exists()){
                filePath.mkdirs();
            }

            //最终文件名
            String originalFilename = file.getOriginalFilename();
            String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = "show_" + System.currentTimeMillis() + fileNameSuffix.toLowerCase();
            File realFile=new File(path+File.separator+fileName);
            file.transferTo(realFile);

            //解决IFrame拒绝的问题
            response.setHeader("X-Frame-Options", "SAMEORIGIN");

            map.put("success", "上传成功");
            map.put("filename", "/blogImg/"+fileName);
        } catch (Exception e) {
            map.put("error", "上传失败");
            logger.error("uploadfile:" + e);
            e.printStackTrace();
        }finally {
            return map;
        }
    }
}
