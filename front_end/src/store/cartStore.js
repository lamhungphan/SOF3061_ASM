import { defineStore } from 'pinia';

export const useCartStore = defineStore('cart', {
  state: () => ({
    cart: JSON.parse(localStorage.getItem('cart')) || []
  }),

  getters: {
    totalPrice: (state) =>
      state.cart.reduce((total, item) => total + item.price * item.quantity, 0)
  },

  actions: {
    addToCart(product) {
      const existingProduct = this.cart.find(item => item.id === product.id);
      if (existingProduct) {
        existingProduct.quantity++;
      } else {
        this.cart.push({ ...product, quantity: 1 });
      }
      this.saveCart();
    },

    removeItem(index) {
      this.cart.splice(index, 1);
      this.saveCart();
    },

    increaseQuantity(index) {
      this.cart[index].quantity++;
      this.saveCart();
    },

    decreaseQuantity(index) {
      if (this.cart[index].quantity > 1) {
        this.cart[index].quantity--;
        this.saveCart();
      }
    },

    saveCart() {
      localStorage.setItem('cart', JSON.stringify(this.cart));
    },

    clearCart() {
      this.cart = [];
      localStorage.removeItem('cart');
    }
  }
});
