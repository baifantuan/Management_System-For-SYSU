package com.itbaifantuan.service;

import com.itbaifantuan.pojo.Clazz;
import com.itbaifantuan.pojo.ClazzQueryParam;
import com.itbaifantuan.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    void insert(Clazz clazz);

    Clazz getById(Integer id);

    void update(Clazz clazz);

    void deleteById(Integer id);

    List<Clazz> getList();

}
