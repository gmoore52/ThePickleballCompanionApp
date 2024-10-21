<script setup>
import { ref, computed} from 'vue'
import { VTimePicker } from 'vuetify/labs/VTimePicker'
import { onMounted } from '@vue/runtime-core';
import { fetchData } from '@/util/fetchData';

// Search query reactive variable
const searchQuery = ref(null)
const showDialog = ref(false);
const formErrors = ref(null) // delete later 

const eventTitle = ref(null);  // TODO the events backend doesn't even have location </3
const eventLocation = ref(null); 
const startDate = ref(null);
const endDate = ref(null);
const startTime = ref(null);
const endTime = ref(null);
const startAMPM = ref(null)
const endAMPM = ref(null)
const eventDescription = ref(null); 

const JSONCourts = ref([])

const picker1Render = ref(false);
const picker2Render = ref(false);

const timeStamps = ref(['AM','PM']);
const locations = ref(['temp']);
const locationDict = ref({}); // used to index between loc_id and location_name

const ongoingEvents = ref([]);
const upcomingEvents = ref([]);
const pastEvents = ref([]);

const frontendStartTime = computed({ // cleans selected time for frontend 
  get(){
    return frontendTime(startTime.value)
  }
})

const frontendEndTime = computed({ // cleans selected time for frontend 
  get(){
    return frontendTime(endTime.value)
  }
})

// TODO: add profile pictures

// Sample data for courts (you can replace this with API data)

const titleRules = [
    value => {
      if (value) return true;
      return 'Must select a title';
    },
  ];

const locationRules = [
  value => {
    if (value) return true;
    return 'Must select a location';
  },
];

const startDateRules = [
  value => {
    if (value) return true;
    return 'Select a start date ';
  },
];

const endDateRules = [
  value => {
    if (value) return true;
    return 'Select an end date';
  },
];

const startTimeRules = [
  value => {
    if (value) return true;
    return 'Select ';
  },
];

const startAMPMRules = [
  value => {
    if (value) return true;
    return 'Select AM / PM';
  },
];

const endTimeRules = [
  value => {
    if (value) return true;
    return 'Select AM / PM';
  },
];

const endAMPMRules = [
  value => {
    if (value) return true;
    return 'Must choose AM / PM';
  },
];

