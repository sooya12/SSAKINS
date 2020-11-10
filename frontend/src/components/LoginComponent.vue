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
    sessionStorage.setItem("email",this.email)
    this.$router.push('/main')
  }
}
</script>

<style scoped>

</style>