
// simple example

(

// create an XMLScore

z = XMLScore.new;

// add a voice to the XMLScore

z.add(y = XMLPart(id: 5, meter: XMLMeter(4, 4), key: XMLKey.major(\Bf)));

// create a B-flat major scale in quarter notes

// uses XMLVoice's internal 'clock' to keep track of beats

[58, 60, 62, 63, 65, 67, 69, 70].do({arg keynum;

	y.add(XMLNote(keynum, y.now, \q))

	});
z.add(y = XMLPart(id: 3, meter: XMLMeter(4, 4), key: XMLKey.major(\Bf)));

// create a B-flat major scale in quarter notes

// uses XMLVoice's internal 'clock' to keep track of beats

([58, 60, 62, 63, 65, 67, 69, 70] - 10).do({arg keynum;

	y.add(XMLNote(keynum, y.now, \q))

	});

// output the XMLScore

z.output("~/Desktop/test.xml".standardizePath);

)


// same as above, but with triplet eighth notes

(

z = XMLScore.new;

z.add(y = XMLPart(meter: XMLMeter(4, 4), key: XMLKey.major(\Bf)));

[58, 60, 62, 63, 65, 67, 69, 70].do({arg keynum;

	y.add(XMLNote(keynum, y.now, \e, 3/2)); // 3 eighths in the space of 2

	});

z.output("~/Desktop/test.xml".standardizePath);

)


// a short melodic example with changing meters, and tuplets in simple meters

(

var curkey, notedur, tuplet, curMeter;

// create an XMLScore

z = XMLScore.new;

// add a voice to the XMLScore

z.add(y = XMLPart(key: XMLKey.major(\Bf)));

y.addMeter(1, XMLMeter(4, 4));

y.addMeter(2, XMLMeter(6, 8, \compound)); // a compound meter

y.addMeter(4, XMLMeter(4, 4));


// uses XMLVoice's internal 'clock' to keep track of beats

while({

	// see XMLVoice for more utilities for getting values from a voice's attributes

	curkey = [58, 60, 62, 63, 65, 67, 69, 70].choose;

	curMeter = y.getMeterFromBeat(y.now);

	tuplet = (curMeter.type == \compound).if({1/1}, {3/2});

	y.add(XMLNote(curkey, y.now, \e, tuplet));

	(y.now < 13)

	});



// output the XMLScore

z.output("~/Desktop/test.xml".standardizePath);

)