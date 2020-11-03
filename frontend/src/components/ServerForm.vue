<template>
  <div id="Server">
  <!-- <v-form ref="form" v-model="valid" lazy-validation> -->
    Kind
    <v-select :items="computedItems" v-model="serverSelected" placeholder="Server Kind"></v-select><br>
    <div v-show="serverSelected=='Spring'">
      Spring임
    </div>
    <div v-show="serverSelected=='Vue'">
      Vue임
    </div>
    <v-btn @click="saveServer">v</v-btn>
  <!-- </v-form> -->
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
      serverSelected: '',

      show1: false,
      rules: {
        required: value => !!value || 'Required.',
        min: v => v.length >= 8 || 'Min 8 characters',
      },
      
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
      if(this.serverSelected=='Spring'){
        // if(rai){
        //   this.serverSelected=this.serverSelected+'_maven'
        // }
      }
      this.$emit('update',{
        kind: 'ServerKind'+this.serverSelected,
      })
    }
  }
}
</script>

<style scoped>
#Server {
  position: relative;
  width: 80%;
  margin: 2vw;
}
</style>