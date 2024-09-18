<script setup>
import { ref } from 'vue';
import { onMounted } from '@vue/runtime-core';

  const userScore = ref(null);
  const oppScore = ref(null);
  const gameDate = ref(null);
  const locations = ref(["loc1","loc2","loc3","loc4"]);
  const location = ref(null);
  const notes = ref(null);
  
  const players1 = ref(["bob1","sue1","ron1","jon1"]);
  const players2 = ref(["bob2","sue2","ron2","jon2"]);
  const players3 = ref(["bob3","sue3","ron3","jon3"]);
  const players4 = ref(["bob4","sue4","ron4","jon4"]);

  const player1 = ref(null);
  const player2 = ref(null);
  const player3 = ref(null);
  const player4 = ref(null);
  
  function getData(){
    // unused currently, will connect to the backend
    
  }

  function parseData(){
    

  }

  function submit(){
    var jsonGame = {};
    var dataNames = ['userScore','oppScore','gameDate','location','notes','player1','player2','player3','player4'];
    var dataValues = [userScore.value, oppScore.value, gameDate.value, location.value, notes.value, player1.value, player2.value, player3.value, player4.value]
    //console.log('left side stuff', userScore.value, oppScore.value, gameDate.value, location.value, notes.value, player1.value, player2.value, player3.value, player4.value);

    for (let i = 0; i< dataNames.length; i++){
      jsonGame[dataNames[i]] = dataValues[i];
    }
    console.log(jsonGame);
   
  }

  onMounted(() => {
    getData();
    parseData();
  })

  // todo: add all the functionality LOL
  // todo: seems that we are onto form validation, resizing for phone screen, and some sort of option for singles v doubles 

  const dummyData = 
  [{
    username: "john_doe",
    firstname: "John",
    lastname: "Doe",
    email_address: "user1@example.com",
    password: "password123",
    profile_img: "profile_img_1.png",
    skill_level: 5,
    acc_creation_date: "2023-01-10"
  },
  {
    username: "jane_smith",
    firstname: "Jane",
    lastname: "Smith",
    email_address: "user2@example.com",
    password: "mypassword456",
    profile_img: "profile_img_2.png",
    skill_level: 7,
    acc_creation_date: "2023-02-15"
  },
  {
    username: "michael_johnson",
    firstname: "Michael",
    lastname: "Johnson",
    email_address: "user3@example.com",
    password: "password789",
    profile_img: "profile_img_3.png",
    skill_level: 4,
    acc_creation_date: "2023-03-20"
  },
  {
    username: "emily_davis",
    firstname: "Emily",
    lastname: "Davis",
    email_address: "user4@example.com",
    password: "securepass987",
    profile_img: "profile_img_4.png",
    skill_level: 6,
    acc_creation_date: "2023-04-25"
  },
  {
    username: "james_wilson",
    firstname: "James",
    lastname: "Wilson",
    email_address: "user5@example.com",
    password: "user5pass",
    profile_img: "profile_img_5.png",
    skill_level: 8,
    acc_creation_date: "2023-05-30"
  },
  {
    username: "olivia_brown",
    firstname: "Olivia",
    lastname: "Brown",
    email_address: "user6@example.com",
    password: "mypassword123",
    profile_img: "profile_img_6.png",
    skill_level: 5,
    acc_creation_date: "2023-06-14"
  },
  {
    username: "william_taylor",
    firstname: "William",
    lastname: "Taylor",
    email_address: "user7@example.com",
    password: "userpass789",
    profile_img: "profile_img_7.png",
    skill_level: 9,
    acc_creation_date: "2023-07-19"
  },
  {
    username: "sophia_moore",
    firstname: "Sophia",
    lastname: "Moore",
    email_address: "user8@example.com",
    password: "pass456user",
    profile_img: "profile_img_8.png",
    skill_level: 6,
    acc_creation_date: "2023-08-24"
  },
  {
    username: "benjamin_martinez",
    firstname: "Benjamin",
    lastname: "Martinez",
    email_address: "user9@example.com",
    password: "9userpass321",
    profile_img: "profile_img_9.png",
    skill_level: 3,
    acc_creation_date: "2023-09-29"
  },
  {
    username: "amelia_garcia",
    firstname: "Amelia",
    lastname: "Garcia",
    email_address: "user10@example.com",
    password: "10userpass654",
    profile_img: "profile_img_10.png",
    skill_level: 7,
    acc_creation_date: "2023-10-04"
  }];

</script>
<template>
  <v-container fluid>
    <h1>Log Game</h1>
    <v-layout row wrap>
        <v-card class="w-50 card">
          <v-row id="row1">
            <!-- <v-col cols="3">Your Score</v-col>
            <v-col cols="3"></v-col>
            <v-col cols="3">Opp. Score</v-col>
            <v-col cols="3"></v-col> -->
            
            <v-col cols="4">
              <v-text-field v-model="userScore" label="Your Score" type="number" class="num"></v-text-field>
            </v-col>
            <v-col cols="4">
              
            </v-col>
          
            <v-col cols="4">
              <v-text-field v-model="oppScore" label="Opp Score" type="number" class="num"></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field v-model="gameDate" label="Date" type="date" class="text"></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-autocomplete v-model="location" clearable label="Location"type="text" class="text" :items=locations></v-autocomplete>
            </v-col> 
            <v-col cols="12">
              <v-text-field v-model="notes" label="(Optional) Notes"type="text" class="text"></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-btn @click="submit" variant="tonal" class="w-100"> Submit Game</v-btn>
            </v-col>
          </v-row color="green">
      </v-card>  
      <v-card class="w-50 card">
        <v-row id="row2 w-100">
          <v-col> 
            <div class="court border-lg border-b-md border-e-md">
              <v-autocomplete v-model="player1" class="player-search" clearable label="Player 1" :items="players1">

              </v-autocomplete>
            </div> 
            <div class="court border-lg border-b-md border-s-md">
              <v-autocomplete v-model="player2" class="player-search" clearable label="Player 2" :items="players2">
          
              </v-autocomplete>
            </div>
            <div class="court border-lg border-t-md border-e-md">
              <v-autocomplete v-model="player3" class="player-search" clearable label="Player 3" :items="players3">
              </v-autocomplete>
            </div>
            <div class="court border-lg border-t-md border-s-md">
              <v-autocomplete v-model="player4" class="player-search" clearable label="Player 4" :items="players4">

              </v-autocomplete>
            </div>
          </v-col>
        </v-row>
      </v-card> 
    </v-layout>
  </v-container>
</template>

<style scoped>
  .card{
    padding: 0.8em;
    margin: 0.1em;
    /* height: 700px; */
  }
  #notes{
    height:200px;
  }
  #row2{
    width: 50% !important;
  }
  .court{
    height:15rem;
    width:50%;
    display: inline-block;
    margin: auto;
    text-align: center;
  }
  .player-search{
    margin-top: 1rem;
    margin-left: 1rem;
    margin-right: 1rem;
  }
  
</style>