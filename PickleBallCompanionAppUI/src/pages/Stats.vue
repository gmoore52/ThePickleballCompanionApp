<template>
  <v-app>
    <v-container>
      <v-row >
        <!-- Main Left Section (Welcome and Events) -->
        <v-col cols="12" md="12" class="d-flex flex-column" >
          <!-- Profile Box -->
          <v-card class="pa-4 flex-grow-1 big-container" outlined >
            <v-row>
              <v-col cols="12" md="3">
                <v-card-title class="white--text text-h4">Stats</v-card-title>

                <v-card-subtitle class="white--text text-h6">{{ store.state.selectedUsername }}</v-card-subtitle>
                <v-btn v-if="store.state.user?.userName !== store.state.selectedUsername" prepend-icon="mdi-account-arrow-left" class="mt-2 mx-2" color="blue" @click="returnToOtherProfile(store.state.selectedUsername)">return</v-btn>


                <v-card-title class="white--text">Total Games:</v-card-title>
                <v-card-text class="white--text">{{userStatsAcc.totalGames}}</v-card-text>
                <v-card-title class="white--text">Total Wins:</v-card-title>
                <v-card-text class="white--text">{{userStatsAcc.totalWins}}</v-card-text>
                <v-card-title class="white--text">Total Losses:</v-card-title>
                <v-card-text class="white--text">{{userStatsAcc.totalLosses}}</v-card-text>
                <v-card-title class="white--text">W/L Ratio:</v-card-title>
                <v-card-text class="white--text">{{userStatsAcc.winLossRatio}}</v-card-text>

                <v-card-title class="white--text">Most Frequented Court:</v-card-title>
                <v-card-text class="white--text">{{userStatsAcc.mostFrequentLocationID}}</v-card-text>

                <v-card-title class="white--text">Top Teammate:</v-card-title>
                <v-card-text class="d-flex align-center">
                  <!-- <v-card-text class = "white--text">{{userStatsAcc.mostFrequentTeammateUsername}}</v-card-text> -->
                  <go-to-player-profile-button
                  :playerUsername="userStatsAcc.mostFrequentTeammateUsername"
                  ></go-to-player-profile-button>

                          <!-- <v-img
                            src="https://static.vecteezy.com/system/resources/previews/046/300/541/non_2x/avatar-user-profile-person-icon-gender-neutral-silhouette-profile-picture-suitable-for-social-media-profiles-icons-screensavers-free-png.png"
                            max-width="50"
                            max-height="50"
                            contain
                          ></v-img> -->
                </v-card-text>
                <v-card-title class="white--text">Strongest Opponent</v-card-title>
                <v-card-text class="d-flex align-center">

                  <go-to-player-profile-button
                  :playerUsername="userStatsAcc.strongestOpponentUsername"
                  ></go-to-player-profile-button>
                  <!-- <v-card-text class = "white--text">{{userStatsAcc.strongestOpponentUsername}}</v-card-text> -->
                          <!-- <v-img
                            src="https://static.vecteezy.com/system/resources/previews/046/300/541/non_2x/avatar-user-profile-person-icon-gender-neutral-silhouette-profile-picture-suitable-for-social-media-profiles-icons-screensavers-free-png.png"
                            max-width="50"
                            max-height="50"
                            contain
                          ></v-img> -->
                </v-card-text>

              </v-col>

              <v-col cols="12" md="8">
                <apexchart v-for="item in userStatsHst" height="500" :options="chartOptions" :series="item.series"></apexchart>
<!--                <apexchart height="500" :options="chartOptions" :series="userStatsHst.totalWins.series"></apexchart>-->
                <v-card class="chart-options">
                  <div>
                    <v-menu offset-y>
                      <template v-slot:activator="{ props }">
                        <v-btn v-bind="props" class="chart-options">Chart Options</v-btn>
                      </template>
                      <v-list>
                        <v-list-item
                          v-for="(action, index) in actions"
                          :key="index"
                          @click="handleAction(action)"
                        >
                          <v-list-item-title>{{ action.title }}</v-list-item-title>
                        </v-list-item>
                      </v-list>
                    </v-menu>
                  </div>


                  <!-- Conditionally Render the Selected Image -->
                  <div v-if="selectedImage">
                    <v-img
                      :src="selectedImage"
                      aspect-ratio="16/9"
                      cover
                      class="mt-4"
                    ></v-img>
                  </div>
                </v-card>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

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

