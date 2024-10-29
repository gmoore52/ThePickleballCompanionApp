<template>
  <!-- App Bar -->
  <v-app-bar app color="green darken-4" dark>
    <v-toolbar-title class="text-center">
      <span class="text-h4 font-italic">Pickleball Companion</span>
    </v-toolbar-title>

    <v-spacer></v-spacer>

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

  <!-- Alert Section -->
  <v-alert
    class="alert"
    v-if="alert.visible"
    :type="alert.type"
    dismissible
    @click:close="closeAlert"
    absolute
    top
    left
    transition="slide-x-transition"
  >
    {{ alert.message }}
  </v-alert>

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
import { useAlert } from '@/util/alert.js'; // Import the alert utility

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

// Use alert state and methods
const { alert, showAlert, closeAlert } = useAlert();

// Navigation functions
const goToLogin = () => {
  router.push('/login');
}

const goToRegister = () => {
  router.push('/register');
}

const goToProfile = () => {
  router.push(`/profile`);
}

// Example of triggering an alert (you can call this from anywhere)
const triggerErrorAlert = () => {
  showAlert('error', 'Error: Incorrect email/username or password.', 5000); // Show alert for 5 seconds
};
</script>
<style scoped>
.alert {
  position: absolute;
  z-index: 9999;
  margin-top: 35px;
  right: 50%;
  transform: translateX(50%);
}
</style>
