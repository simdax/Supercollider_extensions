Fux : Mel {
	var <>seed;
	var engrave = true;

	*global_f{
		var diff = ~next - ~next.sign;
		if (~global_f.notNil){
			^~global_f.(diff);
		} {
			var mel, durs;
			mel = if(~mel_f.notNil)
			{~mel_f.(diff)}
			{this.mel_f(diff)};
			durs = if(~rythm_f.notNil)
			{~rythm_f.(mel, ~r_nb)}
			{this.rythm_f(mel)};
			^[mel, durs]
		}
	}
	*rythm_f {arg a;
		^a.collect(1);
	}
	*mel_f { arg inter;
		^(0 .. inter);
	}
	*pr_event_pat {
		var globs = this.global_f();
		var mel = globs[0], durs = globs[1];
		^Pbind(
			\degree, Pseq(mel + ~degree),
			\dur, Pseq(durs.normalizeSum * ~dur),
			\scale, ~scale,
			\mtranspose, ~mtranspose,
			\instrument, ~instrument,
			\amp, ~amp,
		)
	}
}

Fux_r : Fux {
	*rythm_f {arg b;
		^switch(b.size,
			3, { [2, 1]},
			5, { [2, 1, 2, 1, 1]},
			6, { [2, 1, 2, 1, 2, 1]},
			7, { [2, 1, 2, 1, 2, 1, 1]},
			b.collect({1});
		)
	}
}