const userStatsHst = ref({
  totalWins: {
    series: []
  },
  totalLosses: {
    series: []
  },
  totalGames: {
    series: []
  },
  winLossRatio: {
    series: []
  },
  // mostFreqLocation: {
  // series: [},
  // mostFreqTeammate: {
  // series: [},
  // strongestOpponent: {
  // series: [},
  mostLossesToStrongestOpponent: {
    series: []
  }
})

// const chartData = ref([
//   {
//     name: "High - 2013",
//     data: [28, 29, 33, 36, 32, 32, 33]
//     // data: []
//   },
//   // {
//   //   name: "Low - 2013",
//   //   data: [12, 11, 14, 18, 17, 13, 13]
//   //   // data: []
//   // }
// ]);
// const chartData = ref([]);
const chartOptions = ref({
  chart: {
    height: 350,
    type: 'line',
    dropShadow: {
      enabled: true,
      color: '#000',
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
  colors: ['#77B6EA', '#545454'],
  dataLabels: {
    enabled: true,
  },
  stroke: {
    curve: 'smooth'
  },
  title: {
    text: 'Average High & Low Temperature',
    align: 'left'
  },
  grid: {
    borderColor: '#e7e7e7',
    row: {
      colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.5
    },
  },markers: {
    size: 1
  },
  xaxis: {
    type: 'datetime'
    // {
    //
    // categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
    // title: {
    //   text: 'Month'
    // }
  },
  yaxis: {
    title: {
      text: 'Temperature'
    },
    min: -15,
    max: 15
  },
  legend: {
    position: 'top',
    horizontalAlign: 'right',
    floating: true,
    offsetY: -25,
    offsetX: -5
  },
  noData: {
    text: isChartDataLoading.value ? "Loading...":"No Data present in the graph!",
    align: 'center',
    verticalAlign: 'middle',
    offsetX: 0,
    offsetY: 0,
    style: {
      color: "#000000",
      fontSize: '14px',
      fontFamily: "Helvetica"
    }
  }
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
  // console.log(`Action selected: ${action.title}`);
}

async function fetchUserStatsHst(username, stat) {
  try {
    isChartDataLoading.value = true;
    const json = await fetchData(`/statistics/getUserStatsHst?username=${username}&stat=${stat}`);
    // Object.assign(userStatsHst.value[stat], json); // Merging user data
    userStatsHst.value[stat]["series"] = json;
    // console.log(JSON.stringify(userStatsHst.value))
    // console.log(JSON.stringify(json))
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
  // put fetch function here (also see the watch function below)
  if (isLoggedIn.value) {
    // fetchData(``)
    fetchUserStatsAcc();
    for(let stat in userStatsHst.value){
      fetchUserStatsHst(store.state.selectedUsername, stat); // Fetch the user data for the selected profile

    }
    // fetchFriends(); // Fetch the friends of the selected profile
    // generateDataSeries(null, null, "winLossRatio");
  }
});

// this will be a function that will re-populate the stats page if you navigate there from someone else's page
watch(
  () => store.state.selectedUsername,
  (newUsername, oldUsername) => {
    if (newUsername !== oldUsername) {
      // TODO: pls put the function you use to fetch data here, which will be re ran once you reload the page
    }
  }
);

function returnToOtherProfile(userName){
  // store.commit('SET_SELECTED_USERNAME', "Peter_Dinklage3");
  router.push(`/profile/${userName}`); // /${user.value}
}

</script>

<style scoped>
.v-app {
  height: 100vh;
}

.v-container {
  height: 100%;
}
.big-container{
  border-radius: 8px;
}

.chart-options{
  background-color: #42424254;
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
</style>
