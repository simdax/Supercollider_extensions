// used to get and fix errors in a generated score

Patch {
	var pattern;

	*new{ arg pattern;
		^super.newCopyArgs(pattern).init();
	}
	init {}
	cons {
		var g, k;

		g = BigBrowser(pattern).sort(\degree);
		k = g.collect{arg f; if(f[1].size > 1){f[1]}}
		.collect{ arg chord, i;
			if (chord.notNil) {
				var c = (chord % 7).asSet.asArray
				.sort.differentiate[1..]; // removeFirst
				if (c.includes(1)) {[i, c]}
		}}.reject(_.isNil);
		// da patch
		^k.collect{arg r; g[r[0]]}
	}
	gen {

	}
}