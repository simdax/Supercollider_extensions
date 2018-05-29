import {gros_fetch, mutation_basic} from './utils'
import VueX from 'vuex'
import Vue from 'vue'
import X2JS from 'x2js'
const js2xml = new X2JS().js2xml
Vue.use(VueX)

let state = {
		db_path: "http://localhost:5984/test/",
		data: "",
		midi: ""
}

let mutations = {
		DATA: mutation_basic('data'),
		MIDI: mutation_basic('midi'),
}

let getters = {
		svg(state){
				return js2xml(state.data.svg)
		}
}

let actions = {
		getSVG : (store, id) => {
//				return new Promise ((res, rej) => {
						let url =  store.state.db_path + id
						gros_fetch(url, store.commit, 'DATA')
						//				gros_fetch(url + '/midi', store.commit, 'MIDI')
				// 		res()
				// })
		}
}

export default new VueX.Store ({
		state, getters, mutations, actions
})
