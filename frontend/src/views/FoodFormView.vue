<template>
  <section class="page form-page">
    <van-nav-bar :title="isEdit ? '编辑食材' : '新增食材'" left-arrow @click-left="router.back()" />

    <van-form @submit="submit">
      <van-cell-group inset>
        <van-field v-model="form.name" name="name" label="名称" placeholder="例如 牛奶" :rules="rules.name" />
        <van-field
          v-model="categoryName"
          label="分类"
          placeholder="选择分类"
          readonly
          is-link
          :rules="rules.category"
          @click="showCategoryPicker = true"
        />
        <van-field v-model="form.quantity" name="quantity" label="数量" type="number" placeholder="1" :rules="rules.quantity" />
        <van-field v-model="form.unit" name="unit" label="单位" placeholder="盒 / 个 / 份" :rules="rules.unit" />
        <van-field
          v-model="form.productionDate"
          label="生产日期"
          placeholder="可选"
          readonly
          is-link
          @click="openDatePicker('productionDate')"
        />
        <van-field v-model="form.shelfLifeDays" label="保质期" type="digit" placeholder="天数，可选" />
        <van-field
          v-model="form.expireDate"
          label="到期日期"
          placeholder="选择日期"
          readonly
          is-link
          :rules="rules.expireDate"
          @click="openDatePicker('expireDate')"
        />
        <van-field v-model="form.location" name="location" label="位置" placeholder="冷藏室" :rules="rules.location" />
        <van-field v-model="form.remark" label="备注" type="textarea" rows="2" placeholder="可选" autosize />
      </van-cell-group>

      <div class="form-actions">
        <van-button block round type="primary" native-type="submit">保存</van-button>
        <van-button v-if="isEdit" block round plain type="danger" @click="remove">删除</van-button>
      </div>
    </van-form>

    <van-popup v-model:show="showCategoryPicker" round position="bottom">
      <van-picker
        :columns="categoryColumns"
        @cancel="showCategoryPicker = false"
        @confirm="confirmCategory"
      />
    </van-popup>

    <van-popup v-model:show="showDatePicker" round position="bottom">
      <van-date-picker
        v-model="datePickerValue"
        title="选择日期"
        :min-date="minDate"
        :max-date="maxDate"
        @cancel="showDatePicker = false"
        @confirm="confirmDate"
      />
    </van-popup>
  </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { showConfirmDialog, showSuccessToast } from 'vant'
import { useRoute, useRouter } from 'vue-router'
import { createFood, deleteFood, fetchFood, updateFood } from '../api/food'
import { useCategoryStore } from '../stores/category'

const route = useRoute()
const router = useRouter()
const categoryStore = useCategoryStore()
const isEdit = computed(() => Boolean(route.params.id))

const form = reactive({
  name: '',
  categoryId: '',
  quantity: 1,
  unit: '份',
  productionDate: '',
  shelfLifeDays: '',
  expireDate: '',
  location: '冷藏室',
  remark: ''
})

const rules = {
  name: [{ required: true, message: '请填写名称' }],
  category: [{ validator: () => Boolean(form.categoryId), message: '请选择分类' }],
  quantity: [{ required: true, message: '请填写数量' }],
  unit: [{ required: true, message: '请填写单位' }],
  expireDate: [{ required: true, message: '请选择到期日期' }],
  location: [{ required: true, message: '请填写位置' }]
}

const showCategoryPicker = ref(false)
const showDatePicker = ref(false)
const activeDateField = ref('expireDate')
const datePickerValue = ref([])
const minDate = new Date(2020, 0, 1)
const maxDate = new Date(2035, 11, 31)

const categoryColumns = computed(() =>
  categoryStore.categories.map((item) => ({ text: item.name, value: item.id }))
)

const categoryName = computed(() => {
  return categoryStore.categories.find((item) => item.id === form.categoryId)?.name || ''
})

function openDatePicker(field) {
  activeDateField.value = field
  datePickerValue.value = (form[field] || today()).split('-')
  showDatePicker.value = true
}

function confirmDate({ selectedValues }) {
  form[activeDateField.value] = selectedValues.join('-')
  showDatePicker.value = false
}

function confirmCategory({ selectedOptions }) {
  form.categoryId = selectedOptions[0].value
  showCategoryPicker.value = false
}

async function submit() {
  const payload = {
    ...form,
    quantity: Number(form.quantity),
    shelfLifeDays: form.shelfLifeDays ? Number(form.shelfLifeDays) : null,
    productionDate: form.productionDate || null,
    remark: form.remark || null
  }
  if (isEdit.value) {
    await updateFood(route.params.id, payload)
  } else {
    await createFood(payload)
  }
  showSuccessToast('保存成功')
  router.push('/foods')
}

async function remove() {
  await showConfirmDialog({ title: '删除食材', message: '确认删除这个食材吗？' })
  await deleteFood(route.params.id)
  showSuccessToast('删除成功')
  router.push('/foods')
}

function today() {
  return new Date().toISOString().slice(0, 10)
}

onMounted(async () => {
  await categoryStore.loadCategories()
  if (isEdit.value) {
    Object.assign(form, await fetchFood(route.params.id))
  } else if (!form.categoryId && categoryStore.categories.length) {
    form.categoryId = categoryStore.categories[0].id
  }
})
</script>
