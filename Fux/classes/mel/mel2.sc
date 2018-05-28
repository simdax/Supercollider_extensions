Mel2 : Pattern {
	var <proxy;
	var base_pattern;

	*new{ arg ... mel;
		//	Event.addEventType(this.name, this.play_f);
		^super.new.init(if (mel[0].isArray, {mel[0]}, {mel}));
	}
	init{ arg mel;
		proxy = PatternProxy(Pseq(mel));
		base_pattern = Pbind(
			\type, this.class.name,
			\degree, proxy,
		);
	}
	p{ arg ev;
		ev.use {
			^Pbind('degree', ~degree + 10).fin(2);
		}
	}
	embedInStream{ arg inval;
		var stream1 = base_pattern.asStream;
		var ev;

		while {
			ev = stream1.next(inval);
			ev.notNil;
		}{
			var stream2 = this.p(ev).asStream;
			while {
				inval = stream2.next(ev); inval.notNil
			}{ inval = inval.yield };
			inval = ev;
		}
		^inval;
	}
}
