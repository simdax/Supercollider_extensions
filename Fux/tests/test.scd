Morceau(3)

Pdef(\pat).play

(s.waitForBoot{
	Pdef(\play, Pdef(\pat).repeat).play;
	Tdef(\bulk, {
		Pdef(\pat).engrave;
		BigBrowser(Pdef(\pat)).asDict(\index).keysValuesDo{
			arg k, v;
			CouchDB(\master_music, k).put(v, false);
			0.1.yield;
		}
	}).play;
})

(
a = (Array.fill(14, _.value) +
	Array.rand(2, 0, 3)).integrate.mod(7)
.collect{ arg val;
	(val==6).if{[0, 1, 2, 3, 4, 5].choose}{val}
};
a[0] = 0;
b = Contrapunctum(a);
c = Contrapunctum(a, b.harmo);
d = Contrapunctum(a, b.harmo +++ c.harmo);
x = [b, c, d].collect{ arg val, i; Fux_r(val)};
x = x.add(Mel(a));
)

(
h = BigBrowser(x).asDict(\time).collect(_.size);
p =	Ppar_options(x,
	// \instrument, \piano,
	\instrument, [\marimba1, \piano, \organdonor].scramble,
	\channel, [0, 1, 2, 3],
	\mtranspose, [0, 0, 0, 0],
	\octave, [3, 4, 4, 5].scramble,
	\amp, Prand([0.2, 0.4, 0.6], Prand([4, 8, 16], inf)),
	\scale, `(Scale.greeks.degrees),
	\tempo, [0.6, 0.8, 0.1, 1.5, 2].choose
	* Pwhite(0.8, 1.2).stutter(Prand([4, 8], inf)),
);
Pdef(\pat, Ppar([p,
	Pbind(\instrument, \noise, \dur, 1, \amp, 0.01),
]));
)
