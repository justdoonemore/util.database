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
package com.jdom.database.android;

import java.io.IOException;
import java.sql.SQLException;

import android.database.sqlite.SQLiteStatement;

import com.jdom.database.rawsql.PreparedStatementWrapper;

/**
 * @author djohnson
 * 
 */
public class AndroidSqlitePreparedStatementWrapper implements
		PreparedStatementWrapper {

	private final SQLiteStatement compileStatement;

	/**
	 * @param compileStatement
	 */
	public AndroidSqlitePreparedStatementWrapper(
			SQLiteStatement compileStatement) {
		this.compileStatement = compileStatement;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Closeable#close()
	 */
	public void close() throws IOException {
		compileStatement.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.PreparedStatementWrapper#setObject(int,
	 *      java.lang.Object)
	 */
	public void setObject(int index, Object object) throws SQLException {
		if (object instanceof String) {
			compileStatement.bindString(index, (String) object);
		} else if (object instanceof Integer) {
			compileStatement.bindLong(index, (Integer) object);
		} else if (object instanceof Long) {
			compileStatement.bindLong(index, (Long) object);
		} else {
			throw new SQLException(
					"Currently does not support objects of type ["
							+ object.getClass().getName() + "]");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.PreparedStatementWrapper#execute()
	 */
	public boolean execute() throws SQLException {
		compileStatement.execute();
		return true;
	}
}
