#!/usr/bin/env sclang

var argv = thisProcess.argv.postln;
"Hello All !".postln;

NetAddr("localhost", 41234).sendMsg("/coucou", "");

OSCdef('/test',
{|msg, time, addr, recvPort| \unmatching.postln},
'/chat');
OSCdef('/test/2',
{|msg, time, addr, recvPort| \unmatching2.postln},
'/chat');

// s.waitForBoot({
// 	OSCFunc.trace;
// 	NetAddr(argv[0], argv[1].asInt).sendMsg("hello world | %".format(NetAddr.langPort));
// })
