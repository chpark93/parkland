package com.park.web.file.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;
import com.park.web.fileUtil.UploadFileUtils;

@Controller
@RequestMapping("/ckeditorUpload")
public class CkeditorUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(CkeditorUploadController.class);
	
	
	@RequestMapping(value="/ckeditorUpload", method=RequestMethod.POST)
    public void ckeditorUpload(HttpServletRequest request, HttpServletResponse response,
        MultipartHttpServletRequest multiFile ) throws Exception {
        
		//JsonObject json = new JsonObject();
		PrintWriter printWriter = null;
		OutputStream os = null;
      
		//헤더 설정
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        MultipartFile file = multiFile.getFile("upload");
    
       try {
    	   String fileName = file.getName();
    	   byte[] bytes = file.getBytes();
    	   
    	   String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload");
    	   File uploadFile = new File(uploadPath);
    	   
    	   if(!uploadFile.exists()){
    		   uploadFile.mkdirs();
    		   
    	   } 
    	   
    	   fileName = UUID.randomUUID().toString();
    	   uploadPath = uploadPath + "/" + fileName;
    	   os = new FileOutputStream(new File(uploadPath));
    	   os.write(bytes);
    	   
    	   printWriter = response.getWriter();
           response.setContentType("text/html");
           String fileUrl = request.getContextPath() + "/resources/upload/" + fileName;
           
           String callback =request.getParameter("CKEditorFuncNum");
           String message="'이미지를 업로드 했습니다.'";
           
           
           StringBuffer sbuffer =new StringBuffer();
           sbuffer.append("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(");         
           sbuffer.append(callback);
           sbuffer.append(", '");
           sbuffer.append(fileUrl);
           sbuffer.append(" ' , " + message);
           sbuffer.append(") </script>");
    
           printWriter.println(sbuffer.toString());
           printWriter.flush();
    	   
       }
       catch(Exception e) {
    	   e.printStackTrace();
       }
       finally{
           if(os != null){
               os.close();
           }
           if(printWriter != null){
               printWriter.close();
           }		
		}

          
             
    }
      
  
}