import VueX from 'vuex'
import Vue from 'vue'
Vue.use(VueX)
import {gros_fetch, mutation_basic} from './utils'
import X2JS from 'x2js'
import PouchDB from 'pouchdb'
import Find from 'pouchdb-find'
PouchDB.plugin(Find);
import {upsert} from "./pouchdb_utils"
import Socket from './socket'
const socket = new Socket
global.socket = socket
const js2xml = new X2JS().js2xml
const master_db = new PouchDB('http://localhost:5984/summa_musicae')

let state = {
		all_svgs: [],
		supercollider: 0,
		current: 0,
		port: 0, IP: "",
		svg: "",
		midi: "",
		changes: {},
}

let mutations = {
		SUPERCOLLIDER: mutation_basic('supercollider'),
		ALL_SVGS: mutation_basic('all_svgs'),
		CURRENT: mutation_basic('current'),
		DATA (state, data) {
				state.svg = data.svg
				state.midi = data.midi 
		},
		NOTE (state, payload) {
				state.changes[payload.i] = payload.offset_note
		}
}

let getters = {
		supercollider(state){ return state.supercollider},
		current(state){ return state.current },
		svgs(state) { return state.all_svgs },
		midi(state) { return atob(state.midi) },
		last_one(state) { return state.all_svgs[state.all_svgs.length - 1] },
		svg(state){ return js2xml(state.svg) }
}

import sc from './sc.js'
let actions = {
		check_supercollider(store){
				socket.send('exists_sc').then(res => {
						store.commit('SUPERCOLLIDER', res)
				}).catch(err => {
						store.commit('SUPERCOLLIDER', 2)}) 
		},
		supercollider_msg(store, args){
				socket.supercollider(JSON.stringify(args), e => {
						console.log("retour de baton", e)
				})
		}, 
		go(store){
				// master_db.changes({
				// 		since: 'now',
				// 		live: true,
				// 		include_docs: true
				// }).on('change', function (change) {
				// 		store.dispatch('allSVG')
				// 		if (change.deleted) {
				// 				console.log("db delete", change)
				// 		} else {
				// 				console.log("db modif", change)
				// 		}
				// }).on('error', function (err) {
				// 		console.log("db errors", err)
				// });
				['check_supercollider', 'allSVG']
						.forEach(e => store.dispatch(e))
		},
		allSVG (store) {
				master_db.query("1/has_midi").then(doc => {
						store.commit('ALL_SVGS', doc.rows.map(v => v.key))
						store.dispatch('getSVG', store.getters.last_one)						
				})
		},
		getSVG (store, id) {
				console.log("getting", id)
				return new Promise ((res, rej) => {
						master_db.get(id).then(doc => {
								store.commit('DATA', doc)
								store.commit('CURRENT', id)
								res()
						})
				})
		},
		delete_changes ({state, commit}, id){
				master_db.query("1/patch_for_svg",
												{key: state.current,
												 include_docs: true})
						.then(doc => {
								let docs = doc.rows.map(d => {
										let ret = d.doc
										ret.degree = 0
										return ret
								})
								master_db.bulkDocs(docs).then(e => {
										console.log("bien bulke")
										// force reupdate
										commit('DATA',
													 {svg: state.svg, midi:state.midi})
								}).catch(e => console.log(2, e))
						}).catch(e=>console.log(1, e))
		},
		update_score ({state, commit}, args){
				let id = this.state.current + '/' + args.i
				master_db.get(id)
						.catch(e => { console.log("1", e);
													return master_db.put({_id: id}) })
						.then(doc => {
								console.log("fa", doc)
 								doc.degree = args.offset_note
								doc._deleted = false
								commit('NOTE', args);
								master_db.put(doc)
						})
						.catch(e => console.log("err", e))
		}
}

export default new VueX.Store ({
		state, getters, mutations, actions
})
