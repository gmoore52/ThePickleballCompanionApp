<script setup>
import { ref, watch, computed} from 'vue';
import { onMounted } from '@vue/runtime-core';
import { fetchData } from '@/util/fetchData';
import { showAlert } from '@/util/alert'
import { useStore } from 'vuex';

  const store = useStore();

  const userScore = ref(null);
  const oppScore = ref(null);
  const gameDate = ref(null);
  const locations = ref([]); // todo: make some sort of thing where if you put an input that already exists, like with a watcher doing it on the players, itll move the old player
  const location = ref(null);
  const notes = ref(null);

  const users = ref(null)
  const JSONCourts = ref([])

  const isInDuosMode = ref(true);

  const players1 = ref([]);
  const players2 = ref([]);
  const players3 = ref([]);
  const players4 = ref([]);
  const userDict = ref({}); // used to index between 'username' and 'firstname lastname - (username)' format
  const locationDict = ref({}); // used to index between loc_id and location_name

  // THIS MUST LATER BE SET DYNAMICALLY AFTER THE USER IS LOGGED IN
  const yourFullName = ref(store.state.user.userFullName)
  const yourUserName = ref(store.state.user.userName)
  const yourUserNameDisplayString = ref(`${yourFullName.value} - (${yourUserName.value})`);

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
    // value => {
    //   if(value > 11){
    //      return 'Score cannot be greater than 11';
    //   }
    //   return true
    // },
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
    // value => {
    //   if(value > 11){
    //      return 'Score cannot be greater than 11';
    //   }
    //   return true
    // },
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

  // const .Rules = [
  //   value => {
  //     if (value) return true;
  //     return 'Select player 1';
  //   },
  // ];

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


  onMounted(async () => {
    player1.value = yourUserNameDisplayString.value
    await getCourts();
    await getUsers();
    await parseData();
  })


const getUsers = async () => {
  users.value = [];
  try {
    const url = '/game/users';
    users.value = await fetchData(url);
    // console.log(users.value)
  } catch (error) {
    console.error(error);
  }
}

