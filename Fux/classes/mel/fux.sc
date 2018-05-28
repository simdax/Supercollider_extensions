Fux : Mel {
	var <>seed;
	classvar <>trace = false;
	var engrave = true;

	*global_f{
		var diff = (~next_mvt ? 0) - (~next_mvt ? 0).sign;
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
		var pat = Pbind(
			\type, \note,
			\degree, Pseq(mel + ~degree),
			\harmo, Pseq(mel + ~harmo) % 7,
			\dur, Pseq(durs.normalizeSum * ~dur),
			\scale, ~scale,
			\mtranspose, ~mtranspose,
			\instrument, ~instrument,
			\amp, ~amp,
		);
		^if (trace){pat.trace}{pat}
	}
	trace{
		this.class.trace = true;
	}
}

Fux_r : Fux {
	classvar <>formulas;
	classvar <>rhythms;

	*new{ arg mel, r, f;
		formulas = f;
		rhythms = r;
		^super.new(mel);
	}
	*rythm_f { arg mel;
		var res = mel.collect{1};
		^rhythms !? {rhythms[mel] ? res}
		?? {
			var hole = mel.size.nextPowerOfTwo - res.size;
			res.collect{arg v, i; v + if(i < hole){1}{0}};
		}
	}
	*mel_f { arg inter;
		var res = (0..inter);
		^formulas !? {formulas[inter] ? res}
		?? { res }
	}
}