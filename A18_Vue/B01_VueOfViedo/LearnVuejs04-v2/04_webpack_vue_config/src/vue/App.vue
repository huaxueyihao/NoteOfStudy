<template>
  <div>
    <h2>{{message}}</h2>
    <button @click="btnClick">按钮</button>
    <h2>{{name}}</h2>
    <Cpn></Cpn>
  </div>
</template>

<script>
import Cpn from './Cpn.vue'



export default {
  components: { Cpn },
  name: "App",
  data(){
    return {
      message: 'Hello Webpack',
      name: 'coderwhy'
    }
  },
  methods:{
    btnClick(){

    }
  }
}
</script>

<style scoped>

</style>