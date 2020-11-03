import Vue from 'vue'
import router from './router'
import store from './store'
import App from './App.vue'
import vuetify from './plugins/vuetify';
//import Vuetify from 'vuetify';

Vue.config.productionTip = false

new Vue({
<<<<<<< HEAD
  router,
  store,
  vuetify,
  render: h => h(App)
=======
    render: h => h(App),
>>>>>>> c3eddf7bcef0f7e6f0071ded6e4b037a37774685
}).$mount('#app')
