<template>
  <v-container id="container">
    <div>
      <headers></headers>
    </div>
    <div style="float:center">
      <div
        id="navigator"
        style="float:left; margin-top:16px; margin-right:20px; margin-left:20px;"
      >
        <navigator></navigator>
      </div>
      <div id="reference" class="rounded-lg">
        <p>레퍼런스</p>
        <img id="error" :src="require('@/assets/error.png')" />
        <br />
        <img id="error" :src="require('@/assets/ssakins.jpg')" />
        <br />
        <img id="error" :src="require('@/assets/error.png')" />
        <br />
        <img id="error" :src="require('@/assets/ssakins.jpg')" />
      </div>
    </div>
  </v-container>
</template>

<script>
import Navigator from "../components/Navigator";
import Header from "../components/Header";

export default {
  name: "notice",
  components: {
    navigator: Navigator,
    headers: Header,
  },
  data() {
    return {};
  },
  methods: {},
};
</script>

<style>
#container {
  width: 100%;
  min-width: 100px;
}

#navigator {
  width: 20%;
  min-width: 100px;
}

#reference {
  border: 1px solid grey;
  float: left;
  margin-top: 16px;
  margin-left: 20px;
  width: 72.5%;
  min-width: 200px;
  text-align: center;
}
</style>
