<template>
  <body class="text-center">
    <main class="form-signin">
      <form>
        <img class="mb-4" src="../assets/smiling.png" alt="" width="72" height="72">
        <h1 class="h3 mb-3 fw-normal">로그인</h1>

        <div class="form-floating">
          <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"
            v-model="state.form.username">
          <label for="floatingInput">닉네임</label>
        </div>
        <div class="form-floating">
          <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
            v-model="state.form.password">
          <label for="floatingPassword">패스워드</label>
        </div>

        <div class="checkbox mb-3">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button class="btn btn-sm btn-primary" @click.self.prevent="submit()">회원가입</button>&nbsp;
        <button class="btn btn-sm btn-primary" @click.self.prevent="submit()">로그인</button>
      </form>
    </main>
  </body>
</template>

<script>
import { reactive } from 'vue';
import axios from 'axios';
import router from '@/router';

export default {
  name: 'LoginPage',
  props: {
    msg: String
  },
  setup() {
    const state = reactive({
      form: {
        username: "",
        password: ""
      }
    })

    const submit = () => {
      axios.post("/login", state.form , { headers: { 'Authorization': 'cos' } }).then((res) => { //header에 cos가 없으면 로그인다시하세요. 있으면 
        console.log(res);
        var jwtToken = res.headers.get("Authorization");
        console.log(jwtToken);
        localStorage.setItem("Authorization",jwtToken);
        window.alert("로그인 되었습니다.");
        router.push('/');
        
      }).catch(() => {
        window.alert("로그인 정보가 존재하지 않습니다.");
      });
    }
    return { state, submit }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
html,
body {
  height: 100%;
}

body {
  display: flex;
  align-items: center;
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #f5f5f5;
}

.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: auto;
}

.form-signin .checkbox {
  font-weight: 400;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
