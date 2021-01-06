<template>
  <div id="app">
    <div>
      <!-- <router-link to="/home" tag="button">首页</router-link>
      <router-link to="/about" tag="button">关于</router-link> -->

      <!-- <button @click="homeClick">首页</button>
      <button @click="aboutClick">关于</button> -->

      <router-link to="/home" tag="button">首页</router-link>
      <router-link to="/about" tag="button">关于</router-link>
      <router-link :to="'/user/'+userId" tag="button">用户</router-link>
      <router-link :to="{path:'/profile', query:{name:'why',age:18}}">档案</router-link>

      <router-view></router-view>
    </div>
  </div>
</template>

<script>

export default {
  name: 'App',
  data(){
    return {
      userId: 'lisi',
    }

  },
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
