<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { fetchData } from '@/util/fetchData.js';
import { useStore } from 'vuex';
import { showAlert } from "@/util/alert";
import { useRouter } from 'vue-router';
import AddFriendModal from '@/components/sub-components/AddFriendModal.vue'; // Import the AddFriendModal component
import FriendCard from '@/components/sub-components/FriendCard.vue';
import DynamicFriendButton from '@/components/sub-components/DynamicFriendButton.vue';

const store = useStore();
const router = useRouter();

const JSONFriends = ref([]); // State for user's friends
const JSONFriendRequests = ref([]);
const friendRequestStatus = ref();

const userData = ref({
  userName: '',
  userFullName: '',
  emailAddress: '',
  accCreationDate: '',
  skillLevel: ''
});

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
    const json = await fetchData(`/users/find?username=${store.state.selectedUsername}`);
    Object.assign(userData.value, json); // Merging user data
  } catch (err) {
    console.error('Error fetching user data:', err);
  }
}

const fetchFriendRequests = async () => {
  JSONFriendRequests.value = [];
  try {
    const url = `/friends/getFriendRequests/${store.state.selectedUsername}`; // TODO change the path and everything here so it works correctly
    JSONFriendRequests.value = await fetchData(url);
    // console.log(JSONFriendRequests.value)
  } catch (error) {
    console.error(error);
  }
}

function returnHome(){
  store.commit('UNSET_SELECTED_USERNAME');
  router.push(`/profile/:userId`); // /${user.value} 
}

// Function to fetch friends data from the database
async function fetchFriends() {
  JSONFriends.value = [];
  try {
    JSONFriends.value = await fetchData(`/friends/getFriends/${store.state.selectedUsername}`); // Fetch friends based on the route parameter
  } catch (err) {
    console.error('Error fetching friends:', err);
  }
}

async function fetchFriendRequestStatus(){
  if (store.state.user.userName !== store.state.selectedUsername){ // if you are looking at someone elses profile, you want to fetch this status to be used on the DynamicFriendButton
    try {
        friendRequestStatus.value = await fetchData(`/friends/status/${store.state.selectedUsername}/${store.state.user.userName}`);
        // console.log(friendRequestStatus.value)
        // showAlert('success', 'Friend request accepted')
    }
      catch (error){
      console.error('Error adding Event:', friendRequestStatus.value);
      // showAlert('error', `Friend request could not be rejected`)
    }
  }
  // console.log(friendRequestStatus.value)
}

function addSelectedFriend(userName){
  // function is not complete yet, we will be calling to the database here
}

function visitStats(userName){
  store.commit('SET_SELECTED_USERNAME', userName);
  router.push(`/stats/:userId`); // /${user.value} 
  window.scrollTo(0, 0);
}

function visitGameHistory(userName){
  store.commit('SET_SELECTED_USERNAME', userName);
  router.push(`/game-history/:userId`); // /${user.value} 
  window.scrollTo(0, 0);
}

// Date formatting utility
const formatDate = (date) => {
  return new Date(date).toLocaleDateString();
};

function visitProfile(userName){
  store.commit('SET_SELECTED_USERNAME', userName);
  router.push(`/profile/${userName}`); // /${user.value} 
  window.scrollTo(0, 0);


  // fetchUserData(); 
  // getUsers();
}

async function loadData(){
  // console.log("@@@@")
  await fetchUserData(); // Fetch the user data for the selected profile
  await fetchFriendRequests();
  await fetchFriends(); // Fetch the friends of the selected profile
  await fetchFriendRequestStatus();
}

onMounted(async () => {
  if (isLoggedIn.value) {
    fetchUserData(); // Fetch the user data for the selected profile
    fetchFriendRequests();
    fetchFriends(); // Fetch the friends of the selected profile
    fetchFriendRequestStatus(); // Fetch the request status for the selected user
  }
});

watch(
  () => store.state.selectedUsername,
  (newUsername, oldUsername) => {
    if (newUsername !== oldUsername) {
      fetchUserData();
      fetchFriendRequests();
      fetchFriends(); // Fetch the friends of the selected profile TODO: pls put whatever function you do here to populate friends, here
      fetchFriendRequestStatus()
    }
  }
);


// Confirm logout action
const confirmLogout = async () => {
  showLogoutConfirm.value = false; // Close the dialog
  await store.dispatch('logout');
  showAlert('success', 'You have been successfully logged out.', 5000);
  await router.push('/');
};


</script>

