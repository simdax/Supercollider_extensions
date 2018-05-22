Mel : Pbind {
	var <proxy;

	*new{ arg ... mel;
		Event.addEventType(this.name, this.play_f);
		^super.new.init(if (mel[0].isArray, {mel[0]}, {mel}));
	}
	init{ arg mel;
		proxy = PatternProxy(Pseq(mel));
		patternpairs = [
			\type, this.class.name,
			\degree, proxy,
			\next, Pnext(proxy),
//			\prev, Pdiff(proxy),
		];
	}
	*classes{
		^[\Fux]
	}
	*play_f{
		^{this.pr_event_pat.play};
	}
	*pr_event_pat{ arg server;
		~type = \note;
		^currentEnvironment;
	}
}