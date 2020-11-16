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
          :items="this.data['project']"
          item-key="name"
          show-select="show-select"
          class="elevation-1"
          @click="select(selected)"
          @click:row="handleClick"
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
                <v-card color="blue" dark>
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
import axios from "axios";
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
      data: [],
      flag: false,
      headers: [
        {
          text: "No.",
          value: "",
        },
        {
          text: "NAME",
          value: "name",
        },
        {
          text: "URL",
          value: "url",
        },
        {
          text: "PORT",
          value: "port",
        },
        /*{
          text: "등록일",
          value: "regDate",
        },
        {
          text: "수정일",
          value: "modDate",
        },*/
      ],
    };
  },
  created() {
    let email = sessionStorage.getItem("email");
    axios
      .get(this.$store.state.server + "project" + "/" + email)
      .then((res) => {
        this.data = res.data;
        console.log(this.data["project"]);
        // for (let index = 0; index <= this.data.length; index++) {
        //   console.log(this.data["project"][index]["index"]);
        // }
      });
  },
  watch: {
    dialog2(val) {
      if (!val) return;

      setTimeout(() => {
        this.dialog2 = false;
        //this.$router.go();
      }, 3000);
    },
  },
  methods: {
    create() {
      this.$router.push("/create");
    },
    deleted() {
      let email = sessionStorage.getItem("email");
      let projectName = [];
      console.log(this.selected);
      for (let index = 0; index < this.selected.length; index++) {
        projectName[index] = this.selected[index]["name"];
      }
      console.log(projectName);
      axios
        .post(
          this.$store.state.server + "project" + "/delete/" + email,
          projectName
        )
        .then(() => {});
      this.dialog = false;
      this.dialog2 = true;
      this.flag = true;
    },
    warning() {
      alert("선택을 해주세요!");
    },
    handleClick(value) {
      this.$router.push({ name: "Detail", params: { name: value.name } });
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
  cursor: pointer;
}
#navigator {
  width: 20%;
  min-width: 100px;
}
</style>
