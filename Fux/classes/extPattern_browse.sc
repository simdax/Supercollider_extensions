+ Pattern {
	pr_browse {arg ev, time, f;
		"(%)% : % @ %".format(ev.type, ev.instrument, ev.degree, time).postln;
		f.(ev, time);
	}
	browse { arg defaultEvent = Event.default, f, time = 0, maxEvents = 100;
		var event, count = 0;
		var stream = this.asStream;

		while { (event = stream.next( defaultEvent )).notNil &&
			{ (count = count + 1) <= maxEvents } }
		{
			event.use({
				if( event.isRest.not )
				{
					var t = if (this.class === Ppar)
					{event.delta}{event.dur};
					(if (event.type.asClass.respondsTo('pr_browse'))
						{event.type.asClass} {this})
					.perform(\pr_browse, event, time, f);
					time = time + t;
				}
			});
		}
		^time;
	}
}

+ Pchain {

}

+ EventPatternProxy {

}

+ Fux {
	*pr_browse { arg event, time, f;
		this.pr_event_pat.browse(event, f, time);
	}
}