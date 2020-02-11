package com.park.web.file.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.park.web.file.service.FileService;
import com.park.web.fileUtil.UploadFileUtils;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Inject
	private FileService fileservice;
	
	
	//업로드
	@RequestMapping(value="/uploadFile", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadFile(MultipartFile file, HttpServletRequest request) throws Exception{
		ResponseEntity<String> entity = null;
		
		try {
			String savedFilePath = UploadFileUtils.uploadFile(file, request);
			entity = new ResponseEntity<String>(savedFilePath, HttpStatus.CREATED);
			
			System.out.println("savedFilePath : " + savedFilePath);
		}
		catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

	
	//첨부파일 출력
	@RequestMapping(value="/displayFile", method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String fileName, HttpServletRequest request) throws Exception {
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders httpHeaders = UploadFileUtils.getHttpHeaders(fileName); //http 헤더 설정 가져오기
		String rootPath = UploadFileUtils.getRootPath(fileName, request); //업로드 기본 경로
		
		//데이터, HttpHeader 전송 
		try(InputStream is = new FileInputStream(rootPath + fileName)) {
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(is), httpHeaders, HttpStatus.CREATED);
		}
		catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//파일 목록
	@RequestMapping(value="/getFiles/{bid}", method=RequestMethod.GET)
	public ResponseEntity<List<String>> getFiles(@PathVariable("bid") Integer bid) throws Exception {
		ResponseEntity<List<String>> entity = null;
		
		try {
			List<String> fileList = fileservice.getFiles(bid);
			entity = new ResponseEntity<List<String>>(fileList, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<String>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	
	//첨부파일 삭제 : boardForm
	//유저 프로필 이미지
	@RequestMapping(value="/deleteFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName, HttpServletRequest request) throws Exception {
		ResponseEntity<String> entity = null;
		
		try {
			UploadFileUtils.deleteFile(fileName, request);
			entity = new ResponseEntity<String>("delete", HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//첨부 파일 삭제 : UpdateBoard
	@RequestMapping(value="/deleteFile/{bid}", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(@PathVariable("bid") Integer bid, String fileName, HttpServletRequest request) throws Exception {
		ResponseEntity<String> entity = null;
		
		try {
			UploadFileUtils.deleteFile(fileName, request);
			fileservice.deleteFile(bid, fileName);
			entity = new ResponseEntity<String>("delete", HttpStatus.OK);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//파일 제거 : 게시글 전체 파일
	@RequestMapping(value="/deleteAllFiles", method=RequestMethod.POST)
	public ResponseEntity<String> deleteAllFiles(@RequestParam("files[]") String[] files, HttpServletRequest request) throws Exception {
		ResponseEntity<String> entity = null;
		
		if(files == null || files.length == 0) {
			return new ResponseEntity<String>("delete", HttpStatus.OK);
		}
		
		try {
			for(String fileName : files) {
				UploadFileUtils.deleteFile(fileName, request);
			}
			entity = new ResponseEntity<String>("delete", HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	
	}
	
	
	
	
	
}
