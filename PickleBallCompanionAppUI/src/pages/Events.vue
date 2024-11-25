<script setup>
import { ref, computed, onMounted} from 'vue'
import { VTimePicker } from 'vuetify/labs/VTimePicker'
import { fetchData } from '@/util/fetchData';
import { showAlert } from '@/util/alert'
import { useStore } from 'vuex';
import { formatDateTime } from '@/util/formatDate.js'


// Search query reactive variable
const searchQuery = ref(null)
const showDialog = ref(false);

const eventTitle = ref(null);  // todo fix REALLY weird off by 5 hours glitch on sending to the backend, frontent looks fine I dont know whats going wrong
const eventLocation = ref(null);
const startDate = ref(null);
const endDate = ref(null);
const startTime = ref(null);
const endTime = ref(null);
const startAMPM = ref(null)
const endAMPM = ref(null)
const eventDescription = ref(null);

const JSONCourts = ref([])
const JSONEvents = ref([])

const picker1Render = ref(false);
const picker2Render = ref(false);

const timeStamps = ref(['AM','PM']);
const locations = ref([]);
const locationDict = ref({}); // used to index between loc_id and location_name
const inverseLocationDict = ref({})

const ongoingEvents = ref([]);
const upcomingEvents = ref([]);
const pastEvents = ref([]);

const store = useStore();

const frontendStartTime = computed({ // cleans selected time for frontend
  get(){
    return frontendTime(startTime.value)
  }
})

const isLoggedIn = computed(() => store.state.isAuthenticated);

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
    return 'Select AM / PM';
  },
];

const descriptionRules = [
  value => {
    if (value) return true;
    return 'Must write a description';
  },
];

function filterTimeOfEvents(){
  const now = new Date();

  for (const event of JSONEvents.value){
    // console.log(event)
    if (event.eventStart > now){ // future events
      // console.log(event.eventStart)
      // console.log(now)
      upcomingEvents.value.push(event)
    }
    else if ((event.eventStart <= now) && (event.eventEnd > now)){ // events occuring now
      ongoingEvents.value.push(event)
    }
    else if (event.eventEnd < now){ // past events
      pastEvents.value.push(event)
    }
  }
}

onMounted(async () => {
  await getCourts();
  await getEvents();
  await formatEvents();
  await parseData();
})

const formatEvents = async () => {
  for (let event of JSONEvents.value){
    event.eventStart = new Date(event.eventStart)
    event.eventEnd = new Date(event.eventEnd)
  }
}

const getEvents = async () => {
  JSONEvents.value = [];
  try {
    const url = '/event/events';
    JSONEvents.value = await fetchData(url);
    // console.log(JSONEvents.value)
  } catch (error) {
    console.error(error);
  }
}

