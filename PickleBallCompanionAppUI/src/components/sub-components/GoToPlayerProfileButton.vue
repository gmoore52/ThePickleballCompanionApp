<script setup>
import { useRouter } from 'vue-router'; 
import { useStore } from 'vuex';
import { showAlert } from '@/util/alert'
import { ref, defineProps, onMounted } from 'vue';

const props = defineProps({
  playerUsername: String // username of the player we want to navigate to
});

const store = useStore();
const router = useRouter();

function visitProfile(userName){
    if (!userName){
        showAlert('error', `Invalid profile username to visit`)
    }
    else{
        store.commit('SET_SELECTED_USERNAME', userName);
        router.push(`/profile/${userName}`); // /${user.value} 
    }
}

function formatButtonText(){
    // used to display text on the button if no username is passed in
    if(props.playerUsername === store.state.user.userName){
        return props.playerUsername + ' (You)'
    }
    
    else if(props.playerUsername){
        return props.playerUsername
    }
    else{
        return "No user selected"
    }
}


</script>
<template>

    <v-btn prepend-icon="mdi-account" color="green" class="player-btns" @click="visitProfile(playerUsername)">{{formatButtonText()}}</v-btn>

</template>
<style scoped>

</style>

