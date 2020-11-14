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
    <div id="edit">
        <v-form v-model="valid">
        <div id="title">
          <h2>CI/CD 설정 생성</h2>
        </div>
        <table dense>
          <tbody>
            <tr>
              <td class="text-left font15" style="width: 10vw">
                설정 CI/CD 명
              </td>
              <td class="text-left font15" style="width: 60vw">
                <v-tooltip bottom color="#E53935">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                    v-model="project.name"
                    :rules="[rules.required, rules.title]"
                    class="font15"
                    placeholder="설정의 이름은 공백없이 영어로 입력해 주세요 (중복 확인 必)"
                    hide-details="auto"
                    dense
                    shaped
                    style="width: 80%; margin: 0 auto"
                    v-bind="attrs" v-on="on"
                    @change="reCheck"
                    >
                      <template v-slot:append >
                        <span><i class="fad fa-check-double" style="color: #004D40" @click="checkDuplication"></i></span>
                      </template>
                    </v-text-field>
                  </template>
                  <span style="font-family: 'S-CoreDream-3Light';">우측의 체크 버튼을 눌러 중복 확인을 해 주세요.</span>
                </v-tooltip>
                <v-snackbar
                    v-model="snackbar"
                    color="green"
                    :timeout="timeout"
                    centered
                  >
                  <div v-if="check">사용 가능한 이름입니다.</div>
                  <div v-if="!check">새로운 이름을 입력해 주세요.</div>
                    <template v-slot:action="{ attrs }">
                      <v-btn
                          color="white"
                          text
                          v-bind="attrs"
                          @click="snackbar=false"
                      >
                        확인
                      </v-btn>
                    </template>
                  </v-snackbar>
                  <v-snackbar
                    v-model="dupcheck"
                    color="green"
                    :timeout="timeout"
                    centered
                  >
                  설정명 중복체크를 해 주세요.
                    <template v-slot:action="{ attrs }">
                      <v-btn
                          color="white"
                          text
                          v-bind="attrs"
                          @click="dupcheck=false"
                      >
                        확인
                      </v-btn>
                    </template>
                  </v-snackbar>
              </td>
            </tr>
            <tr>
              <td class="text-left font15" style="vertical-align: top">
                <br />설정 내용
              </td>
              <td class="text-left font15">
                <div id="content" class="font15">
                  <br />
                  <div style="margin: 0 2vw">
                    <h3>
                      <div class="icon-div"><i class="fab fa-jenkins" style="font-size: 28px; font-weight: bold; color: #004D40"></i></div>
                      Jenkins
                      <v-tooltip right color="#e0f2f1">
                        <template v-slot:activator="{ on, attrs }">
                          <i class="fad fa-question-circle" style="user-select: auto; margin-left: 10px; color: #004D40" v-bind="attrs" v-on="on"></i>
                        </template>
                        <span style="font-family: 'S-CoreDream-3Light'; color: black">젠킨스가 설치될 서버 정보 입력</span>
                      </v-tooltip>
                    </h3><br>
                    <div style="margin: auto; width: 85%">
                      <h4>URL</h4><v-text-field
                      v-model="project.url"
                      :rules="[rules.required, rules.http, rules.space, rules.info]"
                      placeholder="ex) http://k3a201.p.ssafy.io"
                      ></v-text-field>
                      <h4>Port</h4><v-text-field
                      v-model="project.port" 
                      :rules="[rules.required, rules.number, rules.space]"
                      placeholder="포트 번호를 입력해 주세요"
                      ></v-text-field>
                      <br />
                    </div>
                  </div>
                  <div style="margin: 0 2vw">
                    <h3>
                      <div class="icon-div"><i class="fab fa-git-alt" style="font-size: 28px; color: #004D40"></i></div>
                      Git
                      <v-tooltip right color="#e0f2f1">
                        <template v-slot:activator="{ on, attrs }">
                          <i class="fad fa-question-circle" style="user-select: auto; margin-left: 10px; color: #004D40" v-bind="attrs" v-on="on"></i>
                        </template>
                        <span style="font-family: 'S-CoreDream-3Light'; color: black">젠킨스에 연결할 Git Repository 정보 입력</span>
                      </v-tooltip>
                      </h3><br>
                    <div style="margin: auto; width: 85%">
                      <h4>Git URL</h4><v-text-field
                      v-model="project.git.giturl"
                      :rules="[rules.required, rules.korean, rules.git, rules.space]"
                      placeholder="ex) https://lab.ssafy.com/s03-final/s03p31a201.git"
                      ></v-text-field>
                      <h4>Username</h4><v-text-field
                      v-model="project.git.name"
                      :rules="[rules.required, rules.korean, rules.space]"
                      placeholder="해당 Git Repository의 Username을 입력해주세요"
                      ></v-text-field>
                      <h4>Password</h4><v-text-field
                      v-model="project.git.password"
                      placeholder="해당 Git Repository의 Password를 입력해주세요"
                      :append-icon="gitShow ? 'mdi-eye' : 'mdi-eye-off'" 
                      :rules="[rules.required, rules.korean, rules.space]" 
                      :type="gitShow ? 'text' : 'password'"
                      @click:append="gitShow = !gitShow"
                      ></v-text-field>
                      <v-radio-group v-model="project.git.gitKind" row :rules="[rules.required]">
                        <v-radio label="GitLab" value="gitlab"></v-radio>
                        <v-radio label="GitHub" value="github"></v-radio>
                      </v-radio-group>
                      <br>
                    </div>
                  </div>
                  <div style="margin: 0 2vw">
                    <h3>
                      <div class="icon-div" style="background-color: #004D40"><i class="far fa-terminal" style="color: white; font-size: 15px"></i></div>
                      Publish over SSH
                      <v-tooltip right color="#e0f2f1">
                        <template v-slot:activator="{ on, attrs }">
                          <i class="fad fa-question-circle" style="user-select: auto; margin-left: 10px; color: #004D40" v-bind="attrs" v-on="on"></i>
                        </template>
                        <span style="font-family: 'S-CoreDream-3Light'; color: black">배포할 서버 정보 입력</span>
                      </v-tooltip>
                      </h3><br>
                    <div style="margin: auto; width: 85%">
                      <h4>Server Hostname</h4><v-text-field
                      v-model="project.sshServer.hostName"
                      :rules="[rules.required, rules.space]"
                      placeholder="ex) k3a201.p.ssafy.io, 127.0.0.1"
                      ></v-text-field>
                      <h4>Server Username</h4><v-text-field
                      v-model="project.sshServer.userName"
                      :rules="[rules.required, rules.korean, rules.space]"
                      placeholder="ex) root, ubuntu"
                      ></v-text-field>
                      <h4>Server Password</h4><v-text-field
                      v-model="project.sshServer.password"
                      :append-icon="sshShow ? 'mdi-eye' : 'mdi-eye-off'" 
                      :type="sshShow ? 'text' : 'password'"
                      placeholder="(option)"
                      :rules="[rules.korean, rules.space]"
                      @click:append="sshShow = !sshShow"
                      ></v-text-field>
                      <h4>Key</h4><v-textarea
                      v-model="project.sshServer.key"
                      :rules="[rules.required]"
                      placeholder="서버 pem.key의 내용을 입력해 주세요."
                      background-color="teal lighten-5"
                      ></v-textarea>
                    </div>
                  </div>
                  <div style="margin: 2vw">
                    <h3>
                      <div class="icon-div"><i class="fad fa-server" style="font-size: 25px; color: #004D40"></i></div>
                      Server
                      <v-tooltip right color="#e0f2f1">
                        <template v-slot:activator="{ on, attrs }">
                          <i class="fad fa-question-circle" style="user-select: auto; margin-left: 10px; color: #004D40" v-bind="attrs" v-on="on"></i>
                        </template>
                        <span style="font-family: 'S-CoreDream-3Light'; color: black">사용할 Front/Back 서버 정보 입력</span>
                      </v-tooltip>
                    </h3>
                    <div style="margin: 0 auto; width: 85%">
                      <div style="margin-top: 1vw; margin-bottom: 1vw" v-for="(server, index) in project.servers" :key="index">
                        <div v-if="server.kind=='Spring_maven' || server.kind=='Spring_gradle'">
                          <p style="font-size:1.3rem;">Spring</p>
                          <h4>port</h4><v-text-field
                          v-model="server.port"
                          :rules="[rules.required, rules.number, rules.space]"
                          placeholder="포트 번호를 입력해 주세요"
                          ></v-text-field>
                          <h4>pom.xml</h4><v-text-field
                          v-model="server.info"
                          :rules="[rules.required, rules.space, rules.info, rules.korean]"
                          placeholder="pom.xml의 경로를 입력해 주세요 (마지막'/'제외)"
                          ></v-text-field>
                          <v-radio-group
                            v-model="server.kind"
                            row
                            :rules="[rules.required]"
                          >
                            <v-radio
                              label="Maven"
                              value="Spring_maven"
                            ></v-radio>
                            <v-radio
                              label="Gradle"
                              value="Spring_gradle"
                            ></v-radio>
                          </v-radio-group>
                          <h4 v-if="server.options.length!=0">JVM Options</h4><br>
                          <div v-for="(option,idx) in server.options" :key="idx">
                            <h5 style="clear: both; text-align: left">Option</h5>
                            <v-text-field
                            v-model="server.options[idx]"
                            :rules="[rules.required, rules.korean, rules.space]"
                            placeholder='ex) -Dspring.profiles.active="live"'
                            style="width: 90%; float: left; padding-top: 2px"
                            ></v-text-field>
                            <div style="float: right; margin-right: 2vw;">
                              <i
                              class="far fa-cut"
                              style="font-size: 25px; color: #D32F2F"
                              @click="delOption(index, idx)"
                              ></i>
                            </div>
                          </div>
                           <v-btn style="float: right; color: white " color="teal darken-2" depressed  @click="addOption(index)">+ JVM Option</v-btn>
                        </div>
                        <div v-if="server.kind=='Vue'">
                          <p style="font-size:1.3rem;">Vue</p>
                          <h4>port</h4><v-text-field
                          v-model="server.port"
                          :rules="[rules.required, rules.number, rules.space]"
                          placeholder="포트 번호를 입력해 주세요"
                          readonly
                          ></v-text-field>
                          <h4>package.json</h4><v-text-field
                          v-model="server.info"
                          :rules="[rules.required, rules.space, rules.info, rules.korean]"
                          placeholder="package.json의 경로를 입력해 주세요 (마지막'/'제외)"
                          ></v-text-field>
                        </div>
                        <i id="remove-btn" class="fad fa-trash-alt" @click="removeServer(index, server.kind)"></i>
                       <br><br>
                      </div>
                      <div v-for="item in serverForms" :key="item">
                        <server-form v-on:update="saveServer"
                        v-bind="{serverKind:serverKind, disabledKind:disabledKind}"></server-form>
                      </div>
                    </div>
                    <br>
                    <i
                    class="fad fa-plus"
                    v-if="serverForms.length==0 && project.servers.length<2"
                    @click="toggleServerForm"
                    style="margin-left: 20px; font-size: 30px; color: #004D40"
                    ></i>
                    <i
                    class="fad fa-minus"
                    v-if="serverForms.length==1"
                    @click="toggleServerForm"
                    style="margin-left: 20px; font-size: 30px; color: #004D40"
                    ></i>
                  </div>
                  <br />
                </div>
              </td>
            </tr>
            <tr>
              <td>
              </td>
              <td>
                <div id="btn-area">
                  <v-btn class="font15" :disabled="!valid" elevation="2" color="#004D40" style="color: white; font-weight: bold" @click="save">저장하기</v-btn>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </v-form>
    </div>
  </v-container>
