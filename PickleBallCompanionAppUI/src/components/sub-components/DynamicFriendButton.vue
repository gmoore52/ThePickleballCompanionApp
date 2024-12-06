<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useStore } from 'vuex';
import { fetchData } from '@/util/fetchData.js';
import { showAlert } from "@/util/alert";
import { useRouter } from 'vue-router';

const props = defineProps({
  friendRequestStatus: String,
  selectedUsername: String,
  loggedInUsername: String,
});

const store = useStore();
const router = useRouter();

const emit = defineEmits(['reload']);

function sendNewFriendRequest(){
  
  showAlert('success',`Friend request sent to ${store.state.selectedUsername}`)
  
}

function returnFriendStatus(){

  return props.friendRequestStatus;

}

function handleRequestDeny(){
  try {
    // console.log(`/friends/revoke/${store.state.selectedUsername}/${otherUsername}`)
    const response = fetchData(`/friends/revoke/${store.state.user.userName}/${store.state.selectedUsername}`,
    {
      method:'POST',
    });
    console.log(response)
    // showAlert('success', 'Friend request accepted')
    showAlert('success',`${store.state.selectedUsername} removed from your friends list`)
  } 
    catch (error){
    // console.error('Error adding Event:', response);
    showAlert('error', `Friend request could not be rejected`)
  } 
  
  emit('reload');
}

function handleRequestCreate(){
  try {
      // console.log(`/friends/accept/${store.state.selectedUsername}/${store.state.user.userName}`)
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

  // addFriend(otherUsername)
  // otherUsername

  emit('reload');
  //showAlert('error','Friend request could not be accepted')

}

function handlePendingBtnClick(){
  showAlert('warning','Friend is pending to be accepted by ' + store.state.selectedUsername)

  //call the get status here again

}

function handleRequestConfirm(){
  try {
      // console.log(`/friends/accept/${store.state.selectedUsername}/${store.state.user.userName}`)
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


  // addFriend(otherUsername)
  // otherUsername

  emit('reload');
}

// Need a looot of logic here to determine what to do with the button, and when.

</script>
<template>

  <!-- One of the buttons will display when you are viewing someone elses profile -->
  <span v-if="store.state.user.userName !== store.state.selectedUsername">
    
    <!-- Request is pending button 'REC' from this user to you-->
    <v-btn v-if="returnFriendStatus() == `REC - received`" prepend-icon="mdi-account-plus" class="grey-btn" @click="handleRequestConfirm()">
      Accept Friend Request
      <template v-slot:prepend> <v-icon color="success"></v-icon> </template>    
    </v-btn>

    <!-- Request is pending button 'REC' from you to this user-->
    <span v-if="returnFriendStatus() == `REC - sent`" @click="handlePendingBtnClick()">
      <v-btn prepend-icon="mdi-account-plus" disabled class="grey-btn">
        Request pending  
      </v-btn>
    </span>

    <!-- Add friend button 'ACC' to or from you -->
    <v-btn v-if="returnFriendStatus() == `ACC`" prepend-icon="mdi-account-minus" class="grey-btn" @click="handleRequestDeny()">
      Remove Friend
      <template v-slot:prepend> <v-icon color="red"></v-icon> </template>
    </v-btn>

    <!-- It is 'REV', 'REJ' or there is not an entry-->
    <v-btn v-if="returnFriendStatus() == `NONE`" prepend-icon="mdi-account-plus" class="grey-btn" @click="handleRequestCreate()">
      add friend
      <template v-slot:prepend> <v-icon color="success"></v-icon> </template>
    </v-btn>

  </span>

</template>
<style scoped>

.grey-btn{
  background-color:#42424254;
}

</style>