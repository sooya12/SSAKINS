<template>
  <v-card>
    <v-list dense="dense" nav="nav">
      <v-list-item-group v-model="selectedItem" color="primary">
        <v-list-item v-for="item in items" :key="item.title" @click="go(item)">
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }} </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      selectedItem: this.$store.state.count,
      items: [
        {
          title: "SSAKINS 안내",
          icon: "mdi-information-outline",
          value: "notice",
          no: 0,
        },
        {
          title: "프로젝트 목록",
          icon: "mdi-format-list-bulleted",
          value: "main",
          no: 1,
        },
        {
          title: "SSAKINS 문의",
          icon: "mdi-help-box",
          value: "info",
          no: 2,
        },
        {
          title: "SSAKINS 이용약관",
          icon: "mdi-help-box",
          value: "info",
          no: 3,
        },
        {
          title: "개발자를 위한 쉼터",
          icon: "mdi-gamepad-variant",
          value: "game",
          no: 4,
        },
      ],
      right: null,
    };
  },
  methods: {
    go(item) {
      alert(item.value);
      this.$router.push(item.value);
      console.log("안바뀐놈 : " + this.selectedItem);
      this.selectedItem = item.no;
      this.$store.state.count = item.no;
      console.log("바뀐놈 : " + this.$store.state.count);
    },
  },
  // mounted() {
  //   console.log("마운트 : " + this.$store.state.count);
  //   this.selectedItem = this.$store.state.count;
  // },
};
</script>

<style></style>
