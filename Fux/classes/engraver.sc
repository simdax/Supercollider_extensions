// general object
// no need to use
Engrave {
	*new{ arg pattern, write_path;
		// var path = "../pdfs".resolveRelative;
		var path = "/tmp";
		var name = Date.getDate.format("%d-%m-%Y%H-%M")
		++ "_out.mid";
		var real_path = "%/%".format(path, name);
		var midi_bin = "../vendor/midi2ly2".resolveRelative;
		var midi = SimpleMIDIFile.fromPattern(pattern);

		"writing to : %".format(real_path).postln;
		midi.write(real_path);
		"% % %".format(midi_bin, real_path, write_path ? "").postln.unixCmd;
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
		//
		// if( midi.format > 0 )
		// {
		defaultEvent[ \track ] = { // auto recognize tracks by instrument
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
		//		};

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

		// if( format > 0 )
		// { // add instrument names if found
		(midi.tracks - 1).do({ |i|
			if(instruments[i].notNil )
			{ midi.setInstName( instruments[i].asString, i+1 ); };
		});
		^midi;
		// };
		//
		// if( tmode.notNil ) { midi.timeMode = tmode }; // change back to original
	}

	engrave{ arg write_path;
		// var path = "../pdfs".resolveRelative;
		var path = "/tmp";
		var name = Date.getDate.format("%d-%m-%Y%H-%M")
		++ "_out.mid";
		var real_path = "%/%".format(path, name);
		var midi_bin = "../vendor/midi2ly2".resolveRelative;
		var midi = this.pr_engrave;

		"writing to : %".format(real_path).postln;
		midi.write(real_path);
		"% % %".format(midi_bin, real_path, write_path ? "").postln.unixCmd;
	}
}