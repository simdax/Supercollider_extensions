Morceau {
	var <ur_mel;
	var <voix;
	var <pattern;

	*new { arg nb_voix = 1;
		if (nb_voix.class != Integer)
		{ Error("must be nb").throw };
		^super.newCopyArgs().init(nb_voix);
	}
	*newFrom{ arg id;
		var instance = super.new();
		var json = CouchDB(\ur_mel, id).get();
		^instance.newFrom(JSON.parse(json));
	}
	newFrom{ arg json;
		voix = [];
		ur_mel = json.mel;
		json.voix.do{ arg v;
			v = v.toEvent;
			v.keysValuesDo{
				voix = voix.add(Contrapunctum
					.newCopyArgs(-5, 5, v.interdictions,
						v.mel, v.harmo, v.out).postln);
			}
		};
		voix.postln;
		this.create_pattern;
	}
	init{ arg nb_voix;
		this.create_mels(nb_voix);
		this.create_pattern;
		this.push_db;
	}
	create_pattern{
		pattern = Ppar_options(
			voix.collect{ arg val, i; Fux_r(val)}.add(Mel(ur_mel)),
			*this.keys
		);
	}
	create_mels{ arg nb_voix;
		var i = 0;
		var interdictions = [];
		ur_mel = (Array.fill(14, _.value) + Array.rand(2, 0, 3))
		.integrate.mod(7).collect{ arg val;
			(val==6).if{[0, 1, 2, 3, 4, 5].choose}{val}
		};
		ur_mel[0] = 0;
		while {i < nb_voix} {
			voix = voix.add(Contrapunctum(ur_mel, interdictions));
			(interdictions == []).if
			{interdictions = voix[i].harmo}
			{interdictions = voix[i - 1].harmo +++ voix[i].harmo};
			i = i + 1;
		};
	}
	keys {
		^[\instrument, [\piano, \piano, \piano, \piano],
			//		\instrument, [\organdonor, \piano, \organdonor, \piano],
			\channel, [0, 1, 2, 3],
			\mtranspose, [0, 0, 0, 0],
			\octave, [5, 5, 4, 3],
			\amp, [0.67, 0.6, 0.7, 1],
			//		\scale, `(Scale.greeks.degrees),
			\tempo, 0.5]
	}
	push_db{
		CouchDB(\ur_mel, ur_mel.hash).put((mel: ur_mel, voix: voix));
		pattern.engrave;
	}
}