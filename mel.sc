Mel {
	var <>mel;
	var <>add = 0;
	var <>dict;
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
	pattern{
		var m = proxy + add;
		^Pbind(
			\type, this.class.name,
			\degree, m,
			\next, Pnext(m),
			//		\prev, 5,
		) <> (dict ? ());
	}
	repeat{ arg val;
		^this.pattern().repeat(val);
	}
	wrap { arg lo, hi;
		proxy.source = proxy.source.wrap(lo, hi);
		^this;
	}
	<> { arg d;
		dict = d;
	}
	-> { arg val;
		proxy.source = Pseq(val);
	}
	+ { arg val;
		add = val;
	}
	asStream{
		^this.pattern.asStream;
	}
	doesNotUnderstand{ arg sel ... args;
		{
			var pat = this.pattern;
			pat.performList(sel, args);
			^pat;
		}.try({
			("Je ne connais pas " + sel).postln;
		})
	}
}

Fux : Mel {
	var <>seed;

	*rythm_f {arg a;
		^a.collect({1});
	}
	*mel_f { arg inter;
		^(0 .. inter);
	}
	*play_f{ arg rep = inf;
		^{
			var mel = this.mel_f(~next - ~next.sign);
			var durs = this.rythm_f(mel);
			Pbind(
				\degree, Pseq(mel + ~degree),
				\dur, Pseq(durs.normalizeSum * ~dur),
				\scale, ~scale,
				\mtranspose, ~mtranspose,
				\instrument, ~instrument,
				\amp, ~amp,
			).play
		};
	}
}

Fux_r : Fux {
	*rythm_f {arg b;
		^switch(b.size,
			3, { [1, 2, 1]},
			5, { [1, 2, 1, 2]},
			6, { [1, 2, 1, 2]},
			7, { [1, 2, 2, 1, 1]},
			b.collect({1});
		)
	}
}