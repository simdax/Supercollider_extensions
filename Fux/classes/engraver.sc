// general object
// no need to use directly
Engrave {
	*new{ arg pattern, path, format, engine;
		^super.new.go(pattern, path, format, engine)
	}
	go { arg pattern, write_path, format = "svg", json_path;
		var path = "/tmp";
		var name = Date.getDate.format("%d-%m-%Y%H-%M")
		++ "_out.mid";
		var real_path = "%/%".format(path, name);
		var midi_bin = "../vendor/midi2ly2".resolveRelative;
		var script_path = "../vendor/annotations.js".resolveRelative;
		var midi = (pattern.class === Pattern).if
		{SimpleMIDIFile.fromPattern(pattern)} {pattern};

		"writing to : %".format(real_path).postln;
		midi.write(real_path);
		"% % % % % %".format(
			midi_bin, real_path,
			format, write_path,
			script_path, json_path)
		.postln.unixCmd;
	}
}

// re writing SimpleMidiFile from pattern
+ Pattern {
	pr_engrave{ arg time = 0, maxAmp = 1; // ??
		var tmode, defaultEvent, instruments = [];
		var midi = SimpleMIDIFile();

		if( midi.timeMode != \seconds )
		{ tmode = midi.timeMode;
			midi.timeMode = \seconds; };
		defaultEvent = Event.default;
		defaultEvent[ \velocity ] = {
			~amp.value.linlin(0, maxAmp, 1, 127,\minmax) };
		defaultEvent[ \midinote2 ] = {
			if( ~freq.isFunction.not )
			{ ~freq.cpsmidi }
			{ ~midinote.value }
		};
		defaultEvent[ \track ] = {
			if( ~instrument.value.isString,
				{ [ ~instrument.value ] },
				{ ~instrument.value.asCollection })
			.collect({ |inst|
				var track;
				track = instruments.indexOf( inst.asSymbol );
				if( track.notNil )
				{ track + 1 }
				{ instruments = instruments.add( inst.asSymbol );
					track = instruments.size;
					track;
				};
			});
		};

		// go
		this.browse(defaultEvent, { arg event, time;
			[
				event.midinote2,
				event.velocity,
				time.postln,
				event.sustain,
				event.upVelo, // addNote copies noteNumber if nil
				event.channel ? 0,
				event.track ?? {midi.format.min(1)}, // format 0: all in track 0
				false // don't sort (yet)
			].multiChannelExpand // allow multi-note events
			.do({ |array| midi.addNote( *array ) });
		});

		// sorting and post-processing
		midi.adjustTracks;
		midi.sortMIDIEvents;
		midi.adjustEndOfTrack;
		(midi.tracks - 1).do({ |i|
			if(instruments[i].notNil )
			{ midi.setInstName( instruments[i].asString, i+1 ); };
		});
		^midi;
	}

	engrave{ arg format = \svg, write_path;
		Engrave(this.pr_engrave, write_path, format);
	}
}