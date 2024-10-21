<template>
  <!-- App Bar -->
  <v-app-bar app color="green darken-4" dark>
    <!-- Centered Title -->
    <v-toolbar-title class="text-center">
      <span class="text-h4 font-italic">Pickleball Companion</span>
    </v-toolbar-title>

    <!-- Spacer to push the buttons to the right -->
    <v-spacer></v-spacer>

    <!-- Right Section: Full Name or Login/Register -->
    <v-toolbar-items>
      <template v-if="isLoggedIn">
        <v-btn color="white" @click="goToProfile">{{ user.userName }}</v-btn>
      </template>
      <template v-else>
        <v-btn color="white" @click="goToLogin">Login</v-btn>
        <v-btn color="white" @click="goToRegister">Register</v-btn>
      </template>
    </v-toolbar-items>
  </v-app-bar>

  <!-- Tabs Section -->
  <v-tabs v-model="tab" background-color="grey lighten-2" centered>
    <v-tab
      v-for="item in tabs"
      :key="item.title"
      :to="item.route"
      ripple
    >
      {{ item.title }}
    </v-tab>
  </v-tabs>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

// Router instance
const router = useRouter();
const store = useStore();

// Computed properties to access Vuex state
const isLoggedIn = computed(() => store.state.isAuthenticated);
const user = computed(() => store.state.user);

// Tabs data for navigation
const tab = ref(null);
const tabs = ref([
  { title: 'Home', route: '/' },
  { title: 'Courts', route: '/courts' },
  { title: 'Events', route: '/events' },
  { title: 'Log game', route: '/log-game' },
  { title: 'Stats', route: '/stats' },
  { title: 'Profile', route: '/profile' }
]);

// Navigation to login and register pages
const goToLogin = () => {
  router.push('/login');
}

const goToRegister = () => {
  router.push('/register');
}

const goToProfile = () => {
  router.push(`/profile/${user.value.userName}`); // Adjust the route if necessary
}
</script>

<style scoped>
.v-toolbar-title {
  font-weight: bold;
}

.v-app-bar {
  box-shadow: none;
}
</style>
