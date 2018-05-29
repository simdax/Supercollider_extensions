+ BigBrowser {
	pr_switch{ arg by, a, f1, f2;
		^switch(by, \index, {f1.value(a)}, \time, {f2.value(a)},
			{Error("unrecognized by").throw})
	}
	pr_post_process {arg collection, by ... keys;
		var f = {arg v; keys[0]
			!? {keys.collect{arg k; v[k]}}
			?? {v}};
		var f1 = _.collect(f);
		var f2 = _.collect(f1);
		^this.pr_switch(by, collection, f1, f2)
		.asSortedArray.flatten
	}
	pr_filter { arg by = \index, key, val;
		var f1 = _.select{arg v; v[key] == val};
		var f2 = _.collect(f1);
		^this.pr_switch(by, this.asDict(by), f1,
			_.reject(_.isEmpty) <> f2);
	}
	showBy { arg by ... post_key;
		^this.pr_post_process(this.asDict(by), by, *post_key);
	}
	sort_filter { arg by, key, val ... post;
		^this.pr_post_process(this.pr_filter(by, key, val), by, *post);
	}
}
