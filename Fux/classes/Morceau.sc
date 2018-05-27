Morceau {
	var pattern;

	*new {
		^super.new().init();
	}
	init{
		var a, b, c, d, x, p;

		Server.local.waitForBoot{
			//
			//	Pdef(\play, Pdef(\pat).repeat).play;
			Tdef(\bulk, {
				Pdef(\pat).engrave;
				BigBrowser(Pdef(\pat))
				.asDict(\index).keysValuesDo{arg k, v;
					CouchDB(\master_music, k).put(v, false);
					0.008.yield;
				}
			}).play;
			// mel
			a = (Array.fill(14, _.value) +
				Array.rand(2, 0, 3)).integrate.mod(7);
			a[0] = 0;
			b = Contrapunctum(a);
			c = Contrapunctum(a, b.harmo);
			d = Contrapunctum(a, b.harmo +++ c.harmo);
			x = [b, c, d].collect{ arg val, i; Fux_r(val)};
			x = x.add(Mel(a));
			// pattern
			pattern = Pdef('pat', Ppar([
				Ppar_options(x,
					\instrument, [\piano, \piano, \piano, \piano],
					//		\instrument, [\organdonor, \piano, \organdonor, \piano],
					\channel, [0, 1, 2, 3],
					\mtranspose, [0, 0, 0, 0],
					\octave, [5, 5, 4, 3],
					\amp, [0.67, 0.6, 0.7, 1],
					//		\scale, `(Scale.greeks.degrees),
					\tempo, 0.5,
				),
				// Pbind(\instrument, \noise, \dur, 1, ///\amp, 0.01),
			]));
		}
	}
}