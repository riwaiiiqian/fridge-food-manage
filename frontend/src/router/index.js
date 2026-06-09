import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../views/MainLayout.vue'
import DashboardView from '../views/DashboardView.vue'
import FoodListView from '../views/FoodListView.vue'
import FoodFormView from '../views/FoodFormView.vue'
import CategoryView from '../views/CategoryView.vue'
import StatsView from '../views/StatsView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: MainLayout,
      children: [
        { path: '', name: 'dashboard', component: DashboardView },
        { path: 'foods', name: 'foods', component: FoodListView },
        { path: 'categories', name: 'categories', component: CategoryView },
        { path: 'stats', name: 'stats', component: StatsView }
      ]
    },
    { path: '/foods/new', name: 'food-create', component: FoodFormView },
    { path: '/foods/:id/edit', name: 'food-edit', component: FoodFormView, props: true }
  ]
})

export default router
