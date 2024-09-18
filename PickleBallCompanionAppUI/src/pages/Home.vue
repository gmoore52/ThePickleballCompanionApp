<template>
  <v-app>
    <v-container class="fill-height d-flex flex-column" fluid>
      <v-row justify="space-between" align="stretch" class="flex-grow-1">
        <!-- Main Left Section (Welcome and Events) -->
        <v-col cols="12" md="8" class="d-flex flex-column">
          <!-- Welcome Section -->
          <v-card class="pa-1 mb-6 flex-grow-0">
            <v-card-title class="white--text text-h5">Welcome to the site!</v-card-title>
            <v-card-text class="white--text">
              Here we host your game statistics, looking for groups request, events,
              playable courts, and more. The tabs above will take you to the various
              parts of pickleball companion web app.
            </v-card-text>
          </v-card>

          <!-- Events Section -->
          <v-card class="pa-4 flex-grow-1" outlined>
            <v-card-title class="white--text text-h5">Events</v-card-title>
            <v-row>
              <v-col cols="12" md="6">
                <v-card outlined color="#212121">
                  <v-card-title class="white--text">Event Name</v-card-title>
                  <v-card-text class="white--text">Event information</v-card-text>
                </v-card>
              </v-col>
              <v-col cols="12" md="6">
                <v-card outlined>
                  <v-card-title class="white--text">Event Name</v-card-title>
                  <v-card-text class="white--text">Event information</v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-card>
        </v-col>

        <!-- Recent Games Section -->
        <v-col cols="12" md="3" class="d-flex flex-column">
          <v-card class="pa-4 flex-grow-1" outlined>
            <v-card-title class="white--text text-h5">Recent Games</v-card-title>
            <v-card-text>
              <v-card outlined>
                <v-card-title class="white--text">Game summary</v-card-title>
              </v-card>
              <v-card outlined class="mt-2">
                <v-card-title class="white--text">Game summary</v-card-title>
              </v-card>
              <v-card outlined class="mt-2">
                <v-card-title class="white--text">Game summary</v-card-title>
              </v-card>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script setup>
import { ref } from 'vue';
</script>

<style scoped>
.v-app {
  height: 100vh;
}

.v-container {
  height: 100%;
}

.v-card {
  border: 1px solid white;
  padding: 0.8em;
  margin: 0.1em;
  color: #212121 !important;
}

.white--text {
  color: white !important;
}

.v-row {
  margin-bottom: 20px;
}
</style>
