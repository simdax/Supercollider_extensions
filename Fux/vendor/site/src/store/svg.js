import VueX from 'vuex'
import Vue from 'vue'
Vue.use(VueX)
import {gros_fetch, mutation_basic} from './utils'
import X2JS from 'x2js'
import PouchDB from 'pouchdb'
const js2xml = new X2JS().js2xml

const	db_path = "http://localhost:5984/test/"
const db_path_master = "http://localhost:5984/master/"
const master_db = new PouchDB('http://localhost:5984/master')
function update(key, update){
		master_db.get(key).then(doc => {
				for (i in update){
						doc[i] = update[i]
				}
				master_db.put(doc).then(e => console.log(e))
		})
}

let state = {
		data: "",
		midi: ""
}

let mutations = {
		DATA: mutation_basic('data'),
		MIDI: mutation_basic('midi'),
		NOTE () {
				console.log("une note...")
		}
}

let getters = {
		svg(state){
				return js2xml(state.data.svg)
		}
}

let actions = {
		getSVG (store, id) {
				return new Promise ((res, rej) => {
						let url =  db_path + id
						gros_fetch(url, store.commit, 'DATA')
						//gros_fetch(url + '/midi', store.commit, 'MIDI')
						res()
				})
		},
		update_score ({state, commit}, args){
				master_db.get('music').then(doc => {
						doc[args.i].degree = args.offset_note
						master_db.put(doc).then(e => {
								commit('NOTE'); console.log(e)		
						})
				})
		}
}

export default new VueX.Store ({
		state, getters, mutations, actions
})
