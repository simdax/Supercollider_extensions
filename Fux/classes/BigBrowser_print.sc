+ BigBrowser {
	print {
		this.do({ arg time, object, index;
			[index, time, object].postln;
		})
	}
	extract_part { arg name;
		var res = ();
		this.do({ arg time, object, index;
			if (object.instrument == name)
			{ res[index] = object };
		});
		^res;
	}
	pr_extract_all_index{
		var res = ();
		this.do({ arg time, object, index;
			res[index] = object ++ (time: time);
		});
		^res;
	}
	pr_extract_all_time{
		var res = ();
		this.do({ arg time, object, index;
			res[time] = object ++ (index: index);
		});
		^res;
	}
	extract_all{ arg by = \index;
		^if (by === \time) {this.pr_extract_all_time}
		{this.pr_extract_all_index};
	}
	json{ arg s = \index;
		^JSON.stringify(this.extract_all(s));
	}
	putDb{ arg db = \music, id = 1;
		var res = CouchDB(db, id).post(this.extract_all);
		res.postln;
	}
	write{ arg path, s = \index;
		path = path ?
		PathName(thisProcess.nowExecutingPath)
		.pathOnly ++ "test.json";
		File.use(path, "w", {arg f;
			f.write(this.json(s));
			"json (by %) written in %".format(s, path).postln;
		});
	}
}