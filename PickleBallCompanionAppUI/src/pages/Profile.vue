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

const fileLoc = ref(new File([""], ""));

const userData = ref({
  userName: '',
  userFullName: '',
  emailAddress: '',
  accCreationDate: '',
  skillLevel: ''
});

const profileImgPath = ref(`/images/${store.state.selectedUsername}.jpg`)

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
    const url = `/friends/getFriendRequests/${store.state.selectedUsername}`; 
    JSONFriendRequests.value = await fetchData(url);
  } catch (error) {
    console.error(error);
  }
}

function returnHome(){
  store.commit('UNSET_SELECTED_USERNAME');
  router.push(`/profile/:userId`); 
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
    }
      catch (error){
      console.error('Error adding Event:', friendRequestStatus.value);
    }
  }
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

async function uploadProfileImage(){
  const reader = new FileReader();

  reader.onloadend = () => {
    this.encodedImage = reader.result.split(",")[1]; // Remove the data:image/... prefix
  };

  reader.readAsDataURL(fileLoc.value); // Convert the image to base64

  const payload = {
    username: store.state.selectedUsername,
    image: this.encodedImage, // Send the base64 encoded image
  }


  await fetchData("/users/uploadProfileImage", {
    method: 'POST',
    body: JSON.stringify(payload),
    headers: {
        'Content-type':'application/json'
    }});

    loadData(); // used here to populate the page with the new image
}

function visitProfile(userName){
  store.commit('SET_SELECTED_USERNAME', userName);
  router.push(`/profile/${userName}`); // /${user.value}
  window.scrollTo(0, 0);
}

async function loadData(){
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
      profileImgPath.value = `/images/${store.state.selectedUsername}.jpg`
    }
  }
);

function onImageError(){
  profileImgPath.value = '/images/default-profile-image.jpg';
}

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
          <div>          <v-btn v-if="loggedInUserName !== store.state.selectedUsername" prepend-icon="mdi-arrow-left" class="mb-3 mr-10"  @click="returnHome()">my profile</v-btn>
          </div>
          <v-card class="pa-4 flex-grow-1 border-styling" outlined>
            <v-row>
              <!-- User Information Section -->
              <v-col cols="12" md="3">

                <v-card-title class="white--text text-h5">{{ userData.userName }}</v-card-title>
                <v-card-subtitle class="white--text text-h6">{{ userData.userFullName }}</v-card-subtitle>
                <v-card-text>
                <v-img
                    class="profile-img pl-2"
                    :width="180"
                    :height="180"
                    aspect-ratio="1"
                    cover
                    :src="profileImgPath"
                    @error="onImageError"
                  ></v-img>
                </v-card-text>
              </v-col>

              <v-col cols="12" md="9" class="profile-content">

                <div class="profile-details"> 

                <v-card class="info-card">
                  <v-card-title class="white--text">Email</v-card-title>
                  <v-card-text class="white--text">{{ userData.emailAddress }}</v-card-text>
                  <v-card-title class="white--text">Skill Level</v-card-title>
                  <v-card-text class="white--text">{{ skillLevels[userData.skillLevel] }}</v-card-text>
                  <v-card-title class="white--text">Account Creation Date</v-card-title>
                  <v-card-text class="white--text">{{ formatDate(userData.accCreationDate) }}</v-card-text>
                
                </v-card>
                <!-- Displays if you are looking at a profile other than your own -->
                <div class="profile-details-buttons">
                  <v-btn v-if="loggedInUserName !== store.state.selectedUsername" prepend-icon="mdi-chart-line" class="mt-5 grey-btn" @click="visitStats(store.state.selectedUsername)">
                    View Stats
                    <template v-slot:prepend> <v-icon color="info"></v-icon> </template>
                  </v-btn>
                  <v-btn v-if="loggedInUserName !== store.state.selectedUsername" prepend-icon="mdi-history" class="mt-5 mx-2 grey-btn" @click="visitGameHistory(store.state.selectedUsername)">
                    View Game History
                    <template v-slot:prepend>
                      <v-icon color="info"></v-icon>
                    </template>
                  </v-btn>
                </div>
              </div> 
              <div class="profile-utility-buttons">
                <div v-if="loggedInUserName === store.state.selectedUsername" class="profile-upload-container">
                    <v-file-input
                    prepend-icon="mdi-camera"
                    class="file-input"
                    density="compact"
                    accept="image/*"
                    label="Change profile picture"
                    v-model="fileLoc">
                    </v-file-input>

                    <v-btn class ='upload-img-button grey-btn' prepend-icon="mdi-upload" @click="uploadProfileImage()">Upload</v-btn>
                  </div>
                  <v-divider v-if="loggedInUserName === store.state.selectedUsername" class="right-pannel-divider">
                    
                  </v-divider>
                  <dynamic-friend-button
                :friendRequestStatus="friendRequestStatus"
                :selectedUsername="store.state.selectedUsername"
                :loggedInUsername="store.state.user.userName"
                @reload="loadData()"
                >
                </dynamic-friend-button>
                <v-btn v-if="loggedInUserName === store.state.selectedUsername" prepend-icon="mdi-logout" class="mt-0 grey-btn" @click="showLogoutConfirm = true">
                  Logout
                  <template v-slot:prepend> <v-icon color="red"></v-icon> </template>
                </v-btn>
              </div>
              </v-col>
            </v-row>
          </v-card>
        </v-col>

        <!-- Friend Request Section -->
        <v-col v-if="(JSONFriendRequests.length !== 0) && (loggedInUserName === store.state.selectedUsername)"cols="12" md="12" class="pt-3 pb-3">
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
              <v-btn v-if="loggedInUserName === store.state.selectedUsername" prepend-icon="mdi-magnify" class='grey-btn' @click="showAddFriendModal = true">
                Search all users
                <template v-slot:prepend> <v-icon color="success"></v-icon> </template>
              </v-btn>
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

.profile-upload-container{
  width: 250px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.right-pannel-divider{
  margin-top: 5px;
  width: 100%;
  margin-bottom: 5px;
}

.profile-img{
  justify-content: center;
  text-align: center;
  border-radius: 12px;
}

.file-input{
  min-width: 100%;
}

.v-input__details{
  padding-top: 0;
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

.grey-btn{
  background-color: #42424254;
}

.profile-content {
  display: grid;
  grid-template-columns: 1fr auto;
  grid-column-gap: 16px;
}

.profile-details {
  display: grid;
  grid-template-rows: 1fr auto;
}

.profile-utility-buttons {
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: flex-end;
}

</style>
