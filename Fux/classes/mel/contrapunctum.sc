Contrapunctum : Pattern{
	var <>lo, <>hi, <>interdictions;
	var <mel;
	var <harmo;
	var <final;
	var size;

	*new{ arg mel = Array.rand(5, 0, 5), interdictions = [],
		lo = -5, hi = 5;
		if(mel.size < 2)
		{Error.throw("unsifficient size < 2")};
		^super.newCopyArgs(lo, hi, interdictions).init(mel);
	}
	init{ arg m;
		var i = 0;
		size = m.size;
		harmo = [0];
		final = [];
		mel = m;
		this.calc(mel);
		(m +++ harmo).flatten.pairsDo({ arg mvt_mel, harmo;
			final = final.add(this.rule_mvt(mvt_mel, harmo, final[i - 1] ? 0));
			i = i + 1;
		});
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
		out << "mel\t\t\t: " << mel << "\n";
		out << "interdiction: " << interdictions << "\n";
		out << "harmo\t\t: " << harmo << "\n";
		out << "final\t\t: " << final;
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
	asCompileString{
		// used for JSON conversion
		^(mel: mel, interdictions: interdictions, harmo: harmo, out: final)
	}
}
