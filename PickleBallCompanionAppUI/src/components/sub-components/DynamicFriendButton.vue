<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useStore } from 'vuex';
import { showAlert } from "@/util/alert";
import { useRouter } from 'vue-router';

const store = useStore();
const router = useRouter();

function addSelectedFriend(userName){
  // function is not complete yet, we will be calling to the database here
}

// Need a looot of logic here to determine what to do with the button, and when.

</script>
<template>

    <v-btn v-if="store.state.user.userName !== store.state.selectedUsername" prepend-icon="mdi-account-multiple-plus" class="mt-5" color="green" @click="addSelectedFriend(store.state.selectedUsername)">Add Friend</v-btn>

</template>
<style scoped>

</style>