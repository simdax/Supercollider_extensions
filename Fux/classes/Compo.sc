Ppar_options : Ppar{
	*new { arg patterns ... options;
		^super.new(this.init(patterns, options));
	}
	*init { arg patterns, options;
		var res;
		var f = (_.reduce(_ +++ _));
		var options_patterns =
		f.(options.asDict.values
			.sort({arg a,b; a.size > b.size}))
		.collect{ arg i;
			options.asDict.keys.asArray +++ i}
		.collect{arg i; Pbind(*i.flatten)};
		^(patterns +++ options_patterns).flatten
		.collect_pairs{arg pat1, pat2;
			pat1 <> pat2;
		}
	}
}
