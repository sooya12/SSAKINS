<template>
  <v-container id="create">
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
              <v-text-field v-model="name" class="font15" placeholder="내용을 입력해주세요" hide-details="auto" dense filled shaped></v-text-field>
            </td>
          </tr>
          <tr>
            <td class="text-left font15" style="vertical-align: top"><br>설정 내용</td>
            <td class="text-left font15">
              <div id="content" class="font15">
                <br>
                <div style="margin: 0 2vw">
                  <h3>Git</h3> <br>
                  Git URL<v-text-field
                  v-model="giturl"
                  :rules="[rules.required]"
                  ></v-text-field>
                </div> 
                <div style="margin: 2vw">
                  <h3>Credential</h3>
                  <div style="margin: 2vw;" v-for="(credential, index) in credentials" :key="index">
                    <div v-show="credential.kind=='Username_with_password'">
                      Kind<v-text-field
                      v-model="credential.kind"
                      disabled
                      ></v-text-field>
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
                      :rules="[rules.required, rules.min]" 
                      :type="show1 ? 'text' : 'password'"
                      @click:append="show1 = !show1"
                      ></v-text-field>
                    </div>
                    <div v-show="credential.kind=='GitHup_App'">
                      Kind<v-text-field
                      v-model="credential.kind"
                      disabled
                      ></v-text-field>
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
                    <div v-show="credential.kind=='GitLap_API_token'">
                      Kind<v-text-field
                      v-model="credential.kind"
                      disabled
                      ></v-text-field>
                      ID<v-text-field
                      v-model="credential.id"
                      :rules="[rules.required]"
                      ></v-text-field>
                      API token<v-text-field
                      v-model="credential.apiKey"
                      :rules="[rules.required]"
                      ></v-text-field>
                    </div>
                    <div v-show="credential.kind=='SSH_Username_with_private_key'">
                      Kind<v-text-field
                      v-model="credential.kind"
                      disabled
                      ></v-text-field>
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
                    <div v-show="server.kind=='Spring'">
                       Kind<v-text-field
                      v-model="server.kind"
                      disabled
                      ></v-text-field>
                    </div>
                    <div v-show="server.kind=='Vue'">
                       Kind<v-text-field
                      v-model="server.kind"
                      disabled
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
                <!-- <v-checkbox v-model="checkSpring" label="Spring" hide-details=""></v-checkbox>
                <spring v-show="checkSpring"></spring>
                
                <v-checkbox v-model="checkVuejs" label="Vue.js" hide-details=""></v-checkbox>
                <vuejs v-show="checkVuejs"></vuejs> -->
              </div>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <div id="btn-area">
      <v-btn class="font15" elevation="2" color="grey darken-3" style="color: white">저장하기</v-btn>
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
      giturl: null,
      credentials: [],
      credentialForms: [],

      servers: [],
      serverForms: [],

      show1: false,
      rules: {
        required: value => !!value || 'Required.',
        min: v => v.length >= 8 || 'Min 8 characters',
      },

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
      if(this.credentialForms.length==0)
        this.credentialForms.push('CredentialForm')
      else
        this.credentialForms.pop()
    },
    removeCredential: function(index) {
      this.credentials.splice(index, 1)
    },
    saveCredential: function(credential) {
      this.credentialForms.pop()
      this.credentials.push(credential)
    },
    toggleServerForm: function() {
      if(this.serverForms.length==0)
        this.serverForms.push('CredentialForm')
      else
        this.serverForms.pop()
    },
    removeServer: function(index) {
      this.servers.splice(index, 1)
    },
    saveServer: function(server) {
      this.serverForms.pop()
      this.servers.push(server)
    },
    save: function() {
      axios.post(this.$store.state.server + '/', {
        name: this.name,
        giturl: this.giturl,
        credentials: this.credentials,
        servers: this.servers
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