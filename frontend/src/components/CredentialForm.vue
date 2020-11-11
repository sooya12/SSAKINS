<template>
  <div id="Credential">
  <v-form ref="form" v-model="valid">
    <h4>Kind</h4>
    <v-select :items="computedItems" @change="resetForm" v-model="credentialSelected" :rules="[rules.required]" placeholder="Credential Kind"></v-select><br>
      <div v-if="credentialSelected=='Username_with_password'">
        <h4>ID</h4><v-text-field
        v-model="id"
        :rules="[rules.required]"
        ></v-text-field>
        <h4>Username</h4><v-text-field
        v-model="username"
        :rules="[rules.required]"
        ></v-text-field>
        <h4>Password</h4><v-text-field
        v-model="password"
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" 
        :rules="[rules.required]" 
        :type="show1 ? 'text' : 'password'"
        @click:append="show1 = !show1"
        ></v-text-field>
        <v-radio-group v-model="isHub" row :rules="[rules.required]">
          <v-radio label="GitLab" value="false"></v-radio>
          <v-radio label="GitHub" value="true"></v-radio>
        </v-radio-group>
      </div>
      <div v-if="credentialSelected=='GitHub_App'">
        <h4>ID</h4><v-text-field
        v-model="id"
        :rules="[rules.required]"
        ></v-text-field>
        <h4>App ID</h4><v-text-field
        v-model="appID"
        :rules="[rules.required]"
        ></v-text-field>
        <h4>Key</h4><v-textarea
        v-model="key" 
        :rules="[rules.required]"
        background-color="blue-grey lighten-4"
        ></v-textarea>
      </div>
      <div v-if="credentialSelected=='GitLap_API_token'">
        <h4>ID</h4><v-text-field
        v-model="id"
        :rules="[rules.required]"
        ></v-text-field>
        <h4>API token</h4><v-text-field
        v-model="apiKey"
        :rules="[rules.required]"
        ></v-text-field>
      </div>
      <div v-if="credentialSelected=='SSH_Username_with_private_key'">
        <h4>ID</h4><v-text-field
        v-model="id"
        :rules="[rules.required]"
        ></v-text-field>
        <h4>Username</h4><v-text-field
        v-model="username"
        :rules="[rules.required]"
        ></v-text-field>
        <h4>Key</h4><v-textarea
        v-model="key"
        :rules="[rules.required]"
        background-color="blue-grey lighten-4"
        ></v-textarea>
        <h4>Passphrase</h4><v-text-field
        v-model="passphrase"
        :rules="[rules.required]"
        ></v-text-field>
      </div>
      <!-- <div v-if="credentialSelected=='Secret_file'">  
        File
        ID<v-text-field></v-text-field>
      </div>
      <div v-if="credentialSelected=='Secret_text'">  
          ID<v-text-field></v-text-field>
      </div>
      <div v-if="credentialSelected=='Certificate'">  
        서비스 준비중...
      </div> -->
    <v-btn :disabled="!valid" @click="saveCredential">v</v-btn>
  </v-form>
  </div>
</template>

<script>

export default {
  name: 'CredentialForm',
  data() {
    return {
      credentialKind: [
        'Username_with_password',
        'GitHub_App',
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
      credentialSelected: null,
      id: null,
      username: null,
      password: null,
      isHub: null,
      apiKey: null,
      appID: null,
      key: null,
      passphrase: null,

      show1: false,
      rules: {
        required: value => !!value || 'Required.',
      },
      valid: false
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
      // if(this.credentialSelected=='Username_with_password') {
      //   if(this.isHub=="true") {
      //     this.isHub=true
      //   } else if(this.isHub=="false") {
      //     this.isHub=false
      //   }
      // }

      this.$emit('update',{
        kind: this.credentialSelected,
        id: this.id,
        password: this.password,
        isHub: this.isHub,
        apiKey: this.apiKey,
        appID: this.appID,
        key: this.key,
        username: this.username,
        passphrase: this.passphrase
      })
      // ??.push({
      //   id: this.id,
      //   password: this.password,
      //   apiKey: this.apiKey,
      //   appID: this.appID,
      //   key: this.key,
      //   username: this.username,
      //   passphrase: this.passphrase
      // })
    },
    addJVMOption() {
      
    },
    resetForm() {
      let current = this.credentialSelected
      this.$refs.form.reset()
      this.credentialSelected = current
    }
  }
}
</script>

<style scoped>
#Credential {
  position: relative;
  /* width: 85%; */
  margin: 2vw auto;
}
</style>