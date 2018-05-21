(
s.waitForBoot{

	a = (Array.fill(10, _.value) + [1,2,1,5,1,3].rotate(1)).integrate.mod(7);
	b = Contrapunctum(m);
	c = Contrapunctum(m);
	d = Contrapunctum(m);
	"synths.scd".resolveRelative.load;

	Pdef(\i,
		Ppar([
			Ppar([
				Fux_r(a),
				Fux_r(d.final), //+ Pseq([0, 7, 7, 7], inf),
				Fux_r(d.final) + 14,
				Fux_r(d.final) - 7,
			]).trace.repeat <> Pbind(
				\stk_inst, Pseq([6], inf),
				\instrument, Pseq([\piano, \piano], inf),
				// \mtranspose, Pseq([0, 0, 2, 0], inf),
				// \octave, Pseq([4, 5, 5, 6], inf),
				\amp, Pseq([1], inf) * 0.75
			),
//			Pbind(\instrument, \noise, \dur, 1, \amp, 0.01),
			]) <> (scale: Scale.greeks)
	).play
})
	c = Mel(n).proxy.pattern.iter.next;
	a = Pseq(n); a.iter.next
	b = PatternProxy(a).pattern.iter.next