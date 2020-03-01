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
	
	private String accessKey = "****************"; // ������ Ű
	private String secretKey = "****************"; // ���� Ű

	private AmazonS3 conn;
	
		
	public S3Utils() {
		
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		
		this.conn = AmazonS3ClientBuilder.standard()
					.withRegion(Regions.AP_NORTHEAST_2)
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.build();
		
	}
	
	
	//��Ŷ ����Ʈ�� �������� �޼���
	public List<Bucket> getBucketList() {
		return conn.listBuckets();
	}

	//��Ŷ�� �����ϴ� �޼���
	public Bucket createBucket(String bucketName) {
		return conn.createBucket(bucketName);
	}

	//���� ����
	public void createFolder(String bucketName, String folderName) {
		conn.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
	}

	//���� ���ε�
	public void fileUpload(String bucketName, String fileName, byte[] fileData) throws FileNotFoundException {

		String filePath = (fileName).replace(File.separatorChar, '/');
		ObjectMetadata metaData = new ObjectMetadata();

		metaData.setContentLength(fileData.length);   //��Ÿ������ ���� --> ����ũ�⸸ŭ ���۸� ����
	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData); //���� ����

		conn.putObject(bucketName, filePath, byteArrayInputStream, metaData);

	}
	
	//���� ����
	public void fileDelete(String bucketName, String fileName) {
		String imgName = (fileName).replace(File.separatorChar, '/');
		conn.deleteObject(bucketName, imgName);
		System.out.println("����");
	}

	//���� URL
	public String getFileURL(String bucketName, String fileName) {
		System.out.println("fileName : " + fileName);
		String imgName = (fileName).replace(File.separatorChar, '/');
		return conn.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, imgName)).toString();
	}

}
