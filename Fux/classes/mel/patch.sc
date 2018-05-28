// used to get and fix errors in a generated score

Patch {
	var pattern;

	*new{ arg pattern;
		^super.newCopyArgs(pattern).init();
	}
	init {}
	cons {
		^BigBrowser(pattern).sortBy(\time, \degree).asDict
		.collect{arg f; if(f.size > 1){f.flatten}}
		.select{ arg chord, i;
			if (chord.notNil) {
				var c = (chord % 7).asSet.asArray
				.sort.differentiate[1..]; // removeFirst
				c.includes(1)
		}};
	}
	gen {

	}
}