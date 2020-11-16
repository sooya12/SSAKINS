<template>
  <div>
    <div id="header">
      <div id="left" style="float:left; margin-left:10px;" @click="go()">
        <div id="logo" style="float:left">
          <img :src="require('@/assets/ssakins.jpg')" style="width:70px;" />
        </div>
        <div id="ssakins" style="float:left; font-family:S-CoreDream-8Heavy">
          <p>SSAKINS</p>
        </div>
      </div>
      <div id="right" style="float:right">
        <div
          id="profile"
          style="float:left; margin-top:15px; margin-left:50px;"
        >
          <v-row
              v-if="this.userImage != 'none'"
              justify="space-around">
              <v-avatar
                color="indigo"
                size="64"
              >
              <img :src="this.userImage" style="">
              </v-avatar>
          </v-row>
          <v-icon v-else size="60" style="float:center;">mdi-account-circle</v-icon>
        </div>
        <div id="name" style="float:left; margin-top:35px; margin-left:60px;">
          <h3>{{ this.userName }}</h3>
        </div>
        <div
          id="logout"
          style="float:left;  margin-top:30px; margin-left:60px;"
        >
          <v-row align="center" justify="space-around">
            <v-btn text style="float:left; margin-right:20px;" @click="kakaoLogout">
              logout
            </v-btn>
          </v-row>
        </div>
      </div>
    </div>
    <div id="line">
      <v-progress-linear
        color="blue lighten-5"
        rounded
        value="100"
      ></v-progress-linear>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      userName: "",
      userImage: "",
    };
  },
  methods: {
    go() {
      const item = "/main";
      if (this.$route.path !== item) {
        this.$router.push(item);
      } else {
        this.$router.go();
      }
    },
    mouseOver: function() {
      this.active = !this.active;
    },
    kakaoLogout() {
      let win = window.open('https://accounts.kakao.com/logout?continue=https://accounts.kakao.com/weblogin/account')
      win.close()
      sessionStorage.removeItem("email")
      this.$router.push("/")
    },
  },
  mounted() {
    this.userName = sessionStorage.getItem("name")
    this.userImage = sessionStorage.getItem("image")
  }
};
</script>

<style>
#header {
  width: 100%;
  min-width: 200px;
  height: 100px;
  font-family: "S-CoreDream-3Light";
}

img {
  width: 70px;
  margin: 0 auto;
  text-align: center;
}

p {
  margin-top: 15px;
  font-size: 50px;
}

h5 {
  text-align: center;
}

#line {
  width: 96%;
  margin-left: 20px;
}

#left {
  cursor: pointer;
}
</style>
