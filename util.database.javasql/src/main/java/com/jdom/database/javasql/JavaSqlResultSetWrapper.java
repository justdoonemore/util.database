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
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdom.database.rawsql.ResultSetWrapper;

/**
 * @author djohnson
 * 
 */
public class JavaSqlResultSetWrapper implements ResultSetWrapper {

	private final ResultSet resultSet;

	public JavaSqlResultSetWrapper(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ResultSetWrapper#next()
	 */
	public boolean next() throws SQLException {
		return resultSet.next();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ResultSetWrapper#getLong(int)
	 */
	public long getLong(int i) throws SQLException {
		return resultSet.getLong(i);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ResultSetWrapper#getString(int)
	 */
	public String getString(int i) throws SQLException {
		return resultSet.getString(i);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ResultSetWrapper#getInt(int)
	 */
	public int getInt(int i) throws SQLException {
		return resultSet.getInt(i);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Closeable#close()
	 */
	public void close() throws IOException {
		try {
			resultSet.close();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}
}
