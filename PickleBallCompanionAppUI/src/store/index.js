import { createStore } from 'vuex';
import { fetchData } from "@/util/fetchData";

const store = createStore({
  state: {
    user: null,
    isAuthenticated: false,
  },
  mutations: {
    SET_USER(state, user) {
      state.user = user;
      state.isAuthenticated = !!user;
      // Persist the user information to localStorage
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem('isAuthenticated', state.isAuthenticated);
    },
    LOGOUT(state) {
      state.user = null;
      state.isAuthenticated = false;
      localStorage.removeItem('user');
      localStorage.removeItem('isAuthenticated');
    },
  },
  actions: {
    async login({ commit }, credentials) {
      const loginDTO = { username: credentials.login, password: credentials.password, }
      const response = await fetchData('/user/login', {
        method: 'POST',
        body: JSON.stringify(loginDTO),
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (response) {
        commit('SET_USER', response);
      } else {
        throw new Error(response.message);
      }
    },
    logout({ commit }) {
      commit('LOGOUT');
    },
    initializeStore({ commit }) {
      // Initialize state from localStorage
      const user = JSON.parse(localStorage.getItem('user'));
      const isAuthenticated = localStorage.getItem('isAuthenticated') === 'true';

      if (user) {
        commit('SET_USER', user);
      }
      if (isAuthenticated) {
        commit('SET_USER', user); // Set the user if authenticated
      }
    },
  }
});

// Initialize store from localStorage right after creating it
store.dispatch('initializeStore').catch(error => {
  console.error('Failed to initialize store:', error);
});
export default store;
