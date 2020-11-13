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

      <table>
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
            <td class="text-left font15" style="width: 10vw">
              사용 방법
            </td>
            <td class="text-left font15" style="width: 60vw; " >
              <div style="margin-top:5%;" v-if="more">
                <h1>시작하기</h1>
                <br>
                젠킨스를 설치할 서버에서 아래 코드를 복사한 후 실행합니다.<br>
                (만약, 서버에서 unzip을 실행할 수 없다면 <span style="background-color: #e0f2f1; font-weight: bold; padding-left: 5px; " > &nbsp; sudo apt install unzip &nbsp;</span> &nbsp;명령어를 통해 unzip을 먼저 설치해주세요.)<br>
                &nbsp;

                <br>

                <div>
                  <v-text-field
                      v-model="ssakins"
                      label="First Name"
                      solo
                      readonly
                      background-color="teal lighten-5"
                      class="overflow-y-auto"
                      style="width:80%;"
                      prepend-inner-icon="mdi-code-greater-than"
                  >

                    <template v-slot:append >
                        <i class="fad fa-paste" style="color:#004D40;margin-right: 4%; font-size: 30px; cursor:pointer" @click="copy()" ></i>
                    </template>
                  </v-text-field>
                  <v-snackbar
                      v-model="snackbar"
                      color="green"

                      :timeout="timeout"
                      centered

                  >
                    복사되었습니다.

                    <template v-slot:action="{ attrs }">
                      <v-btn
                          color="white"
                          text
                          v-bind="attrs"
                          @click="snackbar = false"
                      >
                        확인
                      </v-btn>
                    </template>
                  </v-snackbar>

                </div>
                설치가 완료되면 폴더 내 install.sh 파일을 <span style="background-color: #e0f2f1; font-weight: bold; padding-left: 5px; " > &nbsp; sh install.sh &nbsp;</span> &nbsp; 명령어로 실행시켜주세요. <br>
                (만약, 서버에 docker가 설치되어 있지 않다면  <v-hover> <span style="font-weight: bold; padding-left: 5px; color: blue; cursor:pointer " @click="installDockerPage()">  &nbsp;도커 설치페이지&nbsp;  </span> </v-hover>에서 운영체제에 맞는 docker를 먼저 설치해주세요.)<br><br>
                서버의 사양에 따라 5~10분 후 젠킨스 서버 설치가 완료됩니다.
                <br>
                <br>
                <br>
                <span style="color:red;">※ 다음의 경우, 정상적인 서비스 사용이 불가하니 참고바랍니다.</span> <br>
                <span style=" margin-left: 2%;">  &centerdot; &nbsp; 입력한 내용이 정확하지 않은 경우</span> <br>
                <span style=" margin-left: 2%;">  &centerdot; &nbsp; 설정한 포트 번호 중 하나 이상의 포트가 이미 사용중인 경우</span> <br>
                <span style=" margin-left: 2%;">  &centerdot; &nbsp; SSAKINS 서비스를 사용하여 두개 이상의 Jenkins 서버를 구동하는 경우</span>
                <br><br><br><br><br><br>
                <span style="color:red; margin-top: 0px;">위 주소를 타인과 공유하거나 공개된 곳에 게시하지 마세요.</span>
                <div style="width: 80%; text-align: center; margin-top: 5%;" @click="more=false">
                  설명 닫기
                </div>
              </div>

              <div style="margin-top:5%;" v-if="!more">
                <div>
                  <v-text-field
                      v-model="ssakins"
                      label="First Name"
                      solo
                      readonly
                      background-color="teal lighten-5"
                      class="overflow-y-auto"
                      style="width:80%;"
                      prepend-inner-icon="mdi-code-greater-than"
                  >

                    <template v-slot:append >
                      <i class="fad fa-paste" style="color:#004D40;margin-right: 4%; font-size: 30px; cursor:pointer" @click="copy()" ></i>
                    </template>
                  </v-text-field>
                  <v-snackbar
                      v-model="snackbar"
                      color="green"

                      :timeout="timeout"
                      centered

                  >
                    복사되었습니다.

                    <template v-slot:action="{ attrs }">
                      <v-btn
                          color="white"
                          text
                          v-bind="attrs"
                          @click="snackbar = false"
                      >
                        확인
                      </v-btn>
                    </template>
                  </v-snackbar>

                </div>

                <div style="width: 80%; text-align: center; margin-bottom: 3%;" @click="more=true">
                  자세히 보기
                </div>
              </div>


            </td>
          </tr>
          <tr>
            <td class="text-left font15" style="vertical-align: top"><br>설정 내용</td>
            <td class="text-left font15">
              <div id="content" class="font15">
                <br>
                <div style="margin: 0">
                  <h3><div class="icon-div"><i class="fab fa-jenkins" style="font-size: 28px; font-weight: bold; color: #004D40"></i></div>Jenkins</h3><br>
                  <div style="margin: auto; width: 90%">
                    <table style="width: 100%">
                      <tbody>
                        <tr style="margin: 0">
                          <td class="text-left" style="width: 15%"><h4>URL</h4></td>
                          <td class="text-left" style="width: 75%">{{ project.url }}</td>
                        </tr>
                          <td class="text-left" style="width: 15%"><h4>Port</h4></td>
                          <td class="text-left" style="width: 75%">{{ project.port }}</td>
                        <tr>
                        </tr>
                      </tbody>
                    </table>
                    <br><br>
                  </div>
                </div>
                <div style="margin: 0">
                  <h3><div class="icon-div"><i class="fab fa-git-alt" style="font-size: 28px; color: #004D40"></i></div>Git</h3><br>
                  <div style="margin: auto; width: 90%">
                     <table style="width: 100%">
                      <tbody>
                        <tr> 
                          <td class="text-left" style="width: 15%"><h4>Git URL</h4></td>
                          <td class="text-left" style="width: 75%">{{ project.git.giturl }}</td>
                        </tr>
                        <tr> 
                          <td class="text-left" style="width: 15%"><h4>name</h4></td>
                          <td class="text-left" style="width: 75%">{{ project.git.name }}</td>
                        </tr>
                        <tr> 
                          <td class="text-left" style="width: 15%"><h4>Password</h4></td>
                          <td class="text-left" style="width: 75%">{{ gitpass }}</td>
                        </tr>
                        <tr>
                          <td class="text-left" style="width: 15%"><h4>Kind</h4></td>
                          <td class="text-left" style="width: 75%">
                            <div v-if="project.git.gitKind=='gitlab'">GitLab</div>
                            <div v-if="project.git.gitKind=='github'">GitHub</div>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                    <br><br>
                  </div>
                </div>
                <div style="margin: 0">
                  <h3><div class="icon-div" style="background-color: #004D40"><i class="far fa-terminal" style="color: white; font-size: 15px"></i></div>Publish over SSH</h3><br>
                  <div style="margin: auto; width: 90%">
                    <table style="width: 100%">
                      <tbody>
                        <tr>
                          <td class="text-left" style="width: 15%"><h4>Hostname</h4></td>
                          <td class="text-left" style="width: 75%">{{ project.sshServer.hostName }}</td>
                        </tr>
                        <tr>
                          <td class="text-left" style="width: 15%"><h4>Username</h4></td>
                          <td class="text-left" style="width: 75%">{{ project.sshServer.userName }}</td>
                        </tr>
                        <tr>
                          <td class="text-left" style="width: 15%"><h4>Password</h4></td>
                          <td class="text-left" style="width: 75%">{{ sshpass }}</td>
                        </tr>
                        <tr>
                          <td class="text-left" colspan="2">
                            <h4>Key
                              <i v-if="!keyArrow" class="fal fa-angle-down" style="margin-left: 20px; font-size: 25px; vertical-align: top;" @click="keyToggle"></i>
                              <i v-if="keyArrow" class="fal fa-angle-up" style="margin-left: 20px; font-size: 25px; vertical-align: top;" @click="keyToggle"></i>
                            </h4>
                            </td>
                        </tr>
                        <tr v-if="keyArrow"> 
                          <td class="text-left" colspan="2">
                            <v-textarea
                            v-model="project.sshServer.key"
                            readonly
                            background-color="teal lighten-5"
                            ></v-textarea>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                    <br><br>
                  </div>
                </div>
                <div style="margin: 0">
                  <h3><div class="icon-div"><i class="fad fa-server" style="font-size: 25px; color: #004D40"></i></div>Server</h3> <br><br>
                  <div style="margin: auto; width: 90%">
                    <div v-for="(server, index) in project.servers" :key="index">
                        <div v-if="server.kind=='Spring'||server.kind=='Spring_maven'||server.kind=='Spring_gradle'">
                          <div style="font-size:17px; font-weight: bold"><div class="icon-div"><i class="fad fa-leaf" style="font-size: 22px; color: #004D40"></i></div>Spring</div>
                          <div style="margin: 0 1.7vw">
                            <table style="width: 100%">
                              <tbody>
                                <tr>
                                  <td class="text-left" style="width: 15%"><h4>port</h4></td>
                                  <td class="text-left" style="width: 75%">{{ server.port }}</td>
                                </tr>
                                <tr>
                                  <td class="text-left" style="width: 15%"><h4>pom.xml</h4></td>
                                  <td class="text-left" style="width: 75%">{{ server.info }}</td>
                                </tr>
                                <tr>
                                  <td class="text-left" style="width: 15%"><h4>build tool</h4></td>
                                  <td class="text-left" style="width: 75%">
                                    <div v-if="server.type=='Spring_maven'">Maven</div>
                                    <div v-if="server.type=='Spring_gradle'">Gradle</div>
                                  </td>
                                </tr>
                                <tr v-if="server.options.length!=0">
                                  <td class="text-left" colspan="2"><h4>JVM Options</h4></td>
                                </tr>
                                <tr v-for="(option,idx) in server.options" :key="idx">
                                  <td class="text-left" style="width: 15%"><h5>Option{{idx+1}}</h5></td>
                                  <td>{{ server.options[idx] }}</td>
                                </tr>
                              </tbody>
                            </table>
                          </div>
                        </div>
                        <div v-if="server.kind=='Vue'">
                          <div style="font-size:17px; font-weight: bold"><div class="icon-div"><i class="fab fa-vuejs" style="font-size: 22px; color: #004D40"></i></div>Vue</div>
                          <div style="margin: 0 1.8vw">
                            <table style="width: 100%">
                              <tbody>
                                <tr>
                                  <td class="text-left" style="width: 15%"><h4>port</h4></td>
                                  <td class="text-left" style="width: 75%">{{ server.port }}</td>
                                </tr>
                                <tr>
                                  <td class="text-left" style="width: 15%"><h4>pom.xml</h4></td>
                                  <td class="text-left" style="width: 75%">{{ server.info }}</td>
                                </tr>
                              </tbody>
                            </table>
                          </div>
                        </div>
                      <br><br>
                      </div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <div id="btn-area">
                <v-tooltip top color="#e0f2f1">
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn class="font15" elevation="2" color="#B2DFDB" style="margin-right: 2vw; font-weight: bold" v-bind="attrs" v-on="on" @click="goEdit">설정 가져가기</v-btn>
                    </template>
                    <span style="font-family: 'S-CoreDream-3Light'; color: black">현재 설정 정보를 가지고 새로운 설정을 생성할 수 있습니다.</span>
                  </v-tooltip>
                <v-btn class="font15" elevation="2" color="#004D40" style="color: white; margin-right: 2vw; font-weight: bold" @click="deleteProject">삭제하기</v-btn>
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
  props:{
    name:{
      type:String
    }
  },
  data() {
    return {
      userEmail: '',
      project: null,
      keyArrow: false,
      gitpass: null,
      sshpass: null,
      ssakins: "wget -o http://localhost:8080/download/email@naver.com && ",
      snackbar: false,
      timeout: 3000,
      more : false,
    }
  },
  methods: {
    goEdit() {
      this.$router.push({ name: "Edit", params: { name: this.name }})
    },
    keyToggle() {
      this.keyArrow=!this.keyArrow
    },
    copy () {
      let copyText = document.createElement('textarea')
      copyText.value=this.ssakins
      document.body.appendChild(copyText);
      copyText.select()
      copyText.setSelectionRange(0, 9999);
      try {
        document.execCommand('copy');
        this.snackbar=true
      } catch (err) {
        alert('Oops, unable to copy');
      }
      document.body.removeChild(copyText)
    },
    deleteProject() {
      let projectName=[];
      projectName.push(this.project.name)
      axios.post(this.$store.state.server + "project/delete/" + this.userEmail, projectName)
      .then(()=>{
        this.$router.push('/main')
      }).catch(err=>{
        console.log(err)
      })
    },
    installDockerPage(){
      window.open('https://docs.docker.com/engine/install/')

    }
  },
  components: {
    navigator: Navigator,
    headers: Header
  },
  mounted() {
    this.userEmail = sessionStorage.getItem('email')
    this.ssakins='wget '+this.$store.state.server+'download/'+this.userEmail+'/'+this.name+' -O && unzip -d ssakins ssakins.zip && rm ssakins.zip'
    axios.get(this.$store.state.server + 'project/' + this.userEmail + '/' + this.name)
    .then(res => {
      this.project = res.data
      this.gitpass = '*'.repeat(res.data.git.password.length)
      if(res.data.sshServer.password!=null && res.data.sshServer.password.length>0){
        this.sshpass = '*'.repeat(res.data.sshServer.password.length)
      }
      this.project.sshServer.key='*******YOU CAN\'T READ THIS.*******'
      console.log(this.project)
    }).catch(err => {
      console.log(err)
    })

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

h3 {
  font-family: 'S-CoreDream-6Bold';
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

/* #content {
  background: #EEEEEE;
} */

#btn-area {
  margin: 20px auto;
}

.icon-div {
  width: 27px;
  height: 27px;
  border-radius:15%;
  float: left;
  margin-right: 10px;
  text-align: center;
  vertical-align:middle;
}

</style>