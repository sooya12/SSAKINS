<template>
  <div id="Credential">
    <v-select :items="computedItems" v-model="credentialSelected" placeholder="Credential Kind"></v-select>
      <div v-show="credentialSelected=='Username_with_password'">
        ID<v-text-field
        v-model="id"
        :rules="[rules.required]"
        ></v-text-field>
        Username<v-text-field
        v-model="username"
        :rules="[rules.required]"
        ></v-text-field>
        Password<v-text-field
        v-model="password"
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" 
        :rules="[rules.required, rules.min]" 
        :type="show1 ? 'text' : 'password'"
        @click:append="show1 = !show1"
        ></v-text-field>
      </div>
      <div v-show="credentialSelected=='GitHup_App'">
        ID<v-text-field
        v-model="id"
        :rules="[rules.required]"
        ></v-text-field>
        App ID<v-text-field
        v-model="appID"
        :rules="[rules.required]"
        ></v-text-field>
        Key<v-textarea
        v-model="key" 
        :rules="[rules.required]"
        background-color="blue-grey lighten-4"
        ></v-textarea>
      </div>
      <div v-show="credentialSelected=='GitLap_API_token'">
        ID<v-text-field
        v-model="id"
        :rules="[rules.required]"
        ></v-text-field>
        API token<v-text-field
        v-model="apiKey"
        :rules="[rules.required]"
        ></v-text-field>
      </div>
      <div v-show="credentialSelected=='SSH_Username_with_private_key'">
        ID<v-text-field
        v-model="id"
        :rules="[rules.required]"
        ></v-text-field>
        Username<v-text-field
        v-model="username"
        :rules="[rules.required]"
        ></v-text-field>
        Key<v-textarea
        v-model="key"
        :rules="[rules.required]"
        background-color="blue-grey lighten-4"
        ></v-textarea>
        Passphrase<v-text-field
        v-model="passphrase"
        :rules="[rules.required]"
        ></v-text-field>
      </div>
      <!-- <div v-show="credentialSelected=='Secret_file'">  
        File
        ID<v-text-field></v-text-field>
      </div>
      <div v-show="credentialSelected=='Secret_text'">  
         ID<v-text-field></v-text-field>
      </div>
      <div v-show="credentialSelected=='Certificate'">  
        서비스 준비중...
      </div> -->
      <v-btn @click="saveCredential">v</v-btn>
  </div>
</template>

<script>

export default {
  name: 'CredentialForm',
  data() {
    return {
      credentialKind: [
        'Username_with_password',
        'GitHup_App',
        'GitLap_API_token',
        'SSH_Username_with_private_key',
        'Secret_file (준비중)',
        'Secret_text (준비중)',
        'Certificate (준비중)'
      ],
      disabledKind: [
        'Secret_file (준비중)',
        'Secret_text (준비중)',
        'Certificate (준비중)'
      ],
      credentialSelected: '',
      id: '',
      password: '',
      apiKey: '',
      appID: '',
      key: '',
      username: '',
      passphrase: '',

      show1: false,
      rules: {
        required: value => !!value || 'Required.',
        min: v => v.length >= 8 || 'Min 8 characters',
      },
      
    }
  },
  computed: {
    computedItems() {
      return this.credentialKind.map(item => {
        return {
          text: item,
          disabled: this.disabledKind.includes(item)
        }
      })
    }
  },
  methods: {
    saveCredential() {
      this.$emit('update',{
        kind: this.credentialSelected,
        id: this.id,
        password: this.password,
        apiKey: this.apiKey,
        appID: this.appID,
        key: this.key,
        username: this.username,
        passphrase: this.passphrase
      })
      this.id
      // ??.push({
      //   id: this.id,
      //   password: this.password,
      //   apiKey: this.apiKey,
      //   appID: this.appID,
      //   key: this.key,
      //   username: this.username,
      //   passphrase: this.passphrase
      // })
    }
  }
}
</script>

<style scoped>
#Credential {
  position: relative;
  width: 80%;
  margin: 2vw auto;
}
</style>