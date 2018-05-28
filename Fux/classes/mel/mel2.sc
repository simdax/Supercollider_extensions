Mel2 : Pattern {
	var <proxy;
	var base_pattern;

	*new{ arg ... mel;
		^super.new.init(if (mel[0].isArray, {mel[0]}, {mel}));
	}
	init{ arg mel;
		proxy = PatternProxy(Pseq(mel));
		base_pattern = Pbind(
			\type, this.class.name,
			\degree, proxy,
		);
	}
	p{
		^Pbind().fin(1);
	}
	embedInStream{ arg inval;
		var stream1 = base_pattern.asStream;
		var stream2, ev;

		while {
			ev = stream1.next(inval);
			ev !? {ev.use {stream2 = this.p.asStream};
				true } ?? {false};
		}{
			inval = stream2.embedInStream(ev);
		}
		^inval;
	}
}
