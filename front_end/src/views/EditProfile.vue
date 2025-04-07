<template>
  <div>
    <HeaderComponent />
    <div class="container-fluid px-4 mt-4">
      <div class="row justify-content-center">
        <div class="col-12 col-md-6">
          <div class="card">
            <div class="card-header fw-bold bg-info">Thông tin cá nhân</div>
            <div class="card-body">
              <div class="card-body text-center">
                <img class="rounded-circle mb-3" alt="User Avatar" width="100" />
              </div>
              <form @submit.prevent="saveChanges">
                <div class="mb-3">
                  <label for="username" class="form-label">Tên người dùng</label>
                  <input v-model="accountRequest.username" type="text" class="form-control" id="username"
                    placeholder="Nhập tên người dùng" />
                </div>
                <div class="mb-3">
                  <label for="email" class="form-label">Email</label>
                  <input v-model="accountRequest.email" type="email" class="form-control" id="email"
                    placeholder="Nhập email" />
                </div>
            
                <div class="mb-3">
                  <label for="fullName" class="form-label">Họ và tên</label>
                  <input v-model="accountRequest.fullName" type="text" class="form-control" id="fullName"
                    placeholder="Nhập họ và tên" />
                </div>
                <div class="mb-3">
                  <label for="phone" class="form-label">Số điện thoại</label>
                  <input v-model="accountRequest.phone" type="text" class="form-control" id="phone"
                    placeholder="Nhập số điện thoại" />
                </div>
                <div class="mb-3">
                  <label for="address" class="form-label">Địa chỉ</label>
                  <input v-model="accountRequest.address" type="text" class="form-control" id="address"
                    placeholder="Nhập địa chỉ" />
                </div>
                <div class="d-flex justify-content-center">

                  <button type="submit" class="btn btn-warning w-50">
                    Cập nhật
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUsers } from "@/store/admin/UserStore";
import { toast } from "vue3-toastify";
import HeaderComponent from "@/components/HeaderComponent.vue";

const router = useRouter();
const usersStore = useUsers();

const accountRequest = ref({
  username: "",
  email: "",
  fullName: "",
  phone: "",
  address: "",
  role: "USER",
});

const userId = ref(null); 

onMounted(async () => {
  const userStr = localStorage.getItem("user");
  if (!userStr) {
    console.error("❌ Không tìm thấy user trong localStorage!");
    return;
  }

  let user;
  try {
    user = JSON.parse(userStr);
  } catch (e) {
    console.error("❌ Lỗi khi parse JSON:", e);
    return;
  }

  if (!user.id) {
    console.error("❌ userId không tồn tại trong user object:", user);
    return;
  }

  userId.value = user.id; 

  const userDetail = await usersStore.getUserDetail(userId.value);
  if (userDetail) {
    accountRequest.value = {
      ...userDetail,
      password: "",
    };
  }
});

const saveChanges = async () => {
  try {
    const { success, message } = await usersStore.updateUser(
      userId.value,
      accountRequest.value
    );

    if (success) {
      toast.error(message);
      router.push("/profile");
    } else {
      toast.success(message);
    }
  } catch (error) {
    toast.error("Đã có lỗi xảy ra. Vui lòng thử lại!");
  }
};

</script>

<style scoped>

.card {
  border-radius: 12px;
  transition: all 0.3s ease-in-out;
}

.form-control,
.form-select {
  border-radius: 8px;
}

.btn-primary {
  background-color: #007bff;
  border: none;
}

.btn-primary:hover {
  background-color: #0056b3;
}
</style>
