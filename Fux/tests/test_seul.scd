(
s.waitForBoot{
	var d={ arg inter;
		(0..inter);
	};
	var r={ arg mel;
		[1];
	};
	var g={ arg diff;
		[[0,5,2], [1,2,1]]
	};
	Pdef(\tdt3,
		(Fux(Array.rand(10, 0, 4)).pattern.repeat
			<> Pbind(\global_f, g);
			//<> Pbind(\mel_f, d, \rythm_f, r)
		)
	).play
}
)


# var a, b = [0, 1]; b;