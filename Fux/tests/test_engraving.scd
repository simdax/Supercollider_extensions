//simple test

(
a = Ppar([
	Pbind(\instrument, \a, \dur, 1),
	Pbind(\instrument, \b, \degree, 3, \dur, 2),
	Pbind(\instrument, \c, \degree, 2, \dur, Pseq([1, 2], inf)),
	Pbind(\instrument, \d, \degree, 5, \dur, 2),
]);
a.engrave;
)

(
Pbind(\degree, Prand([1, 2, 3, 4], inf), \dur, Pseq([1], 40)).browse;
a = (Mel([4, 3, 1])).browse;
a = (Fux(Array.rand(25, 0, 10))).browse;
a = (Fux([4, 3, 8, 1])).engrave;
a = (Mel([4, 3, 8, 1])).engrave;
)
