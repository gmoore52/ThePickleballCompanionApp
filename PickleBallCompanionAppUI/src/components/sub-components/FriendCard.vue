<script setup>
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const props = defineProps({
  friend: Object, // Receive current user's username as a prop
  isRequest: Boolean
});

const store = useStore();
const router = useRouter();

function handleRequestDeny(otherUsername){
  // otherUsername 
}

function handleRequestConfirm(otherUsername){
  // otherUsername
}

</script>
<template>
  <v-card link class="friend-card">
  <v-col cols="12" class="">
    <v-row class="no-formatting">
    <v-col cols="3" class="img-container"> 
      <!-- <img :src="friend.profilerImgLoc"> -->
      <img class="profile-pic" src='https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg'> <!--TODO: default image we will be removing -->
    </v-col>
    <v-col cols="8">
      <div class="inner-container">
        <h3 class="full-name">
          {{friend.userFullName}}
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
      <v-btn @click.stop="handleRequestDeny(friend.userFullName)" prepend-icon="mdi-account-remove" color="red">Deny</v-btn>
      <v-btn @click.stop="handleRequestConfirm(friend.userFullName)" prepend-icon="mdi-account-plus" color="green">Confirm</v-btn> 
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

.img-container{
  
}

.inner-container{
  justify-content: center;
  align-items: center;
}

.profile-pic{
  height: 64px;
  width: 64px;
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