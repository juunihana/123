<template>
  <div class="user-profile">
    <div>
      {{ user.username }}'s profile
    </div>
    <div>
      {{ user.info }}
    </div>
  </div>
</template>

<script>
import UserService from '../service/UserService.js';

export default {
  data() {
    return {
      user: {}
    }
  },
  mounted() {
    UserService.get('alice').then((response) => {
      console.log(response);
      this.user = response.data;
    })
  }
}
</script>

<style scoped>
/*.user-profile {*/
/*  padding: 10px;*/
/*}*/
</style>