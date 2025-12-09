import request from '@/utils/request'

//分页条件查询
export const queryPageApi = (begin, end, name, page, pageSize) => {
  return request.get(`/clazzs?begin=${begin}&end=${end}&name=${name}&page=${page}&pageSize=${pageSize}`);
}

//新增班级
export const addApi = (clazz) => {
  return request.post('/clazzs', clazz);
}

//根据ID查询班级
export const queryInfoApi = (id) => {
  return request.get(`/clazzs/${id}`);
}

//更新班级
export const updateApi = (clazz) => {
  return request.put(`/clazzs`, clazz);
}

//删除班级
export const deleteApi = (id) => {
  return request.delete(`/clazzs/${id}`);
}

//查询全部班级信息
export const queryAllApi = () => {
  return request.get('/clazzs/list');
}
