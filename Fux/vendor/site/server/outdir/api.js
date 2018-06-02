"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const command_exists_1 = __importDefault(require("command-exists"));
class Api {
    constructor(socket) {
        this.socket = socket;
        for (let i of ['onopen', 'onclose',
            'onerror', 'onmessage'])
            this.socket[i] = (msg) => {
                this[i].bind(this)(msg.data || msg);
            };
    }
    onopen() { console.log("connected"); }
    onclose() { console.log("close"); }
    onerror(msg) { console.log("error", msg); }
    onmessage(msg) {
        console.log("received msg", msg);
        try {
            // so un-typescripty
            this[msg]();
        }
        catch (e) {
            console.log("ce message n'existe pas pour la classe", this.constructor.name);
        }
    }
    exists_sc() {
        command_exists_1.default('sclang').then(e => {
            this.socket.send("1");
        }).catch(e => {
            this.socket.send("0");
        });
    }
}
exports.default = Api;
//# sourceMappingURL=api.js.map