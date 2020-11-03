<template>
  <v-card>
    <!-- <v-list-item>
                <v-list-item-content>
                    <v-list-item-title class="title">
                        Application
                    </v-list-item-title>
                    <v-list-item-subtitle>
                        subtext
                    </v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>

            <v-divider></v-divider> -->

    <v-list dense="dense" nav="nav">
      <v-list-item v-for="item in items" :key="item.title" link="link">
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title @click="go(item.value)"
            >{{ item.title }}
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          title: "SSAKINS 안내",
          icon: "mdi-information-outline",
          value: "notice",
        },
        {
          title: "프로젝트 목록",
          icon: "mdi-format-list-bulleted",
          value: "main",
        },
        {
          title: "SSAKINS 문의",
          icon: "mdi-help-box",
          value: "info",
        },
        {
          title: "SSAKINS 이용약관",
          icon: "mdi-help-box",
          value: "info",
        },
        {
          title: "개발자를 위한 쉼터",
          icon: "mdi-gamepad-variant",
          value: "game",
        },
      ],
      right: null,
    };
  },
  methods: {
    go(value) {
      this.$router.push(value);
    },
  },
};
</script>

<style></style>
