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
        <span v-if="isLoggedIn">{{ fullName }}</span>
        <span v-else>
          <v-btn text color="white" @click="goToLogin">Login</v-btn>
          <v-btn text color="white" @click="goToRegister">Register</v-btn>
        </span>
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
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import Register from '../pages/Register.vue'
  import Login from '../pages/Login.vue'
  
  
  // Router instance
  const router = useRouter()
  
  // Mock login status and user full name
  const isLoggedIn = ref(false)  // Set to `true` if user is logged in
  const fullName = ref('John Doe')  // Example name when logged in
  
  // Tabs data for navigation
  const tab = ref(null)
  const tabs = ref([
    { title: 'Home', route: '/' },
    { title: 'Courts', route: '/courts' },
    { title: 'Events', route: '/events' },
    { title: 'Log game', route: '/log-game' },
    { title: 'Stats', route: '/stats' },
    { title: 'Profile', route: '/profile' }
  ])
  
  // Navigation to login and register pages
  const goToLogin = () => {
    router.push('/login')
  }
  
  const goToRegister = () => {
    router.push('/register')
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
  