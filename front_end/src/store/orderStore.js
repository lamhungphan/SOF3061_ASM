import { defineStore } from 'pinia';
import axios from 'axios';

const getOrdersByUserId = (userId) => axios.get(`http://localhost:8080/order/user/${userId}`);

export const useOrderStore = defineStore('order', {
    state: () => ({
        orders: [],
        currentOrder: null,
        orderDetailsList: [],
        isLoading: false,
    }),

    actions: {
        async fetchOrders({ keyword = '', sort = '', page = 1, size = 5 } = {}) {
            this.isLoading = true;
            try {
                const response = await axios.get('http://localhost:8080/order', {
                    params: { keyword, sort, page, size },
                });
                const pageData = response.data.data;
                this.orders = pageData.content;
                return pageData;
            } catch (error) {
                console.error('Error fetching orders:', error);
                throw error;
            } finally {
                this.isLoading = false;
            }
        },

        async fetchOrderById(orderId) {
            if (!orderId) {
                console.error('Không thể fetchOrderById vì orderId không hợp lệ:', orderId);
                return;
            }
            try {
                const response = await axios.get(`http://localhost:8080/order/${orderId}`);
                this.currentOrder = response.data.data;
                return this.currentOrder;
            } catch (error) {
                console.error(`Error fetching order with id ${orderId}:`, error);
                throw error;
            }
        },

        async createOrder(orderData) {
            try {
                const response = await axios.post('http://localhost:8080/order', orderData);
                const newOrder = response.data.data;
                console.log('Order created:', newOrder);

                if (newOrder && newOrder.id) {
                    this.orders.push(newOrder);
                    this.currentOrder = newOrder;
                    await this.fetchOrderById(newOrder.id);
                } else {
                    console.warn('⚠️ createOrder trả về order không có ID:', newOrder);
                }

                return newOrder;
            } catch (error) {
                console.error('Error creating order:', error);
                throw error;
            }
        },

        async updateOrder(orderId, orderData) {
            try {
                const response = await axios.put(`http://localhost:8080/order/${orderId}`, orderData);
                const updatedOrder = response.data.data;
                const index = this.orders.findIndex(order => order.id === orderId);
                if (index !== -1) {
                    this.orders[index] = updatedOrder || orderData;
                }
                if (this.currentOrder && this.currentOrder.id === orderId) {
                    this.currentOrder = updatedOrder || orderData;
                }
                return updatedOrder || orderData;
            } catch (error) {
                console.error(`Error updating order with id ${orderId}:`, error);
                throw error;
            }
        },

        async deleteOrder(orderId) {
            try {
                await axios.delete(`http://localhost:8080/order/${orderId}`);
                this.orders = this.orders.filter(order => order.id !== orderId);
                if (this.currentOrder && this.currentOrder.id === orderId) {
                    this.currentOrder = null;
                }
            } catch (error) {
                console.error(`Error deleting order with id ${orderId}:`, error);
                throw error;
            }
        },

        async fetchOrderDetails({ keyword = '', sort = '', page = 1, size = 5 } = {}) {
            try {
                const response = await axios.get('http://localhost:8080/order-detail', {
                    params: { keyword, sort, page, size },
                });
                const pageData = response.data.data;
                this.orderDetailsList = pageData.content;
                return pageData;
            } catch (error) {
                console.error('Error fetching order details:', error);
                throw error;
            }
        },

        async fetchOrdersByUser(userId) {
            this.loading = true;
            try {
                const response = await getOrdersByUserId(userId);
                this.orders = response.data.data;
            } catch (err) {
                this.error = err;
                console.error("Failed to fetch orders:", err);
            } finally {
                this.loading = false;
            }
        },
    },
});
