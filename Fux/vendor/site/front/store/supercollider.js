import socket from './socket'

export default {
		check_supercollider(store){
 				socket.send('exists_sc').then(res => {
						store.commit('SUPERCOLLIDER', res)
				}).catch(err => {
						store.commit('SUPERCOLLIDER', 2)}) 
		},
		supercollider_msg(store, args){
				if (this[args.name])
						this[args.name](args)
				else
						socket.supercollider(JSON.stringify(args), e => {
								console.log("retour de baton", e)
						})				
		}	
}