const descriptionRules = [
  value => {
    if (value) return true;
    return 'Must write a description';
  },
];
const eventsJSON = ref([
  {
    event_id: 1,
    event_title: "All Year Pickle",
    event_start: new Date("2024-08-01T14:00:00"),
    event_end: new Date("2025-09-22T16:00:00"),
    event_description: "A test event that will be occuring for a year",
    location_name: "Pickleball Central Courts"
  },
  {
    event_id: 2,
    event_title: "Pickle in the Future",
    event_start: new Date("2025-11-23T10:00:00"),
    event_end: new Date("2025-12-23T12:00:00"),
    event_description: "Mixed Doubles - Quarterfinal match featuring top players from the community, aiming for a spot in the semifinals.",
    location_name: "Pickle Courts"
  },
  {
    event_id: 3,
    event_title: "Long Term Pickle Event",
    event_start: new Date("2024-08-01T14:00:00"),
    event_end: new Date("2025-09-22T16:00:00"),
    event_description: "A test event that will be occuring for a year",
    location_name: "Pickleball Central Courts"
  },
  {
    event_id: 4,
    event_title: "Pickle Hapenning Now",
    event_start: new Date("2024-08-01T14:00:00"),
    event_end: new Date("2024-12-22T16:00:00"),
    event_description: "A test event that will be occuring for a year",
    location_name: "Pickleball Central Courts"
  },
  {
    event_id: 5,
    event_title: "Pickle in the Far Future",
    event_start: new Date("2025-11-23T10:00:00"),
    event_end: new Date("2026-12-23T12:00:00"),
    event_description: "Mixed Doubles - Quarterfinal match featuring top players from the community, aiming for a spot in the semifinals.",
    location_name: "Pickle Courts"
  },
  {
    event_id: 6,
    event_title: "Exhibition Showcase",
    event_start: new Date("2024-09-27T12:00:00"),
    event_end: new Date("2024-09-27T14:00:00"),
    event_description: "Women's Doubles - Exhibition match featuring some of the region's most talented players, highlighting their top skills.",
    location_name: "Regional Sports Complex"
  },
  {
    event_id: 7,
    event_title: "Third Place Showdown",
    event_start: new Date("2024-09-28T09:00:00"),
    event_end: new Date("2024-09-28T11:00:00"),
    event_description: "Men's Doubles - Third-place consolation match between top contenders after a hard-fought semifinal round.",
    location_name: "City Park Pickleball Courts"
  },
  {
    event_id: 8,
    event_title: "Junior Rising Stars",
    event_start: new Date("2024-09-29T15:00:00"),
    event_end: new Date("2024-09-29T17:00:00"),
    event_description: "Junior Pickleball - Youth doubles match showcasing the next generation of talent in a friendly competitive setting.",
    location_name: "Junior Sports Arena"
  },
  {
    event_id: 9,
    event_title: "Final Showdown",
    event_start: new Date("2024-09-30T13:30:00"),
    event_end: new Date("2024-09-30T15:30:00"),
    event_description: "Women's Singles - The final showdown to crown the champion of the city-wide pickleball tournament after an exciting season.",
    location_name: "Pickleball Champion Court"
  },
  {
    event_id: 10,
    event_title: "Friendly Competitors",
    event_start: new Date("2024-10-01T11:00:00"),
    event_end: new Date("2024-10-01T13:00:00"),
    event_description: "Mixed Doubles - A highly anticipated friendly match featuring advanced players from different clubs.",
    location_name: "Local Pickleball Club"
  }
]);

fetchData

const locationJSON = ref([
  {
    "loc_id": 1,
    "court_name": "Downtown Court",
    "number_of_courts": 4,
    "address": "123 Main St, Cityville",
    "coordinates": "40.7128,-74.0060",
    "court_meta_id": 101
  },
  {
    "loc_id": 2,
    "court_name": "Westside Park",
    "number_of_courts": 6,
    "address": "456 West St, Townsville",
    "coordinates": "34.0522,-118.2437",
    "court_meta_id": 102
  },
  {
    "loc_id": 3,
    "court_name": "Eastwood Courts",
    "number_of_courts": 3,
    "address": "789 East Ave, Villageton",
    "coordinates": "51.5074,-0.1278",
    "court_meta_id": 103
  },
  {
    "loc_id": 4,
    "court_name": "Riverside Court",
    "number_of_courts": 2,
    "address": "101 River Rd, Waterbury",
    "coordinates": "35.6895,139.6917",
    "court_meta_id": 104
  },
  {
    "loc_id": 5,
    "court_name": "Central Park Courts",
    "number_of_courts": 8,
    "address": "202",
    "coordinates": "31.6895,14.6917",
    "court_meta_id": 105
}]);


function filterTimeOfEvents(){
  const now = new Date();

  for (const event of eventsJSON.value){
    if (event.event_start > now){ // future events 
      upcomingEvents.value.push(event)
    }
    else if ((event.event_start <= now) && (event.event_end > now)){ // events occuring now 
      ongoingEvents.value.push(event)
    }
    else if (event.event_end < now){ // past events 
      pastEvents.value.push(event)
    }
  }
}

onMounted(() => {
  getData();
  parseData();
})

function getData() {
  getCourts()
  }

const getCourts = async () => {
  JSONCourts.value = [];
  try {
    const url = '/data/locations';
    JSONCourts.value = await fetchData(url);
    console.log(JSONCourts.value);
  } catch (error) {
    console.error(error);
  }
}

