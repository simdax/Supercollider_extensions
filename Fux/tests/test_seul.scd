(
s.waitForBoot{
	var d={ arg inter;
		(0..inter);
	};
	var r={ arg mel;
		[1];
	};
	Pdef(\tdt3,
		(Fux(Array.rand(10, 0, 4)).pattern.repeat
			<> Pbind(\mel_f, d, \rythm_f, r)
		)
	).play
}
)