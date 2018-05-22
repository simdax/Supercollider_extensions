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