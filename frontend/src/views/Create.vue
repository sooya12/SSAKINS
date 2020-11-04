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
                    URL<v-text-field
                    v-model="url"
                    :rules="[rules.required]"
                    ></v-text-field>
                    Port<v-text-field
                    v-model="port" 
                    :rules="[rules.required, rules.number]"
                    ></v-text-field>
                    <br>
                  </div>
                  <div style="margin: 0 2vw">
                    <h3>Git</h3>
                    <v-radio-group v-model="gitKind" row :rules="[rules.required]">
                      <v-radio label="GitLab" value="gitlab"></v-radio>
                      <v-radio label="GitHub" value="github"></v-radio>
                    </v-radio-group>
                    Git URL<v-text-field
                    v-model="giturl"
                    :rules="[rules.required]"
                    ></v-text-field>
                  </div> 
                  <div style="margin: 2vw">
                    <h3>Credential</h3>
                    <div style="margin: 2vw;" v-for="(credential, index) in credentials" :key="index">
                      <div v-if="credential.kind=='Username_with_password'" style="font-weight:bold">
                        <p style="font-size:1.3rem">Username_with_password</p>
                        ID<v-text-field
                        v-model="credential.id"
                        :rules="[rules.required]"
                        ></v-text-field>
                        Username<v-text-field
                        v-model="credential.username"
                        :rules="[rules.required]"
                        ></v-text-field>
                        Password<v-text-field
                        v-model="credential.password"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" 
                        :rules="[rules.required]" 
                        :type="show1 ? 'text' : 'password'"
                        @click:append="show1 = !show1"
                        ></v-text-field>
                      </div>
                      <div v-if="credential.kind=='GitHub_App'" style="font-weight:bold">
                        <p style="font-size:1.3rem">GitHub_App</p>
                        ID<v-text-field
                        v-model="credential.id"
                        :rules="[rules.required]"
                        ></v-text-field>
                        App ID<v-text-field
                        v-model="credential.appID"
                        :rules="[rules.required]"
                        ></v-text-field>
                        Key<v-textarea
                        v-model="credential.key" 
                        :rules="[rules.required]"
                        background-color="blue-grey lighten-4"
                        ></v-textarea>
                      </div>
                      <div v-if="credential.kind=='GitLap_API_token'" style="font-weight:bold">
                        <p style="font-size:1.3rem">GitLap_API_token</p>
                        ID<v-text-field
                        v-model="credential.id"
                        :rules="[rules.required]"
                        ></v-text-field>
                        API token<v-text-field
                        v-model="credential.apiKey"
                        :rules="[rules.required]"
                        ></v-text-field>
                      </div>
                      <div v-if="credential.kind=='SSH_Username_with_private_key'" style="font-weight:bold">
                        <p style="font-size:1.3rem">SSH_Username_with_private_key</p>
                        ID<v-text-field
                        v-model="credential.id"
                        :rules="[rules.required]"
                        ></v-text-field>
                        Username<v-text-field
                        v-model="credential.username"
                        :rules="[rules.required]"
                        ></v-text-field>
                        Key<v-textarea
                        v-model="credential.key"
                        :rules="[rules.required]"
                        background-color="blue-grey lighten-4"
                        ></v-textarea>
                        Passphrase<v-text-field
                        v-model="credential.key"
                        :rules="[rules.required]"
                        ></v-text-field>
                      </div>
                      <v-btn @click="removeCredential(index)">X</v-btn>
                    </div>
                    <div v-for="item in credentialForms" :key="item">
                      <credential-form v-on:update="saveCredential"></credential-form>
                    </div>
                    <v-btn v-if="credentialForms.length==0" @click="toggleCredentialForm">+</v-btn>
                    <v-btn v-if="credentialForms.length==1" @click="toggleCredentialForm">-</v-btn>
                  </div>

                  <div style="margin: 2vw">
                    <h3>Server</h3>
                    <div style="margin: 2vw;" v-for="(server, index) in servers" :key="index">
                      <div v-if="server.kind=='Spring_maven' || server.kind=='Spring_gradle'" style="font-weight:bold">
                        <p style="font-size:1.3rem;">Spring</p>
                        port<v-text-field
                        v-model="server.port"
                        :rules="[rules.required]"
                        ></v-text-field>
                        pom.xml<v-text-field
                        v-model="server.info"
                        :rules="[rules.required]"
                        ></v-text-field>
                        <v-radio-group v-model="server.kind" row :rules="[rules.required]">
                          <v-radio label="Maven" value="Spring_maven"></v-radio>
                          <v-radio label="Gradle" value="Spring_gradle"></v-radio>
                        </v-radio-group>
                      </div>
                      <div v-if="server.kind=='Vue'" style="font-weight:bold">
                        <p style="font-size:1.3rem;">Vue</p>
                        port<v-text-field
                        v-model="server.port"
                        :rules="[rules.required]"
                        ></v-text-field>
                        package.json<v-text-field
                        v-model="server.info"
                        :rules="[rules.required]"
                        ></v-text-field>
                      </div>
                      <v-btn @click="removeServer(index)">X</v-btn>
                    </div>
                    <div v-for="item in serverForms" :key="item">
                      <server-form v-on:update="saveServer"></server-form>
                    </div>
                    <v-btn v-if="serverForms.length==0" @click="toggleServerForm">+</v-btn>
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
// import Spring from '@/components/Spring.vue'
// import Vuejs from '@/components/Vuejs.vue'
import CredentialForm from '@/components/CredentialForm.vue'
import ServerForm from '@/components/ServerForm.vue'
import axios from 'axios'

export default {
  name: 'Create',
  data() {
    return {
      name: 'SSAKINS 1차 CI/CD 설정',
      checkSpring: false,
      checkVuejs: false,
      url: null,
      port: null,
      gitKind: null,
      giturl: null,
      credentials: [],
      credentialForms: [],

      servers: [],
      serverForms: [],

      show1: false,
      rules: {
        required: value => !!value || 'Required.',
        number: value => /^[0-9]+$/.test(value) || 'Only number.'
      },

      valid: false
    }
  },
  components: {
    // Spring,
    // Vuejs,
    CredentialForm,
    ServerForm
  },
  methods: {
    toggleCredentialForm: function() {
      if(this.credentialForms.length==0) {
        this.credentialForms.push('CredentialForm')
      } else {
        this.credentialForms.pop()
      }
    },
    removeCredential: function(index) {
      this.credentials.splice(index, 1)
    },
    saveCredential: function(credential) {
      this.credentialForms.pop()
      this.credentials.push(credential)
    },
    toggleServerForm: function() {
      if(this.serverForms.length==0) {
        this.serverForms.push('CredentialForm')
      } else {
        this.serverForms.pop()
      }
    },
    removeServer: function(index) {
      this.servers.splice(index, 1)
    },
    saveServer: function(server) {
      this.serverForms.pop()
      this.servers.push(server)
    },
    save: function() {
      axios.post(this.$store.state.server + 'project/save', {
        project :{
          name: this.name,
          giturl: this.giturl,
          credentials: this.credentials,
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