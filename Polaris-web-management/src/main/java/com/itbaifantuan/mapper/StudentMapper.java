package com.itbaifantuan.mapper;

import com.itbaifantuan.pojo.DegreeOption;
import com.itbaifantuan.pojo.Student;
import com.itbaifantuan.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    List<Student> page(StudentQueryParam studentQueryParam);

    void insert(Student student);

    Student getById(Integer id);

    void update(Student student);

    void deleteByIds(List<Integer> ids);

    @MapKey("clazz")
    List<Map<String, Object>> StudentClazzData();

    List<DegreeOption> getStudentDegreeData();

}
