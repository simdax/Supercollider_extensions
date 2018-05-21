(
var mel = Array.rand(50, 0, 3);
p = Ppar_options([
	Fux_r(mel - 7),
	Fux_r(mel + 7),
	Fux_r(mel),
	Fux_r(mel),
],
\stk_inst, 6,
\instrument, \piano,
\mtranspose, [0, 0, 0, 0],
\octave, [3, 5, 5, 6],
\amp, [1, 0.5, 0.5, 0.3] * 0.75);

// p.trace.play;
//[0].pattern.patterns[1]

)