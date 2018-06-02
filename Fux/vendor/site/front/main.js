import Vue from 'vue'
import VueX from 'vuex'
import store from './store/svg.js'
//for debug
global.store = store

Vue.config.productionTip = false
Vue.use(VueX)

import App from './App.vue'

new Vue({
		render: h => h(App),
		store
}).$mount('#app')
