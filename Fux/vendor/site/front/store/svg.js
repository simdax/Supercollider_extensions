import VueX from 'vuex'
import Vue from 'vue'
Vue.use(VueX)
import {gros_fetch, mutation_basic} from './utils'
import X2JS from 'x2js'
import PouchDB from 'pouchdb'
import Find from 'pouchdb-find'
PouchDB.plugin(Find);
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
		svg(state){
				return js2xml(state.svg)
		}
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
				master_db.changes({
						since: 'now',
						live: true,
						include_docs: true
				}).on('change', function (change) {
						store.dispatch('allSVG')
						if (change.deleted) {
								console.log("db delete", change)
						} else {
								console.log("db modif", change)
						}
				}).on('error', function (err) {
						console.log("db errors", err)
				});
				['check_supercollider', 'allSVG'].forEach(e => store.dispatch(e))
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
