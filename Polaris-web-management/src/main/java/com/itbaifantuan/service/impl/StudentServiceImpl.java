package com.itbaifantuan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itbaifantuan.mapper.StudentMapper;
import com.itbaifantuan.pojo.PageResult;
import com.itbaifantuan.pojo.Student;
import com.itbaifantuan.pojo.StudentQueryParam;
import com.itbaifantuan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());
        List<Student> studentList = studentMapper.page(studentQueryParam);

        Page<Student> p = (Page<Student>) studentList;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void insert(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }

    @Override
    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        studentMapper.deleteByIds(ids);
    }

    @Override
    public void violate(Integer id, Short score) {
        Student student = studentMapper.getById(id);

        Short violationCount = (student.getViolationCount() == null) ? 0 : student.getViolationCount();
        Short violationScore = (student.getViolationScore() == null) ? 0 : student.getViolationScore();
        Short addScore = (score == null) ? 0 : score;

        student.setViolationCount((short) (violationCount + 1));
        student.setViolationScore((short) (violationScore + addScore));

        studentMapper.update(student);
    }
}
