Lilypond {
	*new{ arg pattern;
		var path = "pdfs".resolveRelative;
		var name = Date.getDate.format("%d/%m/%Y%H:%M") ++ "_out.mid";
		var real_path = "%/%".format(path, name);
		var midi_bin = "vendor/midi2ly2".resolveRelative;

		SimpleMIDIFile.fromPattern(pattern)
		.write(real_path);
		"% %".format(midi_bin, real_path).unixCmd;
	}
}