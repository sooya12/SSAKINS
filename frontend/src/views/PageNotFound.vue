<template>
  <div style="text-align: center;">
    <img id="error" :src="require('@/assets/error.png')" />

    <h2>찾을 수 없는 페이지입니다.</h2>
    <h2>요청하신 페이지가 사라졌거나, 잘못된 경로를 이용하셨어요 :)</h2>
    <div style="margin-top:30px;">
      <button class="btn" @click="back()">BACK</button>
      <button class="btn" @click="home()">HOME</button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    back() {
      this.$router.go(-1);
    },
    home() {
      this.$router.push("/main");
    },
  },
};
</script>

<style>
#error {
  margin-top: 100px;
  margin-bottom: 50px;
  width: 40%;
}
h1 {
  font-size: 50px;
}

.btn {
  width: 80px;
  border: 2px solid black;
  background: white;
  padding: 15px 20px;
  margin: 5px;
  color: black;
  /* font-family: "Lato", sans-serif; */
  text-transform: uppercase;
  font-size: 10pt;
  /* letter-spacing: 1px; */
}

.btn:hover {
  background: white;
  transition: all 0.4s ease-out;
}
</style>
