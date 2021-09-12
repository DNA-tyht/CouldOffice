import axios from 'axios'
import {ElMessage} from "element-plus";

//响应拦截器
axios.interceptors.response.use(success => {
  if (success.status && success.status === 200) {
    //业务逻辑错误
    if (success.data.code  === 500 || success.data.code === 401 || success.data.code === 403) {
      ElMessage.error({message: success.data.message});
      return;
    }
    if (success.data.message) {
      ElMessage.success({message: success.data.message});
    }
    return success.data;
  }
}, error => {
  if (error.response.code === 504 || error.response.code === 404) {
    ElMessage.error({message: "服务器被吃掉啦o(╯□╰)o"});
  } else if (error.response.code === 403) {
    ElMessage.error({message: "权限不足，请联系管理员"});
  } else if (error.response.code === 401) {
    ElMessage.error({message: "尚未登录，请登录"});
  } else {
    if (error.response.data.message) {
      ElMessage.error({message: error.response.data.message});
    } else {
      ElMessage.error({message: "未知错误"});
    }
  }
});

//请求拦截器
axios.interceptors.request.use(config => {
  if (window.sessionStorage.getItem("tokenStr")) {
    config.headers["Authorization"] = window.sessionStorage.getItem("tokenStr");
  }
  return config;
}, error => {
  console.log(error);
});

//发送请求
let base = "";
export const postRequest = (url, params) => {
  return axios({
    method: "post",
    url: `${base}${url}`,
    data: params,
  })
};
export const putRequest = (url, params) => {
  return axios({
    method: "put",
    url: `${base}${url}`,
    data: params,
  })
};
export const getRequest = (url, params) => {
  return axios({
    method: "get",
    url: `${base}${url}`,
    data: params,
  })
}
export const deleteRequest = (url, params) => {
  return axios({
    method: "delete",
    url: `${base}${url}`,
    data: params,
  })
}