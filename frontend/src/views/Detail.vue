<template>
  <v-container id="detail">
    <div id="title">
      <h2>CI/CD 설정 상세</h2>
    </div>
    <v-simple-table dense>
      <template v-slot:default>
        <tbody>
          <tr>
            <td class="text-left font15" style="width: 8vw">
              설정 CI/CD 명
            </td>
            <td class="text-left font15" style="width: 40vw">
              {{ name }}
            </td>
            <td class="font15" style="width: 5vw">생성일</td>
            <td class="font15" style="width: 6vw">{{ createDay }}</td>
            <td class="font15" style="width: 5vw">수정일</td>
            <td class="font15" style="width: 6vw">{{ editDay }}</td>
          </tr>
          <tr>
            <td class="text-left font15">설정 내용</td>
            <td colspan="5">
              <div id="content" c class="font15">
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
      <v-btn color="grey lighten-3" style="float:left; margin-left:20px;">삭제하기</v-btn>
      <v-btn elevation="2" color="grey darken-3" style="color: white; float:right; margin-right:20px" @click="goEdit">수정하기</v-btn>
    </div>
  </v-container>
</template>

<script>
export default {
  name: 'Detail',
  data() {
    return {
      name: 'SSAKINS 1차 CI/CD 설정',
      createDay: '2020.10.12',
      editDay: '2020.10.13',
    }
  },
  methods: {
    goEdit() {
      this.$router.push("/edit")
    }
  }
  
}
</script>

<style scoped>
#detail {
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