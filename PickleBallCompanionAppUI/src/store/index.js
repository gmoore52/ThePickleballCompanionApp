import { createStore } from 'vuex';
import {fetchData} from "@/util/fetchData";

const store = createStore({
  state: {
    user: null,
    isAuthenticated: false,
  },
  mutations: {
    SET_USER(state, user) {
      state.user = user;
      state.isAuthenticated = !!user;
    },
    LOGOUT(state) {
      state.user = null;
      state.isAuthenticated = false;
    },
  },
  actions: {
    async login({ commit }, credentials) {
      const loginDTO = { username: credentials.login, password: credentials.password,}
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
  },
});

export default store;

// Fake API login function for demonstration purposes
async function fakeApiLogin(credentials) {
  // Simulate a login request (replace with actual API call)
  const { usernameOrEmail, password } = credentials;
  if (usernameOrEmail === 'user@example.com' && password === 'password') {
    return { success: true, user: { username: 'User', email: 'user@example.com' } };
  }
  return { success: false, message: 'Invalid credentials' };
}
