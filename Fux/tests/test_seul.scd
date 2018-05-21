(
s.waitForBoot{
	var d = { arg inter;
		(0..inter);
	};
	var r = { arg mel;
		[1];
	};
	var g = { arg diff;
		[[0,\r,\r,-1,\r], [0.5, 1,1,1,2,1]]
	};
	Pdef(\tdt3,
		(Fux(Array.rand(10, 0, 4)).pattern.repeat
			<> Pbind(\global_f, g);
			//<> Pbind(\mel_f, d, \rythm_f, r)
		)
	).play
}
)

(
var ex =[
	\bib, [1, 2, 3, 4],
	\gd, [54, 3],
	\sdfg, [\d],
	\fdsafsd, [4, 2, 4, 2,5, 4]
];
var f = { arg a;
	var f = (_.reduce(_ +++ _));
	f.(ex.asDict.values.sort({arg a,b; a.size > b.size})).collect{ arg i;
		ex.asDict.keys.asArray +++ i
	}.collect{arg i; Pbind(*i.flatten)}
};
f.(ex);
)