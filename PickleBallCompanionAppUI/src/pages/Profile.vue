<template>
  <v-app>
    <v-container>
      <v-row v-if="isLoggedIn" justify="space-between" align="stretch" class="flex-grow-1">
        <v-col cols="12" md="12" class="d-flex flex-column">
          <v-card class="pa-4 flex-grow-1" outlined>
            <v-row>
              <!-- User Information Section -->
              <v-col cols="12" md="3">
                <v-card-title class="white--text text-h5">{{ userData.userName }}</v-card-title>
                <v-card-subtitle class="white--text text-h6">{{ userData.userFullName }}</v-card-subtitle>
                <v-img
                  :width="300"
                  aspect-ratio="16/9"
                  cover
                  src="https://hips.hearstapps.com/hmg-prod/images/pickleball-tips-04-1658358796.png"
                ></v-img>
              </v-col>

              <v-col cols="12" md="8">
                <v-card class="info-card">
                  <v-card-title class="white--text">Email</v-card-title>
                  <v-card-text class="white--text">{{ userData.emailAddress }}</v-card-text>
                  <v-card-title class="white--text">Skill Level</v-card-title>
                  <v-card-text class="white--text">{{ skillLevels[userData.skillLevel] }}</v-card-text>
                  <v-card-title class="white--text">Account Creation Date</v-card-title>
                  <v-card-text class="white--text">{{ formatDate(userData.accCreationDate) }}</v-card-text>
                </v-card>
                <v-btn prepend-icon="mdi-logout" class="mt-5" color="red" @click="showLogoutConfirm = true">Logout</v-btn>
              </v-col>
            </v-row>
          </v-card>
        </v-col>

        <!-- Friends Section -->
        <v-col cols="12" md="12" class="mt-4">
          <v-card class="pa-4" outlined>
            <v-card-title class="white--text text-h4 d-flex justify-space-between">
              Friends
              <v-btn prepend-icon="mdi-account-multiple-plus" color="green" @click="showAddFriendModal = true">Add Friend</v-btn>
            </v-card-title>
            <v-list v-if="friends.length">
              <v-list-item v-for="friend in friends" :key="friend.id">
                <v-list-item-content>
                  <v-list-item-title class="white--text">{{ friend.name }}</v-list-item-title>
                  <v-list-item-subtitle class="white--text">{{ friend.email }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-card-text v-else class="white--text">You have no friends added yet.</v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <v-row v-else>
        <v-col class="text-center">
          <v-card class="pa-4 border-dark">
            <v-card-title class="white--text text-h5">You are not logged in</v-card-title>
            <v-card-text class="white--text">
              Please <router-link to="/login" class="text-blue">Click Here</router-link> to log in and access your profile.
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <!-- Add Friend Modal -->
      <AddFriendModal
        :model-value="showAddFriendModal"
        :currentUser="loggedInUserName"
        @close="showAddFriendModal = false"
      />

      <!-- Logout Confirmation Dialog -->
      <v-dialog v-model="showLogoutConfirm" max-width="400">
        <v-card>
          <v-card-title class="white--text">Confirm Logout</v-card-title>
          <v-card-text class="white--text">Are you sure you want to log out?</v-card-text>
          <v-card-actions>
            <v-btn color="green" @click="confirmLogout">Yes</v-btn>
            <v-btn color="grey" @click="showLogoutConfirm = false">Cancel</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
  </v-app>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { fetchData } from '@/util/fetchData.js';
import { useStore } from 'vuex';
import { showAlert } from "@/util/alert";
import router from "@/router";
import AddFriendModal from '@/components/sub-components/AddFriendModal.vue'; // Import the AddFriendModal component

const store = useStore();

const userData = ref({
  userName: '',
  userFullName: '',
  emailAddress: '',
  accCreationDate: '',
  skillLevel: ''
});

const friends = ref([]); // State for user's friends
// Skill level mapping to be used with getSkillLevelText to present skill level as a string on the front end
const skillLevels = {
  1: "Beginner",
  2: "Intermediate",
  3: "Advanced",
  4: "Pro"
};

// Function to get skill level text
const getSkillLevelText = (level) => {
  return skillLevels[level] || "Unknown";
};

const showLogoutConfirm = ref(false); // State for logout confirmation dialog
const showAddFriendModal = ref(false); // State for add friend modal

// Computed property to determine if the user is logged in
const isLoggedIn = computed(() => store.state.isAuthenticated);
const loggedInUserName = computed(() => {
  if (isLoggedIn.value) {
    return store.state.user.userName;
  }
  return null; // Or you can return an empty string or some default value if not logged in
});

// Function to fetch user data from the database
async function fetchUserData() {
  try {
    const json = await fetchData(`/users/find?username=${loggedInUserName.value}`);
    console.log(json);
    Object.assign(userData.value, json); // Merging user data
  } catch (err) {
    console.error('Error fetching user data:', err);
  }
}

// Function to fetch friends data from the database
// async function fetchFriends() {
//   try {
//     friends.value = await fetchData(`/users/${route.params.username}/friends`); // Fetch friends based on the route parameter
//   } catch (err) {
//     console.error('Error fetching friends:', err);
//   }
// }

// Date formatting utility
const formatDate = (date) => {
  return new Date(date).toLocaleDateString();
};

onMounted(() => {
  if (isLoggedIn.value) {
    fetchUserData(); // Fetch the user data for the selected profile
    // fetchFriends(); // Fetch the friends of the selected profile
  }
});


// Confirm logout action
const confirmLogout = async () => {
  showLogoutConfirm.value = false; // Close the dialog
  await store.dispatch('logout');
  showAlert('success', 'You have been successfully logged out.', 5000);
  await router.push('/');
};
</script>

<style scoped>
.info-card{
  background-color: #42424254;
}

/* Add styles as needed */
</style>
