import request from "@/utils/request";

// 查询所有部门
export const queryAllApi = () => {
    return request.get('/depts');
}

//新增部门
export const addApi = (dept) => {
   return request.post('/depts', dept);
}

//根据ID查询部门
export const queryByIdApi = (id) => {
    return request.get(`/depts/${id}`);
}

//更新部门
export const updateApi = (dept) => {
    return request.put('/depts', dept);
}

//删除特定部门
export const deleteByIdApi = (id) => {
    return request.delete(`/depts?id=${id}`);
}

