<template>
  <!-- App Bar -->
  <v-app-bar app color="green darken-4" dark>
    <v-toolbar-title class="text-center">
      <span class="text-h4 font-italic">Pickleball Companion</span>
    </v-toolbar-title>

    <v-spacer></v-spacer>

    <v-toolbar-items>
      <template v-if="isLoggedIn">
        <v-btn color="white" @click="goToProfile">{{ user }}</v-btn>
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
      v-for="item in filteredTabs"
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
const user = computed(() => {
  if (isLoggedIn.value) {
    return store.state.user.userName;
  }
  return ''; // Or you can return an empty string or some default value if not logged in
});
// Filter tabs based on user login status
const filteredTabs = computed(() => {
  // If the user is logged in, show all tabs including "Profile"
  if (isLoggedIn.value) {
    return tabs.value; // Show all tabs
  }
  // If the user is not logged in, only show specific tabs
  return tabs.value.filter(tab => tab.title !== 'Log game' && tab.title !== 'Stats' && tab.title !== 'Profile' && tab.title !== 'Game History');
});

// Tabs data for navigation
const tab = ref(null);
const tabs = ref([
  { title: 'Home', route: '/' },
  { title: 'Courts', route: '/courts' },
  { title: 'Events', route: '/events' },
  { title: 'Log game', route: '/log-game' },
  { title: 'Stats', route: '/stats' },
  { title: 'Game History', route: `/game-history` }, ///${user.value}
  { title: 'Profile', route:  '/profile' },//isLoggedIn.value ? `/profile/${user.value}` :
  
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
  router.push(`/profile`); // /${user.value}
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
