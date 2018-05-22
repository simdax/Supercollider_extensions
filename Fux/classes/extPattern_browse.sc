+ Pattern {
	browse{ arg defaultEvent = Event.default,
		f = {arg ev, time; "note : % @ %".format(ev.degree, time).postln; ev.delta},
		time = 0, maxEvents = 100;

		var event, count = 0;
		var stream = this.asStream;

		while { (event = stream.next( defaultEvent )).notNil &&
			{ (count = count + 1) <= maxEvents } }
		{
			event.use({
				if( event.isRest.not ) // not a \rest
				{time = time + f.(event, time)};
			//	"gros gros time : %".format(time).postln;
			});
		}
		^time;
	}
	engrave {
		Engrave(this);
	}
}

+ Mel {
	browse { arg f, defaultEvent;
		this.pattern.browse(defaultEvent, { arg event, time;
			var t2 = this.class.pr_play_f.browse(event, f, time);
			t2 - time;
		});
	}
}