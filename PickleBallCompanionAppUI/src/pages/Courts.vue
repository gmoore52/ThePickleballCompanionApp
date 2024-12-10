<template>
  <v-container class="big-container">
    <!-- Search and Location Toolbar -->
    <v-row>
      <v-col cols="12" md="8">
        <v-btn prepend-icon="mdi-map-marker-account" color="#212121" id="loc-btn" class="mr-2" @click="getUserLocation">
          Click here to use your precise location
        </v-btn>
      </v-col>
      <v-col cols="12" md="4" class="d-flex">
        <v-text-field
          v-model="searchQuery"
          label="Search Courts"
          single-line
          color="#212121"
          class="white--text no-padding"
          hide-details
        ></v-text-field>
        <v-btn color="#212121" class="ml-1 search-btn">
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-col>
    </v-row>

    <!-- List of Courts -->
    <v-row>
      <v-col
        v-for="court in filteredCourts"
        :key="court.id"
        cols="12"
        class="displayed-court"
      >
        <v-card outlined>
          <v-row>
            <v-col cols="8">
              <v-card-title>{{ court.courtName }}</v-card-title>
              <v-card-subtitle>
                <strong>
                  Distance: {{ court.distance ? `~${court.distance}` : 'Please enable location by clicking the button above.' }}
                </strong>
              </v-card-subtitle>
              <v-card-text>Location: {{ court.address }}</v-card-text>
              <v-card-text>Number of courts: {{ court.numOfCourts }}</v-card-text>
              <v-card-text>Availability: {{ court.meta?.availability || 'Not Available' }}</v-card-text>
              <v-card-text>
                Shelter: {{ court.meta?.shelter === 'T' ? 'Yes' : 'No' }}
              </v-card-text>
              <v-card-text>Net Type: {{ court.meta?.netType || 'Not Specified' }}</v-card-text>
            </v-col>
            <v-col cols="4">
              <v-img :src="court.courtPic" alt="Court Image" />
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { fetchData } from '@/util/fetchData.js';

// State variables
const searchQuery = ref('');
const courts = ref([]);
const courtsMetaData = ref([]);
const userLocation = ref({ latitude: null, longitude: null });

// Fetch courts and metadata on component mount
onMounted(() => {
  getCourts();
  getMetaData();
});

// Fetch courts from API
const getCourts = async () => {
  courts.value = [];
  try {
    const url = '/data/locations';
    courts.value = await fetchData(url);
  } catch (error) {
    console.error(error);
  }
};

// Fetch court metadata and map it to courts
const getMetaData = async () => {
  courtsMetaData.value = [];
  try {
    const url = '/data/locationmeta';
    courtsMetaData.value = await fetchData(url);

    // Map metadata to matching courts
    if (courts.value.length > 0 && courtsMetaData.value.length > 0) {
      courts.value.forEach(court => {
        const matchingMeta = courtsMetaData.value.find(meta => meta.id === court.id);
        if (matchingMeta) {
          court.meta = matchingMeta;
        }
      });
    }
  } catch (error) {
    console.error(error);
  }
};

// Filter courts based on search query
const filteredCourts = computed(() => {
  if (!searchQuery.value) {
    return courts.value;
  }
  return courts.value.filter(court =>
    court.courtName.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    court.address.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

// Get user's location and calculate distances
const getUserLocation = () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      position => {
        userLocation.value.latitude = position.coords.latitude;
        userLocation.value.longitude = position.coords.longitude;
        calculateDistances();
        alert(`Location data turned on! \nYour location is Latitude: ${userLocation.value.latitude}, Longitude: ${userLocation.value.longitude}`);
      },
      error => {
        alert('Unable to retrieve your location.');
      }
    );
  } else {
    alert('Geolocation is not supported by your browser.');
  }
};

// Calculate distances between user and courts
const calculateDistances = () => {
  if (!userLocation.value.latitude || !userLocation.value.longitude) {
    return;
  }

  const R = 3958.8; // Radius of Earth in miles
  courts.value.forEach(court => {
    const [courtLat, courtLon] = court.coordinates.split(',').map(Number);
    const userLat = userLocation.value.latitude;
    const userLon = userLocation.value.longitude;

    const dLat = (courtLat - userLat) * (Math.PI / 180);
    const dLon = (courtLon - userLon) * (Math.PI / 180);

    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(userLat * (Math.PI / 180)) * Math.cos(courtLat * (Math.PI / 180)) *
      Math.sin(dLon / 2) * Math.sin(dLon / 2);

    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c;

    court.distance = `${distance.toFixed(2)} miles`;
  });
};
</script>

<style scoped>
.displayed-court {}

#loc-btn {
  background-color: #4caf50 !important;
}

.v-card {
  background-color: #42424254;
  border-radius: 8px;
  padding: 0.8em;
  margin: 0.1em;
}

.big-container {
  background-color: #212121;
  padding-left: 16px;
  border-radius: 8px;
  margin-bottom: 16px;
  margin-top: 16px;
}

.search-btn {
  height: 100%;
}

.v-btn {
  font-size: 14px;
}

.v-card-title,
.v-card-subtitle,
.v-card-text {
  color: white;
}

.v-text-field {
  background-color: #42424254;
  padding: 0;
}

.v-img {
  object-fit: cover;
  height: 100%;
}

.no-padding {
  padding: 0 !important;
}

.v-container {
  max-width: 1168px;
}
</style>
