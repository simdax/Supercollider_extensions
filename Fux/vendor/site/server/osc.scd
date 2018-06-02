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

OSCdef(\stop, { arg a, b, c;
	Pdef(\play).stop
}, 'test/stop');

OSCdef(\play, { arg a, b, c;
	var res = "curl \'localhost:5984/summa_musicae/_design/1/_view/track_for_svg?key=\"%\"\' | jq .rows[0].id".format(a[1]).unixCmdGetStdOut();
	res.postln;
	s.waitForBoot({
		Pdef(\play, Morceau.newFrom(res)).play;
	})
}, 'test/play');

OSCdef(\patch, {arg a, b, c, d;
	var csdf = "curl \'localhost:5984/summa_musicae/_design/1/_view/patch_for_svg?key=\"%\"\' | jq -c \' .rows | .[] | .value \'".format(a[1]).unixCmdGetStdOut()
	.split($\n).collect(_.parseYAML)
	.reject(_.isNil).collect(_.asInt);
	csdf.postln;
}, '/test/patch');
)
