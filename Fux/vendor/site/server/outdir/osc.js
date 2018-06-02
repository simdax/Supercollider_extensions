"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const lodash_1 = require("lodash");
const OSC = require('osc-js');
class default_1 {
    constructor(addr, port, local = 41234) {
        this.address = addr;
        this.send_port = port;
        this.local_port = local;
        this.osc = new OSC({
            plugin: new OSC.DatagramPlugin({ open: { port: this.local_port },
                send: { port: this.send_port } })
        });
        this.osc.open();
    }
    send(addr, args) {
        const osc_message = new OSC.Message(this.address + '/' + addr, ...lodash_1.flatten(args));
        this.osc.send(osc_message);
    }
}
exports.default = default_1;
//# sourceMappingURL=osc.js.map