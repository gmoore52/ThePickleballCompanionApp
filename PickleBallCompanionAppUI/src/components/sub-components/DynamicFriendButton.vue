<script setup>
import { useStore } from 'vuex';
import { fetchData } from '@/util/fetchData.js';
import { showAlert } from "@/util/alert";

const props = defineProps({
  friendRequestStatus: String,
  selectedUsername: String,
  loggedInUsername: String,
});

const store = useStore();

const emit = defineEmits(['reload']);

function returnFriendStatus(){
  return props.friendRequestStatus;
}

function handleRequestDeny(){
  try {
    const response = fetchData(`/friends/revoke/${store.state.user.userName}/${store.state.selectedUsername}`,
    {
      method:'POST',
    });
    showAlert('success',`${store.state.selectedUsername} removed from your friends list`)
  }
    catch (error){
    showAlert('error', `Friend request could not be rejected`)
  }
  emit('reload');
}

function handleRequestCreate(){
  try {
      const response = fetchData(`/friends/create/${store.state.user.userName}/${store.state.selectedUsername}`,
      {
        method:'POST',
      });
      showAlert('success', 'Friend request sent')
    }
     catch (error){
      console.error('Error adding Event:', response);
      showAlert('error', `Friend request not successfully sent`)
    }
  
    emit('reload');
}

function handlePendingBtnClick(){
  showAlert('warning','Friend is pending to be accepted by ' + store.state.selectedUsername)
}

function handleRequestConfirm(){
  try {
      const response = fetchData(`/friends/accept/${store.state.user.userName}/${store.state.selectedUsername}`,
      {
        method:'POST',
      });
      showAlert('success', 'Friend request accepted')
    }
     catch (error){
      console.error('Error adding Event:', response);
      showAlert('error', `Friend request not successfully accepted`)
    }
  emit('reload');
}

</script>
<template>
  <!-- One of the buttons will display when you are viewing someone elses profile -->
  <span v-if="store.state.user.userName !== store.state.selectedUsername">

    <!-- Request is pending button 'REC' from this user to you-->
    <v-btn v-if="returnFriendStatus() == `REC - received`" prepend-icon="mdi-account-multiple-plus" class="mt-0" color="green" @click="handleRequestConfirm()">Accept Friend Request</v-btn>

    <!-- Request is pending button 'REC' from you to this user-->
    <span v-if="returnFriendStatus() == `REC - sent`" @click="handlePendingBtnClick()">
      <v-btn prepend-icon="mdi-account-multiple-plus" disabled color="green" class="mt-0">Request pending</v-btn>
    </span>

    <!-- Add friend button 'ACC' to or from you -->
    <v-btn v-if="returnFriendStatus() == `ACC`" prepend-icon="mdi-account-multiple-minus" class="mt-0" color="red" @click="handleRequestDeny()">Remove Friend</v-btn>

    <!-- It is 'REV', 'REJ' or there is not an entry-->
    <v-btn v-if="returnFriendStatus() == `NONE`" prepend-icon="mdi-account-multiple-plus" class="mt-0" color="green" @click="handleRequestCreate()">add friend</v-btn>
  </span>
</template>
<style scoped>
</style>
