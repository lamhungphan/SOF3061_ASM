import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import axios from 'axios';

export const useCartStore = defineStore('cart', () => {
  const cart = ref([]);

  const getLocalCart = () => {
    const localCart = localStorage.getItem('localCart');
    return localCart ? JSON.parse(localCart) : [];
  };

  const saveLocalCart = (cartItems) => {
    localStorage.setItem('localCart', JSON.stringify(cartItems));
  };

  const fetchCart = async (userId) => {
    try {
      const response = await axios.get(`http://localhost:8080/cart/${userId}`);
      cart.value = Array.isArray(response.data.data) ? response.data.data : [];
    } catch (error) {
      console.error('Lỗi khi lấy giỏ hàng:', error);
      cart.value = [];
    }
  };

  const addToCart = async (userId, productId, quantityChange = 1) => {
    try {
      if (!userId) {
        let localCart = getLocalCart();
        const existingItem = localCart.find(item => item.productId === productId);
        if (existingItem) {
          existingItem.quantity += quantityChange; // Tăng hoặc giảm theo quantityChange
          if (existingItem.quantity <= 0) {
            localCart = localCart.filter(item => item.productId !== productId); // Xóa nếu <= 0
          }
        } else if (quantityChange > 0) {
          localCart.push({ productId, quantity: quantityChange }); // Thêm mới nếu > 0
        }
        saveLocalCart(localCart);
        cart.value = localCart;
        console.log('Cập nhật giỏ hàng cục bộ thành công!');
      } else {
        const existingItem = cart.value.find(item => item.productId === productId);
        const newQuantity = existingItem ? existingItem.quantity + quantityChange : quantityChange;
        const cartItem = {
          userId,
          productId,
          quantity: newQuantity > 0 ? newQuantity : 0, // Đảm bảo không âm
        };

        if (newQuantity > 0) {
          if (existingItem) {
            await axios.put('http://localhost:8080/cart/update', cartItem);
            console.log('Cập nhật giỏ hàng thành công!');
          } else {
            await axios.post('http://localhost:8080/cart/add', cartItem);
            console.log('Thêm vào giỏ hàng thành công!');
          }
        } else if (existingItem) {
          await axios.delete(`http://localhost:8080/cart/remove/${userId}/${productId}`);
          console.log('Xóa sản phẩm khỏi giỏ hàng!');
        }
        await fetchCart(userId);
      }
    } catch (error) {
      const errorMessage = error.response?.data?.message || error.message;
      console.error('Lỗi khi cập nhật giỏ hàng:', errorMessage);
    }
  };

  const removeFromCart = async (userId, productId) => {
    try {
      if (!userId) {
        let localCart = getLocalCart();
        localCart = localCart.filter(item => item.productId !== productId);
        saveLocalCart(localCart);
        cart.value = localCart;
        console.log('Đã xóa sản phẩm khỏi giỏ hàng cục bộ!');
      } else {
        await axios.delete(`http://localhost:8080/cart/remove/${userId}/${productId}`);
        await fetchCart(userId);
        console.log('Đã xóa sản phẩm khỏi giỏ hàng!');
      }
    } catch (error) {
      const errorMessage = error.response?.data?.message || error.message;
      console.error('Lỗi khi xóa sản phẩm:', errorMessage);
    }
  };

  const syncLocalCartToServer = async (userId) => {
    try {
      const localCart = getLocalCart();
      if (localCart.length > 0) {
        for (const item of localCart) {
          await addToCart(userId, item.productId, item.quantity); // Đồng bộ với số lượng hiện tại
        }
        localStorage.removeItem('localCart');
        console.log('Đồng bộ giỏ hàng lên server thành công!');
      }
      await fetchCart(userId);
    } catch (error) {
      console.error('Lỗi khi đồng bộ giỏ hàng:', error);
    }
  };

  const initializeCart = async (userId) => {
    try {
      if (userId) {
        await syncLocalCartToServer(userId);
        await fetchCart(userId);
      } else {
        cart.value = getLocalCart();
      }
    } catch (error) {
      console.error('Lỗi khi khởi tạo giỏ hàng:', error);
    }
  };

  const cartTotal = computed(() => {
    return cart.value.reduce((sum, item) => sum + (item.price || 0) * (item.quantity || 0), 0);
  });

  return { cart, fetchCart, addToCart, removeFromCart, syncLocalCartToServer, initializeCart, cartTotal };
});