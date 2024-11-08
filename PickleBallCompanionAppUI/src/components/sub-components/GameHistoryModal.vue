<script setup>

import { ref, defineEmits, defineProps, onMounted } from 'vue';
import { fetchData } from "@/util/fetchData";
import { useRouter } from 'vue-router'; // Import useRouter from vue-router
import { useStore } from 'vuex';

const props = defineProps({
  game: Object,
  dialog: Boolean,
  locationDict: Object,
  formattedDate: String, 
  formattedCourt: String,
});

const store = useStore();
const router = useRouter();

const emit = defineEmits(['close']);

function close() {
  emit('close'); // Emit close event to the parent component
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

function visitProfile(userName){
  store.commit('SET_SELECTED_USERNAME', userName);
  router.push(`/profile/${userName}`); // /${user.value} 
}

</script>
<template>
    <v-dialog persistent :model-value="dialog" max-width="500px" >
        <v-row>
            <v-col cols="12">
                <v-card>
                    <v-btn color="red" class="close-btn" @click="close" density="compact" icon="$close" size="large"></v-btn>

             <v-col cols="12">
            <v-card-title class="card-header">Results</v-card-title>
              <v-card-subtitle>Outcome: <strong>{{calcWinLoss(game.userScore, game.oppScore)}}</strong></v-card-subtitle>
              <v-card-subtitle>Your team's score: <strong>{{game.userScore}}</strong></v-card-subtitle>
              <v-card-subtitle>Opponent team's score: <strong>{{game.oppScore}}</strong></v-card-subtitle>
            </v-col>  
             <v-col cols="12">
              <v-card-title class="card-header">Game Info</v-card-title>
              <v-card-subtitle>Location: {{formattedCourt}}</v-card-subtitle>
              <v-card-subtitle>Date: {{game.gameDate.split(" ")[0]}}</v-card-subtitle>
              <v-card-subtitle>Logged by: {{game.player1}}</v-card-subtitle>
            </v-col> 
             <v-col cols="12">
              <v-card-title class="card-header">Players</v-card-title>
              <v-card-subtitle>Team 1: </v-card-subtitle>

              <v-btn prepend-icon="mdi-account" color="green" class="player-btns" @click="visitProfile(game.player1)">{{game.player1}}</v-btn>
              <v-btn prepend-icon="mdi-account" color="green" class="player-btns" v-if="game.player3" @click="visitProfile(game.player3)">{{game.player3}}</v-btn>
              
              <v-card-subtitle>Team 2:</v-card-subtitle>

              <v-btn prepend-icon="mdi-account" color="green"class="player-btns" @click="visitProfile(game.player2)">{{game.player2}}</v-btn>
              <v-btn prepend-icon="mdi-account" color="green" class="player-btns" v-if="game.player4" @click="visitProfile(game.player4)">{{game.player4}}</v-btn>
            </v-col>

            <v-col cols="12">
              <v-card-title class="card-header">Notes</v-card-title>
              <v-card-subtitle class="notes">{{formatNotes(game.notes)}}</v-card-subtitle>
            </v-col> 
            </v-card>
            </v-col>
        </v-row>
    </v-dialog>

</template>


<style scoped>
.v-row{
  margin: 12px;
}

.player-btns{
  margin-left: 16px;
  margin-top: 5px;
  margin-bottom: 5px;
  /* background-color: */

}

.close-btn{
    float: right;
    margin-right: 5px;
    margin-top: 5px;
}

.v-card-subtitle{
    font-size: 15px;
}

.notes{
    white-space: unset;
}

.card-header{
  font-weight: bold;
  padding-top: 0;
  padding-bottom: 0;
}

</style>