<template>
    <router-link to="/login" v-if="!$store.state.jwtToken" >LOGIN  </router-link> 
    <a to="/logout" @click="logout()" v-else>LOGOUT  </a>
    <router-link to="/join">JOIN  </router-link>
    <router-link to="/">HOME  </router-link>
</template>

<script>
import store from '@/scripts/store'
import router from '@/router'
export default {
    name: 'MainHeader',
    setup(){
        const logout = () => {
        store.commit('setToken', null);
        localStorage.removeItem('Authorization');
        router.push({path:'/login'});
    }
    return {logout}
    }
}
</script>

<style>

</style>