(
a = CouchDB("music", 11);
a.send((coucou:"les loulous"));
)

parseYAML(a.get).at("_rev")
(error:3).class
.keysValuesDo{arg k, v; v.class.postln}
