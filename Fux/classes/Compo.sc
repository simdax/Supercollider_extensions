Ppar_options : Ppar{
	*new { arg patterns ... options;
		^super.new(this.init(patterns, options));
	}
	*init { arg patterns, options;
		var res, values, keys, o_dict, options_patterns;
		var f = (_.reduce(_ +++ _));
		o_dict = options.asDict;
		values = o_dict.values.sort({arg a,b;
			a.size > b.size});
		keys = values.collect({arg a;
			o_dict.findKeyForValue(a)
		});
		options_patterns = f.(values)
		.collect{ arg i; keys +++ i}
		.collect{arg i; Pbind(*i.flatten/*.postln*/)};
		^(patterns +++ options_patterns).flatten
		.collect_pairs{arg pat1, pat2;
			pat1 <> pat2;
		}
	}
}
