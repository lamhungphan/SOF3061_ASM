import { defineStore } from 'pinia';
import { ref } from 'vue';
import axios from 'axios';

export const useCartStore = defineStore('cart', () => {
  const cart = ref([]);

  // Lấy giỏ hàng từ localStorage
  const getLocalCart = () => {
    const localCart = localStorage.getItem('localCart');
    return localCart ? JSON.parse(localCart) : [];
  };

  // Lưu giỏ hàng vào localStorage
  const saveLocalCart = (cartItems) => {
    localStorage.setItem('localCart', JSON.stringify(cartItems));
  };

  // Lấy giỏ hàng từ server
  const fetchCart = async (userId) => {
    try {
      const response = await axios.get(`http://localhost:8080/cart/${userId}`);
      cart.value = Array.isArray(response.data.data) ? response.data.data : (response.data.data ? [response.data.data] : []);
      console.log('Dữ liệu giỏ hàng từ server:', cart.value);
    } catch (error) {
      console.error('Lỗi khi lấy giỏ hàng:', error.response?.data || error.message);
      cart.value = []; // Đặt mặc định nếu lỗi
    }
  };

  // Thêm sản phẩm vào giỏ hàng
  const addToCart = async (userId, productId, quantity) => {
    try {
      if (!userId) {
        let localCart = getLocalCart();
        const existingItem = localCart.find(item => item.productId === productId);
        if (existingItem) {
          existingItem.quantity += quantity;
        } else {
          localCart.push({ productId, quantity });
        }
        saveLocalCart(localCart);
        cart.value = localCart;
        alert('Thêm vào giỏ hàng cục bộ thành công! 🎉');
      } else {
        const existingItem = cart.value.find(item => item.productId === productId);
        if (existingItem) {
          await axios.put(`http://localhost:8080/cart/update`, {
            userId,
            productId,
            quantity: existingItem.quantity + quantity
          });
        } else {
          await axios.post('http://localhost:8080/cart/add', { userId, productId, quantity });
        }
        await fetchCart(userId);
        alert('Thêm vào giỏ hàng thành công! 🎉');
      }
    } catch (error) {
      console.error('Lỗi khi thêm vào giỏ hàng:', error.response?.data || error.message);
      alert('Lỗi khi thêm vào giỏ hàng!');
    }
  };

  // Xóa sản phẩm khỏi giỏ hàng
  const removeFromCart = async (userId, productId) => {
    try {
      if (!userId) {
        let localCart = getLocalCart();
        localCart = localCart.filter(item => item.productId !== productId);
        saveLocalCart(localCart);
        cart.value = localCart;
      } else {
        await axios.delete(`http://localhost:8080/cart/remove/${userId}/${productId}`);
        await fetchCart(userId);
      }
    } catch (error) {
      console.error('Lỗi khi xóa sản phẩm:', error.response?.data || error.message);
    }
  };

  // Đồng bộ giỏ hàng cục bộ lên server
  const syncLocalCartToServer = async (userId) => {
    const localCart = getLocalCart();
    if (localCart.length > 0) {
      for (const item of localCart) {
        await addToCart(userId, item.productId, item.quantity);
      }
      localStorage.removeItem('localCart');
    }
    await fetchCart(userId);
  };

  // Khởi tạo giỏ hàng (trả về Promise)
  const initializeCart = async (userId) => {
    if (userId) {
      await fetchCart(userId); // Chờ lấy dữ liệu từ server
    } else {
      cart.value = getLocalCart(); // Lấy từ localStorage ngay lập tức
    }
  };

  return { cart, fetchCart, addToCart, removeFromCart, syncLocalCartToServer, initializeCart };
});