import request from '@/utils/request'

//分页条件查询
export const queryPageApi = (page, pageSize) => {
    return request.get(`/log/page?page=${page}&pageSize=${pageSize}`);
}