+ Contrapunctum {
	// logic
	calc{ arg m;
		var i = 1;
		var mvts = m.differentiate;
		while {i < (size - 1)}{
			var mvt_mel = mvts[i];
			var h = [
				this.rule_harmo(m[i], mvts[i], harmo[i - 1], 0),
				this.rule_harmo(m[i], mvts[i], harmo[i - 1], 4),
				1, 1]
			.collect(this.rule_mult(i));
			harmo = harmo.add(
				[0, 4, 2, 5].wchoose(this.check_pos(h)));
			i = i + 1;
		};
		harmo = harmo.add(0);
	}
	rule_harmo { arg note, harmo_prec, mvt_sign, cons;
		var mvt_harmo = note + cons - harmo_prec;
		^(
			(mvt_sign != mvt_harmo) &&
			([0, 4].includes(harmo_prec).not)
		).asInt;
	}
	check_pos{ arg h;
		if (h == [0, 0, 0, 0]) {
			// "what ??".postln;
			// h.postln;
			h = [1, 0, 0, 0]};
		^h.normalizeSum
	}
	rule_mult { arg j;
		var forbidden_vals = interdictions[j].asArray;
		^{ arg val, i;
			var res;
			var cons = [0, 4, 2, 5][i];
			forbidden_vals.do {arg forbidden_val;
				// no 5-6 chord
				if (((cons == 5) && (forbidden_val == 4)) ||
					((cons == 4) && (forbidden_val == 5)) ||
					// no same
					(cons == forbidden_val))
				{res = 0}
			};
			res ? val;
		}
	}
	rule_mvt{ arg cf, harmo, prec_note;
		var dist = (harmo + cf) - prec_note;
		^cf + if (dist > 3) {harmo - 7} { if (dist < -3) {harmo + 7} {harmo} }
	}
}