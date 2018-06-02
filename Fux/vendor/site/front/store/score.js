import master_db from "./db"

export default {
		allSVG (store) {
				return master_db.allDocs({include_docs: true}).then(doc => {
						store.commit('ALL_SVGS', doc.rows.map(v => v.key))
						store.dispatch('getSVG', store.getters.last_one)
				})
		},
		getSVG (store, id) {
				console.log("getting", id)
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
