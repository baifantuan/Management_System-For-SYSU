package com.itbaifantuan.service.impl;

import com.itbaifantuan.exception.CustomBusinessException;
import com.itbaifantuan.mapper.DeptMapper;
import com.itbaifantuan.mapper.EmpMapper;
import com.itbaifantuan.pojo.Dept;
import com.itbaifantuan.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Integer empCount = getDeptEmpCountById(id);
        if(empCount > 0){
            throw new CustomBusinessException("该部门下存在员工，无法直接删除！");
        }
        deptMapper.deleteById(id);
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }

    public Integer getDeptEmpCountById(Integer id){
        return empMapper.getDeptEmpCountById(id);
    }
}
