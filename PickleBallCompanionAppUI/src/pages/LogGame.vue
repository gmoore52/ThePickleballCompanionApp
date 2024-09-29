<script setup>
import { ref, watch} from 'vue';
import { onMounted } from '@vue/runtime-core';

  const userScore = ref(null);
  const oppScore = ref(null);
  const gameDate = ref(null);
  const locations = ref(["loc1","loc2","loc3","loc4"]); // todo: make some sort of thing where if you put an input that already exists, like with a watcher doing it on the players, itll move the old player 
  const location = ref(null);
  const notes = ref(null);

  const isInDuosMode = ref(true);
  const debugErrors = ref(null) // delete later 
  
  const players1 = ref(["bob1","sue1","ron1","jon1"]);
  const players2 = ref(["bob2","sue2","ron2","jon2"]);
  const players3 = ref(["bob3","sue3","ron3","jon3"]);
  const players4 = ref(["bob4","sue4","ron4","jon4"]);

  // THIS MUST LATER BE SET DYNAMICALLY AFTER THE USER IS LOGGED IN
  const yourUserString = ref('Your User - (must_select)');

  const yourScoreRules = [
    value => {
      if (value) return true;
      return 'You must enter your score';
    },
    value => {
      if(value < 0){
         return 'Score cannot be negative';
      }
      return true
    },
    value => {
      if(value > 11){
         return 'Score cannot be greater than 11';
      }
      return true
    }, 
  ];

  const oppScoreRules = [
    value => {
      if (value) return true;
      return 'You must enter your opponents score';
    },
    value => {
      if(value < 0){
        return 'Score cannot be negative'
      };
      return true;
    },
    value => {
      if(value > 11){
         return 'Score cannot be greater than 11';
      }
      return true
    }, 
  ];
 
  const locationRules = [
    value => {
      if (value) return true;
      return 'You must select a location';
    },
  ];

  const dateRules = [
    value => {
      if (value) return true;
      return 'You must select a date';
    },
    value => {
      const today = new Date();
      if(new Date(value) > today){
        return 'Cannot select a date in the future'
      }
      return true;
    },
    value => {
      const now = new Date();
      const oneYearAgo = new Date(now);
      oneYearAgo.setFullYear(now.getFullYear() - 1);
      if (new Date(value) < oneYearAgo){
        return 'Cannot input a game over a year old'
      }
      return true;
    }
  ];

  const player1Rules = [
    value => {
      if (value) return true;
      return 'Select player 1';
    },
  ];

  const player2Rules = [
    value => {
      if (value) return true;
      return 'Select player 2';
    },
  ];

  const player3Rules = [
    value => {
      if (value || !isInDuosMode.value) return true;
      return 'Select player 3';
    },
  ];

  const player4Rules = [
    value => {
      if (value || !isInDuosMode.value) return true;
      return 'Select player 4';
    },
  ];

  const player1 = ref(null);
  const player2 = ref(null);
  const player3 = ref(null);
  const player4 = ref(null);

  // vee validate stuff
  
  function getData(){
    // unused currently, will connect to the backend
    
  }

  function parseData(){
    let allPlayerStrings = [];
    let allLocationNames = [];
    for (const user of usersJSON) {
      const userString = `${user.firstname} ${user.lastname} - (${user.username})`;
      allPlayerStrings.push(userString);
    }
    for (const loc of locationJSON) {
      const locName = `${loc.court_name}`;
      allLocationNames.push(locName);
    }

    console.log(allPlayerStrings);
    players1.value = allPlayerStrings;
    players2.value = allPlayerStrings;
    players3.value = allPlayerStrings;
    players4.value = allPlayerStrings;

    console.log(allLocationNames);
    locations.value = allLocationNames;
  }

  function handleSubmit(){
    var jsonGame = {};
    var dataNames = ['userScore','oppScore','gameDate','location','notes','player1','player2','player3','player4'];
    var dataValues = [userScore.value, oppScore.value, gameDate.value, location.value, notes.value, player1.value, player2.value, player3.value, player4.value];

    // making the JSON object here 
    for (let i = 0; i< dataNames.length; i++){
     jsonGame[dataNames[i]] = dataValues[i];
    }

    let nullsErr = checkForNulls();
    let scoreErr = verifyScoreLogic();
    let yourUserErr = verifyYourUserSelected();

    if(nullsErr !== false){
      //debugErrors.value = `Null(s) found, first at ${nullsErr}`;
    }
    else if(scoreErr !== false){
      debugErrors.value = `${scoreErr}`;
      //console.log(`Score error found, ${scoreErr}`);
    }
    else if(yourUserErr !== false){
      debugErrors.value = `${yourUserErr}`;
      //console.log(`Player selection error, ${yourUserErr}`)
    }
    else{ // no nulls and no score err condition, send the data here to backend later 
      debugErrors.value = ''; // delete this later
      console.log(jsonGame); 
    }
  }
  
  function verifyScoreLogic(){
    if ((userScore.value == 11) && (oppScore.value == 11)){
      return 'Only one team may score 11 points, no ties allowed'
    }
    else if((userScore.value!= 11 ) && (oppScore.value != 11)){ 
      return 'One score of 11 must be reached'
      } 
    else{
      return false 
    }
  }

  function verifyYourUserSelected(){
    if (isInDuosMode.value && ((player1.value !== yourUserString.value) && (player2.value !== yourUserString.value) &&  // todo, fix this logic, we need to render more or less players with v if
    (player3.value !== yourUserString.value) && (player4.value !== yourUserString.value))){
      return 'You must select yourself as a player in the game (duos)'
    }
    else if(!isInDuosMode.value && ((player1.value !== yourUserString.value) && (player2.value !== yourUserString.value))){
      return 'You must select yourself as a player in the game (singles)'
    }  

    else{
      return false;
    }
  }

  function checkForNulls(){
    if(userScore.value == null){
      return 'userscore';
    }
    else if(oppScore.value == null){
      return 'oppScore';
    }
    else if(gameDate.value == null){
      return 'gameDate';
    }
    else if(location.value == null){
      return 'location';
    }
    else if(userScore.value == null){
      return 'userscore';
    }
    else if(player1.value == null){ 
      return 'player1';
    }
    else if(player2.value == null){  
      return 'player2';
    }
    else if(isInDuosMode.value && player3.value == null){
      return 'player3';
    }
    else if(isInDuosMode.value && player4.value == null){
      return 'player4';
    }
    else{
      return false;
    }
  }

  onMounted(() => {
    getData();
    parseData();
  })

  // todo: add all the functionality LOL
  // todo: seems that we are onto form validation, resizing for phone screen, and some sort of option for singles v doubles 
  // todo: make the watchers replace the value if it exists somehwere else already
  // todo: make it so that the player names also have their username listed Name Name - (Username)

  function checkForPlayerNameListed(name, originNum){
    if ((originNum!= 1) && (name === player1.value)){ // todo 
      player1.value = null;
    }
    else if ((originNum!= 2) && (name === player2.value)){ // todo
      player2.value = null;
    }
    else if(!isInDuosMode){
      return; // breaks the logic for singles
    }
    else if ((originNum!= 3) && (name === player3.value)){
      player3.value = null;
    }
    else if ((originNum!= 4) && (name === player4.value)){
      player4.value = null;
    }
  }

  watch(player1, (name) => {
    checkForPlayerNameListed(name, 1)
  });
  
  watch(player2, (name) => {
    checkForPlayerNameListed(name, 2)
  });

  watch(player3, (name) => {
    checkForPlayerNameListed(name, 3)
  });

  watch(player4, (name) => {
    checkForPlayerNameListed(name, 4)
  });
  watch(isInDuosMode, (bool) =>{
    if (bool === false){
      player3.value=null
      player4.value=null
    }
  });

  const usersJSON = 
  [{
    username: "must_select",
    firstname: "Your",
    lastname: "User",
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
  const locationJSON = [
  {
    "loc_id": 1,
    "court_name": "Downtown Court",
    "number_of_courts": 4,
    "address": "123 Main St, Cityville",
    "coordinates": "40.7128,-74.0060",
    "court_meta_id": 101
  },
  {
    "loc_id": 2,
    "court_name": "Westside Park",
    "number_of_courts": 6,
    "address": "456 West St, Townsville",
    "coordinates": "34.0522,-118.2437",
    "court_meta_id": 102
  },
  {
    "loc_id": 3,
    "court_name": "Eastwood Courts",
    "number_of_courts": 3,
    "address": "789 East Ave, Villageton",
    "coordinates": "51.5074,-0.1278",
    "court_meta_id": 103
  },
  {
    "loc_id": 4,
    "court_name": "Riverside Court",
    "number_of_courts": 2,
    "address": "101 River Rd, Waterbury",
    "coordinates": "35.6895,139.6917",
    "court_meta_id": 104
  },
  {
    "loc_id": 5,
    "court_name": "Central Park Courts",
    "number_of_courts": 8,
    "address": "202",
    "coordinates": "31.6895,14.6917",
    "court_meta_id": 105
  }];
</script>
<template>
  <v-container class="container">
    <v-row>
    <!-- <h1>Log Game</h1> -->
    <v-form validate-on="submit lazy" @submit.prevent="handleSubmit">
      <v-layout>
        <v-col cols="12" sm="6" id="left-pannel">
          <v-card id= "card-1" class="w-100 card">
            <v-row id="row1">
              <v-col cols="12" class="left-pannel-col left-pannel-col-header">
                <h2>Game Summary</h2>
              </v-col>
              <v-col cols="12" id="score-formatting" class="left-pannel-col">
                <v-btn-toggle
                  rounded="4"
                  group
                  v-model="isInDuosMode"
                  mandatory
                  class="left-pannel">
                  <v-btn :value="false" class="duos-toggle">
                    Singles
                  </v-btn>
                  <v-btn :value="true" class="duos-toggle">
                    Duos
                  </v-btn>
                </v-btn-toggle>
              </v-col>
              <v-col sm="6" md="5" class="left-pannel-col">
                <v-text-field v-model="userScore" label="Your Score" type="number" class="num left-pannel" required :rules="yourScoreRules"></v-text-field>
              </v-col>
              <v-col cols="2" class="left-pannel-col d-none d-md-block">
                <!-- invisible col for formatting -->
              </v-col>
              <v-col sm="6" md="5" class="left-pannel-col">
                <v-text-field v-model="oppScore" label="Opp Score" type="number" class="num left-pannel" required :rules="oppScoreRules"></v-text-field>
              </v-col>
              <v-col cols="12" class="left-pannel-col left-pannel-col-header no-margins">
                <h2>Extra Information</h2>
              </v-col>
              
              <v-col cols="12" class="left-pannel-col">
                <v-text-field v-model="gameDate" label="Date" type="date" class="text left-pannel" required :rules="dateRules"></v-text-field>
              </v-col>
              <v-col cols="12" class="left-pannel-col no-margins">
                <v-autocomplete v-model="location" clearable label="Location"type="text" class="text left-pannel" required :items=locations :rules="locationRules"></v-autocomplete>
              </v-col> 
              <v-col cols="12" class="left-pannel-col no-margins">
                <v-textarea v-model="notes" label="(Optional) Notes"type="text" class="text left-pannel"></v-textarea>
              
              </v-col>
            </v-row>
        </v-card>  
      </v-col>
      <v-col cols="12" sm="6" id="right-pannel">
        <v-card id="card2" class="card">
          <v-row id="row2 w-100">
            <v-col cols="">
              <div class="player-container-1">
                <h2 class="team-heading center left-pannel-col-header">Team 1 Players</h2> 
                <div class="court">
                  
                  <v-autocomplete v-model="player1" class="player-search" clearable required label="Player 1" :items="players1" :rules="player1Rules">
                  </v-autocomplete>
                </div> 
                <div class="court">                  
                  <v-autocomplete v-model="player3" :disabled=!isInDuosMode class="player-search" clearable required label="Player 3" :items="players3" :rules="player3Rules">
                  </v-autocomplete>
                </div>
              </div>
              <div class="team-divider">
                
              </div>
              <div class="player-container-2">  
                <h2 class="team-heading left-pannel-col-header">Team 2 Players</h2>
                <div class="court">
                
                  <v-autocomplete v-model="player2" class="player-search" clearable required label="Player 2" :items="players2" :rules="player2Rules">
              
                  </v-autocomplete>
                </div>
                <div class="court">
                  <v-autocomplete v-model="player4" :disabled=!isInDuosMode class="player-search" clearable required label="Player 4" :items="players4" :rules="player4Rules">
                  
                  </v-autocomplete>
                </div>
              </div>
            </v-col>
            <v-col cols="12" class="btn-col">
                <v-btn type="submit" variant="tonal" class="w-100 submit"> Submit Game</v-btn>
            </v-col>
            <v-col cols="12" class="errors">
                {{debugErrors}}
              </v-col>
          </v-row>
        </v-card> 
      </v-col>
      </v-layout>
    </v-form>
  </v-row>
  </v-container>
</template>

<style scoped>
  #card1{
    margin-right: 0.1em;
  }
  #card2{
    margin-left: 0.1em;
  }
  .card{
    border-radius: 8px;
    padding: 16px;
    height: 610px;
  }
  .duos-toggle{
    background-color: #333333;
  }
  .team-heading{
    text-align: center;
    justify-content: center;
    display: block;
    padding-top: 12px;
  }
  .submit{
    background-color: #4caf50;
  }
  .player-container-1{
    border-radius: 8px;
    background-color: #42424254;
    margin-bottom: 0.5rem;
  }
  .player-container-2{
    border-radius: 8px;
    background-color: #42424254;
    
  }
  .left-pannel-col{
    padding-bottom: 0rem;
  }
  #score-formatting {
    padding-bottom: 8px;
  }
  .left-pannel-col-header{
    margin-bottom: 6px;
  }
  .no-margins{
    padding-top: 1px;
    padding-bottom: 0rem;
  }
  .left-pannel{
    margin-bottom: 0rem;
    margin-top: 0rem;
  }
  #left-pannel{
    padding-right: 1px;
    display:block !important;
  }
  #right-pannel{
    padding-left: 1px;
    display:block !important;
  }
  .btn-col{
    padding-top: 0;
  }
  .submit{
    height:3rem;
  }
  #notes{ 
    height:200px;
  }
  #row2{
    width: 50% !important;
  }
  .court{
    height:12rem;
    width:50%;
    display: inline-block;
    margin: auto;
    text-align: center;
  }
  .player-search{
    margin-top: 0.5rem;
    margin-left: 1rem;
    margin-right: 1rem;
  }
  .errors{
    display: inline-block;
    margin: auto;
    text-align: center;
    color: rgb(223, 70, 70);
    font-weight: bold;
    padding-top: 0;
    font-size: 14px;
  }  
</style>