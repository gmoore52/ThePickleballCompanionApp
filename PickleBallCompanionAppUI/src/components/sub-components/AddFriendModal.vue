<template>
  <v-overlay
    v-model="dialog"
    max-width="500px"
    class="d-flex justify-center align-center"
    @update:model-value="loadUsers">
    <v-card class="add-friend-card">
      <!-- Close Button -->
      <v-btn
        class="float-right mt-1 mr-1"
        size="small"
        icon
        color="red"
        @click="close">
        <v-icon>mdi-close</v-icon>
      </v-btn>

      <!-- Card Title -->
      <v-card-title>
        Search For User
      </v-card-title>

      <!-- Autocomplete Input -->
      <v-form ref="form" v-model="valid" class="mb-3 pl-3 pr-3">
        <v-autocomplete
          lazy
          v-model="selectedUser"
          class="mt-6"
          :items="userOptions"
          item-title="userName"
          item-value="userName"
          label="Select a user"
          placeholder="Enter a Username"
          hide-details
          clearable
          no-data-text="No username found"
          @change="selectUser"
        ></v-autocomplete>
      </v-form>

      <!-- Card Actions -->
      <div class="friend-btn">
        <!-- <v-spacer></v-spacer> -->
        <go-to-player-profile-button
          @click="close"
          :playerUsername="selectedUser"
        ></go-to-player-profile-button>
      </div>
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
import { useRouter } from 'vue-router';
import UserProfilePopup from './UserProfilePopup.vue';
import GoToPlayerProfileButton from '@/components/sub-components/GoToPlayerProfileButton.vue';

const dialog = ref(false);
const userOptions = ref([]);
const selectedUser = ref(null);
const selectedUserData = ref({});
const valid = ref(false);
const showUserProfileDialog = ref(false);

const props = defineProps({
  currentUser: String
});

const emit = defineEmits(['close']);
const router = useRouter();

onMounted(loadUsers);

async function loadUsers() {
  try {
    const response = await fetchData('/users/all/users');
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
</script>

<style scoped>
.add-friend-card {
  width: 400px;
  padding: 10px
}

.v-card{
  border-radius: 8px;
}

.friend-btn{
  padding-top: 0px;
  padding: 12px;
}

</style>
