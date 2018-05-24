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

+ Array {
	collect_pairs{ arg f;
		var res = [];
		var i = 0;
		while {i < this.size} {
			res = res.add(f.(this[i], this[i + 1]));
			i = i + 2;
		}
		^res;
	}
}

+ Scale {
	*greeks{
		^[\ionian, \dorian, \phrygian, \lydian, \mixolydian, \minor]
		.collect(this.at(_)).choose
	}
}
