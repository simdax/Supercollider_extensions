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
		var instance = super.newCopyArgs(mel);
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
	pattern{ arg mel, rep = 1;
		var m;
		mel !? {m = Pseq(mel)} ?? {m = proxy} ;
		^Pbind(
			\type, this.class.name,
			\degree, m,
			\next, Pnext(m),
			//		\prev, 5,
			\dur, 1
		).repeat(rep);
	}
	+= { arg val;
		proxy = proxy + val;
	}
	+ { arg val;
		var m = mel + val;
		^this.pattern(m);
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
			(Pbind(
				\degree, Pseq(a) + ~degree,
				\dur, ~dur / (inter.abs + 1);
			)).play
		};
	}
}