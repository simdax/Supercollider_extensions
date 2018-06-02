import * as child from 'child_process'
import * as fs from 'fs'
import OSC from './osc'
import API from './api'

export default class SC extends API {
		private osc:OSC;
		addresses:[string] = ['coucou']
		
		constructor (socket){
				super(socket)
				this.osc = new OSC("/test", 57120)
				//				this.start_supercollider()
				this.addresses.forEach(a => this.on(a))
		}
		private log_color(color:string, msg) {
				console.log('\x1b[%sm%s\x1b[0m', color, msg);  //cyan
		}
		private start_supercollider(){
				let proc = child.spawn('./start.scd', ['localhost', '57127'])
				proc.stdout.setEncoding("utf8")
				proc.stdout.on('data', d => this.log_color('36', d))
				proc.stderr.on('data', d => this.log_color('35', d))
		}
		private on(addr){
				try {						
						this.osc.osc.on(addr, osc_msg => {
								let val = {}
								let types = osc_msg.types.slice(1)
								for(let i in osc_msg.args)
										val[types[i]] = osc_msg.args[i]
								this.socket.send(JSON.stringify(val))
						})					
				}
				catch (e) { console.log(e); }
		}
		onmessage(d) {
				let data = JSON.parse(d)
 				this.osc.send(data.name, data.args)
		}
}
