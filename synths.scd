SynthDef(\noise,  {
	Out.ar([0, 1], BrownNoise.ar * EnvGen.kr(Env.perc, doneAction:2) * 0.1)
}).add;

SynthDef(\piano, { |out=0, freq=440, gate=1|
	var son = MdaPiano.ar(freq, gate, release: 0.9, stereo: 0.3, sustain: 0);
	DetectSilence.ar(son, 0.01, doneAction:2);
	Out.ar(out, son * 0.1);
}).add;

SynthDef(\stk, {arg stk_instr = 0, out=0, freq=220,gate=1,amp=1;
	    var sig,env;
	    env = EnvGen.kr(Env.asr(0,1,0.1),gate,doneAction:2);
	    sig = StkInst.ar(2, freq, gate, amp, 0.5)*env;
	    Out.ar(out, sig.dup);
}).add;

SynthDef(\hit,  { arg freq = 120, amp = 0.1;
	Out.ar([0, 1], SinOsc.ar(freq) * EnvGen.kr(Env.perc, doneAction:2) * amp)
}).add;
