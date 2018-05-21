Pnext : FilterPattern {
	embedInStream { arg event;
		var stream = pattern.asStream;
		var curr = stream.next(event);
		var next;
		while {
			next = stream.next(event);
			curr.notNil;
		}{
			if (next.isNil, {event = 0.yield}, {
				event = (next - curr).yield
			});
			curr = next;
		}
		^event
	}
}

+ Scale {
	*greeks{
		^[\ionian, \dorian, \phrygian, \lydian, \myxolydian, \minor]
		.collect(this.at(_)).choose
	}
}
