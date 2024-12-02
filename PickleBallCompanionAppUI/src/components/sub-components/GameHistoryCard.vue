<script setup>
import { ref, computed, defineProps, onMounted, watch } from 'vue'
import { fetchData } from '@/util/fetchData.js'
import { useStore } from 'vuex';
import { formatDateTime } from '@/util/formatDate.js'
import { useRouter } from 'vue-router';
import GameHistoryModal from '@/components/sub-components/GameHistoryModal.vue'; 

const props = defineProps({
  game: Object, 
  index: Number,
  expandedView: Boolean,
  dialog: Boolean,
  location: String,
  modalStates: Object
});

const store = useStore();
const router = useRouter();

function calcWinLoss(game){
  if (game.player1 === store.state.selectedUsername || game.player3 === store.state.selectedUsername){
    // selected view user is on team 1 
    if(parseInt(game.userScore) < parseInt(game.oppScore)){
      return "Loss"
    }
    else if(parseInt(game.userScore) > parseInt(game.oppScore)){
      return "Win"
    }
  }
  else{ // selected user is on team 2 (aka player2, player4)
    if(parseInt(game.userScore) < parseInt(game.oppScore)){
      return "Win"
    }
    else if(parseInt(game.userScore) > parseInt(game.oppScore)){
      return "Loss"
    }
  }
}

function formatScoreWithDash(game){
  if (game.player1 === store.state.selectedUsername || game.player3 === store.state.selectedUsername){
    // selected view user is on team 1 
    return `${game.userScore} - ${game.oppScore}`
  }
  else{ // selected user is on team 2
    return `${game.oppScore} - ${game.userScore}`
  }
}

function returnSelectedTeamScore(game){
  if (game.player1 === store.state.selectedUsername || game.player3 === store.state.selectedUsername){
    // selected view user is on team 1 
    return game.userScore
  }
  else{ // selected user is on team 2
    return game.oppScore
  }
}

function returnOtherTeamScore(game){
  if (game.player1 === store.state.selectedUsername || game.player3 === store.state.selectedUsername){
    // selected view user is on team 1 
    return game.oppScore
  }
  else{ // selected user is on team 2
    return game.userScore
  }
}

// logic to do with knowing which team has won based on which player TODO this is just not working logically .......
function calcTeamWinLoss(game, teamNum){
  if (teamNum === 1){
    if(parseInt(game.userScore) < parseInt(game.oppScore)){
        return "Losing"
      }
      else if(parseInt(game.userScore) > parseInt(game.oppScore)){
        return "Winning"
      }
  }
  else{ // teamNum == 2 
    if(parseInt(game.userScore) < parseInt(game.oppScore)){
      return "Winning"
    }
    else if(parseInt(game.userScore) > parseInt(game.oppScore)){
      return "Losing"
    }
  }
  
    // if (teamNum === 1){
  //   if(parseInt(game.userScore) < parseInt(game.oppScore)){
  //     return "Win"
  //   }
  //   else if(parseInt(game.userScore) > parseInt(game.oppScore)){
  //     return "Loss"
  //   }
  // }
  // if (teamNum === 2){
  //   if(parseInt(game.userScore) < parseInt(game.oppScore)){
  //     return "Loss"
  //   }
  //   else if(parseInt(game.userScore) > parseInt(game.oppScore)){
  //     return "Win"
  //   }
  // }
}

function returnCorrectScore(game, teamNum){
  // if(teamNum === 2){
  //   return game.oppScore
  // }if(teamNum === 1){
  //   return game.userScore
  // }
}


