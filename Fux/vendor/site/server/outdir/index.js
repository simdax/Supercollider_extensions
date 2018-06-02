"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const app = require('express')();
require('express-ws')(app);
const api_1 = __importDefault(require("./api"));
const supercollider_1 = __importDefault(require("./supercollider"));
const parcel_bundler_1 = __importDefault(require("parcel-bundler"));
let bundler = new parcel_bundler_1.default('../front/index.html');
console.log("waiting for parcel to bundle");
bundler.bundle().then(a => {
    console.log("parcel :", a ? "done" : "error");
    app.ws('/', socket => {
        new api_1.default(socket);
    });
    app.ws('/supercollider', socket => {
        new supercollider_1.default(socket);
    });
    app.use(bundler.middleware());
});
app.listen(5000);
//# sourceMappingURL=index.js.map