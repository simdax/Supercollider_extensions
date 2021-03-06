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
	// core functions
	browse {
		if (bigData.isEmpty){
			score.browse(f: { arg event, time;
				bigData.put(time, event);
			})
		};
	}
	do{ arg f, as=\time;
		var i = 0;
		this.browse(as);
		while {bigData.notEmpty} {
			f.value(bigData.topPriority, bigData.pop, i);
			i = i + 1;
		}
	}
}