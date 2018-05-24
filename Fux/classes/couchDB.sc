CouchDB {
	var <>db;
	var <>id;
	var <rev;

	*new { arg db, id;
		db ?? {Error("no DB").throw};
		id ?? {Error("no ID").throw};
		^super.newCopyArgs(db, id).init();
	}
	init{
		var ret = this.get;
		rev = parseYAML(ret).at("_rev");
	}
	curl{ arg verb = \GET;
		^"curl -X % localhost:5984/%/% ".format(verb, db, id)
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
