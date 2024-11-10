<template>
  <v-app>
    <v-container class="fill-height d-flex flex-column big-container">
      <v-row  align="stretch" class="flex-grow-1">
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
            <v-card-title class="white--text text-h5">Upcoming Events</v-card-title>
            <v-row>
              <v-col v-for="event in sortedEvents" :key="event.eventId" cols="12" md="6">
                <v-card class="inner-card">
                  <v-card-title class="white--text">{{ event.eventTitle }}</v-card-title>
                  <v-card-text class="white--text">{{ event.eventDesc }}</v-card-text>
                  <v-card-subtitle class="white--text">
                    {{ formatDateTime(new Date(event.eventStart).toLocaleString()) }}
                  </v-card-subtitle>
                </v-card>
              </v-col>
            </v-row>
          </v-card>
        </v-col>

        <!-- Recent Games Section -->
        <v-col cols="12" md="4" class="d-flex flex-column">
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
import { ref, onMounted, computed } from 'vue';
import { fetchData } from "@/util/fetchData";
import { formatDateTime } from '@/util/formatDate.js'

const JSONEvents = ref([]);

const getEvents = async () => {
  JSONEvents.value = [];
  try {
    JSONEvents.value = await fetchData("/event/events");
    console.log(JSONEvents.value)
  } catch (error) {
    console.error(error);
  }
};

// Fetch events when the component is mounted
onMounted(() => {
  getEvents();
});

// Computed property to filter and sort events by EVENT_START date
const sortedEvents = computed(() => {
  const now = new Date();
  return JSONEvents.value
    .filter(event => new Date(event.eventStart) > now) // Only future events
    .sort((a, b) => new Date(a.eventStart) - new Date(b.eventStart)); // Sort by date
});
</script>
<style scoped>
.v-app {
  height: 100vh;
}

.big-container{
  background-color:#212121;
  margin-top: 16px;
  margin-bottom: 16px;
}

.v-container {
  height: 100%;
  border-radius: 8px;
}

.inner-card{
  background-color: none;
}

.v-card {
  background-color: #42424254;
  border: 1px solid white;
  border-radius: 8px;
  padding: 0.8em;
  margin: 0.1em;
  color: #212121 !important;
  border:none;
}

.white--text {
  color: white !important;
}

.v-row {
  margin-bottom: 20px;
}
</style>