</template>

<script>
import ServerForm from '@/components/ServerForm.vue'
import Navigator from "../components/Navigator";
import Header from "../components/Header";
import axios from 'axios'

export default {
  name: 'Edit',
  props:{
    name:{
      type:String
    }
  },
  data() {
    return {
      userEmail: null,
      project: null,
      tempname: null,

      serverForms: [],

      gitShow: false,
      sshShow: false,
      rules: {
        required: value => !!value || 'Required.',
        number: value => /^[0-9]+$/.test(value) || 'Only Number.',
        space: value => /^[^\s]*$/.test(value) || 'No Space',
        git: value => /^(http(s)?:\/\/).+\.git$/.test(value) || 'Start with http:// or https://. End with .git',
        http: value => /^(http(s)?:\/\/)/.test(value) || 'Start with http:// or https://',
        title: value => /^[a-zA-Z]*([-_]?([a-zA-Z]))*$/.test(value) || "Start/End with Alphabet. You can use Alphabet or '-' or '_'",
        info: value => /[^/]$/.test(value) || "Please remove last'/'",
        korean: value => /^[^ㄱ-ㅎㅏ-ㅣ가-힣]*$/.test(value) || 'Please remove Korean'
      },

      valid: false,
      check: false,
      timeout: 2000,
      snackbar: false,
      dupcheck: false,

      serverKind: [
          'Spring',
          'Vue',
          'Django(준비중)',
          'Flask(준비중)',
          'Express(준비중)',
          'React(준비중)',
        ],
        disabledKind: [
          'Django(준비중)',
          'Flask(준비중)',
          'Express(준비중)',
          'React(준비중)',
        ],
    }
  },
  components: {
    ServerForm,
    navigator: Navigator,
    headers: Header
  },
  mounted() {
    this.userEmail = sessionStorage.getItem('email')
    axios.get(this.$store.state.server + 'project/' + this.userEmail + '/' + this.name)
    .then(res => {
      this.project=res.data
      this.project.servers.forEach(el => {
        if(el.kind=='Spring_maven' || el.kind=='Spring_gradle') {
          this.disabledKind.push('Spring')
        } else if(el.kind=='Vue') {
          this.disabledKind.push('Vue')
        }
      });
      console.log(this.project)
    }).catch(err => {
      console.log(err)
    })
  },
  methods: {
    toggleServerForm() {
      if(this.serverForms.length==0) {
        this.serverForms.push('ServerForm')
      } else {
        this.serverForms.pop();
      }
    },
    removeServer(index, kind) {
      if(kind=='Vue') {
        this.disabledKind.splice(this.disabledKind.indexOf('Vue'), 1)
      } else if(kind=='Spring_maven' || kind=='Spring_gradle') {
        this.disabledKind.splice(this.disabledKind.indexOf('Spring'), 1)
      }
      this.project.servers.splice(index, 1)
    },
    saveServer(server) {
      this.serverForms.pop()
      this.project.servers.push(server)
    },
    addOption(index) {
      this.project.servers[index].options.push("")
    },
    delOption(index, idx) {
      this.project.servers[index].options.splice(idx, 1)
    },
    checkDuplication() {
      axios.get(this.$store.state.server + 'project/check/' + this.userEmail + '/' + this.project.name)
      .then(res=>{
        if(res.data=='ok') {
          this.check=true
          this.tempname=this.project.name
        }
        this.snackbar=true
        console.log(res)
      }).catch(err=>{
        if(err.response.data=='duplication') {
          this.check=false
        }
        this.snackbar=true
        console.dir(err)
      })
    },
    reCheck() {
      if(this.project.name!=this.tempname) {
        this.check=false
      }
    },
    save() {
      if(!this.check) {
        this.dupcheck=true
      } else {
        this.project.git.id=this.project.git.gitKind
        axios.post(this.$store.state.server + 'project/save', {
          userEmail: this.userEmail,
          project : this.project
        }).then(res=>{
          console.log(res)
          this.$router.push({name: 'Detail', params: {name: this.project.name}})
        }).catch(err=>{
          console.log(err)
        })
      }
    }
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
  width: 75%;
  margin: 0 auto;
  font-family: 'S-CoreDream-3Light';
  float: left;
}

table {
  table-layout: fixed;
  margin: 0 auto;
  padding: 0;
}

#title {
  margin: 20px;
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

#remove-btn {
  color: #004D40;
  font-size: 30px;
  clear: both;
  margin-left: 23px;
  background-color: transparent !important;
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