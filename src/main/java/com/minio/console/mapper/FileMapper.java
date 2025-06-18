package com.minio.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minio.console.entity.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface FileMapper extends BaseMapper<FileDTO> {
    default boolean insertFile(String filename, String url, String suffix, String wordUrl) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(filename.trim() + "." + suffix);
        fileDTO.setFileUrl(url);
        fileDTO.setFileType(suffix);
        fileDTO.setWordUrl(wordUrl);
        int insert = this.insert(fileDTO);
        return insert > 0 ? true : false;
    }

    @Select("SELECT * FROM file WHERE ORDER BY id DESC LIMIT #{page} ,#{pageSize}")
    List<FileDTO> page(int page, int pageSize);

    @Select("SELECT count(*) FROM file")
    Long count();
}
