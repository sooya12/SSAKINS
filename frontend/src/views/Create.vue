<template>
  <div id="create">
    <h2>CI/CD 설정 생성</h2>
    <div id="createForm">
      <!-- <form @submit.prevent="submint"> -->
        <v-simple-table id="st">
          <tbody> 
            <tr>
              <th class="text-left">
                설정 CI/CD 명
              </th>
              <th>
                <input v-model="name" placeholder="내용을 입력해주세요" >
              </th>
            </tr>
            <tr>
              <th>
                설정 내용
              </th>
              <th>
              </th>
            </tr>
          </tbody>
        </v-simple-table>
        
      <!-- </form> -->
    </div>
    <button>저장하기</button>
  </div>
</template>

<script>
export default {
  name: 'Create',
  data() {
    return {
      name: '',

    }
  }
}
</script>

<style scoped>
#create {
    text-align: center;
    width: 90%;
    margin: 0 auto;

}

#createForm {
    width: 100%;
}

#st {
    width: 100%;
    margin: 0 auto;
}
</style>