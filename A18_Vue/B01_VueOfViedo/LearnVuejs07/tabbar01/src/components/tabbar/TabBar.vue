<template>
    <div id="tab-bar">
      <div class="tab-bar-item"> 
          <img src="" alt="">
          首页1
      </div>
      <div class="tab-bar-item"> 
          <img src="" alt="">
          首页2
      </div>
      <div class="tab-bar-item">
          <img src="" alt="">
          首页3
      </div>
      <div class="tab-bar-item">
          <img src="" alt="">
          首页4
      </div>
    </div>
</template>


<script>
export default {
    name: "TabBar"
}
</script>

<style  scoped>

  #tab-bar {
    display: flex;
    background-color: #f6f6f6;

    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;

    box-shadow: 0px -3px 1px rgba(100, 100, 100, .1);
  }

  .tab-bar-item {
    flex: 1;
    text-align: center;
    height: 49px;
  }
</style>