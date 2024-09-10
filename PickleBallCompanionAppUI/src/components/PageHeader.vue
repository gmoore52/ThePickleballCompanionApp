<template>
<v-app-bar app color="green darken-4" dark>
    <v-toolbar-title class="text-center">
    <span class="text-h4 font-italic">Pickleball Companion</span>
    </v-toolbar-title>
</v-app-bar>

<v-tabs v-model="tab" background-color="grey lighten-2" centered>
    <v-tab
    v-for="item in tabs"
    :key="item.title"
    :to="item.route"
    ripple
    >
    {{ item.title }}
    </v-tab>
</v-tabs>
</template>

<script>
import { ref } from 'vue';

export default {
name: 'HeaderComponent',
setup() {
    const tab = ref(null);
    const tabs = ref([
    { title: 'Home', route: '/' },
    { title: 'Courts', route: '/courts' },
    { title: 'Events', route: '/events' },
    { title: 'Log game', route: '/log-game' },
    { title: 'Stats', route: '/stats' },
    { title: 'Profile', route: '/profile' }
    ]);

    return {
    tab,
    tabs
    };
}
};
</script>

<style scoped>

</style>
  