<template>
  <div id="app">
    <div>
      <!-- <router-link to="/home" tag="button">首页</router-link>
      <router-link to="/about" tag="button">关于</router-link> -->

      <button @click="homeClick">首页</button>
      <button @click="aboutClick">关于</button>

      <router-view></router-view>
    </div>
  </div>
</template>

<script>

export default {
  name: 'App',
  method: {
    homeClick(){
      this.$router.replace('/home');
    },
    aboutClick(){
      this.$router.replace('/about');
    }
  },
  components: {

  }
}
</script>

<style>

</style>
