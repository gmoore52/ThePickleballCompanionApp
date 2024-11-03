<script setup>
import { ref, computed, onMounted } from 'vue'
import { fetchData } from '@/util/fetchData.js'
import { useStore } from 'vuex';
import { formatDateTime } from '@/util/formatDate.js'

const JSONGames = ref()
const JSONCourts = ref({});
const locationDict = ref({}); // used to index between loc_id and location_name
const gameRefs = ref([]); // This will hold refs initialized to false

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
    console.log(JSONGames.value)
  } catch (error) {
    console.error(error);
  }
}

const getCourts = async () => {
  JSONCourts.value = [];
  try {
    const url = '/data/locations';
    JSONCourts.value = await fetchData(url);
    console.log(JSONCourts.value);
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

const parseData = async () =>{
  for (const loc of JSONCourts.value) {
    const locName = `${loc.courtName}`;
    locationDict.value[loc.id] = locName
  }
  console.log(locationDict.value)
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
      <v-col cols="12">
        <h2>Recent Game History for {{ store.state.user.userName }}</h2>
      </v-col>
      <v-col cols="12" class="outer">
        
          <v-col col="12" class="inner">
            <div
                class="left-card"
                v-for="(game, index) in JSONGames"
                :key="game.id"
              >
              
                <v-card class="mb-3 card-item">
                  <v-row>
                    <v-col cols="3">
                      <v-card-title class="card-header">{{calcWinLoss(game.userScore, game.oppScore)}}</v-card-title>
                      <v-card-title>{{game.userScore}} - {{game.oppScore}}</v-card-title>
                    </v-col> 
                    <v-col cols="2">
                      <v-card-title class="card-header">Info</v-card-title>
                      <v-card-subtitle>{{formatCourt(game.location)}}</v-card-subtitle>
                      <v-card-subtitle>{{formatDateTime(game.gameDate)}}</v-card-subtitle>
                    </v-col>
                    <v-col cols="2">
                      <v-card-title class="card-header">Notes</v-card-title>
                      <v-card-subtitle>{{formatNotes(game.notes)}}</v-card-subtitle>
                    </v-col>
                    <v-col cols="3">
                      <v-card-title class="card-header">Players</v-card-title>
                      <v-card-subtitle>{{game.player1}}</v-card-subtitle>
                      <v-card-subtitle>{{game.player2}}</v-card-subtitle>
                      <v-card-subtitle>{{game.player3}}</v-card-subtitle>
                      <v-card-subtitle>{{game.player4}}</v-card-subtitle>
                    </v-col>
                    <v-col cols="2">
                      
                    </v-col>
                    <!-- <v-btn class="dropdown-btn"
        :icon="gameRefs[index].value ? 'mdi-chevron-up' : 'mdi-chevron-down'"
        @click="gameRefs[index].value = !gameRefs[index].value"
      ></v-btn> -->
   

    <!-- <v-expand-transition> -->
      <!-- <div v-show="gameRefs[index].value"> -->
        <!-- <v-divider></v-divider>

        <v-card-text>
          In today's fast-paced world, we often find ourselves rushing through our daily routines, overlooking the beauty that surrounds us. Taking a moment to pause and appreciate the small things can bring immense joy and clarity to our lives. Whether it's a stunning sunset, the sound of leaves rustling in the wind, or the warmth of a loved one’s smile, these moments remind us of what truly matters. Let us strive to cultivate gratitude, cherishing the present while also envisioning a bright future. Embrace each day with optimism and a sense of wonder, for life is a precious gift.
        </v-card-text> -->
      <!-- </div> -->
    <!-- </v-expand-transition> -->
                </v-row>
                </v-card>
              </div>
          </v-col>
        
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.card-header{
  font-weight: bold;
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
