package com.jdom.database.rawsql;

import java.sql.SQLException;
import java.util.List;

import com.jdom.database.api.DatabaseUtil;
import com.jdom.logging.api.LogFactory;
import com.jdom.logging.api.Logger;

public abstract class BaseRawDatabase implements RawDatabase {

	private static final String CREATE_METADATA_TABLE = "CREATE TABLE metadata (version INTEGER PRIMARY KEY)";

	private final Logger log = LogFactory.getLogger(BaseRawDatabase.class);

	private ConnectionWrapper connection;

	@Override
	public void start() throws Exception {
		startInternal();

		connection = getConnection();

		createSchema();
	}

	@Override
	public void shutdown() throws SQLException {
		DatabaseUtil.close(connection);
	}

	private void createSchema() throws SQLException {

		long currentDbVersion = getCurrentVersion();
		long dbVersion = getSoftwareVersion();

		log.info("Current database version: " + currentDbVersion);
		log.info("Software database version: " + dbVersion);

		if (currentDbVersion == DatabaseUtil.UNSET) {
			log.info("Creating schema for database.");

			List<String> databaseSchema = getSchemaDdl();
			for (String string : databaseSchema) {
				executeSql(string);
			}

			executeSql(CREATE_METADATA_TABLE);

			log.info("Inserting database version: " + dbVersion);

			executeSql("INSERT INTO metadata VALUES(" + dbVersion + ");");
		} else if (currentDbVersion < dbVersion) {
			log.info("Upgrading database.");

			for (long i = currentDbVersion + 1; i <= dbVersion; i++) {
				List<String> deltaScripts = getUpgradeDeltaScripts(i);
				if (deltaScripts == null || deltaScripts.isEmpty()) {
					throw new IllegalStateException(
							"Unable to find delta script to upgrade to version "
									+ i + "!");
				}
				log.info("Upgrading database to version: " + i);
				for (String deltaScript : deltaScripts) {
					executeSql(deltaScript);
				}

				executeSql("UPDATE metadata set version = " + dbVersion + ";");
			}
		} else if (dbVersion < currentDbVersion) {
			log.warn("Database version is newer than software version, incompatibilities may result!");
		} else {
			log.info("Database is up to date.");
		}
	}

	private void executeSql(String string) throws SQLException {
		log.info("Running sql: " + string);
		connection.executeSql(string);
	}

	@Override
	public long getCurrentVersion() {
		QueryPreparedStatementWrapper stmt = null;
		ResultSetWrapper resultSet = null;

		try {
			stmt = connection
					.prepareQueryStatement("SELECT version from metadata;");
			stmt.execute();

			resultSet = stmt.getResultSet();
			long currentDbVersion = DatabaseUtil.UNSET;
			if (resultSet.next()) {
				currentDbVersion = resultSet.getLong(1);
			}
			return currentDbVersion;
		} catch (SQLException e) {
			// Means the schema needs to be created
			return DatabaseUtil.UNSET;
		} finally {
			DatabaseUtil.close(resultSet);
			DatabaseUtil.close(stmt);
		}
	}

	protected abstract void startInternal() throws Exception;

	protected abstract List<String> getSchemaDdl();

	protected abstract List<String> getUpgradeDeltaScripts(long toVersion);
}
