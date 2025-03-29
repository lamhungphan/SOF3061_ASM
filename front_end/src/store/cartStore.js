import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useCartStore = defineStore('cart', () => {
  const cart = ref([]);

  // 🛍 Lấy giỏ hàng từ API
  const fetchCart = async (userId) => {
    try {
      const response = await fetch(`http://localhost:8080/cart/${userId}`);
      const data = await response.json();
      cart.value = data.data ? [data.data] : []; // Chuyển thành mảng nếu API trả về object
    } catch (error) {
      console.error('Lỗi khi lấy giỏ hàng:', error);
    }
  };

  // 🛒 Gửi API thêm sản phẩm vào giỏ hàng
  const addToCart = async (userId, productId, quantity) => {
    try {
      const response = await fetch('http://localhost:8080/cart/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, productId, quantity }),
      });

      if (!response.ok) throw new Error('Thêm vào giỏ hàng thất bại!');
      await fetchCart(userId);
      alert('Thêm vào giỏ hàng thành công! 🎉');
    } catch (error) {
      console.error(error);
      alert('Lỗi khi thêm vào giỏ hàng!');
    }
  };

  // ❌ Xóa một sản phẩm khỏi giỏ hàng
  const removeFromCart = async (userId, productId) => {
    try {
      await fetch(`http://localhost:8080/cart/remove/${userId}/${productId}`, {
        method: 'DELETE',
      });
      await fetchCart(userId);
    } catch (error) {
      console.error('Lỗi khi xóa sản phẩm khỏi giỏ hàng:', error);
    }
  };

  // 🗑 Xóa toàn bộ giỏ hàng của user
  const clearCart = async (userId) => {
    try {
      await fetch(`http://localhost:8080/cart/clear/${userId}`, {
        method: 'DELETE',
      });
      cart.value = [];
    } catch (error) {
      console.error('Lỗi khi xóa toàn bộ giỏ hàng:', error);
    }
  };

  return { cart, fetchCart, addToCart, removeFromCart, clearCart };
});
