package com.picturebed.model.dto;


import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author moZiA
 * @date 2025/5/12 18:00
 * @description
 */
@Getter
@Setter
public class ImageDto {


  @NotBlank
  private Long id;
  private String originalName;
  private Boolean isPublic;
  private LocalDateTime updateDate;



}