/** 
 *  Copyright (C) 2013  Just Do One More
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jdom.database.javasql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdom.database.api.DatabaseUtil;
import com.jdom.database.rawsql.ConnectionWrapper;
import com.jdom.database.rawsql.PreparedStatementWrapper;
import com.jdom.database.rawsql.QueryPreparedStatementWrapper;

/**
 * @author djohnson
 * 
 */
public class JavaSqlConnectionWrapper implements ConnectionWrapper {

	private final Connection connection;

	public JavaSqlConnectionWrapper(Connection connection) {
		this.connection = connection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ConnectionWrapper#prepareStatement(java.lang.String)
	 */
	@Override
	public PreparedStatementWrapper prepareStatement(String sql)
			throws SQLException {
		return new JavaSqlPreparedStatementWrapper(
				connection.prepareStatement(sql));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ConnectionWrapper#prepareQueryStatement(java.lang.String)
	 */
	@Override
	public QueryPreparedStatementWrapper prepareQueryStatement(String sql)
			throws SQLException {
		return new JavaSqlPreparedStatementWrapper(
				connection.prepareStatement(sql));
	}

	@Override
	public void executeSql(String sql) throws SQLException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} finally {
			DatabaseUtil.close(statement);
		}
	}

	@Override
	public boolean isClosed() throws SQLException {
		return connection.isClosed();
	}
}
