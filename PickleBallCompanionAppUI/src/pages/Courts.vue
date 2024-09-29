<template>
  <v-container>
    <!-- Search and Location Toolbar -->
    <v-row class="mb-4" no-gutters>
      <v-col cols="12" md="8">
        <v-btn color="#212121" class="mr-2" @click="getUserLocation">
          Click here to use your precise location
        </v-btn>
      </v-col>
      <v-col cols="12" md="4" class="d-flex">
        <v-text-field
          v-model="searchQuery"
          label="Search Courts"
          single-line
          dense
          color="#212121"
          class="white--text no-padding"
          hide-details
        ></v-text-field>
        <v-btn color="#212121" class="ml-1 search-btn" @click="searchCourts">
          <v-icon>mdi-magnify</v-icon>  
        </v-btn>
      </v-col>
    </v-row>

    <!-- List of Courts -->
    <v-row>
      <v-col
        v-for="court in filteredCourts"
        :key="court.name"
        cols="12"
      >
        <v-card class="mb-3" outlined>
          <v-row no-gutters>
            <v-col cols="8">
              <v-card-title>{{ court.name }}</v-card-title>
              <v-card-subtitle>Distance: {{ court.distance }} km</v-card-subtitle>
              <v-card-text>Location: {{ court.location }}</v-card-text>
              <v-card-text>Number of courts: {{ court.numCourts }}</v-card-text>
            </v-col>
            <v-col cols="4">
              <v-img :src="court.image" alt="Court Image" />
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, computed } from 'vue'

// search bar string
const searchQuery = ref('')

// sample data
const courts = ref([
  {
    name: 'Court Name 1',
    distance: '5',
    location: 'Location 1',
    numCourts: 4,
    image: 'court-image-1.jpg'
  },
  {
    name: 'Court Name 2',
    distance: '10',
    location: 'Location 2',
    numCourts: 3,
    image: 'court-image-2.jpg'
  },
  {
    name: 'Court Name 3',
    distance: '8',
    location: 'Location 3',
    numCourts: 2,
    image: 'court-image-3.jpg'
  }
])

// search logic
const filteredCourts = computed(() => {
  if (!searchQuery.value) {
    return courts.value
  }
  return courts.value.filter(court =>
    court.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    court.location.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

// Get user location
const getUserLocation = () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(position => {
      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;
      alert(`Your location is Latitude: ${latitude}, Longitude: ${longitude}`);
      // Add functionality to use this location (e.g., filter courts by proximity)
    }, error => {
      alert('Unable to retrieve your location.');
    });
  } else {
    alert('Geolocation is not supported by your browser.');
  }
}

// Search action for search button
const searchCourts = () => {
  // Perform search based on searchQuery (this is already reactive)
  console.log('Search triggered for:', searchQuery.value);
}
</script>

<style scoped>
.v-card {
  color: #212121;
  padding: 0.8em;
  margin: 0.1em;
}

.search-btn {
  height: 100%;
}

.v-btn {
  font-size: 14px;
}

.v-card-title, .v-card-subtitle, .v-card-text {
  color: white;
}

.v-text-field {
  background-color: #212121;
  padding: 0; /* Remove extra padding */
}

.v-img {
  object-fit: cover;
  height: 100%;
}

.no-padding {
  padding: 0 !important;
}
</style>
