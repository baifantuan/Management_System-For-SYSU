package com.itbaifantuan.controller;

import com.itbaifantuan.pojo.Emp;
import com.itbaifantuan.pojo.EmpQueryParam;
import com.itbaifantuan.pojo.Result;
import com.itbaifantuan.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        return Result.success(empService.page(empQueryParam));
    }

    @PostMapping
    public Result insert(@RequestBody Emp emp) {
        empService.insert(emp);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        empService.deleteByIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据信息查询员工{}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工:" + emp);
        empService.update(emp);
        return Result.success();
    }
}
