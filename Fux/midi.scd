(
var path = "pdfs".resolveRelative;
var name = "io.mid";
var real_path = "%/%".format(path, name);

a = SimpleMIDIFile.fromPattern(Pfindur(1, Pbind()));
a.write(real_path);
"./% %".format("vendor/midi2ly2".resolveRelative, real_path).unixCmd;
)
