package com.picturebed.model.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author moZiA
 * @date 2025/5/10 19:50
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Image {


  private Long id;
  private Long userId;
  private String originalName;
  private String storedName;
  private Long fileSize;
  private String fileType;
  private String hashSha256;
  private Boolean isPublic;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
  private String storagePath;

}