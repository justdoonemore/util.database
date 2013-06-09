package com.jdom.database.javasql;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jdom.database.api.DatabaseUtil;
import com.jdom.database.rawsql.ConnectionWrapper;
import com.jdom.database.rawsql.PreparedStatementWrapper;
import com.jdom.database.rawsql.QueryPreparedStatementWrapper;

public class DatabaseTest {

	private final File file = new File(System.getProperty("java.io.tmpdir"),
			DatabaseTest.class.getName());

	private FileDatabaseVersion1 db = new FileDatabaseVersion1(file);
	private ConnectionWrapper connection;

	@Before
	public void setUp() throws Exception {
		file.delete();

		db.start();

		connection = db.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		db.shutdown();
	}

	@Test
	public void schemaCreationIsRunWhenDatabaseDoesntExist() throws Exception {
		PreparedStatementWrapper prepareStatement = connection
				.prepareStatement("INSERT INTO testtable VALUES(1, 'test');");
		try {
			prepareStatement.execute();
		} finally {
			DatabaseUtil.close(prepareStatement);
		}

		QueryPreparedStatementWrapper prepareQueryStatement = connection
				.prepareQueryStatement("SELECT * FROM testtable");
		try {
			prepareQueryStatement.execute();
			assertThat(prepareQueryStatement.getResultSet().getInt(1), is(1));
		} finally {
			DatabaseUtil.close(prepareQueryStatement);
		}
	}

	@Test
	public void schemaCreationIsNotRunWhenDatabaseExists() throws Exception {
		db.shutdown();
		db.start();
		connection = db.getConnection();

		// Would throw an exception if the table didn't exist
		QueryPreparedStatementWrapper prepareQueryStatement = connection
				.prepareQueryStatement("SELECT * FROM testtable");
		try {
			prepareQueryStatement.execute();
		} finally {
			DatabaseUtil.close(prepareQueryStatement);
		}
	}

	@Test
	public void upgradeScriptsRunWhenDatabaseIsOlderVersion() throws Exception {
		db.shutdown();
		db = new FileDatabaseVersion2(file);
		db.start();
		connection = db.getConnection();

		// Would throw an exception if the table didn't exist
		QueryPreparedStatementWrapper prepareQueryStatement = connection
				.prepareQueryStatement("SELECT * FROM version2table");
		try {
			prepareQueryStatement.execute();
		} finally {
			DatabaseUtil.close(prepareQueryStatement);
		}
	}

	@Test
	public void whenDatabaseIsUpgradedTheVersionIsIncremented()
			throws Exception {
		db.shutdown();
		db = new FileDatabaseVersion2(file);
		db.start();
		connection = db.getConnection();

		long dbVersion = db.getCurrentVersion();

		assertThat(dbVersion, is(equalTo(db.getSoftwareVersion())));
	}
}
