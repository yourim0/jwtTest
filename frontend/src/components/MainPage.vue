<template>
        <button class="btn btn-primary" @click="user()">USER PAGE</button>
        <button class="btn btn-primary" @click="admin()">ADMIN PAGE</button>
</template>

<script>
import axios from 'axios'

export default {
    
    setup(){
    const user = () =>{
        var jwtToken = localStorage.getItem("Authorization");
        axios.get('/auth/api/v1/user', { headers: { 'Authorization': jwtToken } }).then((res) => {
            console.log(res);
        }).catch(() => {
            window.alert('user token error');
        })
    }

    const admin = () => {
        axios.get('/auth/api/v1/admin').then((res) => {
            console.log(res);
        }).catch(() => {
            window.alert('admin token err');
        })
    }
    return {user ,admin}

    }

}
</script>

<style>


</style>