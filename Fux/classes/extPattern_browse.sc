Pbind_print : Pbind {
	var midi;

	pr_browse { arg event, time, midi;
		[
			event.midinote2,
			event.velocity,
			time,
			event.sustain,
			event.upVelo, // addNote copies noteNumber if nil
			event.channel ? 0,
			event.track ?? {midi.format.min(1)}, // format 0: all in track 0
			false // don't sort (yet)
		].multiChannelExpand // allow multi-note events
		.do({ |array| midi.addNote( *array ) });
	}
}

+ Pattern {
	midi{ ^nil }
	pr_browse {arg ev, time;
		"% : % @ %".format(ev.type, ev.degree, time).postln;
	}
	browse { arg defaultEvent = Event.default, f, time = 0, maxEvents = 100;
		var event, count = 0;
		var stream = this.asStream;

		while { (event = stream.next( defaultEvent )).notNil &&
			{ (count = count + 1) <= maxEvents } }
		{
			event.use({
				if( event.isRest.not ) // not a \rest
				{
					f !? {f.(event, time)} ??
					{this.pr_browse(event, time, this.midi)};
					time = time + event.delta;
				}
			});
		}
		^time;
	}
	engrave {
		Engrave(this);
	}
}

+ Fux {
	pr_browse { arg event, time;
		^this.class.pr_event_pat.browse(event, time: time) - time;
	}
}