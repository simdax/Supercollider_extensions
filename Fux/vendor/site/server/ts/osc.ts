import {flatten} from 'lodash'
const OSC = require('osc-js')

export default class {
		osc;
		address: string;
		send_port: number;
		local_port: number;
		
		constructor (addr:string, port:number, local:number = 41234){
				this.address = addr;
				this.send_port = port;
				this.local_port = local;
				this.osc = new OSC({
						plugin: new OSC.DatagramPlugin(
								{ open: { port: this.local_port },
									send: { port: this.send_port } })
				});
				this.osc.open()
		}
		send(addr:string, args){
				const osc_message = new OSC.Message(
						this.address + '/' +  addr, ...flatten(args));
				this.osc.send(osc_message); 								
		}
}
