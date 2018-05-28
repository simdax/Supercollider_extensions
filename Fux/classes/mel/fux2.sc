Fux2 : Mel2 {
	var <>seed;

	*new{ arg ... mel;
		(mel[0].class !== Contrapunctum).if
		{Error("Attention: no Contrapunctum unsupported").throw}
		^super.new(mel);
	}
	global_f{
		var mel, durs;
		var diff = (~next_mvt ? 0) - (~next_mvt ? 0).sign;

		if (~global_f.notNil){
			^~global_f.(diff);
		} {
			mel = if(~mel_f.notNil)
			{~mel_f.(diff)}{this.mel_f(diff)};
			durs = if(~rythm_f.notNil)
			{~rythm_f.(mel, ~r_nb)}{this.rythm_f(mel)};
			^[mel, durs]
		}
	}
	rythm_f {arg a;
		^a.collect(1);
	}
	mel_f { arg inter;
		^(0 .. inter);
	}
	p {
		var globs = this.global_f();
		var mel = globs[0], durs = globs[1];
		^Pbind(
			\degree, Pseq(mel + ~degree),
			\harmo, Pseq(mel + ~harmo) % 7,
			\dur, Pseq(durs.normalizeSum * ~dur),
		);
	}
}

Fux_r2 : Fux2 {
	var <>formulas;
	var <>rhythms;

	// *new{ arg mel, r, f;
	// 	^super.newCopyArgs(mel, f, r);
	// }
	rythm_f { arg mel;
		var res = mel.collect{1};
		^rhythms !? {rhythms[mel] ? res}
		?? {
			var hole = mel.size.nextPowerOfTwo - res.size;
			res.collect{arg v, i; v + if(i < hole){1}{0}};
		}
	}
	mel_f { arg inter;
		var res = (0..inter);
		^formulas !? {formulas[inter] ? res}
		?? { res }
	}
}