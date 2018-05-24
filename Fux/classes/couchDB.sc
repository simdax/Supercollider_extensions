CouchDB {
	var <db;
	var <id;
	var <rev;
	var <ip = \localhost;
	var <port = 5984;
	classvar <ip = \localhost;
	classvar <port = 5984;

	// init
	*new { arg db, id;
		db ?? {Error("no DB").throw};
		id ?? {Error("no ID").throw};
		^super.newCopyArgs(db, id).init();
	}
	// maybe check at creation ?
	// *create_db{
	// 	var res = this.curl(\PUT);
	// 	if()
	// }
	init{
		rev = this.get.asString.parseYAML.at("_rev");
	}
	// interface
	curl{ arg verb = \GET;
		^"curl -X % %:%/%/% ".format(verb, ip, port, db, id).postln
	}
	get{
		^this.curl.unixCmdGetStdOut.replace($\n, "");
	}
	put{ arg content;
		var send = rev !? {(_rev: rev)} ?? {()} ++ content;
		(this.curl(\PUT) ++ "-d " ++
			JSON.stringify(send).shellQuote)
		.postln.unixCmd(postOutput: true);
	}
}
