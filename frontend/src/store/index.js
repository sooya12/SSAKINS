import Vue from 'vue'
import Vuex from 'vuex'
// import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
    plugins: [
        // createPersistedState()
    ],
    state:{
        //server: 'http://localhost:8080/',
        server: 'https://www.ssakins.com/api/',
        //front: 'http://localhost/',
        front: 'https://www.ssakins.com/'
    },
})