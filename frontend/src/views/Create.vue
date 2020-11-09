<template>
  <v-container id="create">
    <v-form v-model="valid">
      <div id="title">
        <h2>CI/CD 설정 생성</h2>
      </div>
      <v-simple-table dense>
        <template v-slot:default>
          <tbody>
            <tr>
              <td class="text-left font15" style="width: 8vw">
                설정 CI/CD 명
              </td>
              <td class="text-left font15" style="width: 62vw">
                <v-text-field v-model="name" :rules="[rules.required]" class="font15" placeholder="설정의 이름을 입력해주세요" hide-details="auto" dense filled shaped></v-text-field>
              </td>
            </tr>
            <tr>
              <td class="text-left font15" style="vertical-align: top"><br>설정 내용</td>
              <td class="text-left font15">
                <div id="content" class="font15">
                  <br>
                  <div style="margin: 0 2vw">
                    <h3>Jenkins</h3><br>
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
                    <h3>Git</h3><br>
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
                    <h3>Publish over SSH</h3><br>
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
                      ></v-text-field>
                      <h4>Key</h4><v-textarea
                      v-model="SSHServer.key"
                      :rules="[rules.required]"
                      background-color="blue-grey lighten-4"
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
                    <h3>Server</h3>
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
                            <h5 style="clear: both">Option</h5>
                            <v-text-field v-model="server.options[idx]" :rules="[rules.required]" style="width: 90%; float: left"></v-text-field>
                            <v-btn style="float: right; margin-right 1vw" @click="delOption(index, idx)">삭제</v-btn>
                          </div>
                          <v-btn style="float: right" @click="addOption(index)">+ JVM Option</v-btn>
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
                        <v-btn @click="removeServer(index, server.kind)">X</v-btn>
                      </div>
                      <div v-for="item in serverForms" :key="item">
                        <server-form v-on:update="saveServer"></server-form>
                      </div>
                    </div>
                    <v-btn v-if="serverForms.length==0 && servers.length<2" @click="toggleServerForm">+</v-btn>
                    <v-btn v-if="serverForms.length==1" @click="toggleServerForm">-</v-btn>
                  </div>
                  <br>
                </div>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-form>
    <div id="btn-area">
      <v-btn class="font15" :disabled="!valid" elevation="2" color="grey darken-3" style="color: white" @click="save">저장하기</v-btn>
    </div>
  </v-container>
</template>

<script>
// import CredentialForm from '@/components/CredentialForm.vue'
import ServerForm from '@/components/ServerForm.vue'
import axios from 'axios'

export default {
  name: 'Create',
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

      gitShow:false,
      show1: false,
      rules: {
        required: value => !!value || 'Required.',
        number: value => /^[0-9]+$/.test(value) || 'Only number.'
      },

      valid: false
    }
  },
  components: {
    // CredentialForm,
    ServerForm
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
    toggleServerForm: function() {
      if(this.serverForms.length==0) {
        this.serverForms.push('CredentialForm')
      } else {
        this.serverForms.pop()
      }
    },
    removeServer: function(index, kind) {
      if(kind=='Vue') {
        this.$store.state.disabledKind.splice(this.$store.state.disabledKind.indexOf('Vue'), 1)
      } else if(kind=='Spring_maven' || kind=='Spring_gradle') {
        this.$store.state.disabledKind.splice(this.$store.state.disabledKind.indexOf('Spring'), 1)
      }
      this.servers.splice(index, 1)
    },
    saveServer: function(server) {
      this.serverForms.pop()
      this.servers.push(server)
    },
    addOption(index) {
      this.servers[index].options.push("")
    },
    delOption(index, idx) {
      this.servers[index].options.splice(idx, 1)
    },
    save: function() {
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