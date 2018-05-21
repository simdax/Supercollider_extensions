(
var path = "pdfs".resolveRelative;
var name = "io.mid";
var real_path = "%/%".format(path, name);
var midi_bin = "vendor/midi2ly2".resolveRelative;

{arg pattern;
	SimpleMIDIFile.fromPattern(pattern)
	.write(real_path);
	"% %".format(midi_bin, real_path).unixCmd;
})
