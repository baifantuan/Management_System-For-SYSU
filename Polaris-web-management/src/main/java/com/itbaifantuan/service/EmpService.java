package com.itbaifantuan.service;

import com.itbaifantuan.pojo.Emp;
import com.itbaifantuan.pojo.EmpQueryParam;
import com.itbaifantuan.pojo.LoginInfo;
import com.itbaifantuan.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);

    LoginInfo login(Emp emp);

}
