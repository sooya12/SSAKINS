import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        // front: 'http://localhost/',
        front: 'https://www.ssakins.com/',

        // server: 'http://localhost:8080/api/',
        server: 'https://www.ssakins.com/api/',
    },
})