const getCourts = async () => {
  JSONCourts.value = [];
  try {
    const url = '/data/locations';
    JSONCourts.value = await fetchData(url);
    // console.log(JSONCourts.value);
  } catch (error) {
    console.error(error);
  }
}

  const parseData = async () =>{
    let allPlayerStrings = [];
    let allLocationNames = [];
    userDict.value = {}
    for (const user of users.value) {
      if (user.userName !== yourUserName.value){
        const userString = `${user.userFullName} - (${user.userName})`;
        userDict.value[userString] = user.userName
        allPlayerStrings.push(userString);
      }
    }
    for (const loc of JSONCourts.value) {
      const locName = `${loc.courtName}`;
      locationDict.value[locName] = loc.id
      allLocationNames.push(locName);
    }

    //console.log(allPlayerStrings);
    players1.value = allPlayerStrings;
    players2.value = allPlayerStrings;
    players3.value = allPlayerStrings;
    players4.value = allPlayerStrings;

    //console.log(allLocationNames);
    locations.value = allLocationNames;
  }

  function handleSubmit(){
    // console.log(gameDate.value)
    var jsonGame = {};
    var dataNames = ['userScore','oppScore','gameDate','location','notes','player1','player2','player3','player4'];
    var dataValues = [parseInt(userScore.value), parseInt(oppScore.value), `${gameDate.value} 00:00:00`, Number(locationDict.value[location.value]), notes.value, yourUserName.value, userDict.value[player2.value], userDict.value[player3.value], userDict.value[player4.value]];

    // making the JSON object here
    for (let i = 0; i < dataNames.length; i++){
      jsonGame[dataNames[i]] = dataValues[i];
      //console.log(dataValues[i])
    }

    let nullsErr = checkForNulls();
    let scoreErr = verifyScore();
    // let yourUserErr = verifyYourUserSelected();
    let dateErr = verifyDate();

    if(nullsErr !== false){
      showAlert('error', `Error: First error found in field: '${nullsErr}'`)
    }
    else if(dateErr !== false){
      showAlert('error', dateErr)
    }
    else if(scoreErr !== false){
      showAlert('error', scoreErr)
    }
    else{ // no nulls and no score err condition, send the data here to backend later

      console.log(jsonGame);

    try {
        const response = fetchData("/game/logGame", {
        method: 'POST', // (or 'GET')
        body: JSON.stringify(jsonGame),
        headers: {
            'Content-type':'application/json',
        }
      });

      console.log(jsonGame)
      if(jsonGame["player1"] != null){
        fetchData(`/statistics/stageUserStatsHst?username=${jsonGame["player1"]}`)
      }
      if(jsonGame["player2"] != null){
        fetchData(`/statistics/stageUserStatsHst?username=${jsonGame["player2"]}`)
      }
      if(jsonGame["player3"] != null){
        fetchData(`/statistics/stageUserStatsHst?username=${jsonGame["player3"]}`)
      }
      if(jsonGame["player4"] != null){
        fetchData(`/statistics/stageUserStatsHst?username=${jsonGame["player4"]}`)
      }

    console.log('Success - game added :', response);
    clearForm()
    showAlert('success', 'Game successfully posted')
    } catch (error){
      console.error('Error adding game:', error);
    }
  }
  }

  function verifyScore(){
    if ((userScore.value == 11) && (oppScore.value == 11)){
      return 'Only one team may score 11 points, no ties allowed'
    }
    else if((userScore.value < 11) && (oppScore.value < 11)){
      return 'One score of at least 11 must be reached'
      }
    else if((userScore.value == 11 && oppScore.value == 10) || (userScore.value == 10 && oppScore.value == 11)){
      return 'The winning team must win by 2 points'
    }
    else if ((userScore.value > 11 || oppScore.value > 11) && (Math.abs(userScore.value - oppScore.value) !== 2)) { // case where a win needs to occur by at least 2 points in OT (ie 10-11 can't work)
      return `To have a score of over 11, the winning team must win by exactly 2 points`;
    }
    else{
      return false
    }
  }

  function verifyDate(){
    // note that we are returning empty string here because the date picker already lets you know what the error is
    const today = new Date();
    const now = new Date();
    const oneYearAgo = new Date(now);
    oneYearAgo.setFullYear(now.getFullYear() - 1);

    if(new Date(gameDate.value) > today){
      return 'Cannot select a date in the future' // date is in the future
    }
    else if(new Date(gameDate.value) < oneYearAgo){
      return 'Cannot input a game over a year old' // date was over a year ago
    }
    else{
      return false
    }
  }

  // function verifyYourUserSelected(){
  //   if (isInDuosMode.value && ((userDict.value[player1.value] !== yourUserName.value) && (userDict.value[player2.value] !== yourUserName.value) &&  // todo, fix this logic, we need to render more or less players with v if
  //   (userDict.value[player3.value] !== yourUserName.value) && (userDict.value[player4.value] !== yourUserName.value))){
  //     //console.log(player1.value, player2.value, player3.value, player4.value)
  //     return 'You must select yourself as a player in the game (duos)'
  //   }
  //   else if(!isInDuosMode.value && ((userDict.value[player1.value] !== yourUserName.value) && (userDict.value[player2.value] !== yourUserName.value))){
  //     return 'You must select yourself as a player in the game (singles)'
  //   }

  //   else{
  //     return false;
  //   }
  // }

  function clearForm(){
    // form.value.reset()
    // userScore.value = null
    // gameDate.value = null
    // player1.value = yourUserNameDisplayString.value
    // player2.value = null
    // player3.value = null
    // player4.value = null
    // location.value = null

    // form.value.reset()
    // isInDuosMode.value = true


    // window.location.reload()
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

  // todo: add all the functionality LOL
  // todo: seems that we are onto form validation, resizing for phone screen, and some sort of option for singles v doubles
  // todo: make the watchers replace the value if it exists somehwere else already
  // todo: make it so that the player names also have their username listed Name Name - (Username)
  //todo: Lol you cant enter a game over 11 points for some reason haha

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

</script>
<template>
  <v-container class="container">
    <v-row>
    <!-- <h1>Log Game</h1> -->
    <v-form validate-on="submit lazy" @submit.prevent="handleSubmit" ref="form">
      <v-layout>
        <v-row class="no-styling px-3-xm row-container">
        <v-col cols="12" sm="6" id="left-pannel" class="pr-xs-5">
          <v-card id= "card-1" class="w-100 card m-sm-0">
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
                  <v-btn prepend-icon="mdi-account":value="false" class="duos-toggle">
                    Singles
                  </v-btn>
                  <v-btn prepend-icon="mdi-account-multiple":value="true" class="duos-toggle">
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
                <v-text-field v-model="oppScore" label="Opponent Score" type="number" class="num left-pannel" required :rules="oppScoreRules"></v-text-field>
              </v-col>
              <v-col cols="12" class="left-pannel-col left-pannel-col-header no-margins">
                <h2>Extra Information</h2>
              </v-col>

              <v-col cols="12" class="left-pannel-col">
                <v-text-field v-model="gameDate" label="Date" type="date" class="text left-pannel" required :rules="dateRules" ref="gd"></v-text-field>
              </v-col>
              <v-col cols="12" class="left-pannel-col no-margins">
                <v-autocomplete v-model="location" label="Game location" clearable no-data-text="Location not found" type="text" class="text left-pannel" required :items=locations :rules="locationRules"></v-autocomplete>
              </v-col>
              <v-col cols="12" class="left-pannel-col no-margins">
                <v-textarea v-model="notes" label="(Optional) Notes"type="text" class="text left-pannel"></v-textarea>

              </v-col>
            </v-row>
        </v-card>

      </v-col>
        <v-col cols="12" sm="6" id="right-pannel" class="">
        <v-card id="card2" class="card">
          <v-row id="row2 w-100">
            <v-col cols="">
              <div class="player-container-1">
                <h2 class="team-heading center left-pannel-col-header">Your Team Players</h2>
                <div class="court">
                  <v-text-field v-model="player1" class="player-search" readonly required label="You (selected)" :items="players1">
                  </v-text-field>
                </div>
                <div class="court">
                  <v-autocomplete v-model="player3" no-data-text="Player not found" :disabled=!isInDuosMode class="player-search" auto-select-first clearable required label="Player 3" :items="players3" :rules="player3Rules">
                  </v-autocomplete>
                </div>
              </div>
              <div class="team-divider">

              </div>
              <div class="player-container-2">
                <h2 class="team-heading left-pannel-col-header">Opposing Team Players</h2>
                <div class="court">

                  <v-autocomplete v-model="player2" class="player-search" clearable required no-data-text="Player not found" label="Player 2" :items="players2" :rules="player2Rules">

                  </v-autocomplete>
                </div>
                <div class="court">
                  <v-autocomplete v-model="player4" no-data-text="Player not found" :disabled=!isInDuosMode class="player-search" clearable required label="Player 4" :items="players4" :rules="player4Rules">

                  </v-autocomplete>
                </div>
              </div>
            </v-col>
            <v-col cols="12" class="btn-col">
                <v-btn prepend-icon="mdi-folder-account" type="submit" variant="tonal" class="w-100 submit"> Submit Game</v-btn>
            </v-col>
            <v-col cols="12" class="errors">
              </v-col>
          </v-row>
        </v-card>
        </v-col>
      </v-row>
      </v-layout>
    </v-form>
  </v-row>
  </v-container>
</template>

<style scoped>
  .no-styling{
    margin:0px;
  }
  /* #card1{
    margin-right: 0.1em;
  }
  #card2{
    margin-left: 0.1em;
  } */
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

  @media (max-width: 599px) {
    #left-pannel {
      padding-left: 2px;
      padding-bottom: 2px;
    }
    #right-pannel{
      padding-right: 2px;
      padding-top: 2px;
    }
    .container{
      padding-right: 25px;
      padding-left: 25px;
    }
  }
  .btn-col{
    padding-top: 0;
  }
  .submit{
    height:3rem;
  }
  #notes{
    height:100px;
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

  /* .v-container {
  max-width: 1168px;
  } */
</style>
