
export function gros_fetch(url, commit, action){
		fetch(url).then(e => e.json().then(data => {
				commit(action, data)							
		}))
}

export function mutation_basic(key){
		return (state, data) => {
				state[key] = data
		}
}

