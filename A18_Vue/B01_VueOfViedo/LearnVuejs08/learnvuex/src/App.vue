<template>
  <div id="app">
    <h2>-------------App内容--------------</h2>
    <h2>{{ $store.state.counter }}</h2>
    <button @click="addition">+</button>
    <button @click="subtraction">-</button>

    <h2>-------------hello Vuex内容--------------</h2>
    <hello-vuex />
    <router-view />
  </div>
</template>

<script>
import HelloVuex from "./components/HelloVuex.vue";

export default {
  name: "App",
  components: {
    HelloVuex,
  },
  data(){
    return {
      message: '我是App组件'
    }
  },
  methods: {
    addition(){
      this.$store.commit('increment');
    },
    subtraction(){
      this.$store.commit('decrement');
    }
  }
};
</script>

<style>
</style>
