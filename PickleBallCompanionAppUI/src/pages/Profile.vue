<template>
  <v-app>
    <v-container fluid>
      <v-row justify="space-between" align="stretch" class="flex-grow-1">
        <!-- Main Left Section (Welcome and Events) -->
        <v-col cols="12" md="12" class="d-flex flex-column">
          <!-- Profile Box -->
          <v-card class="pa-4 flex-grow-1" outlined>
            <v-row>
              <v-col cols="12" md ="3">
                <v-card-title class="white--text text-h4">{{userData.userName}}</v-card-title> <!--Pulled from DB-->
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
                  <v-card-text class="white--text">{{ userData.emailAddress }}</v-card-text> <!-- Editable, on separate window? Are we doing that? -->
                  <v-card-title class="white--text">Location (city)</v-card-title>
                  <v-card-text class="white--text">Springfield, MO --not currently in DB</v-card-text>
                  <v-card-title class="white--text">Birthday</v-card-title>
                  <v-card-text class="white--text">xx/xx/xxxx --not currently in DB</v-card-text>
                  <v-card-title class="white--text">Account Creation Date</v-card-title>
                  <v-card-text class="white--text">{{new Date(userData.accCreationDate).toLocaleDateString()}}</v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-card>
        <v-row> <!--Empty space--></v-row>
        </v-col>
        <v-card-title class="white--text text-h4">Friends</v-card-title>
        <!-- Will probably need to render the friends on the JS side here. Can you give v-card a custom class? Iterate through and append to v-card class = "friends"?-->
      </v-row>
    </v-container>
  </v-app>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { fetchData } from '@/util/fetchData.js';
import { useRoute } from 'vue-router';

  const userData = ref({
    userName: '',
    userFullName: '',
    emailAddress: '',
    password:'',
    profileImgLoc: '',
    skillLevel: '',
    accCreationDate: ''
  });

  //function to fetch user data from DB
async function fetchUserData(username) {
  console.log('username' + username);
  try {
    const json = await fetchData(`/users/find?username=${username}`
    );

    console.log('json' + JSON.stringify(json));

    userData.value = json[0];

  } catch(err) {
    console.err('error fetchUserData', err);
  }
}

  const route = useRoute();

 onMounted(() => {
   const userNameFromURL = route.params.username; //defined as this in index.js

   if (userNameFromURL) {
     fetchUserData(userNameFromURL);
   }
   else {
     fetchUserData('john_doe');
   }
 })

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
