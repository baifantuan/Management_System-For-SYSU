package com.itbaifantuan.mapper;

import com.itbaifantuan.pojo.Emp;
import com.itbaifantuan.pojo.EmpQueryParam;
import com.itbaifantuan.pojo.GenderOption;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    List<Emp> page(EmpQueryParam empQueryParam);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp getInfo(Integer id);

    void updateById(Emp emp);

    @MapKey("pos")
    List<Map<String, Object>> EmpJobData();

    List<GenderOption> getEmpGenderData();

    Integer getDeptEmpCountById(Integer id);

    Emp selectByEmpUsernameAndPassword(Emp emp);
}
