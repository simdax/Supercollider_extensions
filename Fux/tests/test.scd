
(s.waitForBoot{
	Pdef(\play, Pdef(\pat).repeat).play;
	Tdef(\bulk, {
		Pdef(\pat).engrave;
		BigBrowser(Pdef(\pat)).asDict(\index).keysValuesDo{
			arg k, v;
			CouchDB(\master_music, k).put(v, false);
			0.25.yield;
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
x = [b, c, d].collect{ arg val, i; Fux_r2(val)};
x = x.add(Mel2(a));

p =	Ppar_options(x,
	// \mel_f, [`[0, 1, 2], `[0], `[0], `[0, 2]],
	// \mel_f, Pseed(100.rand, Prand([`[0, 1], `[0]], inf)),
	// \rythm_f, `[1, 2, 1],
	\instrument, [\piano, \blips1, \piano, \organdonor].scramble,
	// \instrument, \piano,
	\channel, [0, 1, 2, 3],
	\mtranspose, [0, 0, 0, 0],
	\octave, [5, 4, 4, 3],
	\amp, Prand([0.2, 0.4, 0.6], Prand([1, 2, 4] * 4, inf)),
	\scale, `(Scale.greeks.degrees),
	\tempo, Array.rand(100, 0.7, 2).choose
	* Pwhite(0.8, 1.2).stutter(Prand([1, 2, 4] * 4, inf)),
);
BigBrowser(p).sort(\degree)

)

y = Patch(p).cons.flatten.asDict.postln;
Pdef(\pat, Pbind(\time, Ptime(), \finish, Pfunc{arg e;
	// e.time.postln;
	if(y.keys.includes(e.time)){
		y[e.time].postln;
		e.degree.postln;
}; true}) <> p,
//Pbind(\instrument, \noise, \dur, 1, \amp, 0.01),
);
Pdef(\pat).browse;
)
BigBrowser(p).asDict[12]
sort(\degree)

Pdef(\pat).play
