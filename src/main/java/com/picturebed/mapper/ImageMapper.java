package com.picturebed.mapper;


import com.picturebed.mapper.sql.ImageMapperSqlProvider;
import com.picturebed.model.entity.Image;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author moZiA
 * @date 2025/5/10 19:45
 * @description
 */
@Mapper
public interface ImageMapper {

  @InsertProvider(type = ImageMapperSqlProvider.class , method = "insertImage")
  Integer insertImage(Image image);


  @SelectProvider(type = ImageMapperSqlProvider.class , method = "selectImageByOriginalName")
  Image selectImageByOriginalName(String originalName);

}