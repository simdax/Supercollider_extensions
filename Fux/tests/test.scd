(
s.waitForBoot{

	a = (Array.fill(20, _.value)+ Array.rand(2, 0, 3))
	.integrate.mod(7);
	b = Contrapunctum(a);
	c = Contrapunctum(a);
	d = Contrapunctum(a);
	"../synths/synths.scd".resolveRelative.load;

	Pdef(\i,
		Ppar([
			Ppar([
				Fux_r(c.final - 7),
				Fux_r(b.final + 7),
//				Fux_r(a) - 14,
				// Fux_r(d.final),
			]).trace.repeat <> Pbind(
				\stk_inst, Pseq([6], inf),
				\instrument, Pseq([\piano], inf),
				// \mtranspose, Pseq([0, 0, 0, 0], inf),
				// \octave, Pseq([3, 5, 5, 6], inf),
				\amp, Pseq([1, 0.5, 0.5, 0.3], inf) * 0.75
			),
			// Pbind(\instrument, \noise, \dur, 1, ///\amp, 0.01),
			]) <> (scale: Scale.greeks, tempo: 3

		)
	).play
})
