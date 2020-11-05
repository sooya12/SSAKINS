<template>
  <div id="Server">
  <v-form ref="form" v-model="valid">
    <h4>Kind</h4>
    <v-select :items="computedItems" @change="resetForm" v-model="serverSelected" :rules="[rules.required]" placeholder="Server Kind"></v-select><br>
    <div v-if="serverSelected=='Spring'">
      <h4>port</h4><v-text-field
      v-model="port"
      :rules="[rules.required, rules.number]"
      ></v-text-field>
      <h4>pom.xml</h4><v-text-field
      v-model="info"
      :rules="[rules.required]"
      ></v-text-field>
      <v-radio-group v-model="tool" row :rules="[rules.required]">
        <v-radio label="Maven" value="Spring_maven"></v-radio>
        <v-radio label="Gradle" value="Spring_gradle"></v-radio>
      </v-radio-group>
      <h4 v-if="options.length!=0">JVM Options</h4><br>
      <div v-for="(option,index) in options" :key="index">
        <h5 style="clear: both">Option</h5>
        <v-text-field v-model="options[index]" :rules="[rules.required]" style="width: 90%; float: left"></v-text-field>
        <v-btn style="float: right; margin-right 1vw" @click="delOption(index)">삭제</v-btn>
      </div>
      <v-btn style="float: right" @click="addOption">+ JVM Option</v-btn>
    </div>
    <div v-if="serverSelected=='Vue'">
      <h4>port</h4><v-text-field
      v-model="port"
      :rules="[rules.required, rules.number]"
      ></v-text-field>
      <h4>package.json</h4><v-text-field
      v-model="info"
      :rules="[rules.required]"
      ></v-text-field>
    </div>
    <v-btn :disabled="!valid" style="clear: both" @click="saveServer">v</v-btn>
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
      options: [],

      rules: {
        required: value => !!value || 'Required.',
        number: value => /^[0-9]+$/.test(value) || 'Only number.'
      },
      valid: false
    }
  },
  components: {

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
        port: this.port,
        options: this.options
      })
    },
    resetForm() {
      let current = this.serverSelected
      this.$refs.form.reset()
      this.serverSelected = current
    },
    addOption() {
      this.options.push("")
    },
    delOption(index) {
      this.options.splice(index, 1)
    }
  }
}

</script>

<style scoped>
#Server {
  position: relative;
  /* width: 85%; */
  margin: 2vw auto;
}
</style>