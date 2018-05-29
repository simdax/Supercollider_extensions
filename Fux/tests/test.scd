
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
	// \instrument, [\piano, \blips1, \piano, \organdonor].scramble,
	// \instrument, \piano,
	\channel, [0, 1, 2, 3],
	\mtranspose, [0, 0, 0, 0],
	\octave, [5, 4, 4, 3],
	\scale, `(Scale.greeks.degrees),
	\amp, Prand([0.2, 0.4, 0.6], Prand([1, 2, 4] * 4, inf)),
	\tempo, Array.rand(100, 0.7, 2).choose
	* Pwhite(0.9, 1.1).stutter(Prand([1, 2, 4] * 4, inf)),
);
o = 0;
y = Patch(p).cons;
Pdef(\pat,
	Pbind(\finish, Pfunc{arg e;
	o = o + e.delta;
	if(y.keys.includes(o)){
		e.degree = e.degree + 1;
	};
	true}
) <> p
//Pbind(\instrument, \noise, \dur, 1, \amp, 0.01),
);
Pdef(\pat).engrave;
CouchDB(\master, \music).put(Pdef(\pat).b.asDict)

)

(s.waitForBoot{
	Pdef(\play, Pdef(\pat).repeat).play;
})