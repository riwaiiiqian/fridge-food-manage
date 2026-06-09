<template>
  <section class="page">
    <header class="page-header compact">
      <h1>分类管理</h1>
      <van-button icon="plus" type="primary" size="small" round @click="openEditor()" />
    </header>

    <van-empty v-if="!categories.length" image-size="80" description="暂无分类" />
    <div v-for="category in categories" :key="category.id" class="category-row">
      <span class="category-dot large" :style="{ backgroundColor: category.color }"></span>
      <div>
        <strong>{{ category.name }}</strong>
        <p>{{ category.itemCount || 0 }} 个食材</p>
      </div>
      <van-button icon="edit" size="small" plain @click="openEditor(category)" />
      <van-button icon="delete-o" size="small" plain type="danger" @click="remove(category)" />
    </div>

    <van-dialog v-model:show="showEditor" title="分类" show-cancel-button @confirm="save">
      <van-field v-model="form.name" label="名称" placeholder="分类名称" />
      <van-field v-model="form.color" label="颜色" placeholder="#3f8cff" />
      <van-field v-model="form.sortOrder" label="排序" type="digit" placeholder="0" />
      <div class="color-swatches">
        <button
          v-for="color in swatches"
          :key="color"
          :style="{ backgroundColor: color }"
          @click.prevent="form.color = color"
        ></button>
      </div>
    </van-dialog>
  </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { showConfirmDialog, showSuccessToast } from 'vant'
import { createCategory, deleteCategory, updateCategory } from '../api/category'
import { useCategoryStore } from '../stores/category'

const categoryStore = useCategoryStore()
const categories = computed(() => categoryStore.categories)
const showEditor = ref(false)
const editingId = ref(null)
const form = reactive({ name: '', color: '#3f8cff', sortOrder: 0 })
const swatches = ['#39b980', '#ff9f43', '#f05656', '#5b8def', '#a071ff', '#00a6a6']

function openEditor(category) {
  editingId.value = category?.id || null
  Object.assign(form, {
    name: category?.name || '',
    color: category?.color || '#3f8cff',
    sortOrder: category?.sortOrder || 0
  })
  showEditor.value = true
}

async function save() {
  if (editingId.value) {
    await updateCategory(editingId.value, form)
  } else {
    await createCategory(form)
  }
  showSuccessToast('保存成功')
  await categoryStore.loadCategories()
}

async function remove(category) {
  await showConfirmDialog({ title: '删除分类', message: `确认删除「${category.name}」吗？` })
  await deleteCategory(category.id)
  showSuccessToast('删除成功')
  await categoryStore.loadCategories()
}

onMounted(() => categoryStore.loadCategories())
</script>
