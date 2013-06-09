package com.jdom.database.javasql;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.jdom.database.rawsql.BaseRawDatabase;
import com.jdom.database.rawsql.ConnectionWrapper;

public abstract class JavaSqlDatabase extends BaseRawDatabase {

	@Override
	protected void startInternal() throws Exception {
		Class.forName(getDriverClass());
	}

	@Override
	public ConnectionWrapper getConnection() throws SQLException {
		return new JavaSqlConnectionWrapper(
				DriverManager.getConnection(getJdbcUrl()));
	}

	protected abstract String getJdbcUrl();

	protected abstract String getDriverClass();
}