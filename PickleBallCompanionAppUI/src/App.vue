<template>
  <v-app>
    <v-main>
      <PageHeader />
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import PageHeader from './components/PageHeader.vue'

export default {
  name: 'App',
  components: {
    PageHeader,
  },
};
</script>
