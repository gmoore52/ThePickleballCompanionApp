import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  { path: '/', component: () => import('@/pages/Home.vue') },
  { path: '/courts', component: () => import('@/pages/Courts.vue') },
  { path: '/events', component: () => import('@/pages/Events.vue') },
  { path: '/log-game', component: () => import('@/pages/LogGame.vue') },
  { path: '/stats', component: () => import('@/pages/Stats.vue') },
  { path: '/profile', component: () => import('@/pages/Profile.vue') },
  // Add any other routes here
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
