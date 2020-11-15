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
    },
    name: {
      type: String
    },
    image: {
      type: String
    }
  },
  mounted() {
    sessionStorage.setItem("email", this.email)
    sessionStorage.setItem("name", this.name)
    sessionStorage.setItem("image", decodeURIComponent(this.image))
    this.$router.push('/main')
  }
}
</script>

<style scoped>

</style>