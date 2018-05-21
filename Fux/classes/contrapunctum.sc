Contrapunctum{
	var <>lo, <>hi;
	var <mel;
	var <harmo;
	var <final;

	*new{ arg mel, lo = -5, hi = 5;
		if(mel.size < 2)
		{Error.throw("unsifficient size < 2")};
		^super.new(lo, hi).init(mel);
	}
	// logic
	rule_harmo { arg harmo_prec, mvt_sign, mvt_note_sign;
		^((mvt_sign != mvt_note_sign) && ([0, 4].includes(harmo_prec).not)).asInt;
	}
	rule_mvt{ arg cf, harmo, prec_note;
		var dist = (harmo + cf) - prec_note;
		^cf + if (dist > 3) {harmo - 7} { if (dist < -3) {harmo + 7} {harmo} }
	}
	init{ arg m;
		var i = 1;
		harmo = [0];
		final = [];
		while {i < m.size}{
			var note = m[i];
			var mvt_mel = (note - m[i - 1]).sign;
			var h = [
				this.rule_harmo(harmo[i - 1], note, mvt_mel),
				this.rule_harmo(harmo[i - 1], note + 4, mvt_mel),
				1, 1].normalizeSum;
			harmo = harmo.add([0, 4, 2, 5].wchoose(h));
			i = i + 1;
		};
		i = 0;
		(m +++ harmo).flatten.pairsDo({ arg note, harmo;
			final = final.add(this.rule_mvt(note, harmo, final[i - 1] ? 0));
			i = i + 1;
		});
		mel = m;
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
		out << "mel :" << mel << "\n";
		out << "harmo :" << harmo << "\n";
		out << "final :" << final;
	}
	doesNotUnderstand{arg sel ... args;
		^final.performList(sel, args);
	}
}
