<template>
  <section class="page">
    <header class="page-header compact">
      <h1>食材清单</h1>
      <van-button icon="plus" type="primary" size="small" round @click="router.push('/foods/new')" />
    </header>

    <van-search v-model="query.keyword" placeholder="搜索食材名称" shape="round" @search="refresh" @clear="refresh" />

    <van-dropdown-menu>
      <van-dropdown-item v-model="query.categoryId" :options="categoryOptions" @change="refresh" />
      <van-dropdown-item v-model="query.status" :options="statusOptions" @change="refresh" />
    </van-dropdown-menu>

    <van-pull-refresh v-model="refreshing" @refresh="refresh">
      <van-empty v-if="!loading && !foods.length" image-size="80" description="还没有食材" />
      <FoodCard v-for="food in foods" :key="food.id" :food="food" @edit="goEdit" />
      <van-loading v-if="loading" class="loading-center" />
    </van-pull-refresh>
  </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { fetchFoods } from '../api/food'
import { useCategoryStore } from '../stores/category'
import FoodCard from '../components/FoodCard.vue'

const route = useRoute()
const router = useRouter()
const categoryStore = useCategoryStore()
const foods = ref([])
const loading = ref(false)
const refreshing = ref(false)

const query = reactive({
  keyword: '',
  categoryId: route.query.categoryId ? Number(route.query.categoryId) : '',
  status: route.query.status || '',
  page: 1,
  pageSize: 50
})

const statusOptions = [
  { text: '全部状态', value: '' },
  { text: '正常', value: 'NORMAL' },
  { text: '即将过期', value: 'EXPIRING_SOON' },
  { text: '已过期', value: 'EXPIRED' }
]

const categoryOptions = computed(() => [
  { text: '全部分类', value: '' },
  ...categoryStore.categories.map((item) => ({ text: item.name, value: item.id }))
])

async function refresh() {
  loading.value = true
  try {
    const params = {
      ...query,
      categoryId: query.categoryId || undefined,
      status: query.status || undefined
    }
    const result = await fetchFoods(params)
    foods.value = result.records
  } finally {
    loading.value = false
    refreshing.value = false
  }
}

function goEdit(food) {
  router.push(`/foods/${food.id}/edit`)
}

onMounted(async () => {
  await categoryStore.loadCategories()
  await refresh()
})
</script>