<template>
  <v-app>
    <v-container>
      <v-row v-if="isLoggedIn" justify="space-between" class="flex-grow-1">
        <v-col cols="12" md="12" class="d-flex flex-column pb-3">
          <v-card class="pa-4 flex-grow-1 border-styling" outlined>
            <v-row>
              <!-- User Information Section -->
              <v-col cols="12" md="3">
                <v-card></v-card>
                <v-card-title class="white--text text-h5">{{ userData.userName }}</v-card-title>
                <v-card-subtitle class="white--text text-h6">{{ userData.userFullName }}</v-card-subtitle>
                <v-card-text>
                <v-img
                    class="profile-img pl-2"
                    :width="180"
                    :height="180"
                    aspect-ratio="1"
                    cover
                    :src="`/images/${userData.userName}.jpg`"
                  ></v-img>
                </v-card-text>
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

                <v-btn v-if="loggedInUserName === store.state.selectedUsername" prepend-icon="mdi-logout" class="mt-5" color="red" @click="showLogoutConfirm = true">Logout</v-btn>
                
                <!-- Displays if you are looking at a profile other than your own -->

                 <!-- NOTE: THIS BUTTON GOTTA BE DISABLED AND SAY "Friend Added" ONCE WE FINISH FRIEND REQUESTS IF YOU ARE ALREADY THEIR FRIEND-->
                 <v-btn v-if="loggedInUserName !== store.state.selectedUsername" prepend-icon="mdi-exit-run" class="mt-5 mr-10" color="white" @click="returnHome()">Return</v-btn>
                <v-btn v-if="loggedInUserName !== store.state.selectedUsername" prepend-icon="mdi-account-arrow-right" class="mt-5" color="blue" @click="visitStats(store.state.selectedUsername)">View Stats</v-btn>
                <v-btn v-if="loggedInUserName !== store.state.selectedUsername" prepend-icon="mdi-account-arrow-right" class="mt-5 mx-2" color="blue" @click="visitGameHistory(store.state.selectedUsername)">View Game History</v-btn>
                <!-- <v-btn v-if="loggedInUserName !== store.state.selectedUsername" prepend-icon="mdi-account-multiple-plus" class="mt-5" color="green" @click="addSelectedFriend(store.state.selectedUsername)">Add Friend</v-btn> -->

                <dynamic-friend-button
                :friendRequestStatus="friendRequestStatus"
                :selectedUsername="store.state.selectedUsername"
                :loggedInUsername="store.state.user.userName"
                @reload="loadData()"
                >
                </dynamic-friend-button>

              </v-col>
            </v-row>
          </v-card>
        </v-col>

        <!-- Friend Request Section -->
        <v-col v-if="(JSONFriendRequests.length !== 0) && (loggedInUserName === store.state.selectedUsername)"
        cols="12" md="12" class="pt-3 pb-3">
          <v-card class="pa-4 border-styling" outlined>
              <v-card-title class="white--text text-h4 d-flex justify-space-between">
                Friend Requests ({{ JSONFriendRequests.length }})
              </v-card-title>
              <v-row class="friend-container">
                <v-col v-for="(friend, index) in JSONFriendRequests" cols="4">
                  <v-row class="card-row">
                  <FriendCard 
                  @click="visitProfile(friend.userName)"
                  :friend="friend" 
                  :isRequest="true"
                  @reload="loadData()"
                  >
                  </FriendCard>
                </v-row>
                </v-col>
              </v-row>
            </v-card>
        </v-col>
      
        <!-- Friends Section -->
        <v-col cols="12" md="12" class="pt-3 pb-3">
          <v-card class="pa-4 border-styling" outlined>
            <v-card-title class="white--text text-h4 d-flex justify-space-between">
              Friends ({{ JSONFriends.length }})
              <v-btn v-if="loggedInUserName === store.state.selectedUsername" prepend-icon="mdi-account-multiple-plus" color="green" @click="showAddFriendModal = true">Search all users</v-btn>
            </v-card-title>
            <v-row class="friend-container">
              <v-col v-for="(friend, index) in JSONFriends" cols="4">
                <v-row class="card-row">
                <FriendCard 
                @click="visitProfile(friend.userName)"
                :friend="friend" 
                :isRequest="false"
                >
                
                </FriendCard>
              </v-row>
              </v-col>
            </v-row>

            <v-card-text v-if="(JSONFriends.length == 0) && (JSONFriends !== null)" class="white--text">No friends added for {{ store.state.selectedUsername }}.</v-card-text>
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
        :currentUser="store.state.selectedUsername"
        @close="showAddFriendModal = false"
      />

      <!-- Logout Confirmation Dialog -->
      <v-dialog v-model="showLogoutConfirm" max-width="400">
        <v-card class="logout-modal">
          <v-card-title class="white--text centered">Confirm Logout</v-card-title>
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

<style scoped>
.border-styling{
  border-radius: 8px;
}

.profile-img{
  justify-content: center;
  text-align: center;
  border-radius: 12px;
}

.info-card{
  background-color: #42424254;
  border-radius: 8px;
}

.card-row{
  padding-left: 12px;
  padding-right: 12px;
  padding-top: 12px;
  padding-bottom: 12px;
}

.friend-container{
  padding-left: 16px;
  padding-right: 16px;
  display: flex;
  white-space: unset;
  border-radius: 8px;
}

.friend-card{
  background-color: #42424254;
}

.v-container {
    max-width: 1168px;
}

.logout-modal{
  border-radius: 8px !important;
}

/* Add styles as needed */
</style>
