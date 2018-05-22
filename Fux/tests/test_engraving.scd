//simple test

(
a = Ppar([
	Pbind(\instrument, \a, \dur, 1),
	Pbind(\instrument, \b, \degree, 3, \dur, 2),
	Pbind(\instrument, \c, \degree, 2, \dur, Pseq([1, 2], inf)),
	Pbind(\instrument, \d, \degree, 5, \dur, 2),
]);
Engrave(a);
)

(
Pbind(\dur, Pseq([1])).browse;
)

(
// a = (Fux([4, 3, 1])).browse;
a = (Fux([4, 3, 1])).engrave;
)

a = SimpleMIDIFile().fromPattern(Pbind())
a.write("/home/simdax/coucou")

// with Pdef phrase type

(
Pdef(\test, {
	Pbind(\dur, 0.3, \degree, Prand([0, 3, 1, 4], inf));
});
a = Ppar([
	Pbind(\type, \phrase, \instrument, \test, \dur, 2),
]);

)
