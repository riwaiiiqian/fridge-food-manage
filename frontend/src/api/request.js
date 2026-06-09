import axios from 'axios'
import { showToast } from 'vant'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.response.use(
  (response) => {
    const body = response.data
    if (body && body.success === false) {
      showToast(body.message || '请求失败')
      return Promise.reject(new Error(body.message || '请求失败'))
    }
    return body ? body.data : response.data
  },
  (error) => {
    showToast(error.response?.data?.message || error.message || '网络异常')
    return Promise.reject(error)
  }
)

export default request
