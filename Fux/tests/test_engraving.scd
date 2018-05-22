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

(
Ppar([Pbind(\degree, [0, 3, 2].iter), Fux(1, 5, 6, 1)]).browse;
Ppar([
	Pbind(\degree, [-10, -10, -10].iter),
	Ppar([
		Pbind(\degree, [10, 10, 10].iter),
		Fux(1, 5, 6, 2)]);
]).engrave;
Ppar([
	Pbind(\instrument, \a, \degree, [0, 3, 2].iter),
	Fux(1, 5) <> (instrument: \b)]).engrave;
)

(
(Pbind() <> (dur: 1/3)).engrave
(Pbind() <> Pbind(\dur, Prand([1, 2, 4], inf))).engrave
Ppar([
	Pbind(\channel, 1, \degree, 10) <> Pbind(\dur, Prand([1, 2], inf)),
	Pbind(\channel, 0, \degree, 0) <> Pbind(\dur, Prand([1, 2], inf))
]).engrave
)

(
Pseed(10, Ppar_options([
	Pbind(\degree, 10) <> Pbind(\dur, Prand([1, 2, 4], inf)),
	Pbind(\degree, 0) <> Pbind(\dur, Prand([1, 2, 4], inf))
], \channel, [0, 1])).browse
)
