(

m = 	"../midi.scd".resolveRelative.load;

s.waitForBoot{

	a = (Array.fill(20, _.value)+ Array.rand(2, 0, 3))
	.integrate.mod(7);
	b = Contrapunctum(a);
	c = Contrapunctum(a);
	d = Contrapunctum(a);
	"../synths/synths.scd".resolveRelative.load;

	p = Pdef(\i,
		Ppar([
			Ppar_options([
				Fux_r(c.final - 7),
				Fux_r(b.final + 7),
				Fux_r(a) - 14,
				Fux_r(d.final),
			],
			\stk_inst, [6],
			\instrument, [\piano],
			\mtranspose, [0, 0, 0, 0],
			\octave, [3, 5, 5, 6],
			\amp, [1, 0.5, 0.5, 0.3] * 0.75),
			// Pbind(\instrument, \noise, \dur, 1, ///\amp, 0.01),
		]) <> (scale: Scale.greeks, tempo: 3)
	);
	//p.play
	m.(p.pattern);
})

p.list