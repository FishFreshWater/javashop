<template>
  <div id="app">
    <transition name="fade">
      <router-view></router-view>
    </transition>
  </div>
</template>
<script>
export default {
  
}
</script>

<style lang="scss">
body{
  overflow-x: hidden;
}
#app {
  height: 100%;
  width: 100%;
  min-width: 1000px;
}
.pad10{
  padding: 10px;
}
.mar-t10{
  margin-top: 10px;
}
</style>
