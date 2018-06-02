"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
class Api {
    constructor(socket) {
        this.api = {
            onopen() { console.log("connection"); },
            onclose(msg) { console.log("error"); },
            onerror(msg) { console.log("error"); },
            onmessage(msg) {
                console.log("msg", msg.data);
                this.msg_hook(msg.data);
            }
        };
        for (let i in this.api) {
            socket[i] = this.api[i];
        }
    }
}
exports.default = Api;
(msg) => {
    console.log("rien mon gros loulou");
};
//# sourceMappingURL=mainapi.js.map