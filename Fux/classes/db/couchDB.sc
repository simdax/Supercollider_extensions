CouchDB{
	var <db;
	var <>id;
	var <rev;
	var <ip = \localhost;
	var <port = 5984;

	*new { arg db, id;
		db ?? {Error("no DB").throw};
		id ?? {Error("no ID").throw};
		^super.newCopyArgs(db, id).init();
	}
	init{
		rev = this.get.asString.parseYAML.at("_rev");
	}
	// interface
	curl{ arg verb = \GET;
		^"curl -X % %:%/%/% ".format(verb, ip, port, db, id)
	}
	create_db{
		"curl -X % %:%/%".format(\PUT, ip, port, db).unixCmd;
	}
	get{
		^this.curl.unixCmdGetStdOut.replace($\n, "");
	}
	add{ arg key, value;
		var doc = JSON.parse(this.get());
		doc[key] = value;
		this.put(doc);
	}
	put{ arg content, print = true;
		var send = rev !? {(_rev: rev)} ?? {()} ++ content;
		// wake up db if necessary
		this.create_db;
		// push content
		(this.curl(\PUT) ++ "-d " ++ send.json.shellQuote)
		.postln.unixCmd(postOutput: print);
	}
}
