package com.itbaifantuan.mapper;

import com.itbaifantuan.pojo.Clazz;
import com.itbaifantuan.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClazzMapper {
    List<Clazz> page(ClazzQueryParam clazzQueryParam);

    void insert(Clazz clazz);

    Clazz getById(Integer id);

    void update(Clazz clazz);

    void deleteById(Integer id);

    List<Clazz> getList();

    Integer getClazzStudentCountById(Integer id);

}
