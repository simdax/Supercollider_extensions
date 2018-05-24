(
a = (Array.fill(20, _.value) +
	Array.rand(2, 0, 3)).integrate.mod(7);
b = Contrapunctum(a);
c = Contrapunctum(a);
d = Contrapunctum(a).postln;
//"../synths/synths.scd".resolveRelative.load;

x = [b, c, d].collect{ arg val, i;
	var mel = Fux_r(val);
	BigBrowser(Pbind()).putDb(id:i.postln);
	mel
};
x = x.add(Fux_r(a));

// p = Pdef(\i,
p =	Ppar([
	Ppar_options(x,
		\stk_inst, [6],
		\instrument, \default,
		\channel, [0, 1, 2, 3],
		\mtranspose, [0, 0, 0, 0],
		\amp, [1, 0.5, 0.5, 0.3] * 0.75,
		//\scale, Scale.greeks,
		\tempo, 1,
	),
	// Pbind(\instrument, \noise, \dur, 1, ///\amp, 0.01),
]);
p.engrave;
BigBrowser(p).putDb(id:"master")
)

(s.waitForBoot{
	p.repeat.play
})