const getCourts = async () => {
  JSONCourts.value = [];
  try {
    const url = '/data/locations';
    JSONCourts.value = await fetchData(url);
  } catch (error) {
    console.error(error);
  }
}
const parseData = async () =>{
  filterTimeOfEvents();
  let allLocationNames = [];
  for (const loc of JSONCourts.value) {
    const locName = `${loc.courtName}`;
    locationDict.value[locName] = loc.id
    inverseLocationDict.value[loc.id] = locName
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

function convertToDateString(date, time, ampm) {
  const time24hr = convertTo24HourTime(time, ampm);
  return `${date}T${time24hr}`;
}

function convertLocIdToName(id){
  return inverseLocationDict.value[id]
}

function handleSubmit(){
  var jsonEvent = {};
  var dataNames = ['eventTitle','eventLoc','eventDesc'];
  var dataValues = [eventTitle.value, locationDict.value[eventLocation.value], eventDescription.value];

  // console.log(`Event Location ID: ${locationDict.value[eventLocation.value]}`); // Check the value before submission

  for (let i = 0; i < dataNames.length; i++){
    jsonEvent[dataNames[i]] = dataValues[i];
    //console.log(dataValues[i])
  }

  let nullsErr = checkForNulls();
  let timeAndDateLogic = checkForTimeAndDateLogic(nullsErr);

  if(nullsErr !== false){
    showAlert('error', `Error: First error found in field: '${nullsErr}'`)
  }
  else if(timeAndDateLogic !== false){
    showAlert('error', timeAndDateLogic)
  }
  else{ // no errors, everything should go through here

    // date objects are getting created once the time logic is completed
    jsonEvent['eventStart'] = convertToDateString(startDate.value, startTime.value, startAMPM.value);
    jsonEvent['eventEnd'] = convertToDateString(endDate.value, endTime.value, endAMPM.value);
    console.log(jsonEvent);


    try {
        const response = fetchData("/event/logEvent", {
        method: 'POST', // (or 'GET')
        body: JSON.stringify(jsonEvent),
        headers: {
            'Content-type':'application/json',
        }
      });

    console.log('Success - game added :', response);

    // this is the re-fetch that occurs when you add the game to be able to display it 
    // getEvents();
    // formatEvents();
    // parseData();

    } catch (error){
      console.error('Error adding Event:', error);
    }

    // post event will occur here

    closeModal()
  }
}

function checkForTimeAndDateLogic(nullsErr){
  if (nullsErr !== false){
    return null // short circuit to prevent errors trying to create dates with bad data
  }

  let startObj = new Date(convertToDateString(startDate.value, startTime.value, startAMPM.value));
  let endObj = new Date(convertToDateString(endDate.value, endTime.value, endAMPM.value));
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
  console.log(date.toLocaleString())
 
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
    event.eventTitle.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    event.eventDesc.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    convertLocIdToName(event.eventLoc).toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

const filteredOngoingEvents = computed(() => {
  if (!searchQuery.value) {
    return ongoingEvents.value
  }
  return ongoingEvents.value.filter(event =>
    event.eventTitle.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    event.eventDesc.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    convertLocIdToName(event.eventLoc).toLowerCase().includes(searchQuery.value.toLowerCase())
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
            <v-btn prepend-icon="mdi-calendar-edit" v-if="isLoggedIn" id='event-btn' class="btn mr-2" @click="showDialog = true">Add New Event</v-btn>
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
                    <v-btn prepend-icon="mdi-calendar-plus" class="submit" type="submit">
                      Add Event
                    </v-btn>
                  </v-col>
                  <v-col cols="12" class="errors">

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
                      <v-card-title>{{ event.eventTitle }}</v-card-title>
                      <!-- <v-card-subtitle>Event ID: {{ event.event_id }}</v-card-subtitle> -->
                      <v-card-subtitle>Start: {{ formatDateTime(event.eventStart.toLocaleString()) }}</v-card-subtitle>
                      <v-card-subtitle>End: {{ formatDateTime(event.eventEnd.toLocaleString()) }}</v-card-subtitle>
                      <v-card-text>{{ event.eventDesc }} </v-card-text>
                      <v-card-subtitle>Hosted at {{ convertLocIdToName(event.eventLoc) }}</v-card-subtitle>
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
                      <v-card-title>{{ event.eventTitle }}</v-card-title>
                      <!-- <v-card-subtitle>Event ID: {{ event.eventId }}</v-card-subtitle> -->
                      <v-card-subtitle>Start: {{ formatDateTime(event.eventStart.toLocaleString()) }}</v-card-subtitle>
                      <v-card-subtitle>End: {{ formatDateTime(event.eventEnd.toLocaleString())}}</v-card-subtitle>
                      <v-card-text>Description: {{ event.eventDesc }} </v-card-text>
                      <v-card-subtitle>Hosted at {{ convertLocIdToName(event.eventLoc) }}</v-card-subtitle>
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
    background-color: #222222;
    padding: 2.5em;
    /* max-height: 672px; */
  }
  .card-item{
    border-radius: 8px;
    padding: 0.8rem;
    background-color: #42424254;
  }

  .v-card{
    border-radius: 8px !important;
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

  .v-container {
    max-width: 1168px;
  }

</style>
