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

const playerProfilePath = ref(`/images/${props.playerUsername}.jpg`)

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
    // console.log(props.playerUsername)
    if(props.playerUsername === store.state.user.userName){
        return props.playerUsername + ' (You)'
    }
    
    else if(props.playerUsername){
        return props.playerUsername
    }
    else{
        return "No user found"
    }
}

function onImageError(){
    playerProfilePath.value = '/images/default-profile-image.jpg';
}


</script>
<template>

    <v-btn class="player-btns grey-btn px-2" @click="visitProfile(playerUsername)">
        <v-img
                    v-if="playerUsername !== null"
                    class="profile-img-icon"
                    :width="24"
                    :height="24"
                    aspect-ratio="1"
                    cover
                    :src="playerProfilePath"
                    @error="onImageError"
                  ></v-img>
                  {{formatButtonText()}}
    </v-btn>

</template>
<style scoped>
.grey-btn{
    background-color: #424242;
}

.profile-img-icon{
    border-radius: 4px;
    margin-right: 8px;
}

</style>

