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
      <div id="table" style="float:left; margin-top:16px; margin-left:20px;">
        <v-data-table
          v-model="selected"
          :headers="headers"
          :items="lists"
          item-key="name"
          show-select="show-select"
          class="elevation-1"
          @click="select(selected)"
        >
        </v-data-table>
        <v-btn
          color="primary"
          style="float:center; margin-right:20px; margin-top:20px;"
          @click="create"
        >
          생성하기
        </v-btn>
        <v-btn
          color="error"
          style="float:center; margin-top:20px;"
          @click="deleted"
        >
          삭제하기
        </v-btn>
      </div>
    </div>
  </v-container>
</template>

<script>
import Navigator from "../components/Navigator";
import Header from "../components/Header";

export default {
  components: {
    navigator: Navigator,
    headers: Header,
  },
  data() {
    return {
      selected: [],
      headers: [
        {
          text: "No.",
          align: "start",
          value: "name",
        },
        {
          text: "설정 CI/CD 명",
          value: "calories",
        },
        {
          text: "특징",
          value: "fat",
        },
        {
          text: "생성일",
          value: "carbs",
        },
        {
          text: "수정일",
          value: "protein",
        },
        {
          text: "비고",
          value: "iron",
        },
      ],
      lists: [
        {
          name: "Frozen Yogurt",
          calories: 159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          iron: "1%",
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
          fat: 9.0,
          carbs: 37,
          protein: 4.3,
          iron: "1%",
        },
        {
          name: "Eclair",
          calories: 262,
          fat: 16.0,
          carbs: 23,
          protein: 6.0,
          iron: "7%",
        },
        {
          name: "Cupcake",
          calories: 305,
          fat: 3.7,
          carbs: 67,
          protein: 4.3,
          iron: "8%",
        },
        {
          name: "Gingerbread",
          calories: 356,
          fat: 16.0,
          carbs: 49,
          protein: 3.9,
          iron: "16%",
        },
        {
          name: "Jelly bean",
          calories: 375,
          fat: 0.0,
          carbs: 94,
          protein: 0.0,
          iron: "0%",
        },
        {
          name: "Lollipop",
          calories: 392,
          fat: 0.2,
          carbs: 98,
          protein: 0,
          iron: "2%",
        },
        {
          name: "Honeycomb",
          calories: 408,
          fat: 3.2,
          carbs: 87,
          protein: 6.5,
          iron: "45%",
        },
        {
          name: "Donut",
          calories: 452,
          fat: 25.0,
          carbs: 51,
          protein: 4.9,
          iron: "22%",
        },
        {
          name: "KitKat",
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          iron: "6%",
        },
      ],
    };
  },
  methods: {
    create() {
      this.$router.push("/create");
    },
    deleted() {
      console.log(this.selected);
      if (this.selected.length > 0) {
        alert("선택한 항목을 삭제하시겠습니까?");
      } else {
        alert("선택을 하세요...............");
      }
    },
  },
};
</script>

<style>
#container {
  width: 100%;
  min-width: 100px;
}

#table {
  width: 72.5%;
  min-width: 200px;
  text-align: center;
}
#navigator {
  width: 20%;
  min-width: 100px;
}
</style>
