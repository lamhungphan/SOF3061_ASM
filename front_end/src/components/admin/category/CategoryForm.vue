<template>
    <!-- Form Thêm / Sửa danh mục -->
    <form class="mb-3 p-3 border rounded shadow-sm bg-light" @submit.prevent="addCategory">
        <div class="mb-2">
            <label for="categoryName" class="form-label">Tên Danh Mục</label>
            <input v-model="category.name" type="text" class="form-control" id="categoryName" required />
        </div>
        <div class="d-flex justify-content-end mt-2 gap-2">
            <button type="reset" class="btn btn-warning" @click="category.name = ''; category.id = null">Đặt Lại</button>
            <button type="submit" class="btn btn-primary">
                Thêm Danh Mục
            </button>
            <button type="button" @click="updateCategory" class="btn btn-primary">
                Cập Nhật
            </button>
            <button type="button" @click="remove" class="btn btn-primary">
                Xoá
            </button>
        </div>
    </form>
</template>

<script setup>

const props = defineProps({
  category: Object
})

const emit = defineEmits(['add', 'update', 'remove'])

function addCategory() {
  emit('add', props.category)
}

function updateCategory() {
  emit('update', props.category)
}

function remove() {
  emit('remove', props.category)
}
</script>