import Vue from 'vue'
import VueX from 'vuex'
import App from './App.vue'
import store from './store/svg.js'

Vue.config.productionTip = false
Vue.use(VueX)

new Vue({
		render: h => h(App),
		store
}).$mount('#app')
