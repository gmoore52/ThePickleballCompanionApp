<!-- UserProfilePopup.vue -->
<template>
  <v-dialog :model-value="dialog" max-width="500px">
    <v-card>
      <v-card-title class="headline">{{ props.userInfo.userName }}</v-card-title>
      <v-card-subtitle>{{ props.userInfo.userFullName }}</v-card-subtitle>

      <v-card-text>
        <p>Account Creation Date: {{ props.userInfo.accCreationDate }}</p>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green" @click="close">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  userInfo: Object,
  dialog: Boolean,
});

const emit = defineEmits(['close']);

function close() {
  emit('close'); // Emit close event to the parent component
}
</script>

<style scoped>
/* Add styles as needed */
</style>
