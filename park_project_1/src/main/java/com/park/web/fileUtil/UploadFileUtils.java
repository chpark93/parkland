package com.park.web.fileUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	
	
	//파일 업로드 
    public static String uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {

    	S3Utils s3 = new S3Utils();
		String bucketName = "chparklandbucket";
    	
        String ori_fileName = file.getOriginalFilename(); //파일명
        byte[] fileData = file.getBytes();  //파일 데이터

        //파일명 중복 방지 
        String uuidFileName = getUuidFileName(ori_fileName);

        //파일 업로드 경로 설정
        String rootPath = getRootPath(ori_fileName, request); //기본 경로 추출
        String datePath = getDatePath(rootPath); //날짜 경로 추출, 날짜 생성

        //서버에 파일 저장
        File target = new File(rootPath + datePath, uuidFileName); //파일 객체 생성
        FileCopyUtils.copy(fileData, target); //파일 객체에 파일 데이터 복사

        //썸네일이미지 생성
        if (MediaUtils.getMediaType(ori_fileName) != null) {
            uuidFileName = makeThumbnail(rootPath, datePath, uuidFileName);
        }
        
        s3.fileUpload(bucketName, uuidFileName, fileData);

        //파일 저장 경로 치환
        return replaceFilePath(datePath, uuidFileName);
    }
    
    //ckeditor uploadFile
    public static String ckeditorUploadFile(MultipartFile file, HttpServletRequest request) throws Exception {
    	 
		String ori_fileName = file.getOriginalFilename(); //파일명
	    byte[] fileData = file.getBytes();  //파일 데이터
        
    	//파일명 중복 방지 
        String uuidFileName = getUuidFileName(ori_fileName);

        //파일 업로드 경로 설정
        String rootPath = getRootPath(ori_fileName, request); //기본 경로 추출
        String datePath = getDatePath(rootPath); //날짜 경로 추출, 날짜 생성

        //서버에 파일 저장
        File target = new File(rootPath + datePath, uuidFileName); //파일 객체 생성
        FileCopyUtils.copy(fileData, target); //파일 객체에 파일 데이터 복사
 
        //파일 저장 경로 치환
        return replaceFilePath(datePath, uuidFileName);
    }
 
    
    
    //파일 삭제
    public static void deleteFile(String fileName, HttpServletRequest request) {

        String rootPath = getRootPath(fileName, request); //기본 경로 추출

        //이미지 파일 삭제
        MediaType mediaType = MediaUtils.getMediaType(fileName);
        if (mediaType != null) {
            String ori_img = fileName.substring(0, 12) + fileName.substring(14);
            new File(rootPath + ori_img.replace('/', File.separatorChar)).delete();
        }

        //파일 삭제
        new File(rootPath + fileName.replace('/', File.separatorChar)).delete();
    }
    
    
    //파일 출력 설정
    public static HttpHeaders getHttpHeaders(String fileName) throws Exception {
    	
        MediaType mediaType = MediaUtils.getMediaType(fileName); //파일 타입 확인
        HttpHeaders httpHeaders = new HttpHeaders();

        //이미지 파일 O
        if (mediaType != null) {
            httpHeaders.setContentType(mediaType);
            return httpHeaders;
        }

        //이미지 파일 X
        fileName = fileName.substring(fileName.indexOf("_") + 1); //UUID 제거
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM); //다운로드 MIME 타입 설정
        
        //파일명 한글 인코딩
        httpHeaders.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"),
                                "ISO-8859-1")+"\"");

        return httpHeaders;
    }
    
    
    //기본 경로 
    public static String getRootPath(String fileName, HttpServletRequest request) {
    	
        String rootPath = "/resources/upload";
        MediaType mediaType = MediaUtils.getMediaType(fileName); //파일타입 확인
        
        if (mediaType != null)
            return request.getSession().getServletContext().getRealPath(rootPath + "/images"); //이미지 경로

        return request.getSession().getServletContext().getRealPath(rootPath + "/files"); //일반파일 경로
    }
    
    //날짜 폴더명
    private static String getDatePath(String uploadPath) {

        Calendar calendar = Calendar.getInstance();
        String yearPath = File.separator + calendar.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));

        makeDateDir(uploadPath, yearPath, monthPath, datePath);
        
        return datePath;
    }

    //날짜별 폴더
    private static void makeDateDir(String uploadPath, String... paths) {

        //날짜별 폴더 이미 존재시 메서드 종료
        if (new File(uploadPath + paths[paths.length - 1]).exists())
            return;

        for (String path :  paths) {
            File dirPath = new File(uploadPath + path);
            if (!dirPath.exists())
                dirPath.mkdirs();
       
        }
    }

    //파일 저장 경로 
    private static String replaceFilePath(String datePath, String fileName) {
        String savedFilePath = datePath + File.separator + fileName;
        return savedFilePath.replace(File.separatorChar, '/');
    }

    //파일명 중복방지 
    private static String getUuidFileName(String ori_fileName) {
        return UUID.randomUUID().toString() + "_" + ori_fileName;
    }
    
  

    //썸네일 생성
    private static String makeThumbnail(String uploadRootPath, String datePath, String fileName) throws Exception {

        //원본이미지 메모리상에 로딩
        BufferedImage ori_img = ImageIO.read(new File(uploadRootPath + datePath, fileName));
        
        //원본이미지 축소(Sclar)
        //썸네일
        int dw = 100;
        int dh = 100;
        
        //원본
        int ow = ori_img.getWidth();
        int oh = ori_img.getHeight();
        
        //원본 기준 썸네일 높이 계산
        int nw = ow;
        int nh = (ow * dh) / dw;
        
        //원본 높이 기준 썸네일 너비 조정
        if(nh > oh) {
        	nw = (oh * dw) / dh;
        	nh = oh;
        }
        //원본 이미지 크롭
        BufferedImage cropImg = Scalr.crop(ori_img, (ow - nw)/2, (oh-nh)/2, nw, nh);
        BufferedImage thumbImg = Scalr.resize(cropImg, dw, dh);
        //BufferedImage thumbImg = Scalr.resize(ori_img, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
        
        //썸네일 파일명
        String thumbImgName = "s_" + fileName;
        //썸네일 업로드 경로
        String fullPath = uploadRootPath + datePath + File.separator + thumbImgName;
        //썸네일  객체생성
        File newFile = new File(fullPath); 
        //썸네일 확장자 추출
        String formatName = MediaUtils.getFormatName(fileName);
        //썸네일 파일 저장
        ImageIO.write(thumbImg, formatName, newFile);

        return thumbImgName;
    }

    
    
}
