<script setup>
import {ref, onMounted, watch, computed} from 'vue';
import { useRouter } from 'vue-router'; // Import useRouter from vue-router
import { useStore } from 'vuex';
import {fetchData} from "@/util/fetchData";
import GoToPlayerProfileButton from '@/components/sub-components/GoToPlayerProfileButton.vue';

const store = useStore();
const router = useRouter();

const isLoggedIn = computed(() => store.state.isAuthenticated);

const actions = ref([
  { title: 'Bar Chart', image: 'https://chartexpo.com/Content/Images/charts/Clustered-Column-Graph.jpg' },
  { title: 'Line Chart', image: 'https://onlinestatbook.com/2/graphing_distributions/line_graphs_files/image004b.jpg' },
  { title: 'Pie Chart', image: 'https://images.wondershare.com/edrawmax/article2023/pie-chart-examples/edrawmax-pie-chart-example-10.jpg' },
]);

const isChartDataLoading = ref(true);

const inverseLocationDict = ref({})
const JSONCourts = ref([]);

const userStatsHst = ref({
  totalWins: {
    series: []
  },
  totalLosses: {
    series: []
  },
  winLossRatio: {
    series: []
  },
  mostLossesToStrongestOpponent: {
    series: []
  }
})

const chartOptions = {
  chart: {
    height:400,
    type: 'line',
    dropShadow: {
      enabled: true,
      color: '#FFFFFF',
      top: 18,
      left: 7,
      blur: 10,
      opacity: 0.2
    },

    zoom: {
      enabled: false
    },
    toolbar: {
      show: false
    }
  },
  forecolor: "#FFFFFF",
  dataLabels: {
    enabled: true,
  },
  stroke: {
    curve: 'smooth'
  },
  title: {
    text: 'Loading...',
    offsetX: 0,
    offsetY: -5,
    align: 'center',
    style: {
      fontSize:  '25px',
      color: "#FFFFFF",
      fontFamily:  'Roboto',
    }
  },
  grid: {
    borderColor: 'rgba(255, 255, 255, 0.3)',
    row: {
      colors: ['#F0F0F0', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.05
    },
  },markers: {
    size: 1
  },
  xaxis: {
    type: 'datetime',
    tickPlacement: 'between',
    labels: {
      style: {
        colors: "#FFFFFF",
        fontFamily:  'Roboto',
      }
    }
  },
  yaxis: {
    title: {
      text: 'Loading...',
      style: {
        color: "#FFFFFF",
        fontFamily:  'Roboto',
      }
    },
    min: -15,
    max: 15,
    labels: {
      style: {
        colors: "#FFFFFF",
        fontFamily:  'Roboto',
      }
    }
  },
  legend: {
    position: 'top',
    horizontalAlign: 'right',
    floating: true,
    offsetY: -25,
    offsetX: -5,
    fontWeight: 700,
    labels: {
      colors: "#FFFFFF"
    },
    markers: {
          size: 5,
          shape: undefined,
          strokeWidth: 2,
      },
  },
  noData: {
    text: isChartDataLoading.value ? "Loading...":"No Data present in the graph!",
    align: 'center',
    verticalAlign: 'middle',
    offsetX: 0,
    offsetY: 0,
    style: {
      color: "#FFFFFF",
      fontSize: '14px',
      fontFamily:  'Roboto',
    }
  }
};

const chartOptionsStats = ref({
  totalWins: structuredClone(chartOptions),
  totalLosses: structuredClone(chartOptions),
  totalGames: structuredClone(chartOptions),
  winLossRatio: structuredClone(chartOptions),
  mostLossesToStrongestOpponent: structuredClone(chartOptions)
});

const selectedImage = ref(null); // Reactive variable to hold the selected image
const userStatsAcc = ref({
  totalGames: 0,
  totalWins: 0,
  totalLosses: 0,
  winLossRatio: 0,
  mostFrequentLocationID: 0,
  mostFrequentTeammateUsername: "",
  strongestOpponentUsername: "",
  mostLossesToStrongestOpponent: 0
});

function handleAction(action) {
  selectedImage.value = action.image; // Update the selected image
}

function generateChartOptions(stat){
  let series = userStatsHst.value[stat]["series"]

  chartOptionsStats.value[stat] = structuredClone(chartOptions);
  chartOptionsStats.value[stat]["title"]["text"] = series[0].name.substring(0, series[0].name.indexOf(' -')) + " Over Time";
  chartOptionsStats.value[stat]["yaxis"]["title"]["text"] = series[0].name.substring(0, series[0].name.indexOf(' -'))
  let overallMin = Infinity;
  let overallMax = -Infinity;

// Loop through each object in the list
  series.forEach(obj => {
    // Loop through each inner list in the data array
    obj.data.forEach(innerList => {
      // The second element is at index 1
      const secondElement = innerList[1];

      // Update overall min and max
      if (secondElement < overallMin) {
        overallMin = secondElement;
      }
      if (secondElement > overallMax) {
        overallMax = secondElement;
      }
    });
  });

  let padding = 2;
  chartOptionsStats.value[stat]["yaxis"]["min"] = overallMin - padding;
  chartOptionsStats.value[stat]["yaxis"]["max"] = overallMax + padding;
}

async function fetchUserStatsHst(username, stat) {
  try {
    isChartDataLoading.value = true;
    const json = await fetchData(`/statistics/getUserStatsHst?username=${username}&stat=${stat}`); // add commas here like this ${username},${username} to generate stats for more users
    userStatsHst.value[stat]["series"] = json;
    generateChartOptions(stat);

    isChartDataLoading.value = false;
  } catch (err) {
    console.error('Error fetching user data:', err);
    isChartDataLoading.value = false;
  }
}

async function fetchUserStatsAcc() {
  try {
    const json = await fetchData(`/statistics/getUserStats?username=${store.state.selectedUsername}`);
    Object.assign(userStatsAcc.value, json); // Merging user data
  } catch (err) {
    console.error('Error fetching user data:', err);
  }
}

onMounted(async () => {
  if (isLoggedIn.value) {
    fetchUserStatsAcc();
    for(let stat in userStatsHst.value){
      fetchUserStatsHst(store.state.selectedUsername, stat); // Fetch the user data for the selected profile
    }
  }

  await getCourts();      // Fetch courts data 
  inverseLocationDict.value = {};

  // Map through the courts to populate dictionaries and ensure camelCase consistency (from original parseData function)
  for (const loc of JSONCourts.value) {
    const locName = loc.courtName;
    inverseLocationDict.value[loc.id] = locName;
  }
});

watch(
  () => store.state.selectedUsername,
  (newUsername, oldUsername) => {
    if (newUsername !== oldUsername) {
      // TODO: pls put the function you use to fetch data here, which will be re ran once you reload the page
    }
  }
);

const getCourts = async () => {
  JSONCourts.value = [];
  try {
    const url = '/data/locations';
    JSONCourts.value = await fetchData(url);
  } catch (error) {
    console.error(error);
  }
}

function returnToOtherProfile(userName){
  router.push(`/profile/${userName}`); 
}

function convertLocIdToName(id) {
  if (id == null || id == ''){
    return 'No court data found'
  }
  else{
    return inverseLocationDict.value[id]
  }
}

</script>
<template>
  <v-app>
    <v-container>
      <v-row>
        <!-- Main Left Section (Welcome and Events) -->
        <v-col cols="12" md="12" class="d-flex flex-column big-col">
          <!-- Profile Box -->
          <div v-if="store.state.user?.userName !== store.state.selectedUsername" cols="12" md="12" class="pb-3">
                <v-btn prepend-icon="mdi-arrow-left" class="return-btn" @click="returnToOtherProfile(store.state.selectedUsername)">return</v-btn>
          </div>
          <v-card class="pa-4 flex-grow-1 big-container">
            <v-row>
              <v-col cols="12" md="4">
                <v-card class="stats-container">

                <v-card-title class="white--text text-h4 pb-0">Stats</v-card-title>
                <v-card-subtitle class="white--text text-h6">{{ store.state.selectedUsername }}</v-card-subtitle>

                <v-card-title class="white--text">Total Games Played:</v-card-title>
                <v-card-text class="white--text">{{userStatsAcc.totalGames}}</v-card-text>
                <v-card-title class="white--text">Total Wins:</v-card-title>
                <v-card-text class="white--text">{{userStatsAcc.totalWins}}</v-card-text>
                <v-card-title class="white--text">Total Losses:</v-card-title>
                <v-card-text class="white--text">{{userStatsAcc.totalLosses}}</v-card-text>
                <v-card-title class="white--text">Win/Loss Ratio:</v-card-title>
                <v-card-text class="white--text">{{(userStatsAcc.winLossRatio).toFixed(3)}}</v-card-text>
                <v-card-title class="white--text">Most Frequented Court:</v-card-title>
                
                <v-card-text class="white--text">{{convertLocIdToName(userStatsAcc.mostFrequentLocationID)}}</v-card-text>

                <v-card-title class="white--text">Most Wins With Teammate:</v-card-title>
                <v-card-text class="d-flex align-center" :v-if="userStatsAcc.mostFrequentTeammateUsername !== 'Not enough games played'">
                  <go-to-player-profile-button
                  v-if="userStatsAcc.mostFrequentTeammateUsername !== ''"
                  :playerUsername="userStatsAcc.mostFrequentTeammateUsername"
                  ></go-to-player-profile-button>
                  <span v-else class="white--text">No teammate data found</span>
                </v-card-text>
                <v-card-title class="white--text">Most Losses Against Opponent:</v-card-title>
                <v-card-text class="d-flex align-center" :v-if="userStatsAcc.strongestOpponentUsername !== 'No strongest opponent'">

                  <go-to-player-profile-button
                  v-if="userStatsAcc.strongestOpponentUsername !== ''"
                  :playerUsername="userStatsAcc.strongestOpponentUsername"
                  ></go-to-player-profile-button>
                  <span v-else class="white--text">No opponent data found</span>
                </v-card-text>
              </v-card>
              </v-col>

              <v-col cols="12" md="8" class="pl-1">
                <v-card class='stats-container chart-container' v-for="(item, index) in userStatsHst">

                <apexchart height="500" :options="JSON.stringify(chartOptionsStats) != '{}' ? chartOptionsStats[index] : chartOptions" :series="item.series"></apexchart>
                </v-card>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<style scoped>
.v-app {
  height: 100vh;
}

.stats-container{
  background-color: #42424254;
  border-radius: 8px;
}

.chart-container{
  margin-bottom: 16px;
}

.v-container {
  height: 100%;
}
.big-container{
  border-radius: 8px;
}

.v-card {
  border: 1px solid white;
  padding: 0.8em;

  color: #212121 !important;
  border:none;
}

.white--text {
  color: white !important;
}

.v-row {
  margin-bottom: 20px;
}

.v-container {
  max-width: 1168px;
  }


.apexcharts-tooltip span {
  color: #ffffff;
}

.return-btn{
  background-color: #42424254;
}

text.apexcharts-title-text{
  font-size: 123px !important;
}

</style>
