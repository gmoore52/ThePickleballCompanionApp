<template>
  <v-app>
    <v-container fluid>
      <v-row justify="space-between" align="stretch" class="flex-grow-1">
        <!-- Main Left Section (Welcome and Events) -->
        <v-col cols="12" md="12" class="d-flex flex-column">
          <!-- Profile Box -->
          <v-card class="pa-4 flex-grow-1" outlined>
            <v-row>
              <v-col cols="12" md="3">
                <v-card-title class="white--text text-h4">Stats</v-card-title>
                <v-card-subtitle class="white--text text-h6">Username</v-card-subtitle>

                <v-card-title class="white--text">Total Wins:</v-card-title>
                <v-card-text class="white--text">##</v-card-text>
                <v-card-title class="white--text">Total Losses:</v-card-title>
                <v-card-text class="white--text">##</v-card-text>
                <v-card-title class="white--text">W/L Ratio:</v-card-title>
                <v-card-text class="white--text">##</v-card-text>
                
                <v-card-title class="white--text">Most Frequented Court:</v-card-title>
                <v-card-text class="white--text">Location Court</v-card-text>

                <v-card-title class="white--text">Top Teammate:</v-card-title>
                <v-card-text class="d-flex align-center">
                  <v-card-text class = "white--text">Top Teammate</v-card-text>
                          <v-img
                            src="https://static.vecteezy.com/system/resources/previews/046/300/541/non_2x/avatar-user-profile-person-icon-gender-neutral-silhouette-profile-picture-suitable-for-social-media-profiles-icons-screensavers-free-png.png"
                            max-width="50"
                            max-height="50"
                            contain
                          ></v-img>
                </v-card-text>
                <v-card-title class="white--text">Strongest Opponent</v-card-title>
                <v-card-text class="d-flex align-center">
                  <v-card-text class = "white--text">Strongest Oppponent</v-card-text>
                          <v-img
                            src="https://static.vecteezy.com/system/resources/previews/046/300/541/non_2x/avatar-user-profile-person-icon-gender-neutral-silhouette-profile-picture-suitable-for-social-media-profiles-icons-screensavers-free-png.png"
                            max-width="50"
                            max-height="50"
                            contain
                          ></v-img>
                </v-card-text>

              </v-col>

              <v-col cols="12" md="8">
                <v-card>
                  <div>
                    <v-menu offset-y>
                      <template v-slot:activator="{ props }">
                        <v-btn v-bind="props">Chart Options</v-btn>
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
import { ref } from 'vue';

const actions = ref([
  { title: 'Bar Chart', image: 'https://chartexpo.com/Content/Images/charts/Clustered-Column-Graph.jpg' },
  { title: 'Line Chart', image: 'https://onlinestatbook.com/2/graphing_distributions/line_graphs_files/image004b.jpg' },
  { title: 'Pie Chart', image: 'https://images.wondershare.com/edrawmax/article2023/pie-chart-examples/edrawmax-pie-chart-example-10.jpg' },
]);

const selectedImage = ref(null); // Reactive variable to hold the selected image

function handleAction(action) {
  selectedImage.value = action.image; // Update the selected image
  console.log(`Action selected: ${action.title}`);
}
</script>

<style scoped>
.v-app {
  height: 100vh;
}

.v-container {
  height: 100%;
}

.v-card {
  border: 1px solid white;
  padding: 0.8em;
  margin: 0.1em;
  color: #212121 !important;
}

.white--text {
  color: white !important;
}

.v-row {
  margin-bottom: 20px;
}
</style>
