<template>
  <v-container id="contianer">
    <div>
      <headers></headers>
    </div>
    <div
      id="navigator"
      style="float:left; margin-top:16px; margin-right:20px; margin-left:20px;"
    >
      <navigator></navigator>
    </div>
    <div id="detail">
      <div id="title">
        <h2>CI/CD 설정 상세</h2>
      </div>
      <table dense>
        <tbody>
          <tr>
            <td class="text-left font15" style="width: 10vw">
              설정 CI/CD 명
            </td>
            <td class="text-left font15" style="width: 60vw">
              {{ name }}
            </td>
          </tr>
          <tr>
            <td class="text-left font15" style="vertical-align: top"><br>설정 내용</td>
            <td>
              <div id="content" class="font15">
              
              </div>
            </td>
          </tr>
          <tr>
            <td>
            </td>
            <td>
              <div id="btn-area">
                <v-btn color="grey lighten-3" style="margin-right: 1vw"  @click="goEdit">수정하기</v-btn>
                <v-btn elevation="2" color="grey darken-3" style="color: white; margin-left: 1vw">삭제하기</v-btn>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </v-container>
</template>

<script>
import Navigator from "../components/Navigator";
import Header from "../components/Header";
import axios from 'axios'

export default {
  name: 'Detail',
  data() {
    return {
      userEmail: 'sooya@ssakins.com',
      name: 'test4',
      project: null,
    }
  },
  methods: {
    goEdit() {
      this.$router.push("/edit")
    },
    getDetail() {
      axios.get(this.$store.state.server, {

      }).then(res => {
        console.log(res)
      }).catch(err => {
        console.log(err)
      })
    }
  },
  components: {
    navigator: Navigator,
    headers: Header
  },
  mounted: {

  }
  
}
</script>

<style scoped>
#container {
  width: 100%;
  min-width: 100px;
}

#navigator {
  width: 20%;
  min-width: 100px;
}

#detail {
  text-align: center;
  width: 75%;
  margin: 0 auto;
  font-family: 'S-CoreDream-3Light';
}

table {
  table-layout: fixed;
  margin: 0 auto;
  padding: 0;
}

.font15 {
  font-size: 15px;
}

#title {
  margin: 20px;
}

#content {
  background: #EEEEEE;
}

#btn-area {
  margin: 20px auto;
}

</style>