import request from '@/utils/request'

//分页条件查询
export const queryPageApi = (clazzId, degree, name, page, pageSize) => {
  return request.get(`/students?clazzId=${clazzId}&degree=${degree}&name=${name}&page=${page}&pageSize=${pageSize}`);
}
  

//新增学生
export const addApi = (stu) => {
  return request.post('/students', stu);
}

//根据ID查询学生
export const queryInfoApi = (id) => {
  return request.get(`/students/${id}`);
}

//修改学生
export const updateApi = (stu) => {
  return request.put(`/students`, stu);
}

//删除学生
export const deleteApi = (ids) => {
  return request.delete(`/students/${ids}`);
}

//违纪处理
export const handleViolationApi = (id, score) => {
  return request.put(`/students/violation/${id}/${score}`);
}
