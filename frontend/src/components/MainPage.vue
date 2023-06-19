<template>
        <button class="btn btn-primary" @click="user()">USER PAGE</button>
        <button class="btn btn-primary" @click="admin()">ADMIN PAGE</button>
</template>

<script>
import axios from 'axios'

export default {
   
    setup(){
    const user = () =>{
        const jwtToken = localStorage.getItem("Authorization");
        axios.get('/auth/api/v1/user', { headers: { 'Authorization': jwtToken } }).then((res) => {
            console.log(res);
            window.alert('user 접근가능');
        }).catch(() => {
            window.alert('user token error');
        })
    }

    const admin = () => {
        const jwtToken = localStorage.getItem("Authorization");
        axios.get('/auth/api/v1/admin', { headers: { 'Authorization': jwtToken } }).then((res) => {
            console.log(res);
            window.alert('admin 접근가능');
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