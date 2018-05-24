Contrapunctum : Pattern{
	var <>lo, <>hi;
	var <mel;
	var <harmo;
	var <final;

	*new{ arg mel = Array.rand(5, 0, 5),
		lo = -5, hi = 5;
		if(mel.size < 2)
		{Error.throw("unsifficient size < 2")};
		^super.new(lo, hi).init(mel);
	}
	init{ arg m;
		var i = 1;
		harmo = [0];
		final = [];
		while {i < m.size}{
			var mvt_mel = m[i];
			var mvt_harmo = (mvt_mel - m[i - 1]).sign;
			var h = [
				this.rule_harmo(harmo[i - 1], mvt_mel, mvt_harmo),
				this.rule_harmo(harmo[i - 1], mvt_mel + 4, mvt_harmo),
				1, 1].normalizeSum;
			harmo = harmo.add([0, 4, 2, 5].wchoose(h));
			i = i + 1;
		};
		i = 0;
		(m +++ harmo).flatten.pairsDo({ arg mvt_mel, harmo;
			final = final.add(this.rule_mvt(mvt_mel, harmo, final[i - 1] ? 0));
			i = i + 1;
		});
		mel = m;
	}
	// logic
	rule_harmo { arg harmo_prec, mvt_sign, mvt_note_sign;
		^(
			(mvt_sign != mvt_note_sign) &&
			([0, 4].includes(harmo_prec).not)
		).asInt;
	}
	rule_mvt{ arg cf, harmo, prec_note;
		var dist = (harmo + cf) - prec_note;
		^cf + if (dist > 3) {harmo - 7} { if (dist < -3) {harmo + 7} {harmo} }
	}
	// OOP
	add{ arg item;
		final = final.add(item);
	}
	at{ arg index;
		^final[index];
	}
	size{
		^final.size;
	}
	do{ arg f;
		^final.do(f);
	}
	printOn{ arg out;
		out << this.class.name << "\n";
		out << "mel\t\t: " << mel << "\n";
		out << "harmo\t: " << harmo << "\n";
		out << "final\t: " << final;
	}
	embedInStream{ arg inval;
		var i = 0;
		var diffs = final.differentiate;
		while {final[i].notNil}{
			inval.harmo = harmo[i];
//			inval.mvt = diffs[i];
			inval.next_mvt = diffs[i + 1] ? 0;
			inval = final[i].yield;
			i = i + 1;
		}
		^inval;
	}
	// doesNotUnderstand{arg sel ... args;
	// 	^final.performList(sel, args);
	// }
}
