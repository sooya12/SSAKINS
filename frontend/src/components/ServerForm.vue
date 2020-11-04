<template>
  <div id="Server">
  <v-form ref="form" v-model="valid">
    Kind
    <v-select :items="computedItems" @change="resetForm" v-model="serverSelected" :rules="[rules.required]" placeholder="Server Kind"></v-select><br>
    <div v-if="serverSelected=='Spring'">
      port<v-text-field
      v-model="port"
      :rules="[rules.required, rules.number]"
      ></v-text-field>
      pom.xml<v-text-field
      v-model="info"
      :rules="[rules.required]"
      ></v-text-field>
      <v-radio-group v-model="tool" row :rules="[rules.required]">
        <v-radio label="Maven" value="Spring_maven"></v-radio>
        <v-radio label="Gradle" value="Spring_gradle"></v-radio>
      </v-radio-group>
    </div>
    <div v-if="serverSelected=='Vue'">
      port<v-text-field
      v-model="port"
      :rules="[rules.required, rules.number]"
      ></v-text-field>
      package.json<v-text-field
      v-model="info"
      :rules="[rules.required]"
      ></v-text-field>
    </div>
    <v-btn :disabled="!valid" @click="saveServer">v</v-btn>
  </v-form>
  </div>
</template>

<script>

export default {
  name: 'ServerForm',
  data() {
    return {
      serverKind: [
        'Spring',
        'Django(준비중)',
        'Flask(준비중)',
        'Express(준비중)',
        'Vue',
        'React(준비중)',
      ],
      disabledKind: [
        'Django(준비중)',
        'Flask(준비중)',
        'Express(준비중)',
        'React(준비중)',
      ],
      serverSelected: null,
      
      info: null,
      port: null,
      tool: null,

      rules: {
        required: value => !!value || 'Required.',
        number: value => /^[0-9]+$/.test(value) || 'Only number.'
      },
      valid: false
    }
  },
  computed: {
    computedItems() {
      return this.serverKind.map(item => {
        return {
          text: item,
          disabled: this.disabledKind.includes(item)
        }
      })
    }
  },
  methods: {    
    saveServer() {
      if(this.serverSelected=='Spring') {
        this.serverSelected=this.tool
      }
      this.$emit('update',{
        kind: this.serverSelected,
        info: this.info,
        port: this.port
      })
    },
    resetForm() {
      let current = this.serverSelected
      this.$refs.form.reset()
      this.serverSelected = current
    }
  }
}
</script>

<style scoped>
#Server {
  position: relative;
  width: 80%;
  margin: 2vw;
  font-weight: bold;
}
</style>