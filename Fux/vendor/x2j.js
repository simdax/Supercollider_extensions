#!/usr/bin/env node

var X2JS = require("x2js");
var fs = require('fs');
var x2js = new X2JS();
var arg1 = process.argv[2];
var arg2 = process.argv[3];

function go(type, text){
				let result;
								if(type === 'json')
												result = x2js.js2xml(text);
								else if(type === 'xml')
												result = JSON.stringify(x2js.xml2js(text));
								else
												throw new Error('unrecognized extension');
				console.log(result);
}

if (arg1 && arg2)
{
				fs.readFile(arg2, 'utf8', (err, data) => {
								if (err)
												throw new Error(err)
								else
											go(arg1, data)
				})
}
else
				throw new Error('missing argument');

