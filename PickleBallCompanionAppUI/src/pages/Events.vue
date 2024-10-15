<script setup>
import { ref, computed } from 'vue'

// Search query reactive variable
const searchQuery = ref('')
const defaultDate = "2024-09-28T12:00:00";

// TODO: do sorting based on the date if it has occured or not, and sort them into the two columns 
// TODO: add profile pictures

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

function formatDate(date){
  let newDate = new Date(date);
  let fullDate = newDate.toDateString();
  let hours = newDate.getHours();
  let minutes = newDate.getMinutes();

  let ampm = hours >= 12 ? 'PM' : 'AM';
  hours = hours % 12 || 12;

  if(minutes == 0){
    minutes = '00'
  }

  return `${fullDate} ${hours}:${minutes}${ampm}`;
}

// Computed property to filter courts based on search query
const filteredEvents = computed(() => {
  if (!searchQuery.value) {
    return eventsJSON.value
  }
  return eventsJSON.value.filter(event =>
    event.event_title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    event.event_description.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    event.location_name.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})
</script>
<template>
  <v-container class="main-container">
    <div class="layout">
      <!-- Search and Location Toolbar -->
      <v-row>
        <v-col cols="12" md="8">
          <v-btn class="btn mr-2">
            Placeholder button
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


      <v-row class="main-row">
        <v-col cols="6">
          <v-row class="main-row-1">
            <v-col cols="12" class="main-col-1">
              <h2 class="cards-header" id="header-1">Ongoing Events</h2>
              <div
                class="left-card"
                v-for="event in filteredEvents"
                :key="event.event_title"
                cols="6"
              >
                <v-card class="mb-3" outlined>
                  <v-row no-gutters>
                    <v-col cols="8">
                      <v-card-title>{{ event.event_title }}</v-card-title>
                      <!-- <v-card-subtitle>Event ID: {{ event.event_id }}</v-card-subtitle> -->
                      <v-card-subtitle>Start: {{ formatDate(event.event_start) }}</v-card-subtitle>
                      <v-card-subtitle>End: {{ formatDate(event.event_end)}}</v-card-subtitle>
                      <v-card-text>{{ event.event_description }} </v-card-text>
                      <v-card-subtitle>Hosted at {{ event.location_name }}</v-card-subtitle>
                    </v-col>
                  <v-col cols="4">
                    <v-img class="img" cover src='https://www.groupestate.gr/images/joomlart/demo/default.jpg' alt="Default image"></v-img>
                  </v-col>
                  </v-row>
                </v-card>
              </div>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="6">
          <v-row class="main-row-2">
            <v-col cols="12" class="main-col-2">
              <h2 class="cards-header" id="header-2">Upcoming Events</h2>
              <div
                class="right-card"
                v-for="event in filteredEvents"
                :key="event.event_title"
                cols="6"
              >
                <v-card class="mb-3" outlined>
                  <v-row no-gutters>
                    <v-col cols="8">
                      <v-card-title>{{ event.event_title }}</v-card-title>
                      <v-card-subtitle>Event ID: {{ event.event_id }}</v-card-subtitle>
                      <v-card-subtitle>Start: {{ formatDate(event.event_start) }}</v-card-subtitle>
                      <v-card-subtitle>End: {{ formatDate(event.event_end)}}</v-card-subtitle>
                      <v-card-text>Description: {{ event.event_description }} </v-card-text>
                      <v-card-subtitle>Hosted at {{ event.location_name }}</v-card-subtitle>
                    </v-col>
                  <v-col cols="4">
                    <v-img class="img" cover src='https://www.groupestate.gr/images/joomlart/demo/default.jpg' alt="Default image"></v-img>
                  </v-col>
                  </v-row>
                </v-card>
              </div>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<style scoped>
  .main-container{
    border-radius: 8px;
    padding: 1rem;
    /* padding-right: 16rem;
    padding-left: 16rem; */
  }
  div.layout{
    background-color: #222222;
    border-radius: 8px;
    padding:1rem;
  }
  .cards-header{
    background-color: #42424254;
    border-radius: 8px;
    padding: 0.9rem;
    margin-bottom: 12px;
    text-align: center;
  }
   .v-card{
    border-radius: 8px;
    padding: 0.8rem;
    background-color: #42424254;
  }
  .btn{
    background-color: #42424254;
  }
  .card{
    padding: 0.8em;
    margin: 0.1em;
    /* height: 700px; */
  }
  .right-card{

  }
  .left-card{

  }
  .img{
    width: 100%;
    height: 90%;
  }
  .row-container{
    display:inline-block;
    border :2px;
  }

</style>
