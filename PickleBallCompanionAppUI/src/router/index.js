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

  // Check if the route requires a dynamic userId parameter
  const isDynamicRoute = dynamicRoutes.some(route => to.path.includes(route));

  if (isDynamicRoute) {
    // Fetch user data if `selectedUsername` is not set
    if (!store.state.selectedUsername) {
      await store.dispatch('initializeStore');
    }

    // Check if `selectedUsername` is now loaded
    if (store.state.selectedUsername) {
      // Redirect only if the `userId` parameter does not match `selectedUsername`
      if (to.params.userId !== store.state.selectedUsername) {
        return next(`/${to.path.split('/')[1]}/${store.state.selectedUsername}`);
      }
    }
  }

  // If no redirection is needed, proceed to the next route
  next();
});

export default router;