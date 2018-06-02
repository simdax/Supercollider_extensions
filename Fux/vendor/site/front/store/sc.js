export default {
 		check_supercollider(store){
				socket.send('start_sc').then(res => {
						store.commit('SUPERCOLLIDER', res)
				}).catch(err => {
						store.commit('SUPERCOLLIDER', 2)}) 
		},
		supercollider_msg(store, args){
				console.log("mais heu", args)
				socket.supercollider(JSON.stringify(args), e => {
						console.log("retour de baton", e)
				})				
		},		
}
