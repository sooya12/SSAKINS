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
    <div id="create">
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
                <v-text-field v-model="name" :rules="[rules.required]" class="font15" placeholder="설정의 이름을 입력해주세요" hide-details="auto" dense shaped></v-text-field>
              </td>
            </tr>
            <tr>
              <td class="text-left font15" style="vertical-align: top"><br>설정 내용</td>
              <td class="text-left font15">
                <div id="content" class="font15">
                  <br>
                  <div style="margin: 0 2vw">
                    <h3><div class="icon-div"><i class="fab fa-jenkins" style="font-size: 28px; font-weight: bold; color: #004D40"></i></div>Jenkins</h3><br>
                    <div style="margin: auto; width: 85%">
                      <h4>URL</h4><v-text-field
                      v-model="url"
                      :rules="[rules.required]"
                      ></v-text-field>
                      <h4>Port</h4><v-text-field
                      v-model="port" 
                      :rules="[rules.required, rules.number]"
                      ></v-text-field>
                      <br>
                    </div>
                  </div>
                  <div style="margin: 0 2vw">
                    <h3><div class="icon-div"><i class="fab fa-git-alt" style="font-size: 28px; color: #004D40"></i></div>Git</h3><br>
                    <div style="margin: auto; width: 85%">
                      <h4>Git URL</h4><v-text-field
                      v-model="git.giturl"
                      :rules="[rules.required]"
                      ></v-text-field>
                      <h4>ID</h4><v-text-field
                      v-model="git.id"
                      :rules="[rules.required]"
                      ></v-text-field>
                      <h4>Username</h4><v-text-field
                      v-model="git.username"
                      :rules="[rules.required]"
                      ></v-text-field>
                      <h4>Password</h4><v-text-field
                      v-model="git.password"
                      :append-icon="gitShow ? 'mdi-eye' : 'mdi-eye-off'" 
                      :rules="[rules.required]" 
                      :type="gitShow ? 'text' : 'password'"
                      @click:append="gitShow = !gitShow"
                      ></v-text-field>
                      <v-radio-group v-model="git.gitKind" row :rules="[rules.required]">
                        <v-radio label="GitLab" value="gitlab"></v-radio>
                        <v-radio label="GitHub" value="github"></v-radio>
                      </v-radio-group>
                      <br>
                    </div>
                  </div>
                  <div style="margin: 0 2vw">
                    <h3><div class="icon-div" style="background-color: #004D40"><i class="far fa-terminal" style="color: white; font-size: 15px"></i></div>Publish over SSH</h3><br>
                    <div style="margin: auto; width: 85%">
                      <h4>Server Hostname</h4><v-text-field
                      v-model="SSHServer.hostName"
                      :rules="[rules.required]"
                      ></v-text-field>
                      <h4>Server Username</h4><v-text-field
                      v-model="SSHServer.userName"
                      :rules="[rules.required]"
                      ></v-text-field>
                      <h4>Server Remote Directory</h4><v-text-field
                      v-model="SSHServer.remoteDirectory"
                      :rules="[rules.required]"
                      ></v-text-field>
                      <h4>Server Password</h4><v-text-field
                      v-model="SSHServer.password"
                      :append-icon="sshShow ? 'mdi-eye' : 'mdi-eye-off'" 
                      :rules="[rules.required]" 
                      :type="sshShow ? 'text' : 'password'"
                      @click:append="sshShow = !sshShow"
                      ></v-text-field>
                      <h4>Key</h4><v-textarea
                      v-model="SSHServer.key"
                      :rules="[rules.required]"
                      background-color="teal lighten-5"
                      ></v-textarea>
                    </div>
                  </div>
                  <!-- <div style="margin: 2vw">
                    <h3>Credential</h3>
                    <div style="margin: 0 auto; width: 85%">
                      <div style="margin-top: 1vw; margin-bottom: 1vw" v-for="(credential, index) in credentials" :key="index">
                        <div v-if="credential.kind=='Username_with_password'">
                          <p style="font-size:1.3rem">Username_with_password</p>
                          <h4>ID</h4><v-text-field
                          v-model="credential.id"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <h4>Username</h4><v-text-field
                          v-model="credential.username"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <h4>Password</h4><v-text-field
                          v-model="credential.password"
                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" 
                          :rules="[rules.required]" 
                          :type="show1 ? 'text' : 'password'"
                          @click:append="show1 = !show1"
                          ></v-text-field>
                          <v-radio-group v-model="credential.isHub" row :rules="[rules.required]">
                            <v-radio label="GitLab" value="false"></v-radio>
                            <v-radio label="GitHub" value="true"></v-radio>
                          </v-radio-group>
                        </div>
                        <div v-if="credential.kind=='GitHub_App'">
                          <p style="font-size:1.3rem">GitHub_App</p>
                          <h4>ID</h4><v-text-field
                          v-model="credential.id"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <h4>App ID</h4><v-text-field
                          v-model="credential.appID"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <h4>Key</h4><v-textarea
                          v-model="credential.key" 
                          :rules="[rules.required]"
                          background-color="blue-grey lighten-4"
                          ></v-textarea>
                        </div>
                        <div v-if="credential.kind=='GitLap_API_token'">
                          <p style="font-size:1.3rem">GitLap_API_token</p>
                          <h4>ID</h4><v-text-field
                          v-model="credential.id"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <h4>API token</h4><v-text-field
                          v-model="credential.apiKey"
                          :rules="[rules.required]"
                          ></v-text-field>
                        </div>
                        <div v-if="credential.kind=='SSH_Username_with_private_key'">
                          <p style="font-size:1.3rem">SSH_Username_with_private_key</p>
                          <h4>ID</h4><v-text-field
                          v-model="credential.id"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <h4>Username</h4><v-text-field
                          v-model="credential.username"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <h4>Key</h4><v-textarea
                          v-model="credential.key"
                          :rules="[rules.required]"
                          background-color="blue-grey lighten-4"
                          ></v-textarea>
                          <h4>Passphrase</h4><v-text-field
                          v-model="credential.key"
                          :rules="[rules.required]"
                          ></v-text-field>
                        </div>
                        <v-btn @click="removeCredential(index)">X</v-btn>
                      </div>
                      <div v-for="item in credentialForms" :key="item">
                        <credential-form v-on:update="saveCredential"></credential-form>
                      </div>
                    </div>
                    <v-btn v-if="credentialForms.length==0" @click="toggleCredentialForm">+</v-btn>
                    <v-btn v-if="credentialForms.length==1" @click="toggleCredentialForm">-</v-btn>
                  </div> -->

                  <div style="margin: 2vw">
                    <h3><div class="icon-div"><i class="fad fa-server" style="font-size: 25px; color: #004D40"></i></div>Server</h3>
                    <div style="margin: 0 auto; width: 85%">
                      <div style="margin-top: 1vw; margin-bottom: 1vw" v-for="(server, index) in servers" :key="index">
                        <div v-if="server.kind=='Spring_maven' || server.kind=='Spring_gradle'">
                          <p style="font-size:1.3rem;">Spring</p>
                          <h4>port</h4><v-text-field
                          v-model="server.port"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <h4>pom.xml</h4><v-text-field
                          v-model="server.info"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <v-radio-group v-model="server.kind" row :rules="[rules.required]">
                            <v-radio label="Maven" value="Spring_maven"></v-radio>
                            <v-radio label="Gradle" value="Spring_gradle"></v-radio>
                          </v-radio-group>
                          <h4 v-if="server.options.length!=0">JVM Options</h4><br>
                          <div v-for="(option,idx) in server.options" :key="idx">
                            <h5 style="clear: both; text-align: left">Option</h5>
                            <v-text-field v-model="server.options[idx]" :rules="[rules.required]" style="width: 90%; float: left; padding-top: 2px"></v-text-field>
                            <div style="float: right; margin-right: 2vw;">
                              <i
                              class="far fa-cut"
                              style="font-size: 25px; color: #D32F2F"
                              @click="delOption(index, idx)"
                              ></i>
                            </div>
                            <!-- <v-icon style="float: right; margin-right: 1vw" color="red lighten-1" large @click="delOption(index, idx)">mdi-tooltip-remove-outline</v-icon> -->
                          </div>
                           <v-btn style="float: right; color: white " color="teal darken-2" depressed  @click="addOption(index)">+ JVM Option</v-btn>
                        </div>
                        <div v-if="server.kind=='Vue'">
                          <p style="font-size:1.3rem;">Vue</p>
                          <h4>port</h4><v-text-field
                          v-model="server.port"
                          :rules="[rules.required]"
                          ></v-text-field>
                          <h4>package.json</h4><v-text-field
                          v-model="server.info"
                          :rules="[rules.required]"
                          ></v-text-field>
                        </div>
                        <i id="remove-btn" class="fad fa-trash-alt" @click="removeServer(index, server.kind)"></i>
                        
                        <!-- <v-btn @click="removeServer(index, server.kind)">X</v-btn> -->
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
                    v-if="serverForms.length==0 && servers.length<2"
                    @click="toggleServerForm"
                    style="margin-left: 20px; font-size: 30px; color: #004D40"
                    ></i>
                    <i
                    class="fad fa-minus"
                    v-if="serverForms.length==1"
                    @click="toggleServerForm"
                    style="margin-left: 20px; font-size: 30px; color: #004D40"
                    ></i>
                    <!-- <v-icon v-if="serverForms.length==0 && servers.length<2" @click="toggleServerForm" large>mdi-plus</v-icon> -->
                    <!-- <v-icon v-if="serverForms.length==1" @click="toggleServerForm" large>mdi-toy-brick-remove-outline</v-icon> -->

                  </div>
                  <br>
                </div>
              </td>
            </tr>
            <tr>
              <td>
              </td>
              <td>
                <div id="btn-area">
                  <v-btn class="font15" :disabled="!valid" elevation="2" color="#004D40" style="color: white;" @click="save">저장하기</v-btn>
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
// import CredentialForm from '@/components/CredentialForm.vue'
import ServerForm from '@/components/ServerForm.vue'
import Navigator from "../components/Navigator";
import Header from "../components/Header";
import axios from 'axios'

