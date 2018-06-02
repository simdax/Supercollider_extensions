export default class {
		constructor(){
				this.callbacks = {};
				this.socket = new WebSocket('ws://localhost:5000')
 				this.supercollider_socket = new WebSocket('ws://localhost:5000/supercollider')
		}
		send(msg, timeout = 10000){
				return new Promise((res, rej) => {
						if (!this.socket.readyState)
								this.socket.onopen = () => {
										console.log("not ready yet")
										res(this.send(msg, timeout))
								}
						else
						{
								this.socket.send(msg)
								this.socket.onmessage = (msg) => res(msg.data)
								setTimeout(() => rej("too long response"),
													 timeout)
						}
				})
		}
		supercollider(msg, handler){
				this.supercollider_socket.onmessage = handler
				this.supercollider_socket.send(msg)
		}
}
