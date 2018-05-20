Pnext : FilterPattern {
	embedInStream { arg event;
		var stream = pattern.asStream;
		var curr = stream.next(event);
		var next;
		while {
			next = stream.next(event);
			curr.notNil;
		}{
			if (next.isNil, {event = 0.yield}, {
				event = (next - curr).yield
			});
			curr = next;
		}
		^event
	}
}

Mel {
	var <>mel;
	var <proxy;

	*new{ arg ... mel;
		var instance;
		if (mel[0].isArray,
			{instance = super.newCopyArgs(mel[0])},
			{instance = super.newCopyArgs(mel)});
		Event.addEventType(this.name, this.play_f);
		^instance.init();
	}
	init{
		proxy = PatternProxy(Pseq(mel));
	}
	*play_f{
		^{arg server;
			~type = \note;
			currentEnvironment.play;
		}
	}
	pattern{ arg add = 0, mul = 1, rep = 1;
		var m = (proxy + add) * mul;
		^Pbind(
			\type, this.class.name,
			\degree, m,
			\next, Pnext(m),
			//		\prev, 5,
			\dur, 1
		).repeat(rep);
	}
	repeat{ arg val;
		^this.pattern().repeat(val);
	}
	+= { arg val;
		proxy.source = proxy.source + val;
	}
	-> { arg val;
		proxy.source = Pseq(val);
	}
	+ { arg val;
		^this.pattern(val);
	}
	asStream{
		^this.pattern.asStream;
	}
	doesNotUnderstand{ arg sel ... args;
		{
			var pat = this.pattern.trace;
			pat.performList(sel, args);
			^pat;
		}.try({
			("Je ne connais pas " + sel).postln;
		})
	}
}

Fux : Mel {
	var <>seed;

	*play_f{ arg rep = inf;
		^{ arg server;
			var inter = ~next - ~next.sign;
			var a = (0 .. inter);
			// currentEnvironment.removeAt(\delta);
			// currentEnvironment.removeAt(\server);
			// currentEnvironment.postln;
			(Pbind(
				\degree, Pseq(a) + ~degree,
				\dur, ~dur / (inter.abs + 1),
				\scale, ~scale,
				\amp, ~amp,
				)
			).play
		};
	}
}