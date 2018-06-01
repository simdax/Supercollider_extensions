import VueX from 'vuex'
import Vue from 'vue'
Vue.use(VueX)
import {gros_fetch, mutation_basic} from './utils'
import X2JS from 'x2js'
import PouchDB from 'pouchdb'
const js2xml = new X2JS().js2xml

const	db_path = "http://localhost:5984/test/"
const db_path_master = "http://localhost:5984/master/"
const master_db = new PouchDB('http://localhost:5984/svgs')
function update(key, update){
		master_db.get(key).then(doc => {
				for (i in update){
						doc[i] = update[i]
				}
				master_db.put(doc).then(e => console.log(e))
		})
}

let state = {
		all_svgs: [],
		svg: "",
		midi: "",
		changes: {},
}

let mutations = {
		ALL_SVGS: mutation_basic('all_svgs'),
		DATA (state, data) {
				state.svg = data.svg
				state.midi = data.midi 
		},
		NOTE (state, payload) {
				state.changes[payload.i] = payload.offset_note
		}
}

let getters = {
		svgs(state) { return state.all_svgs },
		midi(state) { return atob(state.midi) },
		svg(state){
				return js2xml(state.svg)
		}
}

let actions = {
		allSVG (store) {
				return PouchDB("http://localhost:5984/svgs/").get("_all_docs").then(doc => {
						store.commit('ALL_SVGS', doc.rows.map(v => v.key))
				})
		},
		getSVG (store, id) {
				console.log(id)
				return new Promise ((res, rej) => {
						master_db.get(id).then(doc => {
								store.commit('DATA', doc)
								res()
						})
				})
		},
		update_score ({state, commit}, args){
				master_db.get('music').then(doc => {
						doc[args.i].degree = args.offset_note
						master_db.put(doc).then(e => {
								commit('NOTE', args);
						})
				})
		}
}

export default new VueX.Store ({
		state, getters, mutations, actions
})
