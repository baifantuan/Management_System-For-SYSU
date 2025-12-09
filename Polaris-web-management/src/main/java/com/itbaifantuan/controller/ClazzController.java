package com.itbaifantuan.controller;

import com.itbaifantuan.pojo.Clazz;
import com.itbaifantuan.pojo.ClazzQueryParam;
import com.itbaifantuan.pojo.PageResult;
import com.itbaifantuan.pojo.Result;
import com.itbaifantuan.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam){
        log.info("查询班级信息{}:", clazzQueryParam);
        PageResult<Clazz> res = clazzService.page(clazzQueryParam);
        return Result.success(res);
    }

    @PostMapping
    public Result insert(@RequestBody Clazz clazz){
        log.info("新增班级信息{}", clazz);
        clazzService.insert(clazz);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("查询班级的id:{}", id);
        Clazz clazz = clazzService.getById(id);
        return Result.success(clazz);
    }

    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        log.info("修改了班级信息:{}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除的班级id:{}", id);
        clazzService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result getList(){
        List<Clazz> clazzList = clazzService.getList();
        return Result.success(clazzList);
    }
}
