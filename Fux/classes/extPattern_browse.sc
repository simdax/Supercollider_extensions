+ Pattern {
	midi{ ^nil }
	pr_browse {arg ev, time, f;
		"% : % @ %".format(ev.type, ev.degree, time).postln;
		f.(ev, time);
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
					this.pr_browse(event, time, f);
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
	pr_browse { arg event, time, f;
		^this.class.pr_event_pat.browse(event, f, time) - time;
	}
}