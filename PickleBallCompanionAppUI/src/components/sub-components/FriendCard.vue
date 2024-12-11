<script setup>
import { ref, defineEmits, defineProps, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { showAlert } from '@/util/alert'
import { fetchData } from '@/util/fetchData.js';

const props = defineProps({
  friend: Object, // Receive current user's username as a prop
  isRequest: Boolean
});

const store = useStore();
const router = useRouter();

const friendCardImagePath = ref(`/images/${props.friend.userName}.jpg`)

const emit = defineEmits(['reload']);

function formatFullName(user){
  if (user.userName == store.state.user.userName){
    return user.userFullName + ' (You)'
  }
  else{
    return user.userFullName
  }
}

function handleRequestDeny(otherUsername){
  try {
      // console.log(`/friends/accept/${store.state.selectedUsername}/${otherUsername}`)
      const response = fetchData(`/friends/reject/${store.state.selectedUsername}/${otherUsername}`,
      {
        method:'POST',
      });
    }
     catch (error){
      console.error('Error adding Event:', error);
      showAlert('error', `Friend request not successfully rejected`)
    }

    emit('reload');
}

function handleRequestConfirm(otherUsername){
  try {
      // console.log(`/friends/accept/${store.state.selectedUsername}/${otherUsername}`)
      const response = fetchData(`/friends/accept/${store.state.selectedUsername}/${otherUsername}`,
      {
        method:'POST',
      });
      showAlert('success', 'Friend request accepted')
    }
     catch (error){
      console.error('Error adding Event:', error);
      showAlert('error', `Friend request not successfully accepted`)
    }


  // addFriend(otherUsername)
  // otherUsername

  emit('reload');
}

function onImageError(){
  friendCardImagePath.value = '/images/default-profile-image.jpg';
}

</script>
<template>
  <v-card link class="friend-card">
  <v-col cols="12" class="">
    <v-row class="no-formatting">
    <v-col cols="3" class="img-container">
      <!-- <img :src="friend.profilerImgLoc"> -->
      <v-img class="profile-pic" :src="friendCardImagePath" 
      aspect-ratio="1"
      cover
      @error="onImageError"></v-img>
    </v-col>
    <v-col cols="8">
      <div class="inner-container">
        <h3 class="full-name">
          {{ formatFullName(friend) }}
        </h3>
        <p class="user-name">
          {{friend.userName}}
        </p>
      </div>
    </v-col>
    <v-col cols="1">
        <!-- <v-btn></v-btn> -->

    </v-col>
    <v-col v-if="isRequest" cols="12" class="btn-container">
      <v-divider></v-divider>
      <v-card-actions class="btn-actions">
      <v-btn @click.stop="handleRequestDeny(friend.userName)">
        <!-- <template v-slot:prepend> <v-icon color="red"></v-icon> </template>  -->
        Deny
      </v-btn>
      <v-btn @click.stop="handleRequestConfirm(friend.userName)" class="accept-friend-req-btn">
        <!-- <template v-slot:prepend> <v-icon color="success"></v-icon> </template>  -->
        Confirm
      </v-btn> 
    </v-card-actions>
    </v-col>
  </v-row>
  </v-col>
  </v-card>

  <!-- mdi-account-cancel -->

</template>
<style scoped>

.card-row{
  padding-left: 12px;
  padding-right: 12px;
  padding-top: 12px;
  padding-bottom: 12px;
}

.no-formatting{
  padding: 0;
}

.btn-container{
  padding: 0;
}

.btn-actions{
  float: right;
}

.accept-friend-req-btn{
  background-color: rgb(33, 150, 243);
}

.img-container{
  padding: 8px;
}

.inner-container{
  justify-content: center;
  align-items: center;
}

.profile-pic{
  height: 100%;
  width:100%;
  float: left;
  border-radius: 8px;
}

.user-name{
  font-size: 14px;
}

.full-name{
  font-size: 20px;
}

.friend-card{
  display: flex;
  padding: 6px;
  background-color: #42424254;
  border-radius: 8px;
  /* height: 100px; */
  width: 100%;
}

.v-container {
  max-width: 1168px;
  }

</style>
