<template>
    <div>
        <table class="table table-bordered text-center">
            <thead>
                <tr>
                    <th>Hình</th>
                    <th>Tên</th>
                    <th>Mô Tả</th>
                    <th>Giá</th>
                    <th>Danh Mục</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="product in products" :key="product.id">
                    <td><img :src="product.image" class="table-img" /></td>
                    <td>{{ product.name }}</td>
                    <td>{{ product.size }}</td>
                    <td>{{ product.price}} đ</td>
                    <td>{{ product.categoryId}}</td>
                    <td>
                        <button type="button" @click="selected(product)" class="btn btn-sm btn-primary me-1">Sửa</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup>

const props = defineProps({
  products: Array,
})
const emits = defineEmits(['edit'])

function selected(product) {
  emits('edit', product)
}

</script>