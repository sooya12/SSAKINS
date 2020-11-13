<template>
  <v-container id="container">
    <div>
      <headers></headers>
    </div>
    <div
        id="navigator"
        style="float:left; margin-top:16px; margin-right:20px; margin-left:20px;"
    >
      <navigator></navigator>
    </div>
  </v-container>
</template>

<script>
import Navigator from "../components/Navigator";
import Header from "../components/Header";

export default {
  name: "TermsOfUse",
  components: {
    navigator: Navigator,
    headers: Header,
  }
}
</script>

<style scoped>

</style>