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
    <div id="create">
      <div id="title">
        <h2>CI/CD 설정 수정</h2>
      </div>
      <v-simple-table dense>
        <template v-slot:default>
          <tbody>
            <tr>
              <td class="text-left font15" style="width: 8vw">
                설정 CI/CD 명
              </td>
              <td class="text-left font15" style="width: 62vw">
                <v-text-field  v-model="name" class="font15" placeholder="내용을 입력해주세요" hide-details="auto" dense filled shaped></v-text-field>
              </td>
            </tr>
            <tr>
              <td class="text-left font15">설정 내용</td>
              <td>
                <div id="content" class="font15">
                컴포넌트들이 들어갈 자리입니다.
                <p>아</p>
                <p>아</p>
                <p>아</p>
                <p>아</p>
                <p>아</p>
                <p>아</p>
                <p>아</p>
                <p>아</p>
                <p>아</p>
                <p>아</p>
                </div>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <div id="btn-area">
        <v-btn elevation="2" color="grey darken-3" style="color: white">저장하기</v-btn>
      </div>
    </div>
  </v-container>
</template>

<script>
import Navigator from "../components/Navigator";
import Header from "../components/Header";

export default {
  name: 'Edit',
  data() {
    return {
      name: 'SSAKINS 1차 CI/CD 설정',
    
    }
  },
  components: {
    navigator: Navigator,
    headers: Header
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

#edit {
    text-align: center;
    width: 70vw;
    margin: 0 auto;
}

v-simple-table {
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

tbody tr:hover {
  background-color: transparent !important;
}

</style>