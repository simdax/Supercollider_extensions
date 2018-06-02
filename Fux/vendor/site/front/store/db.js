import PouchDB from 'pouchdb'

class DB {
//		db:PouchDB
		
		constructor(){
				this.db = new PouchDB('http://localhost:5984/svgs')
		}
		get(id){ return this.db.get(id) }
		put(doc){ return this.db.put(doc) }
		allDocs(options){ return this.db.get(options) }
		changes(store) {
				this.db.changes({
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
		}
}

export default new DB()
