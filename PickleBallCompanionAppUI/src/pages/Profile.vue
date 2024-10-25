<template>
  <v-app>
    <v-container fluid>
      <!-- Check if user is logged in -->
      <v-row v-if="isLoggedIn" justify="space-between" align="stretch" class="flex-grow-1">
        <!-- Main Left Section (Profile Information) -->
        <v-col cols="12" md="12" class="d-flex flex-column">
          <v-card class="pa-4 flex-grow-1" outlined>
            <v-row>
              <v-col cols="12" md="3">
                <v-card-title class="white--text text-h4">{{ userData.userName }}</v-card-title>
                <v-card-subtitle class="white--text text-h6">{{ userData.userFullName }}</v-card-subtitle>
                <v-img
                  :width="300"
                  aspect-ratio="16/9"
                  cover
                  src="https://hips.hearstapps.com/hmg-prod/images/pickleball-tips-04-1658358796.png"
                ></v-img>
              </v-col>

              <v-col cols="12" md="8">
                <v-card>
                  <v-card-title class="white--text">Email</v-card-title>
                  <v-card-text class="white--text">{{ userData.emailAddress }}</v-card-text>
                  <v-card-title class="white--text">Location (City)</v-card-title>
                  <v-card-text class="white--text">Springfield, MO -- not currently in DB</v-card-text>
                  <v-card-title class="white--text">Birthday</v-card-title>
                  <v-card-text class="white--text">xx/xx/xxxx -- not currently in DB</v-card-text>
                  <v-card-title class="white--text">Account Creation Date</v-card-title>
                  <v-card-text class="white--text">{{ formatDate(userData.accCreationDate) }}</v-card-text>
                </v-card>
                <v-btn class="mt-5" color="red" @click="logout">Logout</v-btn>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
        <v-card-title class="white--text text-h4">Friends</v-card-title>
        <!-- Friends rendering logic will go here -->
      </v-row>

      <!-- Render message with link to login page if user is not logged in -->
      <v-row v-else>
        <v-col class="text-center">
          <v-card class="pa-4" outlined>
            <v-card-title class="white--text text-h5">You are not logged in</v-card-title>
            <v-card-text class="white--text">
              Please <router-link to="/login" class="text-blue">click here</router-link> to log in and access your profile.
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue';
import {fetchData} from '@/util/fetchData.js';
import {useStore} from 'vuex';
import {showAlert} from "@/util/alert";
import router from "@/router";

const store = useStore();

const userData = ref({
  userName: '',
  userFullName: '',
  emailAddress: '',
  accCreationDate: ''
});

// Computed property to determine if the user is logged in
const isLoggedIn = computed(() => store.state.isAuthenticated);

// Function to fetch user data from the database
async function fetchUserData(username) {
  try {
    const json = await fetchData(`/users/find?username=${username}`);
    Object.assign(userData.value, json); // Merging user data
  } catch (err) {
    console.error('Error fetching user data:', err);
  }
}

// Date formatting utility
const formatDate = (date) => {
  return new Date(date).toLocaleDateString();
};

onMounted(() => {
  if (isLoggedIn.value){
    fetchUserData(store.state.user.userName);
  }
});

// Logout method
const logout = async () => {
  await store.dispatch('logout');
  await router.push('/');
  showAlert('success', 'You have been successfully logged out.', 5000);
};
</script>

<style scoped>
.v-app {
  height: 100vh;
}

.v-container {
  height: 100%;
}

.v-card {
  border: 1px solid white;
  padding: 0.8em;
  margin: 0.1em;
  color: #212121 !important;
}

.white--text {
  color: white !important;
}

.v-row {
  margin-bottom: 20px;
}
</style>
