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
          dark
          @click.stop="dialog = true"
        >
          삭제하기
        </v-btn>
        <v-dialog v-model="dialog" max-width="400">
          <v-card v-if="selected.length == 0">
            <v-card-title class="headline">
              프로젝트를 선택해주세요!
            </v-card-title>

            <v-card-text>
              삭제할 프로젝트가 선택되지 않았습니다.
            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" text @click="dialog = false">
                확인
              </v-btn>
            </v-card-actions>
          </v-card>

          <v-card v-if="selected.length > 0">
            <v-card-title class="headline">
              정말 삭제하시겠습니까?
            </v-card-title>

            <v-card-text>
              모든 프로젝트 설정 내용이 삭제됩니다.
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>

              <v-btn
                class="white--text"
                color="blue darken-1"
                text
                @click="deleted()"
              >
                예
              </v-btn>

              <v-dialog
                v-if="flag && selected.length > 0"
                v-model="dialog2"
                hide-overlay
                persistent
                width="300"
              >
                <v-card color="blue-grey" dark>
                  <v-card-text>
                    프로젝트를 삭제중입니다.
                    <v-progress-linear
                      indeterminate
                      color="white"
                      class="mb-0"
                    ></v-progress-linear>
                  </v-card-text>
                </v-card>
              </v-dialog>

              <v-btn color="red darken-1" text @click="dialog = false">
                아니오
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
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
      dialog: false,
      dialog2: false,
      selected: [],
      flag: false,
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
  watch: {
    dialog2(val) {
      if (!val) return;

      setTimeout(() => {
        this.dialog2 = false;
        this.$router.go();
      }, 3000);
    },
  },
  methods: {
    create() {
      this.$router.push("/create");
    },
    deleted() {
      console.log(this.selected);
      this.dialog = false;
      this.dialog2 = true;
      this.flag = true;
    },
    warning() {
      alert("선택을 해주세요!");
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
