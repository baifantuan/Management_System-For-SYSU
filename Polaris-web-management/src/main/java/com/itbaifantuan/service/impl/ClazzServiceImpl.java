package com.itbaifantuan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itbaifantuan.exception.CustomBusinessException;
import com.itbaifantuan.mapper.ClazzMapper;
import com.itbaifantuan.pojo.Clazz;
import com.itbaifantuan.pojo.ClazzQueryParam;
import com.itbaifantuan.pojo.PageResult;
import com.itbaifantuan.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());

        List<Clazz> clazzList = clazzMapper.page(clazzQueryParam);
        clazzList.forEach(clazz -> setClazzStatus(clazz));

        Page<Clazz> p = (Page<Clazz>) clazzList;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void insert(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        setClazzStatus(clazz);
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz getById(Integer id) {
        Clazz clazz = clazzMapper.getById(id);
        return clazz;
    }

    @Override
    public void update(Clazz clazz) {
        clazzMapper.update(clazz);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteById(Integer id) {
        Integer studentCount = getClazzStudentCountById(id);

        if(studentCount > 0){
            throw new CustomBusinessException("此班级下还有学生，无法直接删除！");
        }
        clazzMapper.deleteById(id);
    }

    @Override
    public List<Clazz> getList() {
        return clazzMapper.getList();
    }

    public void setClazzStatus(Clazz clazz){
        if(LocalDate.now().compareTo(clazz.getBeginDate()) < 0){
            clazz.setStatus("未开始");
        }
        else if(LocalDate.now().compareTo(clazz.getEndDate()) > 0){
            clazz.setStatus("已结课");
        }
        else{
            clazz.setStatus("开课中");
        }
    }

    public Integer getClazzStudentCountById(Integer id){
        return clazzMapper.getClazzStudentCountById(id);
    }
}

