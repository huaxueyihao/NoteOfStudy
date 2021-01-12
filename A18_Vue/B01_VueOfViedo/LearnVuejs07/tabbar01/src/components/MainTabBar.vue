<template>
  <div id="main-tab-bar">
    <tab-bar>
      <tab-bar-item path="/home" activeColor="pink">
        <img slot="item-icon" src="" alt="" />
        <img slot="item-icon-active" src="" alt="" />
        <div slot="item-text">首页</div>
      </tab-bar-item>

      <tab-bar-item path="/category" activeColor="pink">
        <img slot="item-icon" src="" alt="" />
        <img slot="item-icon-active" src="" alt="" />
        <div slot="item-text">分类</div>
      </tab-bar-item>

      <tab-bar-item path="/cart" activeColor="pink">
        <img slot="item-icon" src="" alt="" />
        <img slot="item-icon-active" src="" alt="" />
        <div slot="item-text">购物车</div>
      </tab-bar-item>

      <tab-bar-item path="/profile" activeColor="pink">
        <img slot="item-icon" src="" alt="" />
        <img slot="item-icon-active" src="" alt="" />
        <div slot="item-text">我的</div>
      </tab-bar-item>
    </tab-bar>
  </div>
</template>


<script>
import TabBar from './tabbar/TabBar.vue'
import TabBarItem from './tabbar/TabBarItem.vue'

export default {
  name: "MainTabBar",
  components: {
    TabBar,
    TabBarItem
  }
};
</script>

<style  scoped>
</style>