<template>
  <MainHeader />
  <br/>
  <router-view></router-view>

</template>

<script>
import MainHeader from './components/MainHeader.vue'
import store from './scripts/store';


export default {
  name: 'App',
  components: {
    MainHeader
  },
  setup() {
    const jwtToken = localStorage.getItem("Authorization");

    if(jwtToken){
      store.commit('setToken',jwtToken);
    }
  }
}
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
