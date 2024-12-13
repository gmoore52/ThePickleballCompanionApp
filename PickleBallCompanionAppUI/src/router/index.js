import { createRouter, createWebHistory } from 'vue-router';
import { useStore } from 'vuex';

const routes = [
  { path: '/', component: () => import('@/pages/Home.vue') },
  { path: '/courts', component: () => import('@/pages/Courts.vue') },
  { path: '/events', component: () => import('@/pages/Events.vue') },
  { path: '/log-game', component: () => import('@/pages/LogGame.vue') },
  { path: '/stats/:userId', component: () => import('@/pages/Stats.vue') },
  { path: '/profile/:userId', component: () => import('@/pages/Profile.vue') },
  { path: '/register', component: () => import('@/pages/Register.vue') },
  { path: '/login', component: () => import('@/pages/Login.vue') },
  { path: '/game-history/:userId', component: () => import('@/pages/GameHistory.vue') }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  const store = useStore();
  const dynamicRoutes = ['/game-history', '/stats', '/profile'];

  const isDynamicRoute = dynamicRoutes.some(route => to.path.includes(route));

  // Wait for the store to initialize
  if (!store.state.isInitialized) {
    await store.dispatch('initializeStore');
  }

  if (isDynamicRoute) {
    if (store.state.selectedUsername) {
      if (to.params.userId !== store.state.selectedUsername) {
        return next(`/${to.path.split('/')[1]}/${store.state.selectedUsername}`);
      }
    }
  }

  next();
});

export default router;