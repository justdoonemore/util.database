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

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jdom.database.rawsql.QueryPreparedStatementWrapper;
import com.jdom.database.rawsql.ResultSetWrapper;

/**
 * @author djohnson
 * 
 */
public class AndroidSqliteQueryPreparedStatementWrapper implements
		QueryPreparedStatementWrapper {

	private final SQLiteDatabase db;

	private final String sql;

	private Cursor cursor;

	/**
	 * @param db
	 */
	public AndroidSqliteQueryPreparedStatementWrapper(SQLiteDatabase db,
			String sql) {
		this.db = db;
		this.sql = sql;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.PreparedStatementWrapper#execute()
	 */
	@Override
	public boolean execute() throws SQLException {
		try {
			this.cursor = db.rawQuery(sql, new String[0]);
			return true;
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.QueryPreparedStatementWrapper#getResultSet()
	 */
	@Override
	public ResultSetWrapper getResultSet() throws SQLException {
		return new AndroidCursorWrapper(cursor);
	}
}
