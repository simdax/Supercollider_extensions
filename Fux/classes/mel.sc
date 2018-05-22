// Mel {
// 	var <>mel;
// 	var <>add = 0;
// 	var <>dict;
// 	var <proxy;
//
// 	*new{ arg ... mel;
// 		var instance;
// 		if (mel[0].isArray,
// 			{instance = super.newCopyArgs(mel[0])},
// 		{instance = super.newCopyArgs(mel)});
// 		Event.addEventType(this.name, this.play_f);
// 		^instance.init();
// 	}
// 	init{
// 		proxy = PatternProxy(Pseq(mel));
// 	}
// 	*play_f{
// 		^{this.pr_play_f.play};
// 	}
// 	*pr_play_f{ arg server;
// 		~type = \note;
// 		^currentEnvironment;
// 	}
// 	pattern{
// 		var m = proxy + add;
// 		^Pbind(
// 			\type, this.class.name,
// 			\degree, m,
// 			\next, Pnext(m),
// 			\prev, Pdiff(m),
// 		) <> (dict ? ());
// 	}
// 	repeat{ arg val;
// 		^this.pattern().repeat(val);
// 	}
// 	wrap { arg lo, hi;
// 		proxy.source = proxy.source.wrap(lo, hi);
// 		^this;
// 	}
// 	<> { arg d;
// 		dict = d;
// 	}
// 	-> { arg val;
// 		proxy.source = Pseq(val);
// 	}
// 	+ { arg val;
// 		add = val;
// 	}
// 	asStream{
// 		^this.pattern.asStream;
// 	}
// 	doesNotUnderstand{ arg sel ... args;
// 		{
// 			var pat = this.pattern;
// 			pat.performList(sel, args);
// 			^pat;
// 		}.try({
// 			("Je ne connais pas " + sel).warn;
// 		})
// 	}
// }