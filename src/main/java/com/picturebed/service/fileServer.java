package com.picturebed.service;


import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author moZiA
 * @date 2025/5/9 12:26
 * @description
 */
public interface fileServer {

  Map<String, String> uploadImage(MultipartFile file, String hashSha256 , Boolean isPublic);


}