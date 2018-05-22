(
a = (Array.fill(20, _.value)+ Array.rand(2, 0, 3)).integrate.mod(7);
b = Contrapunctum(a);
c = Contrapunctum(a);
d = Contrapunctum(a);
"../synths/synths.scd".resolveRelative.load;

p = Pdef(\i,
	Ppar([
		Ppar_options([
			Fux_r(b.final),
			Fux_r(a),
			// Fux_r(d.final),
			// Fux_r(c.final - 2),
		],
		\stk_inst, [6],
		\instrument, [\piano, \piano, \b, \c],
		\mtranspose, [0, 0, 0, 0],
		\octave, [5, 4, 4, 3],
		\amp, [1, 0.5, 0.5, 0.3] * 0.75),
		// Pbind(\instrument, \noise, \dur, 1, ///\amp, 0.01),
	]) <> (scale: Scale.greeks, tempo: 1)
);
//p.browse;
)

(s.waitForBoot{
	p.repeat.play
})