import { createStore } from 'vuex';
import { reactive } from 'vue';
import { fetchData } from "@/util/fetchData";

const store = createStore({
  state: {
    user: null,
    selectedUsername: null,
    isAuthenticated: false,
  },
  mutations: {
    SET_USER(state, user) {
      state.user = user;
      state.selectedUsername = user.userName;
      state.isAuthenticated = !!user;
      // Persist the user information to localStorage
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem('isAuthenticated', state.isAuthenticated);
      localStorage.setItem('selectedUsername', user.userName);
      fetchData(`/statistics/stageUserStatsHst?username=${store.state.selectedUsername}`)
    },
    LOGOUT(state) {
      state.user = null;
      state.isAuthenticated = false;
      localStorage.removeItem('user');
      localStorage.removeItem('isAuthenticated');
      localStorage.removeItem('selectedUsername', state.selectedUsername);
    },
    SET_SELECTED_USERNAME(state, userName) {
      state.selectedUsername = userName
      localStorage.setItem('selectedUsername', userName);
    },
    UNSET_SELECTED_USERNAME(state) {
      if (state.isAuthenticated) {
        state.selectedUsername = state.user.userName
        localStorage.setItem('selectedUsername', state.user.userName);
      }
    }
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
      const selectedUsername = localStorage.getItem('selectedUsername')

      if (user) {
        commit('SET_USER', user);
      }
      if (isAuthenticated) {
        commit('SET_USER', user); // Set the user if authenticated
      }
      if (selectedUsername) {
        commit('SET_SELECTED_USERNAME', selectedUsername);
      }
    }
  }
});

// Initialize store from localStorage right after creating it
store.dispatch('initializeStore').catch(error => {
  console.error('Failed to initialize store:', error);
});

export default store;
