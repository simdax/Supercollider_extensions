import VueX from 'vuex'
import Vue from 'vue'
import {gros_fetch, mutation_basic} from './utils'
import socket from './socket'
import master_db from './db'

let state = {
		all_svgs: [],
		supercollider: 0,
		port: 0, IP: "",
		svg: "",
		midi: "",
		changes: {},
}

let mutations = {
		SUPERCOLLIDER: mutation_basic('supercollider'),
		ALL_SVGS: mutation_basic('all_svgs'),
		DATA (state, data) {
				state.svg = data.svg
				state.midi = data.midi 
		},
		NOTE (state, payload) {
				state.changes[payload.i] = payload.offset_note
		}
}

import X2JS from 'x2js'
const js2xml = new X2JS().js2xml
let getters = {
		supercollider(state){ return state.supercollider},
		last_one(state) { return state.all_svgs[state.all_svgs.length - 3] },
		svgs(state) { return state.all_svgs },
		midi(state) { return atob(state.midi) },
		svg(state){ return js2xml(state.svg) }
}

import sc from './supercollider'
import score from './score'

let actions = {}

for(let i in sc){
		actions[i] = sc[i]
}
for(let i in score){
		actions[i] = score[i]		
}
actions.go = (store) => {
		master_db.changes(store)
		['check_supercollider', 'allSVG'].forEach(
				e => store.dispatch(e))
}

Vue.use(VueX)

export default new VueX.Store ({
		state, getters, mutations, actions
})
