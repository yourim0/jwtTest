<template>
<body class="bg-body-tertiary">
    <div class="container">
      <main>
        <div class="py-5 text-center">
         <img class="mb-4" src="../assets/smiling.png" alt="" width="72" height="72">
          <h2>회원가입</h2>
        </div>
            <form class="needs-validation" novalidate>
              <div class="row g-3">
                <div class="col-12">
                    <label for="username" class="form-label">닉네임</label>
                    <div class="input-group has-validation">
                    <input type="text" class="form-control" id="username" placeholder="닉네임" v-model="state.form.username" required>
                    <button class="btn btn-sm btn-primary" @click.prevent="checkId()">중복확인</button>
                    </div>
                </div>

                <div class="col-12">
                     <label for="password" class="form-label">패스워드</label>
                      <div class="input-group has-validation">
                        <input type="password" class="form-control" id="password" placeholder="패스워드" v-model="state.form.password" required> &nbsp;
                        <input type="password" class="form-control" id="passwordcheck" placeholder="패스워드" v-model="state.passwordcheck" required>
                        <div class="passwordchk" v-if="state.passwordValidFlag">
                          패스워드가 일치하지 않습니다.
                      </div>
                      </div>
                </div>

                <div class="col-12">
                  <label for="email" class="form-label">이메일 <span class="text-body-secondary"></span></label>
                  <input type="email" class="form-control" id="email" placeholder="you@example.com" v-model="state.form.email">
                </div>

              </div> <br/> 
              <button class="btn btn-primary btn-sm" @click.prevent="submit()">회원가입</button>
            </form>
      </main>

      <footer class="my-5 pt-5 text-body-secondary text-center text-small">
        <p class="mb-1">&copy; 2017–2023 Company Name</p>
      </footer>
    </div>  
</body>

</template>

<script>
import { reactive, watch } from 'vue'; //반응형 ref
import axios from 'axios';
 
export default {

  setup(){
    const state = reactive({
      form:{
        username:"",
        password:"",  
        email:""
      },
      passwordcheck: "",
      passwordValidFlag : false,
    })

    watch(
      () => state.passwordcheck,() => {
        if(state.form.password !== state.passwordcheck){
          state.passwordValidFlag = true;
          console.log(state.passwordValidFlag)
        }else{
          state.passwordValidFlag = false;
          console.log(state.passwordValidFlag)
        }

    })

    const submit = () =>{
      if (!state.form.username) {
        window.alert("닉네임을 입력하세요");
        return false;
      } else if (!state.form.password) {
        window.alert("패스워드를 입력하세요");
        return false;
      } else if (!state.form.email) {
        window.alert("이메일을 입력하세요");
        return false;
      }

      axios.post('/auth/join', state.form, { headers: { 'Authorization': 'cos' } }).then((res) => {
         window.alert(res);
      }).catch(()=>{
         window.alert("이미 가입된 회원입니다.");
      })
    }

    const checkId = () =>{ //아이디 중복확인 //로그인이 안됐을텐데 header 가 필요한가
      console.log(state.form.username);
      axios.post('/auth/checkId', {username : state.form.username}, { headers: { 'Authorization': 'cos' } }).then((res) => {
               window.alert(res.data);
      }).catch(() => {
        window.alert("이미 가입된 회원입니다.");
      })
    }

    return { state, submit, checkId }
  }

}
</script>

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

.passwordchk{
  color : red;
}

</style>