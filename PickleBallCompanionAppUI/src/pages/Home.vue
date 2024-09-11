<template>
  <v-app>
    <v-container class="fill-height" fluid>
      <v-row justify="space-between">
        <!-- Main Left Section (Welcome and Events) -->
        <v-col cols="12" md="8">
          <!-- Welcome Section -->
          <v-card class="pa-4 mb-4" outlined color="black" style="background-color: #003300;">
            <v-card-title class="white--text text-h5">Welcome to the site!</v-card-title>
            <v-card-text class="white--text">
              Here we host your game statistics, looking for groups request, events,
              playable courts, and more. The tabs above will take you to the various
              parts of pickleball companion web app.
            </v-card-text>
          </v-card>

          <!-- Events Section -->
          <v-card class="pa-4" outlined color="black" style="background-color: #003300;">
            <v-card-title class="white--text text-h5">Events</v-card-title>
            <v-row>
              <v-col cols="12" md="6">
                <v-card outlined color="black" style="background-color: #003300;">
                  <v-card-title class="white--text">Event Name</v-card-title>
                  <v-card-text class="white--text">Event information</v-card-text>
                </v-card>
              </v-col>
              <v-col cols="12" md="6">
                <v-card outlined color="black" style="background-color: #003300;">
                  <v-card-title class="white--text">Event Name</v-card-title>
                  <v-card-text class="white--text">Event information</v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-card>
        </v-col>

        <!-- Recent Games Section -->
        <v-col cols="12" md="3">
          <v-card class="pa-4" outlined color="black" style="background-color: #003300;">
            <v-card-title class="white--text text-h5">Recent Games</v-card-title>
            <v-card-text>
              <v-card outlined color="black" style="background-color: #003300;">
                <v-card-title class="white--text">Game summary</v-card-title>
              </v-card>
              <v-card outlined color="black" style="background-color: #003300;" class="mt-2">
                <v-card-title class="white--text">Game summary</v-card-title>
              </v-card>
              <v-card outlined color="black" style="background-color: #003300;" class="mt-2">
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
.v-card {
  border: 2px solid white;
}

.white--text {
  color: white !important;
}

.v-container {
  background-color: #003300;
}

.v-row {
  margin-bottom: 20px;
}
</style>
