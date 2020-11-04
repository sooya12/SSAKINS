<template>
  <div id="Credential">
  <v-form ref="form" v-model="valid">
    Kind
    <v-select :items="computedItems" v-model="credentialSelected" :rules="[rules.required]" placeholder="Credential Kind"></v-select><br>
    <div v-if="credentialSelected=='Username_with_password'">
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
      :rules="[rules.required]" 
      :type="show1 ? 'text' : 'password'"
      @click:append="show1 = !show1"
      ></v-text-field>
    </div>
    <div v-if="credentialSelected=='GitHub_App'">
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
    <div v-if="credentialSelected=='GitLap_API_token'">
      ID<v-text-field
      v-model="id"
      :rules="[rules.required]"
      ></v-text-field>
      API token<v-text-field
      v-model="apiKey"
      :rules="[rules.required]"
      ></v-text-field>
    </div>
    <div v-if="credentialSelected=='SSH_Username_with_private_key'">
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
      password: null,
      apiKey: null,
      appID: null,
      key: null,
      username: null,
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
  margin: 2vw;
  font-weight: bold;
}
</style>