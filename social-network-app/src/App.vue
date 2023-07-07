<script setup>
import {RouterView} from 'vue-router'
import SideBlock from './SideBlock.vue'
</script>

<template>
  <div class="root-container">
    <div class="main-block">
      <div class="main-container">
        <router-view/>
      </div>
    </div>
    <div class="side-block">
      <SideBlock/>
    </div>
  </div>
</template>

<style scoped>
.root-container {
  width: 80%;
  max-width: 80%;
  margin: 10px auto;
  display: flex;
  flex-direction: row;
}

.main-block {
  flex: 75;
  margin-right: 5px;
}

.side-block {
  flex: 25;
  margin-left: 5px;
}

.main-block, .side-block {
  padding: 10px;
  background: #fcfcfc;
  border: solid 1px #888;
  border-radius: 5px;

  font-family: Verdana, Arial, sans-serif;
  font-size: 1rem;
  box-shadow: 4px 4px 5px #666666;
}

.main-container {
  padding: 10px;
}
</style>
