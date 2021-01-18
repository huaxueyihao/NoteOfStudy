// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import axios from 'axios'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})


axios({
  url: 'https://www.bilibili.com/video/BV15741177Eh?p=143'


}).then(res =>{
  console.log(res);
})


// 2.axios 发送并发请求
axios.all([axios(),axios()])
.then(results=>{
  
})