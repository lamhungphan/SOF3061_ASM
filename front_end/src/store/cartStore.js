import { defineStore } from 'pinia';
import { ref } from 'vue';
import axios from 'axios';

export const useCartStore = defineStore('cart', () => {
  const cart = ref([]);

  // 🛍 Lấy giỏ hàng từ API
  const fetchCart = async (userId) => {
    try {
      const response = await axios.get(`http://localhost:8080/cart/${userId}`);
      cart.value = Array.isArray(response.data.data) ? response.data.data : (response.data.data ? [response.data.data] : []);
      console.log('Dữ liệu giỏ hàng:', cart.value);
    } catch (error) {
      console.error('Lỗi khi lấy giỏ hàng:', error.response?.data || error.message);
    }
  };

  // 🛒 Gửi API thêm sản phẩm vào giỏ hàng
  const addToCart = async (userId, productId, quantity) => {
    try {
      const existingItem = cart.value.find(item => item.productId === productId);
  
      if (existingItem) {
        // Nếu sản phẩm đã tồn tại, cập nhật số lượng
        await axios.put(`http://localhost:8080/cart/update`, {
          userId, 
          productId, 
          quantity: existingItem.quantity + quantity
        });
      } else {
        // Nếu sản phẩm chưa tồn tại, thêm mới
        await axios.post('http://localhost:8080/cart/add', { userId, productId, quantity });
      }
  
      // Lấy lại giỏ hàng từ server để đảm bảo dữ liệu đồng bộ
      await fetchCart(userId);
      alert('Thêm vào giỏ hàng thành công! 🎉');
    } catch (error) {
      console.error('Lỗi khi thêm vào giỏ hàng:', error.response?.data || error.message);
      alert('Lỗi khi thêm vào giỏ hàng!');
    }
  };

  // ❌ Xóa 1 sản phẩm khỏi giỏ hàng
  const removeFromCart = async (userId, productId) => {
    try {
      await axios.delete(`http://localhost:8080/cart/remove/${userId}/${productId}`);
      await fetchCart(userId);
    } catch (error) {
      console.error('Lỗi khi xóa sản phẩm khỏi giỏ hàng:', error.response?.data || error.message);
    }
  };

  // 🗑 Xóa toàn bộ giỏ hàng của user
  const clearCart = async (userId) => {
    try {
      await axios.delete(`http://localhost:8080/cart/clear/${userId}`);
      cart.value = [];
    } catch (error) {
      console.error('Lỗi khi xóa toàn bộ giỏ hàng:', error.response?.data || error.message);
    }
  };

  return { cart, fetchCart, addToCart, removeFromCart, clearCart };
});