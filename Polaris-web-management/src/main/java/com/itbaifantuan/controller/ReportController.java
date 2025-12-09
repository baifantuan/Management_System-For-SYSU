package com.itbaifantuan.controller;


import com.itbaifantuan.pojo.*;
import com.itbaifantuan.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        List<GenderOption> genderOptions = reportService.getEmpGenderData();
        return Result.success(genderOptions);
    }

    @GetMapping("/studentCountData")
    public Result getStudentClazzData(){
        ClazzOption clazzOption = reportService.getStudentClazzData();
        return Result.success(clazzOption);
    }

    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        List<DegreeOption> degreeOptions = reportService.getStudentDegreeData();
        return Result.success(degreeOptions);
    }
}
