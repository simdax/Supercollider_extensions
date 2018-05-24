+ Pattern {
	pr_browse {arg ev, time, f;
//		f.isNil.if {"(%)% : % @ %".format(ev, time).postln}
		f.isNil.if {"% @ %".format(ev, time).postln}
		{f.(ev, time)};
	}
	browse { arg defaultEvent = Event.default, f,
		time = 0, maxEvents = 100;
		var event, count = 0;
		var stream = this.asStream;

		while { (event = stream.next( defaultEvent )).notNil &&
			{ (count = count + 1) <= maxEvents } }
		{
			event.use({
				if( event.isRest.not )
				{
					(if  (event.type.asClass.respondsTo('pr_browse'))
						{event.type.asClass} {this})
					.perform(\pr_browse, event, time, f);
					time = time + event.delta
				}
			});
		}
		^time;
	}
}

+ ListPattern {
	browse {
		arg defaultEvent = Event.default, f,
		time = 0, maxEvents = 100;

		list.do{ arg pattern;
			time = pattern.browse(defaultEvent, f, time, maxEvents);
		}
	}
}

+ Ppar {
	browse {
		arg defaultEvent = Event.default, f,
		time = 0, maxEvents = 100;

		list.do(_.browse(defaultEvent, f, time, maxEvents))
	}
}

+ Fux {
	*pr_browse { arg event, time, f;
		this.pr_event_pat.browse(event, f, time);
	}
}