DB_Backup {
	classvar <ip = \localhost;
	classvar <port = 5984;
	classvar bin_path = "../vendor/couchdb-dump/couchdb-backup.sh";
	classvar db_path = "../db";

	// private
	*pr_line { arg action, name, output;
		^(bin_path.resolveRelative ++
			" -% -H % -d % -f %/%.json"
			.format(action, ip, name,
				db_path.resolveRelative, output ? name)).postln;
	}
	*pr_action{ arg action, db, backupName;
		this.pr_line(action, db, backupName).unixCmd(postOutput: true);
	}
	// interface
	*backup { arg db, backupName, force=false;
		if (force) {
						"rm -rf %/%.json"
			.format(db_path.resolveRelative, backupName).systemCmd};
		this.pr_action(\b, db, backupName);
	}
	*restore { arg ... args;
		this.pr_action(\r, *args);
	}
	// *print{ arg
	//
	// }
}
