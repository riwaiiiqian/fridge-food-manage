<template>
  <article class="food-card" @click="$emit('edit', food)">
    <div class="food-card__top">
      <div>
        <h3>{{ food.name }}</h3>
        <p>{{ food.quantity }}{{ food.unit }} · {{ food.location }}</p>
      </div>
      <van-tag :type="tagType" round>{{ food.statusText }}</van-tag>
    </div>
    <div class="food-card__meta">
      <span class="category-dot" :style="{ backgroundColor: food.categoryColor }"></span>
      <span>{{ food.categoryName }}</span>
      <span>到期 {{ food.expireDate }}</span>
      <span>{{ daysText }}</span>
    </div>
    <p v-if="food.remark" class="food-card__remark">{{ food.remark }}</p>
  </article>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  food: {
    type: Object,
    required: true
  }
})

defineEmits(['edit'])

const tagType = computed(() => {
  if (props.food.status === 'EXPIRED') return 'danger'
  if (props.food.status === 'EXPIRING_SOON') return 'warning'
  return 'success'
})

const daysText = computed(() => {
  const days = props.food.daysLeft
  if (days < 0) return `已过期 ${Math.abs(days)} 天`
  if (days === 0) return '今天到期'
  return `剩余 ${days} 天`
})
</script>
