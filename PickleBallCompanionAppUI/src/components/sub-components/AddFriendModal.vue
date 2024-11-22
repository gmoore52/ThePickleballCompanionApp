<template>
  <v-overlay v-model="dialog" max-width="500px" class="d-flex justify-center align-center" @update:model-value="loadUsers">
    <v-card class="add-friend-card">
      <v-btn
        class="float-right mt-1 mr-1"
        size="small"
        icon
        color="red"
        @click="close">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-card-title class="headline">
        Add Friend
        <v-spacer></v-spacer>
      </v-card-title>

      <v-card-text>
        <v-form ref="form" v-model="valid">
          <!-- Autocomplete for Usernames -->
          <v-autocomplete
            lazy
            v-model="selectedUser"
            class="mt-6"
            :items="userOptions"
            item-title="userName"
            item-value="userName"
            label="Find a User"
            placeholder="Enter a Username"
            clearable
            @change="selectUser"
          ></v-autocomplete>
        </v-form>
      </v-card-text>

      <v-card-actions class="pt-0">
        <v-spacer></v-spacer>
        <v-btn prepend-icon="mdi-account-eye" color="blue darken-1" @click="viewProfile" :disabled="!selectedUser">
          Preview Profile
        </v-btn>
        <v-btn
        prepend-icon="mdi-account-plus"
          color="green darken-1"
          @click="addFriend"
          :disabled="!selectedUser"
        >
          Send Request
        </v-btn>
      </v-card-actions>
    </v-card>

    <!-- User Profile Popup -->
    <UserProfilePopup
      :userInfo="selectedUserData"
      :dialog="showUserProfileDialog"
      @close="showUserProfileDialog = false"
    />
  </v-overlay>
</template>

<script setup>
import { ref, defineEmits, defineProps, onMounted } from 'vue';
import { fetchData } from "@/util/fetchData";
import { useRouter } from 'vue-router'; // Import useRouter from vue-router
import UserProfilePopup from './UserProfilePopup.vue';
import {showAlert} from "@/util/alert"; // Import the UserProfilePopup

const dialog = ref(false);
const userOptions = ref([]); // Holds all users for autocomplete
const selectedUser = ref(null); // Holds selected user data
const selectedUserData = ref({}); // Holds selected user detailed data
const valid = ref(false);
const showUserProfileDialog = ref(false); // State for user profile dialog

// Define props
const props = defineProps({
  currentUser: String // Receive current user's username as a prop
});

// Use the router
const router = useRouter(); // Get the router instance

const emit = defineEmits(['close']);

onMounted(() => {
  loadUsers();
});

// Function to load users and exclude the current user
async function loadUsers() {
  try {
    const response = await fetchData('/users/all/users'); // Fetch all users
    // console.log(response);
    // Filter out the logged-in user
    userOptions.value = response.filter(user => user.userName !== props.currentUser);
  } catch (error) {
    console.error("Error fetching users:", error);
  }
}

function selectUser(user) {
  console.log("Selected User:", user);
}

function close() {
  selectedUser.value = null;
  emit('close');
}

function addFriend() {
  if (selectedUser.value) {
    console.log(`Sending friend request to: ${selectedUser.value}`);
    showAlert('success','Friend Request Sent',2500);
    close();
  }
}

// Date formatting utility
const formatDate = (date) => {
  return new Date(date).toLocaleDateString();
};

async function viewProfile() {
  if (selectedUser.value) {
    // Fetch selected user details
    selectedUserData.value = await fetchData(`/users/find?username=${selectedUser.value}`); // Store the user info
    selectedUserData.value.accCreationDate = formatDate(selectedUserData.value.accCreationDate)
    showUserProfileDialog.value = true; // Show the user profile dialog
  }
}
</script>

<style scoped>
.add-friend-card {
  width: 40vh;
  max-width: 400px;
  margin: auto;
}

.v-container {
  max-width: 1168px;
  }
</style>
