package com.itbaifantuan.service.impl;

import com.itbaifantuan.mapper.ClazzMapper;
import com.itbaifantuan.mapper.EmpMapper;
import com.itbaifantuan.mapper.StudentMapper;
import com.itbaifantuan.pojo.ClazzOption;
import com.itbaifantuan.pojo.DegreeOption;
import com.itbaifantuan.pojo.GenderOption;
import com.itbaifantuan.pojo.JobOption;
import com.itbaifantuan.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String, Object>> list = empMapper.EmpJobData();
        List<Object> jobList = list.stream().map(datamap -> datamap.get("pos")).toList();
        List<Object> dataList = list.stream().map(datamap -> datamap.get("num")).toList();

        return new JobOption(jobList, dataList);
    }

    @Override
    public List<GenderOption> getEmpGenderData() {
        List<GenderOption> empGenderData = empMapper.getEmpGenderData();
        return empGenderData;
    }

    @Override
    public ClazzOption getStudentClazzData() {
        List<Map<String, Object>> list = studentMapper.StudentClazzData();

        List<Object> clazzList = list.stream().map(datamap -> datamap.get("clazz")).toList();
        List<Object> dataList = list.stream().map(datamap -> datamap.get("num")).toList();
        return new ClazzOption(clazzList, dataList);
    }

    @Override
    public List<DegreeOption> getStudentDegreeData() {
        return studentMapper.getStudentDegreeData();
    }
}
