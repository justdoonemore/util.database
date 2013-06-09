package com.jdom.database.javasql;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDatabaseVersion2 extends FileDatabaseVersion1 {

	private static final String CREATE_VERSION2_TABLE = "CREATE TABLE version2table (id INTEGER PRIMARY KEY,name TEXT)";

	public FileDatabaseVersion2(File file) {
		super(file);
	}

	@Override
	protected List<String> getSchemaDdl() {
		List<String> ddl = new ArrayList<String>();
		ddl.addAll(super.getSchemaDdl());
		ddl.add(CREATE_VERSION2_TABLE);

		return ddl;
	}

	@Override
	public long getSoftwareVersion() {
		return 2;
	}

	@Override
	protected List<String> getUpgradeDeltaScripts(long toVersion) {
		if (toVersion == getSoftwareVersion()) {
			return Arrays.asList(CREATE_VERSION2_TABLE);
		} else {
			return super.getUpgradeDeltaScripts(toVersion);
		}
	}
}
