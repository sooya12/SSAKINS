<template>
  <sky style="position: relative; z-index: 1;">
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <star></star>
    <div id="title" style="z-index=2; text-align: center; float:center;">
      <!-- <b>SSAKINS</b> -->
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Monoton&effect=neon"
      />

      <div
        class="font-effect-neon"
        style="font-family: Monoton; font-size: 8em;"
      >
        SSAKINS
      </div>
    </div>
    <div id="logo" style="z-index=2; text-align: center; float:center;">
      <img :src="require('@/assets/test.png')" style="width:23%; z-index=2;" />
    </div>
    <div id="kakaoLogin" style="position: relative; z-index=2; float:center;">
      <img
        :src="require('@/assets/kakao_login_large_narrow.png')"
        @click="kakaoLogin"
      />
    </div>
    <br />
  </sky>
</template>

<script>
export default {
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

<style lang="scss">
body {
  margin: 0;
  overflow: hidden;
}

sky {
  display: block;
  background: black;
  width: 100vw;
  height: 100vh;
}
star {
  border-radius: 50%;
  background: white;
  position: absolute;
  animation: star linear infinite;
  z-index: -1;
  @for $i from 1 through 70 {
    &:nth-child(#{$i}) {
      $size: random(7) + 3px;
      width: $size;
      height: $size;
      animation-duration: random(30) + 15s;
      animation-delay: random(40) - 40s;
      top: random(101) - 1vh;
    }
  }
}
@keyframes star {
  from {
    transform: translate3d(-100%, 0, 1px);
  }
  to {
    transform: translate3d(100vw, 0, 1px);
  }
}

span {
  display: inline-block;
  &.w {
    display: inline;
  }
  @for $i from 1 through 17 {
    &:nth-child(#{$i}) {
      animation: type#{$i} 5s infinite linear both;
      @keyframes type#{$i} {
      }
    }
  }
}

#title {
  position: relative;
  text-align: center;
  margin-top: 50px;
}

#title b {
  font-size: 6vw;
  color: white;
  font-family: "S-CoreDream-8Heavy";
}

#kakaoLogin {
  width: 100%;
  margin: 0 auto;
  text-align: center;
}

#kakaoLogin img {
  width: 15%;
}
</style>
