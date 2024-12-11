<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { fetchData } from '@/util/fetchData.js'
import { useStore } from 'vuex';
import { formatDateTime } from '@/util/formatDate.js'
import { useRouter } from 'vue-router';
import GameHistoryModal from '@/components/sub-components/GameHistoryModal.vue'; 
import GameHistoryCard from '@/components/sub-components/GameHistoryCard.vue'; 


const JSONGames = ref([])
const JSONCourts = ref({});
const locationDict = ref({}); // used to index between loc_id and location_name
const modalStates = ref([]); // This will hold refs initialized to false

const expandedView = ref(false)

const store = useStore();
const router = useRouter();

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
    JSONGames.value = await fetchData(`/game/games?username=${store.state.selectedUsername}`); // 
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

function formatCourt(courtNum){
  return locationDict.value[courtNum]
}

function visitProfile(userName){
  store.commit('SET_SELECTED_USERNAME', "Peter_Dinklage3");
  router.push(`/profile/:userId`); // /${user.value} 
  window.scrollTo(0, 0);
}

function returnToOtherProfile(userName){
  // store.commit('SET_SELECTED_USERNAME', "Peter_Dinklage3");
  router.push(`/profile/:userId`); // /${user.value} 
  window.scrollTo(0, 0);
}

const parseData = async () =>{
  for (const loc of JSONCourts.value) {
    const locName = `${loc.courtName}`;
    locationDict.value[loc.id] = locName    
  }
}

// defined here so that
function checkJSONLength(){
  return JSONGames.length === 0
}

onMounted(async () => {
  await getGames();
  await getCourts();
  await parseData();
  await sortGames()
});

watch(
  () => store.state.selectedUsername,
  (newUsername, oldUsername) => {
    if (newUsername !== oldUsername) {
      getGames();
      getCourts();
      parseData();
      sortGames();
    }
  }
);



</script>

<template>
  <v-app>
  

    <v-container class="big-container">    
      <v-row v-if="store.state.user?.userName !== store.state.selectedUsername" class="pb-3 button-row">
                <v-btn prepend-icon="mdi-arrow-left" class="return-btn" @click="returnToOtherProfile(store.state.selectedUsername)">return</v-btn>
      </v-row>
    <v-row class="bg-div">
      <v-col cols="7" class="top-containers">
        <h2 class="d-inline-block ml-1">Game History for {{ store.state.selectedUsername }}</h2>

        <!-- <v-btn color="orange" @click="temp1()">olivia_brown</v-btn>
        <v-btn color="orange" @click="temp2()">Peter_Dinklage3</v-btn> -->
        <!-- <v-btn color="orange" @click="unset()">unset</v-btn> -->
        <!-- <v-btn color="orange" @click="visitProfile(store.state.selectedUsername)">Go to peters profile</v-btn> -->
      </v-col>
      <v-col cols="5" class="top-containers">
        <div class="game-switch">
          <v-switch v-model="expandedView" class="game-switch" label="Expand" color="primary" ></v-switch>
        </div>
      </v-col>
      <v-col class="no-games" cols="12" v-if="JSONGames.length == 0"> No recent games for {{ store.state.selectedUsername }} </v-col>
      <v-col class=""
              v-for="(game, index) in JSONGames"
              :key="game.id" sm="6" md="4" xs="12">
        <game-history-card 
        :game="game"
        :index="index"
        :modalStates="modalStates"
        :dialog="modalStates[index]"
        :location="formatCourt(game.location)"
        :expandedView="expandedView"
        @close="modalStates[index] = false">
        </game-history-card>
      </v-col>
    </v-row>
  </v-container>
  </v-app>
</template>

<style scoped>
.v-row{
  margin: -12px;
}

.button-row{
  min-height: 58px;
}

.no-games{
  content: center;
  text-align: center;
  height: 110px;

}

.game-switch{
  float: right;
}

.size-btns{
  background-color: #333333;
  padding: 8px;
  margin-left: 4px;
  margin-right: 4px; 
}
.size-btn-group{
  float: right;
}

.return-home-btn{
  background-color: #42424254;
}

.icons{
  float: left;
}

.notes{
    white-space: unset;
}

.med-card{
  justify-content: center;
  text-align:center
}

.large-card{
  justify-content: center;
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
  border-radius: 8px;
  margin-top: 16px;
  margin-bottom: 16px;
}
.bg-div{
  background-color: #212121;
  border-radius: 8px;
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

.top-containers{
  height: 60px;
}

.v-container {
    max-width: 1168px;
  }
</style>
