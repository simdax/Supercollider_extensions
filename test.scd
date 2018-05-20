(
var m = [3, 1, 2, 0, 4, 5].scramble.wrap(-2, 2);
var mm = m.collect{ arg v; v + [-2, 0, 2].choose}.wrap(-3, 3);
var mmm = m.collect{ arg v; v + [-2, 0, 2].choose}.wrap(-3, 3);
SynthDef(\noise,  {
	Out.ar([0, 1], BrownNoise.ar * EnvGen.kr(Env.perc, doneAction:2) * 0.1)
}).add;
SynthDef(\hit,  {
	Out.ar([0, 1], SinOsc.ar(100) * EnvGen.kr(Env.perc, doneAction:2) * 0.1)
}).add;
Pdef(\i,
	Ppar([
		Ppar([
			Fux(m),
			Fux(mm) + 7,
			Fux(m) + 16,
			Fux(mm) + 7,
			Fux(mmm) + 14,
		]).repeat <> (amp: 0.1),
		Pbind(\instrument, \noise, \dur, 1/1),
		Pbind(\amp, 4, \instrument, \hit, \dur, 1/2)
		]) <> (scale: Scale.minor)
).play
)
