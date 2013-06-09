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
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jdom.database.rawsql.PreparedStatementWrapper;
import com.jdom.database.rawsql.QueryPreparedStatementWrapper;
import com.jdom.database.rawsql.ResultSetWrapper;

/**
 * @author djohnson
 * 
 */
public class JavaSqlPreparedStatementWrapper implements
		PreparedStatementWrapper, QueryPreparedStatementWrapper {

	private final PreparedStatement stmt;

	/**
	 * @param stmt
	 */
	public JavaSqlPreparedStatementWrapper(PreparedStatement stmt) {
		this.stmt = stmt;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws SQLException
	 * @see com.jdom.database.api.PreparedStatementWrapper#setObject(int,
	 *      java.lang.Object)
	 */
	public void setObject(int index, Object object) throws SQLException {
		this.stmt.setObject(index, object);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws SQLException
	 * 
	 * @see com.jdom.database.api.PreparedStatementWrapper#execute()
	 */
	public boolean execute() throws SQLException {
		return this.stmt.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.PreparedStatementWrapper#getResultSet()
	 */
	public ResultSetWrapper getResultSet() throws SQLException {
		return new JavaSqlResultSetWrapper(stmt.getResultSet());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Closeable#close()
	 */
	public void close() throws IOException {
		try {
			stmt.close();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

}
