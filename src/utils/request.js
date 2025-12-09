import axios from 'axios'
import { ElMessage } from 'element-plus';
import router from '../router/index';

//创建axios实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

//axios的请求 request 拦截器 - 获取 local 的 token，在请求头中添加 token
request.interceptors.request.use(
  (config) => {
    const loginUser = localStorage.getItem('loginUser') ? JSON.parse(localStorage.getItem('loginUser')) : null;
    if (loginUser && loginUser.token) {
      config.headers.token = loginUser.token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
)


//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { //成功回调
    return response.data
  },
  (error) => { //失败回调
    if(error.response.status === 401){
      //token 过期处理
      ElMessage.error('登录已过期，请重新登录！');
      //进行跳转
      router.push('/login');
    }else{
      ElMessage.error('请求失败，访问异常');
    }
    return Promise.reject(error)
  }
)
 
export default request