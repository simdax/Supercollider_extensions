//simple creation test
(
a = BigBrowser().browse;
a = BigBrowser([Pbind()]).browse;
)

// check time priority queue
(
a = Pbind(\dur, Prand([0.1, 0.4, 0.8], 30));
z = Mel(0.1, 0.4, 0.8);
b = BigBrowser(pat).browse;
b.print;
c = BigBrowser(Ppar([a, a, Ppar([a])]));
c.browse;
c.print;
d = BigBrowser(Ppar([z, z, Ppar([z])]));
d.browse;
d.print;
)

(
y = Fux(Contrapunctum([0, 3, 2, 4, 2]));
e = BigBrowser(y);
e.browse;
e.print;
e = BigBrowser(Ppar([y, y, Ppar([y])]));
e.browse;
e.print;
)

Mel(0, 3, 4, 2, 4).browse
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