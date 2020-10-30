<template>
  <div id="spring">
    <v-radio-group v-model="buildTool">
      빌드 툴
      <v-radio label="Maven" value="Maven"/>
      <v-radio label="Gradle" value="Gradle"/>
      
    </v-radio-group>
  </div>
</template>

<script>

export default {
  name: 'Spring',
  data() {
    return {
      buildTool: 'tool'
    }
  },
}
</script>

<style scoped>
#spring {
  position: relative;
  left: 2vw;
}
</style>