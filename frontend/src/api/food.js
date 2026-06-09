import request from './request'

export function fetchFoods(params) {
  return request.get('/foods', { params })
}

export function fetchFood(id) {
  return request.get(`/foods/${id}`)
}

export function createFood(data) {
  return request.post('/foods', data)
}

export function updateFood(id, data) {
  return request.put(`/foods/${id}`, data)
}

export function deleteFood(id) {
  return request.delete(`/foods/${id}`)
}
