<template>
  <section class="page">
    <header class="page-header">
      <div>
        <p class="eyebrow">Fridge</p>
        <h1>冰箱食材</h1>
      </div>
      <van-button icon="plus" type="primary" size="small" round @click="router.push('/foods/new')" />
    </header>

    <div class="summary-grid">
      <div class="summary-tile total">
        <span>总库存</span>
        <strong>{{ dashboard.totalCount || 0 }}</strong>
      </div>
      <div class="summary-tile">
        <span>正常</span>
        <strong>{{ dashboard.normalCount || 0 }}</strong>
      </div>
      <div class="summary-tile warning">
        <span>即将过期</span>
        <strong>{{ dashboard.expiringSoonCount || 0 }}</strong>
      </div>
      <div class="summary-tile danger">
        <span>已过期</span>
        <strong>{{ dashboard.expiredCount || 0 }}</strong>
      </div>
    </div>

    <section class="section-block">
      <div class="section-title">
        <h2>到期提醒</h2>
        <van-button size="small" plain type="primary" @click="router.push('/foods?status=EXPIRING_SOON')">查看</van-button>
      </div>
      <van-empty v-if="!dashboard.expiringSoonItems?.length" image-size="72" description="暂无即将过期食材" />
      <FoodCard v-for="item in dashboard.expiringSoonItems" :key="item.id" :food="item" @edit="goEdit" />
    </section>

    <section class="section-block">
      <div class="section-title">
        <h2>分类库存</h2>
      </div>
      <div class="category-pills">
        <button
          v-for="category in dashboard.categoryStats"
          :key="category.categoryId"
          class="category-pill"
          @click="router.push(`/foods?categoryId=${category.categoryId}`)"
        >
          <span :style="{ backgroundColor: category.categoryColor }"></span>
          {{ category.categoryName }}
          <strong>{{ category.count }}</strong>
        </button>
      </div>
    </section>
  </section>
</template>

<script setup>
import { onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { fetchDashboard } from '../api/dashboard'
import FoodCard from '../components/FoodCard.vue'

const router = useRouter()
const dashboard = reactive({
  totalCount: 0,
  normalCount: 0,
  expiringSoonCount: 0,
  expiredCount: 0,
  categoryStats: [],
  expiringSoonItems: []
})

async function load() {
  Object.assign(dashboard, await fetchDashboard())
}

function goEdit(food) {
  router.push(`/foods/${food.id}/edit`)
}

onMounted(load)
</script>
