// used to get and fix errors in a generated score

Patch {
	var pattern;

	*new{ arg pattern;
		^super.newCopyArgs(pattern).init();
	}
	init {}
	cons {
		^BigBrowser(pattern).showBy(\time, \degree).asDict
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

Patch_pattern : Pfunc {
	var <values;

	*new{ arg values, resetFunc;
		^super.new({}, resetFunc).init(values);
	}
	init{ arg val;
		var stream, next, counter = 0;
		values = val;
		stream = values.iter;

		next = stream.next;
		nextFunc = { arg ev;
			if (next.notNil){
				if (counter == next[0]) {
					ev.degree = ev.degree + next[1];
					next = stream.next;
				}
			};
			counter = counter + 1;
			ev.delta
		};
	}
}