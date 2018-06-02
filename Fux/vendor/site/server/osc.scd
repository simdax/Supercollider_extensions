OSCFunc.trace
m = NetAddr("127.0.0.1", 57120); // loopback


(
OSCdef(\prout, {arg a, b, c, d;
	// [a, b, c, d].postln;
	c.sendMsg("/coucou", "du",  243.3,"fds")
}, '/test/prout');
OSCdef(\create_mel, { arg a, b, c;
	Morceau(a[1]);
}, 'test/new_track');
OSCdef(\play, { arg a, b, c;
	var res = "curl \'localhost:5984/summa_musicae/_design/1/_view/track_for_svg?key=\"%\"\' | jq .rows[0].id".format(a[1]).unixCmdGetStdOut();
	res.postln;
	Morceau.newFrom(res).play;
}, 'test/play');
)

m.sendMsg('/test/prout', 3, 4, 2, 'fsadf', [2, 3, 1] ,4)