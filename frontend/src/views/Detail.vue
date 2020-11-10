<template>
  <v-container id="contianer">
    <div>
      <headers></headers>
    </div>
    <div
      id="navigator"
      style="float:left; margin-top:16px; margin-right:20px; margin-left:20px;"
    >
      <navigator></navigator>
    </div>
    <div id="detail">
      <div id="title">
        <h2>CI/CD 설정 상세</h2>
      </div>
      <table>
        <tbody>
          <tr>
            <td class="text-left font15" style="width: 10vw">
              설정 CI/CD 명
            </td>
            <td class="text-left font15" style="width: 60vw">
              {{ name }}
            </td>
          </tr>
          <tr>
            <td class="text-left font15" style="vertical-align: top"><br>설정 내용</td>
            <td class="text-left font15">
              <div id="content" class="font15">
                <br>
                <div style="margin: 0 2vw">
                  <h3><div class="icon-div"><i class="fab fa-jenkins" style="font-size: 28px; font-weight: bold; color: #004D40"></i></div>Jenkins</h3><br>
                  <div style="margin: auto; width: 85%">
                    <table style="width: 100%">
                      <tbody>
                        <tr style="margin: 0">
                          <td class="text-left" style="width: 20%"><h4>URL</h4></td>
                          <td class="text-left" style="width: 80%">{{ project.url }}</td>
                        </tr>
                          <td class="text-left" style="width: 20%"><h4>Port</h4></td>
                          <td class="text-left" style="width: 80%">{{ project.port }}</td>
                        <tr>
                        </tr>
                      </tbody>
                    </table>
                    <br><br>
                  </div>
                </div>
                <div style="margin: 0 2vw">
                  <h3><div class="icon-div"><i class="fab fa-git-alt" style="font-size: 28px; color: #004D40"></i></div>Git</h3><br>
                  <div style="margin: auto; width: 85%">
                     <table style="width: 100%">
                      <tbody>
                        <tr> 
                          <td class="text-left" style="width: 20%"><h4>Git URL</h4></td>
                          <td class="text-left" style="width: 80%">{{ project.git.giturl }}</td>
                        </tr>
                        <tr> 
                          <td class="text-left" style="width: 20%"><h4>ID</h4></td>
                          <td class="text-left" style="width: 80%">{{ project.git.id }}</td>
                        </tr>
                        <tr> 
                          <td class="text-left" style="width: 20%"><h4>name</h4></td>
                          <td class="text-left" style="width: 80%">{{ project.git.name }}</td>
                        </tr>
                        <tr> 
                          <td class="text-left" style="width: 20%"><h4>Password</h4></td>
                          <td class="text-left" style="width: 80%">{{ gitpass }}</td>
                        </tr>
                        <tr>
                          <td class="text-left" style="width: 20%"><h4>Kind</h4></td>
                          <td class="text-left" style="width: 80%">
                            <div v-if="project.git.type=='gitlab'">GitLab</div>
                            <div v-if="project.git.type=='github'">GitHub</div>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                    <br><br>
                  </div>
                </div>
                <div style="margin: 0 2vw">
                  <h3><div class="icon-div" style="background-color: #004D40"><i class="far fa-terminal" style="color: white; font-size: 15px"></i></div>Publish over SSH</h3><br>
                  <div style="margin: auto; width: 85%">
                    <table style="width: 100%">
                      <tbody>
                        <tr>
                          <td class="text-left" style="width: 20%"><h4>Hostname</h4></td>
                          <td class="text-left" style="width: 80%">{{ project.sshServer.hostName }}</td>
                        </tr>
                        <tr>
                          <td class="text-left" style="width: 20%"><h4>Username</h4></td>
                          <td class="text-left" style="width: 80%">{{ project.sshServer.userName }}</td>
                        </tr>
                        <tr>
                          <td class="text-left" style="width: 20%"><h4>Password</h4></td>
                          <td class="text-left" style="width: 80%">{{ sshpass }}</td>
                        </tr>
                        <tr>
                          <td class="text-left" colspan="2">
                            <h4>Key
                              <i v-if="!keyArrow" class="fal fa-angle-down" style="margin-left: 20px; font-size: 25px; vertical-align: top;" @click="keyToggle"></i>
                              <i v-if="keyArrow" class="fal fa-angle-up" style="margin-left: 20px; font-size: 25px; vertical-align: top;" @click="keyToggle"></i>
                            </h4>
                            </td>
                        </tr>
                        <tr v-if="keyArrow"> 
                          <td class="text-left" colspan="2">
                            <v-textarea
                            v-model="project.sshServer.key"
                            readonly
                            background-color="teal lighten-5"
                            ></v-textarea>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                    <br><br>
                  </div>
                </div>
                <div style="margin: 0 2vw">
                  <h3><div class="icon-div"><i class="fad fa-server" style="font-size: 25px; color: #004D40"></i></div>Server</h3> <br>
                  <div style="margin: auto; width: 85%">
                    <div v-for="(server, index) in project.servers" :key="index">
                        <!-- <div v-if="server.kind=='Spring_maven' || server.kind=='Spring_gradle'"> -->
                        <div v-if="server.kind=='Spring'">
                          <div style="font-size:17px; font-weight: bold"><div class="icon-div"><i class="fad fa-leaf" style="font-size: 22px; color: #004D40"></i></div>Spring</div>
                          <table style="width: 100%">
                            <tbody>
                              <tr>
                                <td class="text-left" style="width: 20%"><h4>port</h4></td>
                                <td class="text-left" style="width: 80%">{{ server.port }}</td>
                              </tr>
                              <tr>
                                <td class="text-left" style="width: 20%"><h4>pom.xml</h4></td>
                                <td class="text-left" style="width: 80%">{{ server.info }}</td>
                              </tr>
                              <tr>
                                <td class="text-left" style="width: 20%"><h4>build tool</h4></td>
                                <td class="text-left" style="width: 80%">
                                  <div v-if="server.type=='Spring_maven'">Maven</div>
                                  <div v-if="server.type=='Spring_gradle'">Gradle</div>
                                </td>
                              </tr>
                              <tr v-if="server.options.length!=0">
                                <td class="text-left" colspan="2"><h4>JVM Options</h4></td>
                              </tr>
                              <tr v-for="(option,idx) in server.options" :key="idx">
                                <td class="text-left" style="width: 20%"><h5>Option{{idx+1}}</h5></td>
                                <td>{{ server.options[idx] }}</td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                        <div v-if="server.kind=='Vue'">
                          <div style="font-size:17px; font-weight: bold"><div class="icon-div"><i class="fab fa-vuejs" style="font-size: 22px; color: #004D40"></i></div>Vue</div>
                          <table style="width: 100%">
                            <tbody>
                              <tr>
                                <td class="text-left" style="width: 20%"><h4>port</h4></td>
                                <td class="text-left" style="width: 80%">{{ server.port }}</td>
                              </tr>
                              <tr>
                                <td class="text-left" style="width: 20%"><h4>pom.xml</h4></td>
                                <td class="text-left" style="width: 80%">{{ server.info }}</td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      <br><br>
                      </div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <div id="btn-area">
                <v-btn class="font15" elevation="2" color="#B2DFDB" style="margin-right: 1vw; font-weight: bold"  @click="goEdit">수정하기</v-btn>
                <v-btn class="font15" elevation="2" color="#004D40" style="color: white; margin-left: 1vw; font-weight: bold">삭제하기</v-btn>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </v-container>
