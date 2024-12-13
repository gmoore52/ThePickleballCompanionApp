<template>
  <v-dialog :model-value="dialog" max-width="500px">
    <v-card>
      <!-- Make the username larger with a custom class -->
      <v-card-title><h2>{{ props.userInfo.userName }}</h2></v-card-title>
      <v-card-subtitle><h2>{{ props.userInfo.userFullName }}</h2></v-card-subtitle>

      <v-card-text><b>Skill Level:</b> {{ skillLevels[props.userInfo.skillLevel] }}</v-card-text>
      <v-card-text><b>Account Created:</b> {{ props.userInfo.accCreationDate }}</v-card-text>

      <v-card-actions>
        <v-btn color="green" @click="close">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const skillLevels = {
  1: "Beginner",
  2: "Intermediate",
  3: "Advanced",
  4: "Pro"
};

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

.v-container {
  max-width: 1168px;
  }
</style>