function formatUsername(username){
  if (username == store.state.user.userName){
    return username + ' (You)'
  }
  else{
    return username
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
}

</script>
<template>

<v-card link @click="modalStates[index] = !modalStates[index]">
          <v-row>
              <v-col :cols="expandedView ? 1 : 2" class="game-card">
                <v-icon v-if="calcWinLoss(game) === 'Win'" icon="mdi-flag" color="green" size="large" class="icons"></v-icon>
                <v-icon v-if="calcWinLoss(game) === 'Loss'" icon="mdi-flag-off" color="red" size="large" class="icons"></v-icon>
              </v-col>
            <v-col :cols="expandedView ? 10 : 8" class="game-card">
              
              <!-- medium view -->
              <div v-if="!expandedView" class="med-card">
                <v-card-title class="card-header main-header">{{calcWinLoss(game)}}</v-card-title>
                <v-card-title class="card-header">{{formatScoreWithDash(game)}}</v-card-title>
                <v-card-subtitle class="subtitle">{{location}}</v-card-subtitle>
                <v-card-subtitle class="subtitle">{{formatDateTime(game.gameDate)}}</v-card-subtitle>
              </div>

              <!-- large view -->
              <div v-if="expandedView" class="large-card">
                <v-col cols="12">
                <v-card-title class="card-header">Results {{ }}</v-card-title>
                  <v-card-subtitle>Outcome: <strong>{{calcWinLoss(game)}}</strong></v-card-subtitle>
                  <v-card-subtitle>{{store.state.selectedUsername}}'s score: <strong>{{returnSelectedTeamScore(game)}}</strong></v-card-subtitle>
                  <v-card-subtitle>Opponent's score: <strong>{{returnOtherTeamScore(game)}}</strong></v-card-subtitle>
                </v-col>  
                <v-col cols="12">
                  <v-card-title class="card-header">Game Info</v-card-title>
                  <v-card-subtitle>Location: {{location}}</v-card-subtitle>
                  <v-card-subtitle>Date: {{formatDateTime(game.gameDate)}}</v-card-subtitle>
                  <v-card-subtitle>Logged by: {{formatUsername(game.player1)}}</v-card-subtitle>
                </v-col> 
                <v-col cols="12">
                  <v-card-title class="card-header">Players</v-card-title>
                  <v-card-subtitle> {{calcTeamWinLoss(game, 1)}} team with <strong>{{game.userScore}}</strong> point(s)</v-card-subtitle>
                  <v-card-subtitle>- {{formatUsername(game.player1)}}</v-card-subtitle>
                  <v-card-subtitle class="mb-3"> <span v-if="game.player3">- {{formatUsername(game.player3)}}</span></v-card-subtitle>
                  <v-card-subtitle> {{calcTeamWinLoss(game, 2)}} team with  <strong>{{game.oppScore}}</strong> point(s)</v-card-subtitle>
                  <v-card-subtitle>- {{formatUsername(game.player2)}}</v-card-subtitle>
                  <v-card-subtitle v-if="game.player4">- {{formatUsername(game.player4)}}</v-card-subtitle>
                </v-col>

                <v-col cols="12">
                  <v-card-title class="card-header">Notes</v-card-title>
                  <v-card-subtitle class="notes">{{formatNotes(game.notes)}}</v-card-subtitle>
                </v-col> 
              </div>
             
            </v-col>

            <v-col :cols="expandedView ? 1 : 2" class="game-card">
              <v-btn variant="tonal" rounded="lg" icon="mdi-information-outline" class="pop-out-btn"></v-btn>
            </v-col>
            <game-history-modal
            :game="game"
            :dialog="modalStates[index]"
            @close="modalStates[index] = false"
            :formattedDate="formatDateTime(game.gameDate)"
            :formattedCourt="location"
            :winOrLoss="calcWinLoss(game)"
            :selectedTeamScore="returnSelectedTeamScore(game)"
            :otherTeamScore="returnOtherTeamScore(game)"
            :team1WinLoss="calcTeamWinLoss(game, 1)"
            :team2WinLoss="calcTeamWinLoss(game, 2)"
            >
            </game-history-modal>
          </v-row>
        </v-card>

</template>
<style scoped>
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
  color: white;
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

.v-container {
  max-width: 1168px;
  }
</style>