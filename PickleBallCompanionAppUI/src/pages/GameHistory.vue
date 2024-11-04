<script setup>
import { ref, computed, onMounted } from 'vue'
import { fetchData } from '@/util/fetchData.js'
import { useStore } from 'vuex';
import { formatDateTime } from '@/util/formatDate.js'
import GameHistoryModal from '@/components/sub-components/GameHistoryModal.vue'; 

const JSONGames = ref()
const JSONCourts = ref({});
const locationDict = ref({}); // used to index between loc_id and location_name
const modalStates = ref([]); // This will hold refs initialized to false

const smallView = ref(false)
const mediumView = ref(true)
const largeView = ref(false)

const show = ref(false)

const store = useStore();
const isLoggedIn = computed(() => store.state.isAuthenticated);
const loggedInUserName = computed(() => {
  if (isLoggedIn.value) {
    return store.state.user.userName;
  }
  return null; // Or you can return an empty string or some default value if not logged in
});

const getGames = async () => {
  JSONGames.value = [];
  try {
    JSONGames.value = await fetchData(`/game/games?username=${loggedInUserName.value}`);
    modalStates.value = new Array(JSONGames.value.length).fill(false); // Initialize modal states
    // console.log(JSONGames.value)
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

const sortGames = async () => {

  JSONGames.value = JSONGames.value
    .sort((b,a) => new Date(a.gameDate) - new Date(b.gameDate));
}

function calcWinLoss(userScore, oppScore){
  if(parseInt(userScore) < parseInt(oppScore)){
    return "Loss"
  }
  else if(parseInt(userScore) > parseInt(oppScore)){
    return "Win"
  }
}

function formatNotes(notes){
  if (notes === null){
    return 'None'
  }
  else{
    return notes
  }
}

function formatGameDate(date){
  let returnStr

  return date
}

function formatCourt(courtNum){
  return locationDict.value[courtNum]
}

// function displayWinLossBasedOnUser(game){
//   console.log(game.player1);
//   if (game.player1 === loggedInUserName.value || game.player3 === loggedInUserName.value){
//     // means you logged the game OR were on the team of the person that did 
//     return calcWinLoss(game.userScore, game.oppScore)
//   }
//   if (game.player2 === loggedInUserName.value || game.player4 === loggedInUserName.value){
//     // means your opponent logged the game, scores must be swapped
//     return calcWinLoss(game.oppScore, game.userScore)
//   }

// }

// function displayScoresBasedOnUser(game){
//   let newGame = game


function handleSmall(){
  smallView.value = true
  mediumView.value = false
  largeView.value = false
}

function handleMedium(){
  smallView.value = false
  mediumView.value = true
  largeView.value = false
}
function handleLarge(){
  smallView.value = false
  mediumView.value = false
  largeView.value = true
}

const parseData = async () =>{
  for (const loc of JSONCourts.value) {
    const locName = `${loc.courtName}`;
    locationDict.value[loc.id] = locName

  
  for (const game of JSONGames.value){
    if (game.player1 === loggedInUserName.value || game.player3 === loggedInUserName.value){
    // means you logged the game OR were on the team of the person that did, do nothing
    }
    if (game.player2 === loggedInUserName.value || game.player4 === loggedInUserName.value){
    // means your opponent logged the game, scores must be swapped between yourScore and oppScore
    let tempUserScore = game.userScore

    game.userScore = game.oppScore
    game.oppScore = tempUserScore
    }
  }
    
  }

}

onMounted(async () => {
  await getGames();
  await getCourts();
  await parseData();
  await sortGames()
});

</script>

<template>
  <v-container class="big-container">
    <v-row>
      <v-col cols="7">
        <h2>Recent Game History for {{ store.state.user.userName }}</h2>
      </v-col>
      <v-col cols="5">

        <div class="size-btn-group">
        <v-btn @click="handleSmall" class="size-btns"> 
          small view
        </v-btn>
        <v-btn @click="handleMedium" class="size-btns">
          medium view
        </v-btn>
        <v-btn @click="handleLarge" class="size-btns">
          large view
        </v-btn>
      </div>
      </v-col>

      <v-col class=""
              v-for="(game, index) in JSONGames"
              :key="game.id" sm="6" md="4" xs="12">
        <v-card link @click="modalStates[index] = !modalStates[index]">
          <v-row>
              <v-col cols="2" class="game-card">
                <v-icon v-if="calcWinLoss(game.userScore, game.oppScore) === 'Win'" icon="mdi-flag" color="green" size="large" class="icons"></v-icon>
                <v-icon v-if="calcWinLoss(game.userScore, game.oppScore) === 'Loss'" icon="mdi-flag-off" color="red" size="large" class="icons"></v-icon>
              </v-col>
              <v-col cols="10" v-if="largeView">
                <v-btn variant="tonal" rounded="lg" icon="mdi-information-outline" class="pop-out-btn"></v-btn>
              </v-col>

            <v-col :cols="largeView ? 12 : 8" class="game-card">
              
              <!-- small view -->
              <div v-if="smallView"> 
                <v-card-title class="card-header main-header">{{calcWinLoss(game.userScore, game.oppScore)}}</v-card-title>
              </div>
              
              <!-- medium view -->
              <div v-if="mediumView">
                <v-card-title class="card-header main-header">{{calcWinLoss(game.userScore, game.oppScore)}}</v-card-title>
                <v-card-title class="card-header">{{game.userScore}} - {{game.oppScore}}</v-card-title>
                <v-card-subtitle class="subtitle">{{formatCourt(game.location)}}</v-card-subtitle>
                <v-card-subtitle class="subtitle">{{formatDateTime(game.gameDate)}}</v-card-subtitle>
              </div>

              <!-- large view -->
              <div v-if="largeView" class="large-card">
                <v-col cols="12">
                <v-card-title class="card-header">Results</v-card-title>
                  <v-card-subtitle>Outcome: <strong>{{calcWinLoss(game.userScore, game.oppScore)}}</strong></v-card-subtitle>
                  <v-card-subtitle>Your team's score: <strong>{{game.userScore}}</strong></v-card-subtitle>
                  <v-card-subtitle>Opponent team's score: <strong>{{game.oppScore}}</strong></v-card-subtitle>
                </v-col>  
                <v-col cols="12">
                  <v-card-title class="card-header">Game Info</v-card-title>
                  <v-card-subtitle>Location: {{formatCourt(game.location)}}</v-card-subtitle>
                  <v-card-subtitle>Date: {{formatDateTime(game.gameDate)}}</v-card-subtitle>
                </v-col> 
                <v-col cols="12">
                  <v-card-title class="card-header">Players</v-card-title>
                  <v-card-subtitle>Team 1: {{game.player1}} {{game.player3}}</v-card-subtitle>
                  <v-card-subtitle>Team 2: {{game.player2}} {{game.player4}}</v-card-subtitle>
                  <v-card-subtitle>Game logged by: {{game.player1}}</v-card-subtitle>
                </v-col>

                <v-col cols="12">
                  <v-card-title class="card-header">Notes</v-card-title>
                  <v-card-subtitle class="notes">{{formatNotes(game.notes)}}</v-card-subtitle>
                </v-col> 
              </div>
             
            </v-col>

            <v-col v-if="!largeView" cols="2" class="game-card">
              <v-btn variant="tonal" rounded="lg" icon="mdi-information-outline" class="pop-out-btn"></v-btn>
            </v-col>
            <game-history-modal
            :game="game"
            :dialog="modalStates[index]"
            @close="modalStates[index] = false"
            :locationDict="locationDict.value"
            :formattedDate="formatDateTime(game.gameDate)"
            :formattedCourt="formatCourt(game.location)"
            >
            </game-history-modal>

            <!-- <v-col cols="12">

            </v-col>
            <v-col cols="12">

            </v-col> -->
            <!-- <v-col cols="3">
              <v-card-title class="card-header">{{calcWinLoss(game.userScore, game.oppScore)}}</v-card-title>
              <v-card-title>{{game.userScore}} - {{game.oppScore}}</v-card-title>
            </v-col>  -->
            <!-- <v-col cols="2">
              <v-card-title class="card-header">Info</v-card-title>
              <v-card-subtitle>{{formatCourt(game.location)}}</v-card-subtitle>
              <v-card-subtitle>{{formatDateTime(game.gameDate)}}</v-card-subtitle>
            </v-col> -->
            <!-- <v-col cols="2">
              <v-card-title class="card-header">Notes</v-card-title>
              <v-card-subtitle>{{formatNotes(game.notes)}}</v-card-subtitle>
            </v-col> -->
            <!-- <v-col cols="3">
              <v-card-title class="card-header">Players</v-card-title>
              <v-card-subtitle>{{game.player1}}</v-card-subtitle>
              <v-card-subtitle>{{game.player2}}</v-card-subtitle>
              <v-card-subtitle>{{game.player3}}</v-card-subtitle>
              <v-card-subtitle>{{game.player4}}</v-card-subtitle>
            </v-col> -->
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.size-btns{
  background-color: #333333;
  padding: 8px;
  margin-left: 4px;
  margin-right: 4px; 
}
.size-btn-group{
  float: right;
}

.icons{
  float: left;
}

.notes{
    /* white-space: unset; */
}

.game-card{
  justify-content: center;
  text-align:center
}

.large-card{
  justify-content: left;
  text-align:left
}

.pop-out-btn{
  float: right;
  min-width: 32px;
  background-color: #333333;
  height: 32px;
  width: 32px;
}
.main-header{
  font-size: 28px;
}

.card-header{
  font-weight: bold;
  padding-top: 0;
  padding-bottom: 0;
}
.win{
  background-color: #4caf50;
}
.dropdown-btn{
  background-color: #007BFF;

}

.btn{
    background-color: #4caf50;
    height: 90px;
    width:100%
  }

.outer{

}

.inner{

}

.displayed-court{

}

#loc-btn{
  background-color:#4caf50 !important;
}

.v-card {
  background-color: #42424254;
  border-radius: 8px;
  padding: 0.8em;
  margin: 0.1em;
}

.big-container{
  background-color: #212121;

  border-radius: 8px;
  margin-bottom:16px;
  margin-top:16px;
}



.search-btn {
  height: 100%;
}

.v-btn {
  font-size: 14px;
}

.v-card-title, .v-card-subtitle, .v-card-text {
  color: white;
}

.v-text-field {
  background-color: #42424254;
  padding: 0; /* Remove extra padding */
}

.v-img {
  object-fit: cover;
  height: 100%;
}

.no-padding {
  padding: 0 !important;
}
</style>
