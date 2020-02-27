package com.park.web.fileUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class S3Utils {
	
	private String accessKey = "******************"; // 엑세스 키
	private String secretKey = "******************"; // 보안 키

	private AmazonS3 conn;

	
	public S3Utils() {
		
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		
		this.conn = AmazonS3ClientBuilder.standard()
					.withRegion(Regions.AP_NORTHEAST_2)
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.build();
		
	}
	
	
	//버킷 리스트를 가져오는 메서드
	public List<Bucket> getBucketList() {
		return conn.listBuckets();
	}

	//버킷을 생성하는 메서드
	public Bucket createBucket(String bucketName) {
		return conn.createBucket(bucketName);
	}

	//폴더 생성
	public void createFolder(String bucketName, String folderName) {
		conn.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
	}

	//파일 업로드
	public void fileUpload(String bucketName, String fileName, byte[] fileData) throws FileNotFoundException {

		String filePath = (fileName).replace(File.separatorChar, '/');
		ObjectMetadata metaData = new ObjectMetadata();

		metaData.setContentLength(fileData.length);   //메타데이터 설정 --> 파일크기만큼 버퍼를 설정
	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData); //파일 넣음

		conn.putObject(bucketName, filePath, byteArrayInputStream, metaData);

	}
	
	//파일 삭제
	public void fileDelete(String bucketName, String fileName) {
		String imgName = (fileName).replace(File.separatorChar, '/');
		conn.deleteObject(bucketName, imgName);
		System.out.println("삭제");
	}

	//파일 URL
	public String getFileURL(String bucketName, String fileName) {
		System.out.println("fileName : " + fileName);
		String imgName = (fileName).replace(File.separatorChar, '/');
		return conn.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, imgName)).toString();
	}

}
