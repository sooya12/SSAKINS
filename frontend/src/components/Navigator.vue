<template>
  <v-card>
    <v-list dense="dense" nav="nav" style="font-family: S-CoreDream-3Light;">
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
      selectedItem: 1,
      items: [
        {
          title: "SSAKINS 안내",
          icon: "mdi-information-outline",
          value: "/reference",
          no: 0,
        },
        {
          title: "프로젝트 목록",
          icon: "mdi-format-list-bulleted",
          value: "/main",
          no: 1,
        },
        {
          title: "SSAKINS 이용약관",
          icon: "mdi-help-box",
          value: "/info",
          no: 2,
        },
        {
          title: "개발자를 위한 쉼터",
          icon: "mdi-gamepad-variant",
          value: "/game",
          no: 3,
        },
      ],
      right: null,
    };
  },
  methods: {
    go(item) {
      if (this.$route.path !== item.value) {
        this.$router.push(item.value);
      } else {
        this.$router.go();
      }
    },
  },
  mounted() {
    for (let index = 0; index < this.items.length; index++) {
      if (this.items[index].value == window.location.pathname) {
        this.selectedItem = this.items[index].no;
      }
    }
  },
};
</script>

<style></style>
