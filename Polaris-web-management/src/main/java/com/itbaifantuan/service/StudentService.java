package com.itbaifantuan.service;

import com.itbaifantuan.pojo.PageResult;
import com.itbaifantuan.pojo.Student;
import com.itbaifantuan.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    void insert(Student student);

    Student getById(Integer id);

    void update(Student student);

    void deleteByIds(List<Integer> ids);

    void violate(Integer id, Short score);
}
