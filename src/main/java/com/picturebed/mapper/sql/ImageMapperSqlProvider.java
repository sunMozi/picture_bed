package com.picturebed.mapper.sql;


import com.picturebed.common.response.enums.ResponseCodeEnum;
import com.picturebed.exception.BusinessException;
import com.picturebed.model.entity.Image;
import org.apache.ibatis.jdbc.SQL;

public class ImageMapperSqlProvider {

  public String insertImage(Image image) {
    // 强制校验必要字段
    if (image.getUserId() == null) {
      throw new BusinessException(ResponseCodeEnum.CODE_401, "用户ID不能为空");
    }
    return new SQL() {{
      INSERT_INTO("image");
      VALUES("id", "NUll");
      VALUES("user_id", "#{userId}");
      VALUES("original_name", "#{originalName}");
      VALUES("stored_name", "#{storedName}");
      VALUES("file_size", "#{fileSize}");
      VALUES("file_type", "#{fileType}");
      VALUES("hash_sha256", "#{hashSha256}");
      VALUES("is_public", "#{isPublic}");
      VALUES("storage_path", "#{storagePath}");
      VALUES("create_date", "#{createDate}");
      VALUES("update_date", "#{updateDate}");
    }}.toString();
  }

  public String selectImageByOriginalName(String originalName) {
    return new SQL() {{
      SELECT("*");
      FROM("image");
      WHERE("original_name=#{originalName}");
    }}.toString();
  }

  public String selectImageList() {
    return new SQL() {{
      SELECT("*");
      FROM("image");
    }}.toString();
  }
}