<script setup>
import { ref, computed } from 'vue'

// Search query reactive variable
const searchQuery = ref('')

// Sample data for courts (you can replace this with API data)
const eventsJSON = ref([
  {
    event_id: 1,
    event_title: "Championship Showdown",
    event_start: "2024-09-22T14:00:00",
    event_end: "2024-09-22T16:00:00",
    event_description: "Men's Doubles - Championship final to determine the local winners after an intense series of matches.",
    location_name: "Pickleball Central Courts"
  },
  {
    event_id: 2,
    event_title: "Pickle for all!",
    event_start: "2024-09-23T10:00:00",
    event_end: "2024-09-23T12:00:00",
    event_description: "Mixed Doubles - Quarterfinal match featuring top players from the community, aiming for a spot in the semifinals.",
    location_name: "Pickle Courts"
  },
  {
    event_id: 3,
    event_title: "Morning Matchup",
    event_start: "2024-09-24T08:30:00",
    event_end: "2024-09-24T10:30:00",
    event_description: "Women's Singles - Round Robin match to determine semifinal spots in this highly competitive local tournament.",
    location_name: "Grand Pickleball Arena"
  },
  {
    event_id: 4,
    event_title: "The Semifinal Clash",
    event_start: "2024-09-25T18:00:00",
    event_end: "2024-09-25T20:00:00",
    event_description: "Men's Singles - Semifinal match between top-seeded players from the region, expected to be a thrilling contest.",
    location_name: "Pickleball Academy Grounds"
  },
  {
    event_id: 5,
    event_title: "Local Friendlies",
    event_start: "2024-09-26T16:00:00",
    event_end: "2024-09-26T18:00:00",
    event_description: "Mixed Doubles - A friendly match featuring local players, showcasing teamwork and skill on the court.",
    location_name: "Pickle Park Courts"
  },
  {
    event_id: 6,
    event_title: "Exhibition Showcase",
    event_start: "2024-09-27T12:00:00",
    event_end: "2024-09-27T14:00:00",
    event_description: "Women's Doubles - Exhibition match featuring some of the region's most talented players, highlighting their top skills.",
    location_name: "Regional Sports Complex"
  },
  {
    event_id: 7,
    event_title: "Third Place Showdown",
    event_start: "2024-09-28T09:00:00",
    event_end: "2024-09-28T11:00:00",
    event_description: "Men's Doubles - Third-place consolation match between top contenders after a hard-fought semifinal round.",
    location_name: "City Park Pickleball Courts"
  },
  {
    event_id: 8,
    event_title: "Junior Rising Stars",
    event_start: "2024-09-29T15:00:00",
    event_end: "2024-09-29T17:00:00",
    event_description: "Junior Pickleball - Youth doubles match showcasing the next generation of talent in a friendly competitive setting.",
    location_name: "Junior Sports Arena"
  },
  {
    event_id: 9,
    event_title: "Final Showdown",
    event_start: "2024-09-30T13:30:00",
    event_end: "2024-09-30T15:30:00",
    event_description: "Women's Singles - The final showdown to crown the champion of the city-wide pickleball tournament after an exciting season.",
    location_name: "Pickleball Champion Court"
  },
  {
    event_id: 10,
    event_title: "Friendly Competitors",
    event_start: "2024-10-01T11:00:00",
    event_end: "2024-10-01T13:00:00",
    event_description: "Mixed Doubles - A highly anticipated friendly match featuring advanced players from different clubs.",
    location_name: "Local Pickleball Club"
  }
]);

// Computed property to filter courts based on search query
const filteredEvents = computed(() => {
  if (!searchQuery.value) {
    return eventsJSON.value
  }
  return eventsJSON.value.filter(event =>
    event.event_title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    event.event_description.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    event.event_location.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})
</script>
<template>
  <v-container>
    <!-- Search and Location Toolbar -->
    <v-row class="mb-4">
      <v-col cols="12" md="8">
        <v-btn class="mr-2">
          Click here to use your precise location
        </v-btn>
      </v-col>
      <v-col cols="12" md="4">
        <v-text-field 
          v-model="searchQuery" 
          label="Search" 
          append-icon="mdi-magnify"
          single-line
          dense
          color="black"
          class="white--text"
        ></v-text-field>
      </v-col>
    </v-row>

    <!-- List of Courts -->
    <v-row>
      <v-col
        v-for="event in filteredEvents"
        :key="event.event_title"
        cols="12"
      >
        <v-card class="mb-3" outlined>
          <v-row no-gutters>
            <v-col cols="8">
              <v-card-title>{{ event.event_title }}</v-card-title>
              <v-card-subtitle>Event ID: {{ event.event_id }}</v-card-subtitle>
              <v-card-subtitle>Start: {{ event.event_start }}</v-card-subtitle>
              <v-card-subtitle>End: {{ event.event_end }}</v-card-subtitle>
              <v-card-text>Description: {{ event.event_description }} </v-card-text>
              <!-- <v-card-text>Number of courts: {{ event.numCourts }}</v-card-text> -->
            </v-col>
            <!-- <v-col cols="4">
              <v-img :src="event.image" alt="Court Image" />
            </v-col> -->
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
  .v-card{
    padding: 0.8rem;
  }
  .card{
    padding: 0.8em;
    margin: 0.1em;
    /* height: 700px; */
  }

</style>
