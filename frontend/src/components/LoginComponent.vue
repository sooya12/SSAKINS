<template>
  <div>

  </div>
</template>

<script>
export default {
  name: "LoginComponent.vue",
  props: {
    email: {
      type: String
    }
  },
  mounted() {
    console.log(this.email)
    sessionStorage.setItem("email",this.email)
    /**
     * TODO : 수정해야함 >~<
     */
    // sessionStorage.setItem("email","ajph03102@naver.com");
    this.$router.push('/main')
  }
}
</script>

<style scoped>

</style>