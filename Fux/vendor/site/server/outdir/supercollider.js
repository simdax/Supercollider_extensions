"use strict";
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result["default"] = mod;
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const child = __importStar(require("child_process"));
const osc_1 = __importDefault(require("./osc"));
const api_1 = __importDefault(require("./api"));
class SC extends api_1.default {
    constructor(socket) {
        super(socket);
        this.addresses = ['coucou'];
        this.osc = new osc_1.default("/test", 57120);
        //				this.start_supercollider()
        this.addresses.forEach(a => this.on(a));
    }
    log_color(color, msg) {
        console.log('\x1b[%sm%s\x1b[0m', color, msg); //cyan
    }
    start_supercollider() {
        let proc = child.spawn('./start.scd', ['localhost', '57127']);
        proc.stdout.setEncoding("utf8");
        proc.stdout.on('data', d => this.log_color('36', d));
        proc.stderr.on('data', d => this.log_color('35', d));
    }
    on(addr) {
        try {
            this.osc.osc.on(addr, osc_msg => {
                let val = {};
                let types = osc_msg.types.slice(1);
                for (let i in osc_msg.args)
                    val[types[i]] = osc_msg.args[i];
                this.socket.send(JSON.stringify(val));
            });
        }
        catch (e) {
            console.log(e);
        }
    }
    onmessage(d) {
        let data = JSON.parse(d);
        this.osc.send(data.name, data.args);
    }
}
exports.default = SC;
//# sourceMappingURL=supercollider.js.map