BigBrowser {
	var <>bigData;
	var score;
	var db = "music";
	var id = 1;

	*new { arg ... patterns;
		patterns.isEmpty.if {
			patterns = [Pbind()]}
		{
			patterns[0].isArray.if {
				"Warning: keeping only first array".warn;
				patterns = patterns[0]}
		};
		^super.new().init(patterns);
	}
	init{ arg patterns;
		score = Ppar(patterns);
		bigData = PriorityQueue();
	}
	// core function
	browse {
		if (bigData.isEmpty){
			score.browse(f: { arg event, time;
				bigData.put(time, event);
			})
		};
	}
	// utils
	do{ arg f, as=\time;
		var i = 0;
		this.browse(as);
		while {bigData.notEmpty} {
			f.value(bigData.topPriority, bigData.pop, i);
			i = i + 1;
		}
	}
	pr_post_process {arg collection, by, key;
		var f = {arg v; key.asArray.collect{arg k; v[k]}};
		^switch(by, \index, {collection.collect(f)},
			\time, {collection.collect(_.collect(f))},
			{Error("unrecognized by").throw})
		.asSortedArray
	}
	sort { arg by = \instrument;
		^this.pr_post_process(this.asDict(\time), \time, by);
	}
	filter { arg by = \index, key, val, post;
		^switch (by, \index, {
			this.pr_post_process(
				this.asDict(by).select{arg v;
					v[key] == val
				}, by, post)
		}, \time, {
			this.pr_post_process(
				this.asDict(by).collect{arg v;
					v.select{arg v; v[key] == val}
				}.reject(_.isEmpty), by, post)
		}, {Error("unrecognized by").throw});
	}
}