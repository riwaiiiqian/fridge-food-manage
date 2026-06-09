import { defineStore } from 'pinia'
import { fetchCategories } from '../api/category'

export const useCategoryStore = defineStore('category', {
  state: () => ({
    categories: []
  }),
  actions: {
    async loadCategories() {
      this.categories = await fetchCategories()
      return this.categories
    }
  }
})