</template>

<script>
import Navigator from "../components/Navigator";
import Header from "../components/Header";
import axios from 'axios'

export default {
  name: 'Detail',
  data() {
    return {
      userEmail: 'sooya@ssakins.com',
      name: 'test4',
      project: null,
      keyArrow: false,
      gitpass: null,
      sshpass: null,
    }
  },
  methods: {
    goEdit() {
      this.$router.push("/edit")
    },
    keyToggle() {
      this.keyArrow=!this.keyArrow
    }
  },
  components: {
    navigator: Navigator,
    headers: Header
  },
  mounted() {
    // this.userEmail = sessionStorage.getItem('email')

    axios.get(this.$store.state.server + 'mongo/select/' + this.userEmail + '/' + this.name)
    .then(res => {
      this.project = res.data
      this.gitpass = '*'.repeat(res.data.git.password.length)
      this.sshpass = '*'.repeat(res.data.sshServer.password.length)
      console.log(this.project)
    }).catch(err => {
      console.log(err)
    })

  }
  
}
</script>

<style scoped>
#container {
  width: 100%;
  min-width: 100px;
}

#navigator {
  width: 20%;
  min-width: 100px;
}

#detail {
  text-align: center;
  width: 75%;
  margin: 0 auto;
  font-family: 'S-CoreDream-3Light';
}

table {
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

/* #content {
  background: #EEEEEE;
} */

#btn-area {
  margin: 20px auto;
}

.icon-div {
  width: 27px;
  height: 27px;
  border-radius:15%;
  float: left;
  margin-right: 10px;
  text-align: center;
  vertical-align:middle;
}

</style>