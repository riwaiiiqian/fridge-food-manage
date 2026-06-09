import request from './request'

export function fetchDashboard() {
  return request.get('/dashboard')
}
