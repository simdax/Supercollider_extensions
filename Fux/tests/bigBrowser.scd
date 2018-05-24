(
a = BigBrowser().browse;
a = BigBrowser([Pbind()]).browse;
)

(
a = BigBrowser(Pbind(\instrument, 'bob', \dur, 2).fin(3),
	Pbind(\instrument, 'bobi', \dur, 4, \degree, 4).fin(3));
b = a.extract_part(\bob).postln;
b= a.extract_part(\bobi).postln;
)

(
a = BigBrowser(Pbind(\prout, 4));
// a.json;
// a.write;
a.putDb;
)