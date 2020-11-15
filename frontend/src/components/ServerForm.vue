<template>
  <div id="Server">
  <v-form ref="form" v-model="valid">
    <h4>Kind</h4>
    <v-select :items="computedItems" @change="resetForm" v-model="serverSelected" :rules="[rules.required]" placeholder="Server Kind"></v-select><br>
    <div v-if="serverSelected=='Spring'">
      <h4>port</h4><v-text-field
      v-model="port"
      :rules="[rules.required, rules.number, rules.space]"
      placeholder="포트 번호를 입력해 주세요"
      ></v-text-field>
      <h4>pom.xml</h4><v-text-field
      v-model="info"
      :rules="[rules.required, rules.space, rules.info, rules.korean]"
      placeholder="pom.xml의 경로를 입력해 주세요 (마지막'/'제외)"
      ></v-text-field>
      <v-radio-group v-model="tool" row :rules="[rules.required]">
        <v-radio label="Maven" value="Spring_maven"></v-radio>
        <v-radio label="Gradle" value="Spring_gradle"></v-radio>
      </v-radio-group>
      <h4 v-if="options.length!=0">JVM Options</h4><br>
      <div v-for="(option,index) in options" :key="index">
        <h5 style="clear: both; text-align: left">Option</h5>
        <v-text-field
        v-model="options[index]"
        :rules="[rules.required, rules.korean, rules.space]"
        placeholder='ex) -Dspring.profiles.active="live"'
        style="width: 90%; float: left; padding-top: 2px"
        ></v-text-field>
        <i 
        class="far fa-cut"
        style="float: right; margin-right: 2vw; font-size: 25px; color: #D32F2F"
        @click="delOption(index)"
        ></i>
        <!-- <v-icon style="float: right; margin-right: 1vw" color="red lighten-1" large @click="delOption(index)">mdi-tooltip-remove</v-icon> -->
      </div>
      <v-btn style="float: right; color: white " color="teal darken-2" depressed @click="addOption">+ JVM Option</v-btn>
    </div>
    <div v-if="serverSelected=='Vue'">
      <h4>port</h4><v-text-field
      v-model="port"
      :rules="[rules.required, rules.number, rules.space]"
      placeholder="포트 번호를 입력해 주세요"
      readonly
      hint="port number 80 fixed."
      ></v-text-field>
      <h4>package.json</h4><v-text-field
      v-model="info"
      :rules="[rules.required, rules.space, rules.info, rules.korean]"
      placeholder="package.json의 경로를 입력해 주세요 (마지막'/'제외)"
      ></v-text-field>
    </div>
    <v-btn
    id="save-btn"
    :disabled="!valid"
    @click="saveServer"
    depressed
    ><i class="fad fa-save"></i></v-btn>
  </v-form>
  </div>
  
</template>

<script>
export default {
  name: 'ServerForm',
  data() {
    return {
      serverSelected: null,
      
      info: null,
      port: null,
      tool: null,
      options: [],

      sKind: this.serverKind,
      dKind: this.disabledKind,

      rules: {
        required: value => !!value || 'Required.',
        number: value => /^[0-9]+$/.test(value) || 'Only number.',
        space: value => /^[^\s]+$/.test(value) || 'No Space',
        info: value => /[^/]$/.test(value) || "Please remove last'/'",
        korean: value => /^[^ㄱ-ㅎ가-힣]+$/.test(value) || 'Please remove Korean'
      },
      valid: false,

    }
  },
  props: {
    serverKind: Array,
    disabledKind: Array,
  },
  computed: {
    computedItems() {
      return this.sKind.map(item => {
        return {
          text: item,
          disabled: this.dKind.includes(item)
        }
      })
    }
  },
  methods: {    
    saveServer() {
      if(this.serverSelected=='Spring') {
        this.serverSelected=this.tool
        this.dKind.push('Spring')
      } else if(this.serverSelected=='Vue') {
        this.dKind.push('Vue')
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
      if(current=='Vue') {
        this.port="80"
      }
    },
    addOption() {
      this.options.push("")
    },
    delOption(index) {
      this.options.splice(index, 1)
    },
  }
}

</script>

<style scoped>
#Server {
  position: relative;
  /* width: 85%; */
  margin: 2vw auto;
}

#save-btn {
  color: #004D40;
  font-size: 30px;
  clear: both;
  background-color: transparent !important;
}

</style>