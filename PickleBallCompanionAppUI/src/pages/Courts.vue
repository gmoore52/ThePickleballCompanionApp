<template>
    <v-container>
      <!-- Search and Location Toolbar -->
      <v-row class="mb-4">
        <v-col cols="12" md="8">
          <v-btn color="black" class="mr-2">
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
  
  // Search query reactive variable
  const searchQuery = ref('')
  
  // Sample data for courts (you can replace this with API data)
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
  
  // Computed property to filter courts based on search query
  const filteredCourts = computed(() => {
    if (!searchQuery.value) {
      return courts.value
    }
    return courts.value.filter(court =>
      court.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      court.location.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  })
  </script>
  
  <style scoped>
  .v-card {
    background-color: black;
    color: white;
  }
  
  .v-btn {
    font-size: 14px;
  }
  
  .v-card-title, .v-card-subtitle, .v-card-text {
    color: white;
  }
  
  .v-text-field {
    background-color: black;
    border-radius: 5px;
  }
  
  .v-img {
    object-fit: cover;
    height: 100%;
  }
  </style>
  