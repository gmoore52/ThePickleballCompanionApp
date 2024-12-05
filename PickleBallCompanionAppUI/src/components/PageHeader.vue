<template>
  <!-- App Bar -->
  <v-app-bar class="sticky" app color="green darken-4" dark>
    <v-toolbar-title>
      <div class="text-h4 pickle-title">Pickleball Companion</div>
    </v-toolbar-title>

    <!-- Tabs within the app bar -->
    <v-tabs class="d-flex justify-center flex-grow-1" v-model="tab" background-color="grey lighten-2">
      <v-tab
        v-for="item in filteredTabs"
        :key="item.title"
        :to="item.route"
        @click="returnToYourView(item.title)"
        ripple
      >
        {{ item.title }}
      </v-tab>
    </v-tabs>

    <!-- User-specific buttons -->
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
</template>

<script setup>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { useAlert } from '@/util/alert.js';


const router = useRouter();
const store = useStore();

// Computed properties to access Vuex state
const isLoggedIn = computed(() => store.state.isAuthenticated);
const user = computed(() => {
  if (isLoggedIn.value) {
    return store.state.user.userName;
  }
  return '';
});

// Filter tabs based on user login status
const filteredTabs = computed(() => {
  if (isLoggedIn.value) {
    return tabs.value; // Show all tabs
  } else {
    return tabs.value.filter(tab => tab.title !== 'Log game' && tab.title !== 'Stats' && tab.title !== 'Profile' && tab.title !== 'Game History');
  }
});

// Tabs data for navigation
const tab = ref(null);
const tabs = ref([
  { title: 'Home', route: '/' },
  { title: 'Courts', route: '/courts' },
  { title: 'Events', route: '/events' },
  { title: 'Log game', route: '/log-game' },
  { title: 'Stats', route: `/stats/${store.state.selectedUsername}` },
  { title: 'Game History', route: `/game-history/${store.state.selectedUsername}` },
  { title: 'Profile', route: `/profile/${store.state.selectedUsername}` },
]);

// Use alert state and methods
const { alert, showAlert, closeAlert } = useAlert();

// Navigation functions
const goToLogin = () => {
  router.push('/login');
}

const goToRegister = () => {
  router.push(`/register`);
}

const goToProfile = () => {
  store.commit('UNSET_SELECTED_USERNAME');
  router.push(`/profile/:userId`);
}

function returnToYourView(tabName) {
  store.commit('UNSET_SELECTED_USERNAME');
}

// Example of triggering an alert (you can call this from anywhere)
const triggerErrorAlert = () => {
  showAlert('error', 'Error: Incorrect email/username or password.', 5000); // Show alert for 5 seconds
};
</script>

<style scoped>
.alert {
  position: fixed;
  z-index: 9999;
  margin-top: 35px;
  right: 50%;
  transform: translateX(50%);
}

.pickle-title{
  min-width: 10000px;
  font-family: 'Roboto';
}
</style>
