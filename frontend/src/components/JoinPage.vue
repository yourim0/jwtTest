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
                    <input type="text" class="form-control" id="username" placeholder="Username" required>
                    <button class="btn btn-sm btn-primary">중복확인</button>
                    <div class="invalid-feedback">
                        Your username is required.
                    </div>
                    </div>
                </div>

                <div class="col-12">
                     <label for="password" class="form-label">패스워드</label>
                      <div class="input-group has-validation">
                        <input type="password" class="form-control" id="password" placeholder="password" required> &nbsp;
                        <input type="password" class="form-control" id="password" placeholder="password check" required>
                      <div class="invalid-feedback">
                          Password is required.
                        </div>
                      </div>
                </div>

                <div class="col-12">
                  <label for="email" class="form-label">이메일 <span class="text-body-secondary"></span></label>
                  <input type="email" class="form-control" id="email" placeholder="you@example.com">
                  <div class="invalid-feedback">
                    Please enter a valid email address for shipping updates.
                  </div>
                </div>

              </div> <br/> 
              <button class="w-100 btn btn-primary btn-lg" type="submit">회원가입</button>
            </form>
      </main>

      <footer class="my-5 pt-5 text-body-secondary text-center text-small">
        <p class="mb-1">&copy; 2017–2023 Company Name</p>
      </footer>
    </div>  
</body>

</template>

<script>
export default {

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

</style>