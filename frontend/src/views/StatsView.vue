<template>
  <section class="page">
    <header class="page-header compact">
      <h1>库存统计</h1>
    </header>

    <div class="status-bars">
      <div class="status-bar">
        <span>正常</span>
        <strong>{{ dashboard.normalCount || 0 }}</strong>
      </div>
      <div class="status-bar warning">
        <span>即将过期</span>
        <strong>{{ dashboard.expiringSoonCount || 0 }}</strong>
      </div>
      <div class="status-bar danger">
        <span>已过期</span>
        <strong>{{ dashboard.expiredCount || 0 }}</strong>
      </div>
    </div>

    <section class="section-block">
      <div class="section-title">
        <h2>分类分布</h2>
      </div>
      <div v-for="category in dashboard.categoryStats" :key="category.categoryId" class="stat-row">
        <span class="category-dot" :style="{ backgroundColor: category.categoryColor }"></span>
        <span>{{ category.categoryName }}</span>
        <div class="stat-track">
          <i :style="{ width: widthOf(category.count), backgroundColor: category.categoryColor }"></i>
        </div>
        <strong>{{ category.count }}</strong>
      </div>
    </section>
  </section>
</template>

<script setup>
import { computed, onMounted, reactive } from 'vue'
import { fetchDashboard } from '../api/dashboard'

const dashboard = reactive({
  totalCount: 0,
  normalCount: 0,
  expiringSoonCount: 0,
  expiredCount: 0,
  categoryStats: []
})

const maxCount = computed(() => {
  return Math.max(...dashboard.categoryStats.map((item) => item.count), 1)
})

function widthOf(count) {
  return `${Math.max((count / maxCount.value) * 100, count ? 8 : 0)}%`
}

onMounted(async () => {
  Object.assign(dashboard, await fetchDashboard())
})
</script>
