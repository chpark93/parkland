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
	
	
	//���� ���ε� 
    public static String uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {

    	S3Utils s3 = new S3Utils();
		String bucketName = "chparklandbucket";
    	
        String ori_fileName = file.getOriginalFilename(); //���ϸ�
        byte[] fileData = file.getBytes();  //���� ������

        //���ϸ� �ߺ� ���� 
        String uuidFileName = getUuidFileName(ori_fileName);

        //���� ���ε� ��� ����
        String rootPath = getRootPath(ori_fileName, request); //�⺻ ��� ����
        String datePath = getDatePath(rootPath); //��¥ ��� ����, ��¥ ����

        //������ ���� ����
        File target = new File(rootPath + datePath, uuidFileName); //���� ��ü ����
        FileCopyUtils.copy(fileData, target); //���� ��ü�� ���� ������ ����

        //������̹��� ����
        if (MediaUtils.getMediaType(ori_fileName) != null) {
            uuidFileName = makeThumbnail(rootPath, datePath, uuidFileName);
        }
        
        s3.fileUpload(bucketName, uuidFileName, fileData);

        //���� ���� ��� ġȯ
        return replaceFilePath(datePath, uuidFileName);
    }
    
    //ckeditor uploadFile
    public static String ckeditorUploadFile(MultipartFile file, HttpServletRequest request) throws Exception {
    	 
		String ori_fileName = file.getOriginalFilename(); //���ϸ�
	    byte[] fileData = file.getBytes();  //���� ������
        
    	//���ϸ� �ߺ� ���� 
        String uuidFileName = getUuidFileName(ori_fileName);

        //���� ���ε� ��� ����
        String rootPath = getRootPath(ori_fileName, request); //�⺻ ��� ����
        String datePath = getDatePath(rootPath); //��¥ ��� ����, ��¥ ����

        //������ ���� ����
        File target = new File(rootPath + datePath, uuidFileName); //���� ��ü ����
        FileCopyUtils.copy(fileData, target); //���� ��ü�� ���� ������ ����
 
        //���� ���� ��� ġȯ
        return replaceFilePath(datePath, uuidFileName);
    }
 
    
    
    //���� ����
    public static void deleteFile(String fileName, HttpServletRequest request) {

        String rootPath = getRootPath(fileName, request); //�⺻ ��� ����

        //�̹��� ���� ����
        MediaType mediaType = MediaUtils.getMediaType(fileName);
        if (mediaType != null) {
            String ori_img = fileName.substring(0, 12) + fileName.substring(14);
            new File(rootPath + ori_img.replace('/', File.separatorChar)).delete();
        }

        //���� ����
        new File(rootPath + fileName.replace('/', File.separatorChar)).delete();
    }
    
    
    //���� ��� ����
    public static HttpHeaders getHttpHeaders(String fileName) throws Exception {
    	
        MediaType mediaType = MediaUtils.getMediaType(fileName); //���� Ÿ�� Ȯ��
        HttpHeaders httpHeaders = new HttpHeaders();

        //�̹��� ���� O
        if (mediaType != null) {
            httpHeaders.setContentType(mediaType);
            return httpHeaders;
        }

        //�̹��� ���� X
        fileName = fileName.substring(fileName.indexOf("_") + 1); //UUID ����
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM); //�ٿ�ε� MIME Ÿ�� ����
        
        //���ϸ� �ѱ� ���ڵ�
        httpHeaders.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"),
                                "ISO-8859-1")+"\"");

        return httpHeaders;
    }
    
    
    //�⺻ ��� 
    public static String getRootPath(String fileName, HttpServletRequest request) {
    	
        String rootPath = "/resources/upload";
        MediaType mediaType = MediaUtils.getMediaType(fileName); //����Ÿ�� Ȯ��
        
        if (mediaType != null)
            return request.getSession().getServletContext().getRealPath(rootPath + "/images"); //�̹��� ���

        return request.getSession().getServletContext().getRealPath(rootPath + "/files"); //�Ϲ����� ���
    }
    
    //��¥ ������
    private static String getDatePath(String uploadPath) {

        Calendar calendar = Calendar.getInstance();
        String yearPath = File.separator + calendar.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));

        makeDateDir(uploadPath, yearPath, monthPath, datePath);
        
        return datePath;
    }

    //��¥�� ����
    private static void makeDateDir(String uploadPath, String... paths) {

        //��¥�� ���� �̹� ����� �޼��� ����
        if (new File(uploadPath + paths[paths.length - 1]).exists())
            return;

        for (String path :  paths) {
            File dirPath = new File(uploadPath + path);
            if (!dirPath.exists())
                dirPath.mkdirs();
       
        }
    }

    //���� ���� ��� 
    private static String replaceFilePath(String datePath, String fileName) {
        String savedFilePath = datePath + File.separator + fileName;
        return savedFilePath.replace(File.separatorChar, '/');
    }

    //���ϸ� �ߺ����� 
    private static String getUuidFileName(String ori_fileName) {
        return UUID.randomUUID().toString() + "_" + ori_fileName;
    }
    
  

    //����� ����
    private static String makeThumbnail(String uploadRootPath, String datePath, String fileName) throws Exception {

        //�����̹��� �޸𸮻� �ε�
        BufferedImage ori_img = ImageIO.read(new File(uploadRootPath + datePath, fileName));
        
        //�����̹��� ���(Sclar)
        //�����
        int dw = 100;
        int dh = 100;
        
        //����
        int ow = ori_img.getWidth();
        int oh = ori_img.getHeight();
        
        //���� ���� ����� ���� ���
        int nw = ow;
        int nh = (ow * dh) / dw;
        
        //���� ���� ���� ����� �ʺ� ����
        if(nh > oh) {
        	nw = (oh * dw) / dh;
        	nh = oh;
        }
        //���� �̹��� ũ��
        BufferedImage cropImg = Scalr.crop(ori_img, (ow - nw)/2, (oh-nh)/2, nw, nh);
        BufferedImage thumbImg = Scalr.resize(cropImg, dw, dh);
        //BufferedImage thumbImg = Scalr.resize(ori_img, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
        
        //����� ���ϸ�
        String thumbImgName = "s_" + fileName;
        //����� ���ε� ���
        String fullPath = uploadRootPath + datePath + File.separator + thumbImgName;
        //�����  ��ü����
        File newFile = new File(fullPath); 
        //����� Ȯ���� ����
        String formatName = MediaUtils.getFormatName(fileName);
        //����� ���� ����
        ImageIO.write(thumbImg, formatName, newFile);

        return thumbImgName;
    }

    
    
}
