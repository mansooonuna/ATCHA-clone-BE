package com.sparta.atchaclonecoding.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class S3Uploader {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 프로필 업로드
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
//        if (!fileName.toLowerCase().endsWith(".jpg")) {
//            fileName += ".jpg";
//        }
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getSize());
        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // 삭제
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    //
    public String uploadImage(String image){
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(image.length());
        amazonS3.putObject(bucket, image, image);

        return amazonS3.getUrl(bucket, image).toString();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    // 프로필 삭제
    public boolean delete(String fileUrl) {
        try {
            String[] temp = fileUrl.split("/");
            String fileKey = temp[temp.length-1];
            amazonS3.deleteObject(bucket, fileKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
