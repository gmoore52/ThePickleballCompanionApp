<script setup>
import { defineEmits, defineProps} from 'vue';
import { formatDateTime } from '@/util/formatDate.js'
import { useRouter } from 'vue-router'; // Import useRouter from vue-router
import { useStore } from 'vuex';
import GoToPlayerProfileButton from '@/components/sub-components/GoToPlayerProfileButton.vue'; 

const props = defineProps({
  game: Object,
  dialog: Boolean,
  formattedDate: String, 
  formattedCourt: String,
  winOrLoss: String, 
  selectedTeamScore: Number, 
  otherTeamScore: Number, 
  team1WinLoss: String, 
  team2WinLoss: String, 
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
  router.push(`/profile/${userName}`); 
}

</script>
<template>
    <v-dialog persistent :model-value="dialog" max-width="500px">
        <v-row>
            <v-col cols="12">
                <v-card>
                    <v-btn color="red" class="close-btn" @click="close" density="compact" icon="$close" size="large"></v-btn>

                <v-col cols="12">
                <v-card-title class="card-header mt-2">Results</v-card-title>
                  <v-card-subtitle>Outcome: <strong>{{winOrLoss}}</strong></v-card-subtitle>
                  <v-card-subtitle>{{store.state.selectedUsername}}'s score: <strong>{{selectedTeamScore}}</strong></v-card-subtitle>
                  <v-card-subtitle>Opponent's score: <strong>{{otherTeamScore}}</strong></v-card-subtitle>
                </v-col>  
                <v-col cols="12">
                  <v-card-title class="card-header">Game Info</v-card-title>
                  <v-card-subtitle>Location: {{formattedCourt}}</v-card-subtitle>
                  <v-card-subtitle>Date: {{formatDateTime(game.gameDate)}}</v-card-subtitle>
                  <v-card-subtitle>Logged by: {{game.player1}}</v-card-subtitle>
                </v-col> 
              <v-col cols="12">
                <v-card-title class="card-header">Players</v-card-title>
                <v-card-subtitle> {{team1WinLoss}} team with <strong>{{game.userScore}}</strong> point(s)</v-card-subtitle>
                
                <p>
                  <go-to-player-profile-button
                  :playerUsername="game.player1"
                  ></go-to-player-profile-button>
                </p>
                
                <p>
                  <go-to-player-profile-button
                  v-if="game.player3"
                  :playerUsername="game.player3"
                  ></go-to-player-profile-button>
                </p>

                <v-card-subtitle> {{team2WinLoss}} team with  <strong>{{game.oppScore}}</strong> point(s)</v-card-subtitle>
                <p>
                  <go-to-player-profile-button
                  :playerUsername="game.player2"
                  ></go-to-player-profile-button>
                </p>
                <p>
                  <go-to-player-profile-button
                  v-if="game.player4"
                  :playerUsername="game.player4"
                  ></go-to-player-profile-button>
                </p>
              </v-col>
            <v-col cols="12">
              <v-card-title class="card-header">Notes</v-card-title>
              <v-card-subtitle class="notes mb-4">{{formatNotes(game.notes)}}</v-card-subtitle>
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

.v-container {
  max-width: 1168px;
  }

</style>