<template>
  <v-container id="create">
    <div id="title">
      <h2>CI/CD 설정 수정</h2>
    </div>
    <v-simple-table dense>
      <template v-slot:default>
        <tbody>
          <tr>
            <td class="text-left thead">
              설정 CI/CD 명
            </td>
            <td class="text-left">
              <v-text-field  v-model="name" placeholder="내용을 입력해주세요" hide-details="auto" dense outlined></v-text-field>
            </td>
          </tr>
          <tr>
            <td class="text-left">설정 내용</td>
            <td>
              <div id="content">
              컴포넌트들이 들어갈 자리입니다.
              </div>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <div id="btn-area">
      <v-btn elevation="2" color="grey darken-3" style="color: white">저장하기</v-btn>
    </div>
  </v-container>
</template>

<script>
export default {
  name: 'Edit',
  data() {
    return {
      name: '나의 첫 CI/CD',
    
    }
  }
}
</script>

<style scoped>
#create {
    text-align: center;
    width: 70vw;
    margin: 0 auto;
}

v-simple-table {
  table-layout: fixed;
  margin: 0 auto;
  padding: 0;
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

.thead {
  width: 15vw;
  max-width: 180px;
  min-width: 120px;
}

</style>