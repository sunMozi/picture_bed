package com.picturebed.mapper;


import com.picturebed.mapper.sql.ImageMapperSqlProvider;
import com.picturebed.model.dto.ImageDto;
import com.picturebed.model.entity.Image;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author moZiA
 * @date 2025/5/10 19:45
 * @description
 */
@Mapper
public interface ImageMapper {

  @InsertProvider(type = ImageMapperSqlProvider.class, method = "insertImage")
  Integer insertImage(Image image);


  @SelectProvider(type = ImageMapperSqlProvider.class, method = "selectImageByOriginalName")
  Image selectImageByOriginalName(String originalName);

  @SelectProvider(type = ImageMapperSqlProvider.class, method = "selectImageList")
  List<Image> selectImageList(String originalName);

  @SelectProvider(type = ImageMapperSqlProvider.class, method = "selectImageByName")
  Image selectImageByName(String imageName);

  @SelectProvider(type = ImageMapperSqlProvider.class, method = "selectImageById")
  Image selectImageById(@NotBlank Long id);

  @UpdateProvider(type = ImageMapperSqlProvider.class, method = "updateImage")
  void updateImage(ImageDto imageDto);
}