function parseData(){
  filterTimeOfEvents();
  let allLocationNames = [];
  for (const loc of JSONCourts.value) {
    console.log(loc)
    const locName = `${loc.courtName}`;
    locationDict.value[locName] = loc.id
    allLocationNames.push(locName);
  }

  locations.value = allLocationNames;
}

  // potentially going to be used to skirt around the 00 issue on the time picker 
function frontendTime(time){
  if (time != undefined){
    let hours = time.split(':')[0]
    let minutes = time.split(':')[1]
    if (hours == '00'){
      hours = '12'
    }
    else if(hours[0] == '0'){
      hours = hours[1]
    }
  return `${hours}:${minutes}`
  }
  else{
    return null
  }
  
}

// function to format the date for the date object
function convertTo24HourTime(time, ampm) {
  let [hours, minutes] = time.split(':').map(Number);
  if (ampm === 'PM' && hours < 12) hours += 12;
  if (ampm === 'AM' && hours === 12) hours = 0;
  if (hours.toString().length === 1){
    hours = '0' + hours 
  }
  if (minutes.toString().length === 1){ // singular digits, like 1 minute
    minutes = '0' + minutes
  }
  return `${hours}:${minutes}`;
}

function convertToDateObject(date, time, ampm) {
  const time24hr = convertTo24HourTime(time, ampm);
  return new Date(`${date}T${time24hr}`);
}

function handleSubmit(){
  var jsonEvent = {};
  var dataNames = ['eventTitle','locationId','eventDescription'];
  var dataValues = [eventTitle.value, locationDict.value[eventLocation.value], eventDescription.value];

  for (let i = 0; i < dataNames.length; i++){
    jsonEvent[dataNames[i]] = dataValues[i];
    //console.log(dataValues[i])
  } 

  let nullsErr = checkForNulls();
  let timeAndDateLogic = checkForTimeAndDateLogic(nullsErr);

  if(nullsErr !== false){
    // console.log(`Nulls Error detected ${nullsErr}`)
  }
  else if(timeAndDateLogic !== false){
    formErrors.value = `${timeAndDateLogic}`;
    console.log(formErrors.value)
  }
  else{ // no errors, everything should go through here
    
    // date objects are getting created once the time logic is completed 
    jsonEvent['eventStart'] = convertToDateObject(startDate.value, startTime.value, startAMPM.value);
    jsonEvent['eventEnd'] = convertToDateObject(endDate.value, endTime.value, endAMPM.value);

    formErrors.value = ''; // delete this later
    console.log(jsonEvent); 

    // post event will occur here 

    closeModal()
  }
}

function checkForTimeAndDateLogic(nullsErr){
  if (nullsErr !== false){
    return null // short circuit to prevent errors trying to create dates with bad data 
  }

  let startObj = convertToDateObject(startDate.value, startTime.value, startAMPM.value);
  let endObj = convertToDateObject(endDate.value, endTime.value, endAMPM.value);
  let msIn30Mins = 1800000;
  const now = new Date();
  const oneYearAhead = new Date(now);
  oneYearAhead.setFullYear(now.getFullYear() + 1);

  if(startObj > endObj){
    return 'Event Start cannot occur after Event End'
  }
  else if(startObj < now){
    return 'Event cannot start in the past'
  }
  else if(startObj.getTime() === endObj.getTime()){
    return 'Event Start and Event End cannot be at the same time'
  }
  else if((endObj - startObj) < msIn30Mins){ // case to make sure the event lasts for at least 30 minutes
    return 'Event must last for at least 30 minutes' 
  }
  else if(startObj > oneYearAhead){
    return 'Events cannot be logged more than a year in advance'
  }
  else{
    return false; // case where there are no issues 
  }
}

