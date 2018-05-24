(
a = Contrapunctum().postln;
Pbind(\io, a).trace.play;
)

// single
(
a = Contrapunctum().postln;
Fux_r(a).browse;
// Fux_r(a).trace.play;
)

// with ppar
(
a = Contrapunctum().postln;
b = Ppar([Ppar([Fux(a)])]);
e = BigBrowser(b);
b.browse;
e.browse;
)