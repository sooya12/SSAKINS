<template>
  <v-container id="container">
    <div>
      <headers></headers>
    </div>
    <div
        id="navigator"
        style="float:left; margin-top:16px; margin-right:20px; margin-left:20px;"
    >
      <navigator></navigator>
    </div>
    <div id="termsofuse">
      <div id="title">
        <h2>이용 약관</h2>
      </div>
      <br>
      <div>
        <v-textarea :value="content" outlined readonly auto-grow></v-textarea>
      </div>
    </div>
  </v-container>
</template>

<script>
import Navigator from "../components/Navigator";
import Header from "../components/Header";

export default {
  name: "TermsOfUse",
  data: () => ({
    content : '싸킨스(SSAKINS)는 이메일로 계정을 생성합니다. 다만, 실제 이메일의 소유주임을 확인하기 위해서 가입 당시 인증 절차를 거치게 됩니다. \n\n아래의 경우에는 계정 생성을 승인하지 않을 수 있습니다.\n\n다른 사람의 개인정보를 이용하여 계정을 생성하려 한 경우\n계정 생성시 필요한 정보를 입력하지 않거나 허위 정보를 입력한 경우\n싸킨스가 과거에 운영원칙 또는 법률 위반 등의 정당한 사유로 해당 계정을 삭제 또는 징계한 경우\n\n계정은 본인만 이용할 수 있고, 다른 사람에게 이용을 허락하거나 양도할 수 없습니다. 이메일이 바뀐 경우에는 문의(ssakinsauth@gmail.com)를 통해 새 이메일로 인증절차를 걸쳐 수정할 수 있습니다'
  }),
  components: {
    navigator: Navigator,
    headers: Header,
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

#termsofuse {
  text-align: center;
  width: 75%;
  margin: 0 auto;
  font-family: 'S-CoreDream-3Light';
  float: left;
}

#title {
  margin: 20px;
}

</style>