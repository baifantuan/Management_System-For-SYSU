package com.itbaifantuan.controller;

import com.itbaifantuan.pojo.PageResult;
import com.itbaifantuan.pojo.Result;
import com.itbaifantuan.pojo.Student;
import com.itbaifantuan.pojo.StudentQueryParam;
import com.itbaifantuan.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result page(StudentQueryParam studentQueryParam){
        log.info("查询内容:{}", studentQueryParam);
        PageResult<Student> result = studentService.page(studentQueryParam);
        return Result.success(result);
    }

    @PostMapping
    public Result insert(@RequestBody Student student){
        log.info("新增学员信息:{}", student);
        studentService.insert(student);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("查询学员id:{}", id);
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("更新学员信息:{}", student);
        studentService.update(student);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("删除学员id:{}", ids);
        studentService.deleteByIds(ids);
        return Result.success();
    }

    @PutMapping("/violation/{id}/{score}")
    public Result violate(@PathVariable Integer id, @PathVariable Short score){
        studentService.violate(id, score);
        return Result.success();
    }
}
