+ Pattern {
	browse{ arg defaultEvent = Event.default,
		f = {arg ev, time; "% % % ".format(ev, time, ev.delta).postln; ev.delta},
		time = 0, maxEvents = 100;

		var event, count = 0;
		var stream = this.asStream;

		while { (event = stream.next( defaultEvent )).notNil &&
			{ (count = count + 1) <= maxEvents } }
		{
			event.use({
				if( event.isRest.not ) // not a \rest
				{time = time + f.(event, time)};
			});
		}
		^time;
	}
}

+ Mel {
	browse { arg f, defaultEvent;
		this.pattern.browse(defaultEvent, { arg event, time;
			this.class.pr_play_f.browse(event, f, time)
		});
	}
}