export default {
  name: "Create",
  data() {
    return {
      name: 'SSAKINS 1차 CI/CD 설정',
      url: null,
      port: null,
      git: {
        gitKind: null,
        giturl: null,
        id: null,
        username: null,
        password: null
      },
      // credentials: [],
      // credentialForms: [],
      SSHServer: {
        key: null,
        hostName: null,
        userName: null,
        remoteDirectory: null,
        password: null
      },
      servers: [],
      serverForms: [],

      gitShow: false,
      sshShow: false,
      rules: {
        required: value => !!value || 'Required.',
        number: value => /^[0-9]+$/.test(value) || 'Only number.'
      },

      valid: false,


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
    // CredentialForm,
    ServerForm,
    navigator: Navigator,
    headers: Header,
  },
  methods: {
    // toggleCredentialForm: function() {
    //   if(this.credentialForms.length==0) {
    //     this.credentialForms.push('CredentialForm')
    //   } else {
    //     this.credentialForms.pop()
    //   }
    // },
    // removeCredential: function(index) {
    //   this.credentials.splice(index, 1)
    // },
    // saveCredential: function(credential) {
    //   this.credentialForms.pop()
    //   this.credentials.push(credential)
    // },
    toggleServerForm() {
      if(this.serverForms.length==0) {
        this.serverForms.push('CredentialForm')
      } else {
        this.serverForms.pop()
      }
    },
    removeServer(index, kind) {
      if(kind=='Vue') {
        this.disabledKind.splice(this.disabledKind.indexOf('Vue'), 1)
      } else if(kind=='Spring_maven' || kind=='Spring_gradle') {
        this.disabledKind.splice(this.disabledKind.indexOf('Spring'), 1)
      }
      this.servers.splice(index, 1)
    },
    saveServer(server) {
      this.serverForms.pop()
      this.servers.push(server)
    },
    addOption(index) {
      this.servers[index].options.push("")
    },
    delOption(index, idx) {
      this.servers[index].options.splice(idx, 1)
    },
    save() {
      axios.post(this.$store.state.server + 'project/save', {
        project :{
          name: this.name,
          url: this.url,
          port: this.port,
          git: this.git,
          SSHServer: this.SSHServer,
          servers: this.servers
        }
      }).then(res=>{
        console.log(this.credentials)
        console.log(this.servers)
        console.log(res)
      }).catch(err=>{
        console.log(err)
      })
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

#create {
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

/* #content {
  background: #E8F5E9;
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
