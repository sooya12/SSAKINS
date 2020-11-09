<template>
  <div>
    <div id="header">
      <div id="left" style="float:left; margin-left:10px;" @click="go">
        <div id="logo" style="float:left">
          <img :src="require('@/assets/ssakins.jpg')" style="" />
        </div>
        <div id="ssakins" style="float:left; font-family:Recipekorea">
          <p>SSAKINS</p>
        </div>
      </div>
      <div id="right" style="float:right">
        <div
          id="profile"
          style="float:left; margin-top:15px; margin-left:50px;"
        >
          <!-- <v-row justify="space-around">
                    <v-avatar
                    color="indigo"
                    size="64"
                    >
                    <img :src="require('@/assets/profile.jpg')" style="">
                    </v-avatar>
                 </v-row> -->
          <v-icon size="60" style="float:center;">mdi-account-circle</v-icon>
        </div>
        <div id="name" style="float:left; margin-top:35px; margin-left:60px;">
          <h3>감자</h3>
        </div>
        <div
          id="logout"
          style="float:left;  margin-top:30px; margin-left:60px;"
        >
          <v-row align="center" justify="space-around">
            <v-btn text style="float:left; margin-right:20px;">
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
      selectedCursor: `url("data:image/svg+xml,%3Csvg version='1.1' id='Layer_1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' x='0px' y='0px' width='32px' height='32px' viewBox='0 0 512 512' style='enable-background:new 0 0 512 512;' xml:space='preserve'%3E %3Cpath d='M443.6,387.1L312.4,255.4l131.5-130c5.4-5.4,5.4-14.2,0-19.6l-37.4-37.6c-2.6-2.6-6.1-4-9.8-4c-3.7,0-7.2,1.5-9.8,4 L256,197.8L124.9,68.3c-2.6-2.6-6.1-4-9.8-4c-3.7,0-7.2,1.5-9.8,4L68,105.9c-5.4,5.4-5.4,14.2,0,19.6l131.5,130L68.4,387.1 c-2.6,2.6-4.1,6.1-4.1,9.8c0,3.7,1.4,7.2,4.1,9.8l37.4,37.6c2.7,2.7,6.2,4.1,9.8,4.1c3.5,0,7.1-1.3,9.8-4.1L256,313.1l130.7,131.1 c2.7,2.7,6.2,4.1,9.8,4.1c3.5,0,7.1-1.3,9.8-4.1l37.4-37.6c2.6-2.6,4.1-6.1,4.1-9.8C447.7,393.2,446.2,389.7,443.6,387.1z'/%3E %3C/svg%3E"), pointer`,
    };
  },
  methods: {
    go() {
      this.$router.push("/main");
    },
    mouseOver: function() {
      this.active = !this.active;
    },
  },
};
</script>

<style>
#header {
  width: 100%;
  min-width: 200px;
  height: 100px;
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
  cursor: url("../assets/cursor.png"), pointer;
}
</style>
