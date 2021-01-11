<template>
  <div id="app">
    <tab-bar>
      <tab-bar-item>
        <img slot="item-icon" src="" alt=""/>
        <img slot="item-icon-active" src="" alt=""/>
        <div slot="item-text">首页</div>
      </tab-bar-item>

      <tab-bar-item>
        <img slot="item-icon"  src="" alt=""/>
        <img slot="item-icon-active" src="" alt=""/>
        <div slot="item-text">首页</div>
      </tab-bar-item>

      <tab-bar-item>
        <img slot="item-icon"  src="" alt=""/>
        <img slot="item-icon-active" src="" alt=""/>
        <div slot="item-text">首页</div>
      </tab-bar-item>

      <tab-bar-item>
        <img slot="item-icon"  src="" alt=""/>
        <img slot="item-icon-active" src="" alt=""/>
        <div slot="item-text">首页</div>
      </tab-bar-item>

    </tab-bar>
  </div>
</template>

<script>

import TabBar from './components/tabbar/TabBar.vue'
import TabBarItem from './components/tabbar/TabBarItem.vue'


export default {
  name: 'App',
  components: {
    TabBar,
    TabBarItem
  }
}
</script>

<style>
  @import "./assets/css/base.css";


</style>
