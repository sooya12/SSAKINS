<template>
  <div id="app">
    <div id="title">
      <b>SSAKINS</b>
    </div>
    <div id="logo">
      <img src="ssakins_logo.jpg" style="width:23%">
    </div><br>
    <div id="kakaoLogin">
      <img
        :src="require('@/assets/kakao_login_large_narrow.png')"
        @click="kakaoLogin"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  methods: {
    kakaoLogin() {
      window.location.href =
        "https://kauth.kakao.com/oauth/authorize?client_id=5087dfcc2c61ed08e9cf4a4282fbbf2c&redirect_uri=" +
        this.$store.state.server +
        "account/login&response_type=code";
    },
  },
};
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
  margin-top: 6vh;
}

#title b {
  font-size: 6vw;
  font-family: 'S-CoreDream-8Heavy';
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
