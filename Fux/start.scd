#!/usr/bin/env sclang

s.waitForBoot({
				thisProcess.argv.postln;
				NetAddr(argv[0], argv[1]).send("hello world | %".format(NetAddr.langPort));
})