function checkForNulls(){
  if(eventTitle.value == null){
      return 'Event Title';
    }
    else if(eventLocation.value == null){
      return 'Event Location';
    }
    else if(startDate.value == null){
      return 'Start Date';
    }
    else if(endDate.value == null){
      return 'End Date';
    }
    else if(startTime.value == null){
      return 'Start Time';
    }
    else if(startAMPM.value == null){  
      return 'Start AM / PM';
    }
    else if(endTime.value == null){ 
      return 'End Time';
    }
    else if(endAMPM.value == null){  
      return 'End AM / PM';
    }
    else if(eventDescription.value == null){
      return 'Event Description'
    }
    else{
      return false;
    }
}

function closeModal(){
  showDialog.value = false 
  formErrors.value = null

  eventTitle.value = null;
  eventLocation.value = null;
  startDate.value = null;
  endDate.value = null;
  startTime.value = null;
  endTime.value = null;
  startAMPM.value = null;
  endAMPM.value = null;
  eventDescription.value = null;
}

  function convertDateObjToFrontendDate(date){
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
const filteredUpcomingEvents = computed(() => {
  if (!searchQuery.value) {
    return upcomingEvents.value
  }
  return upcomingEvents.value.filter(event =>
    event.event_title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    event.event_description.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    event.location_name.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

const filteredOngoingEvents = computed(() => {
  if (!searchQuery.value) {
    return ongoingEvents.value
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
            <!-- Button to open the modal -->
            <v-btn id='event-btn' class="btn mr-2" @click="showDialog = true">Add New Event</v-btn>
            <!-- START MODAL -->
            <v-dialog class ="modal-container" persistent v-model="showDialog">
              <v-card class="modal-card">
                <v-form validate-on="submit lazy" @submit.prevent="handleSubmit">
                <v-row>
                  <v-col cols="11" class="modal-header">
                    <h2>
                      Add New Event
                    </h2>
                  </v-col>
                  <v-col cols="1" class="close-container">
                   
                    <v-btn class="close-btn" color="red" @click="closeModal()" density="compact" icon="$close"></v-btn>
                  </v-col>

                  <v-col cols="6" class="container">
                    <v-text-field v-model="eventTitle" required label="Event Title" :rules="titleRules">

                    </v-text-field>
                  </v-col>
                  <v-col cols="6" class="container">
                    <v-autocomplete :items="locations" v-model="eventLocation" clearable required label="Event Location" class="" :rules="locationRules">

                    </v-autocomplete>
                  </v-col>
                  <v-col cols="12" class="container">
                    <v-textarea v-model="eventDescription" placeholder="Write a brief description of the event you are creating "  required label="Event Description" :rules="descriptionRules">

                    </v-textarea>
                  </v-col>
                  <v-col cols="6" class="event-labels">
                    <h2>Event Start</h2>
                  </v-col>
                  <v-col cols="6" class="event-labels">
                    <h2>Event End</h2>
                  </v-col>
                  <v-col cols="6" class="container">
                    <v-text-field v-model="startDate" required label="Start Date" type="date" :rules="startDateRules">

                    </v-text-field>
                  </v-col>
                  <v-col cols="6" class="container">
                    <v-text-field v-model="endDate" required label="End Date" type="date" :rules="endDateRules">

                    </v-text-field>
                  </v-col>
                  <v-col cols="3" class="container left-time">
                    <v-text-field  v-model="frontendStartTime" :active="picker1Render" required label="Start Time" readonly :rules="startTimeRules"> <!-- DISPLAYS 00 INSTEAD OF 12 -->
                    <v-menu v-model="picker1Render" :close-on-content-click="false" activator="parent" transition="slide-x-transition">
                      <v-time-picker v-if="picker1Render" scrollable v-model="startTime" full-width color="green-lighten-1"></v-time-picker>
                    </v-menu>
                    </v-text-field>
                  </v-col>
                  <v-col cols="3" class="container right-time">
                    <v-select v-model="startAMPM":items="timeStamps" label="AM / PM" :rules="startAMPMRules"></v-select>
                  </v-col>
                  <v-col cols="3" class="container left-time">
                    <v-text-field v-model="frontendEndTime" :active="picker2Render" required label="End Time" readonly :rules="endTimeRules"> <!-- DISPLAYS 00 INSTEAD OF 12 -->
                      <v-menu v-model="picker2Render" :close-on-content-click="false" activator="parent" transition="slide-x-transition">
                        <v-time-picker v-if="picker2Render" v-model="endTime" scrollable full-width color="green-lighten-1"></v-time-picker>
                      </v-menu>
                    </v-text-field>
                  </v-col>
                  <v-col cols="3" class="container right-time">
                    <v-select v-model="endAMPM" :items="timeStamps" required label="AM / PM" :rules="endAMPMRules"></v-select>
                  </v-col>
          
                  <v-col cols="12" class="">
                    <v-btn class="submit" type="submit">
                      Add Event
                    </v-btn>
                  </v-col>
                  <v-col cols="12" class="errors">
                    {{formErrors}}
                  </v-col>
                </v-row>
              </v-form>
              </v-card>
            </v-dialog>
            <!-- END MODAL -->
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
                v-for="event in filteredOngoingEvents"
                :key="event.event_title"
                cols="6"
              >
                <v-card class="mb-3 card-item" outlined>
                  <v-row no-gutters>
                    <v-col cols="8">
                      <v-card-title>{{ event.event_title }}</v-card-title>
                      <!-- <v-card-subtitle>Event ID: {{ event.event_id }}</v-card-subtitle> -->
                      <v-card-subtitle>Start: {{ convertDateObjToFrontendDate(event.event_start) }}</v-card-subtitle>
                      <v-card-subtitle>End: {{ convertDateObjToFrontendDate(event.event_end) }}</v-card-subtitle>
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
                v-for="event in filteredUpcomingEvents"
                :key="event.event_title"
                cols="6"
              >
                <v-card class="mb-3 card-item" outlined>
                  <v-row no-gutters>
                    <v-col cols="8">
                      <v-card-title>{{ event.event_title }}</v-card-title>
                      <v-card-subtitle>Event ID: {{ event.event_id }}</v-card-subtitle>
                      <v-card-subtitle>Start: {{ convertDateObjToFrontendDate(event.event_start) }}</v-card-subtitle>
                      <v-card-subtitle>End: {{ convertDateObjToFrontendDate(event.event_end)}}</v-card-subtitle>
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
   .container{
    padding-top: 0rem;
    padding-bottom: 0rem;
   }
   .input-format{
    margin-bottom: 0rem;
    margin-top: 0rem;
  }
  .errors{
    display: inline-block;
    margin: auto;
    text-align: center;
    color: rgb(223, 70, 70);
    font-weight: bold;
    padding-top: 0;
    padding-bottom:0;
    font-size: 14px;
    height: 0px
  }  
  .submit{
    background-color: #4caf50;
    width: 100%;
    height: 3.5rem;
    margin-top: 0.4rem
  }
  .event-labels{
    justify-content: center;
    text-align: center;
    padding-top: 0;
  }
  .modal-header{
    padding-left: 57px;
    justify-content: center;
    text-align: center;

    padding-right: 0;
  }
  .left-time{
    padding-right: 6px;
  }
  .right-time{
    padding-left: 6px;
  }
  .test{
    
  }
  .modal-container{
    max-width: 800px;
  }
  .modal-card{
    background-color: #383737;
    padding: 2.5em;
    /* max-height: 672px; */
  }
  .card-item{
    border-radius: 8px;
    padding: 0.8rem;
    background-color: #42424254;
  }
  .close-container{

  }
  .close-btn{
   display:block;
   margin-left: auto;
   margin-right: 0;
  }
  #event-btn{
    background-color:#4caf50;
    width: 16rem;
    height: 3rem;
  }
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
   .btn{
    background-color: #42424254;
  }
  .card{
    padding: 0.8em;
    margin: 0.1em;
    /* height: 700px; */
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
