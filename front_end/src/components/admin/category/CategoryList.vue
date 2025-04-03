<template>
    <!-- Danh sách danh mục -->
    <div>
        <h4 class="mb-3">Danh sách ngành hàng</h4>
        <table class="table table-bordered text-center">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="category in categories" :key="category.id">
                    <td>{{ category.id }}</td>
                    <td>{{ category.name }}</td>
                    <td>
                        <button class="btn btn-sm btn-primary me-1" @click="selected(category)">Sửa</button>
                    </td>
                </tr>
            </tbody>
        </table>
    
    </div>
</template>

<script setup>

const props = defineProps({
  categories: Array
})
const emit = defineEmits(['edit'])

function selected(category) {
  emit('edit', category)
}

</script>