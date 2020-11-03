<template>
  <div id="app">
    <div id="title">
          <b>SSAKINS</b>
    </div>
    <div id="logo">
      <img src="ssakins_logo.jpg" style="width:25%">
    </div>
    <div id="kakaoLogin">
      <img :src="require('@/assets/kakao_login_large_narrow.png')" @click=kakaoLogin>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  methods: {
    kakaoLogin() {
      window.location.href = 'https://kauth.kakao.com/oauth/authorize?client_id=3c19efe27e2021f8a6476f487212d326&redirect_uri=' + this.$store.state.server + '/login&response_type=code'
    }
  } 
}
</script>

<style scoped>    
    
#app {
  width: 100%;
  margin: 0 auto;
  text-align: center;
}

#title {
  position: relative;
  text-align: center;
  margin-top: 8vh;
}

#title b {
  font-size: 6vw;
}

#kakaoLogin {
  width: 100%;
  margin: 0 auto;
  text-align: center;
}

#kakaoLogin img {
  width: auto;
  height: 6vw;
}
</style>