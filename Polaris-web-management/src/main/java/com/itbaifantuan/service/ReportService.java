package com.itbaifantuan.service;

import com.itbaifantuan.pojo.ClazzOption;
import com.itbaifantuan.pojo.DegreeOption;
import com.itbaifantuan.pojo.GenderOption;
import com.itbaifantuan.pojo.JobOption;

import java.util.List;

public interface ReportService {
    JobOption getEmpJobData();

    List<GenderOption> getEmpGenderData();

    ClazzOption getStudentClazzData();

    List<DegreeOption> getStudentDegreeData();

}
