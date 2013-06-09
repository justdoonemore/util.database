package com.jdom.database.javasql;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jdom.database.rawsql.ConnectionWrapper;

public class FileDatabaseVersion1 extends JavaSqlDatabase {

	private static final String CREATE_SCHEMA = "CREATE TABLE testtable (id INTEGER PRIMARY KEY,name TEXT)";

	private ConnectionWrapper connection;

	private final File file;

	public FileDatabaseVersion1(File file) {
		this.file = file;
	}

	@Override
	protected List<String> getSchemaDdl() {
		return Arrays.asList(CREATE_SCHEMA);
	}

	@Override
	public ConnectionWrapper getConnection() throws SQLException {
		if (this.connection == null || connection.isClosed()) {
			this.connection = super.getConnection();
		}
		return this.connection;
	}

	@Override
	public long getSoftwareVersion() {
		return 1;
	}

	@Override
	protected String getJdbcUrl() {
		return "jdbc:sqlite:" + file.getAbsolutePath();
	}

	@Override
	protected String getDriverClass() {
		return "org.sqlite.JDBC";
	}

	@Override
	protected List<String> getUpgradeDeltaScripts(long i) {
		return Collections.emptyList();
	}

}
