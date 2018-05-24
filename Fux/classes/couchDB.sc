CouchDB {
	var <db;
	var <id;
	var <rev;
	var <ip = \localhost;
	var <port = 5984;

	// init
	*new { arg db, id;
		db ?? {Error("no DB").throw};
		id ?? {Error("no ID").throw};
		^super.newCopyArgs(db, id).init();
	}
	init{
		rev = parseYAML(this.get).at("_rev");
	}
	// interface
	curl{ arg verb = \GET;
		^"curl -X % %:%/%/% ".format(verb, ip, port, db, id).postln
	}
	get{
		^this.curl.unixCmdGetStdOut;
	}
	post{ arg content;
		var send = rev !? {(_rev: rev)} ?? {()} ++ content;
		^(this.curl(\PUT) ++ "-d " ++
			JSON.stringify(send).shellQuote)
		.postln.unixCmdGetStdOut;
	}
}
