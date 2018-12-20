package com.lottery.controller;


import com.lottery.common.ResponseModel;
import com.lottery.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/fileupload")
public class FileController {

    @Value("${file.image}")
    private String image;

    @Value("${file.video}")
    private String video;

    @Value("${locationpath}")
    private String locationpath;

    //多文件上传
    @RequestMapping(value = "/multiUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel handleFileUpload(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        ServletContext servletContext = webApplicationContext.getServletContext();
        String projectPath = servletContext.getContextPath();
        String contextpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+projectPath;


        List<String> videoSuffixList = Arrays.asList(video.split(","));
        List<String> imageSuffixList = Arrays.asList(image.split(","));
        List<String> filepathnameList=new ArrayList<>();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            String filePath = locationpath;
            if (!file.isEmpty()) {

                Long filesize=file.getSize();

                try {
                    String uuid = UUID.randomUUID().toString();
                    String fileName=uuid+"_"+file.getOriginalFilename();
                    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                    if (imageSuffixList.contains(suffix)){
                        if (filesize>10*1048576)
                            return new ResponseModel(500l,"图片大小不能大于10M",null);
                        filePath+="images/";
                        filepathnameList.add(contextpath+"/file/images/"+fileName);
                    }
                    else if(videoSuffixList.contains(suffix)){
                        if (filesize>500*1048576)
                            return new ResponseModel(500l,"视频大小不能大于500M",null);
                        filePath+="videos/";
                        filepathnameList.add(contextpath+"/file/videos/"+fileName);
                    }
                    else{
                        if (filesize>100*1048576)
                            return new ResponseModel(500l,"其他类型文件大小不能大于100M",null);
                        filePath+="others/";
                        filepathnameList.add(contextpath+"/file/others/"+fileName);
                    }
                    byte[] bytes = file.getBytes();
                    FileUtil.uploadFile(bytes,filePath,fileName);
                } catch (Exception e) {
                    return new ResponseModel(500l, "第 " + i + " 个文件上传失败  ==> " + e.getMessage(),null);
                }
            } else {
                return new ResponseModel(500l, "第 " + i + " 个文件上传失败因为文件为空",null);
            }
        }
        return new ResponseModel(200l,"上传成功",filepathnameList);
    }

}
