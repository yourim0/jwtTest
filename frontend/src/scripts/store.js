import { createStore } from 'vuex'

// Create a new store instance.
const store = createStore({
  state () {
    return{
        jwtToken : null,
    }
   
  }, 
  getters:{ // state값들을 리턴하기 위한 getter 로직
    isLogin(state){
        return state.jwtToken == null ? false : true;
    }
  },
  mutations: { //state값을 변경하는 로직, commit으로 호출
    setToken(state, _token){
        state.jwtToken = _token;
    }
},
actions :{ //mutation을 호출하는 서비스 로직. dispatch로 호출
    setToken :({commit}, _token) => {
        commit('setToken', _token);
    }
}
})

export default store;