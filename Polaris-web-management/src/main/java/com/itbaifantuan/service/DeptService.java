package com.itbaifantuan.service;

import com.itbaifantuan.pojo.Dept;
import com.itbaifantuan.pojo.Result;

import java.util.List;

public interface DeptService {
    List<Dept> findAll();

    void deleteById(Integer id);

    void add(Dept dept);

    Dept getById(Integer id);

    void update(Dept dept);
}
