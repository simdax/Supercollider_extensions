const app = require('express')()
require('express-ws')(app)
import Socket from "./api"
import SC from './supercollider'

import Bundler from 'parcel-bundler'
let bundler = new Bundler('../front/index.html')
console.log("waiting for parcel to bundle")
bundler.bundle().then(a => {
		console.log("parcel :", a ? "done": "error")
		app.ws('/', socket => {
				new Socket(socket)		
		})
		app.ws('/supercollider', socket => {
				new SC(socket)		
		})
		app.use(bundler.middleware());
});

app.listen(5000);
