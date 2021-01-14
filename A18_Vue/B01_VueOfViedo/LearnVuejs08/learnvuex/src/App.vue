<template>
  <div id="app">
    <h2>-------------App内容--------------</h2>
    <h2>{{ $store.state.counter }}</h2>
    <button @click="$store.state.counter++">+</button>
    <button @click="$store.state.counter--">-</button>

    <h2>-------------hello Vuex内容--------------</h2>
    <hello-vuex />
    <router-view />
  </div>
</template>

<script>
import HelloVuex from "./components/HelloVuex.vue";

export default {
  components: { HelloVuex },
  name: "App",
  components: {
    HelloVuex,
  },
};
</script>

<style>
</style>
