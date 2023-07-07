<template>
  <nav>
    <router-link to="/">Profile</router-link>
    <router-link to="/about">About</router-link>
  </nav>
</template>

<script>
export default {
  name: "SideBlock"
}
</script>

<style scoped>
nav {
  display: flex;
  flex-direction: column;
}

nav a {
  padding: 10px;
  color: black;
  text-decoration: none;
}

nav a:active {
  color: black;
  text-decoration: none;
}

nav a:visited {
  color: black;
  text-decoration: none;
}

nav a:hover {
  /*color: #494949;*/
  text-decoration: none;
  background: #e0e0e0;
  border-radius: 5px;
}
</style>