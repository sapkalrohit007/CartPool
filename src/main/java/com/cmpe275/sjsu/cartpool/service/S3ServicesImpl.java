package com.cmpe275.sjsu.cartpool.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cmpe275.sjsu.cartpool.config.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class S3ServicesImpl implements S3Services
{
    private AmazonS3 s3client;

    @Autowired
    private S3Config s3Config;

    @PostConstruct
    private void initializeAmazon()
    {
        AWSCredentials credentials = new BasicAWSCredentials(s3Config.getAccessKey(), s3Config.getSecretKey());
        this.s3client = new AmazonS3Client(credentials);
    }

    public void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(s3Config.getBucket(),fileName,file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = s3Config.getEndpointUrl() + "/" + s3Config.getBucket() + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }
        return fileUrl;
    }

    @Override
    public String deleteFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(s3Config.getBucket()).withKeys(fileName);
            s3client.deleteObjects(delObjReq);
            //s3client.deleteObject(new DeleteObjectRequest(s3Config.getBucket() , fileName));
            return "Successfully deleted";
        }
        catch(SdkClientException e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
