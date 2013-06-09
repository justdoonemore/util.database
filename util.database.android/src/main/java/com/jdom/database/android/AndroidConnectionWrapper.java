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

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jdom.database.rawsql.ConnectionWrapper;
import com.jdom.database.rawsql.PreparedStatementWrapper;
import com.jdom.database.rawsql.QueryPreparedStatementWrapper;
import com.jdom.logging.api.LogFactory;
import com.jdom.logging.api.Logger;

/**
 * @author djohnson
 * 
 */
public class AndroidConnectionWrapper implements ConnectionWrapper {

	private final Logger log = LogFactory
			.getLogger(AndroidConnectionWrapper.class);

	private final SQLiteOpenHelper dbHelper;

	public AndroidConnectionWrapper(SQLiteOpenHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ConnectionWrapper#prepareStatement(java.lang.String)
	 */
	@Override
	public PreparedStatementWrapper prepareStatement(String sql)
			throws SQLException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		return new AndroidSqlitePreparedStatementWrapper(
				db.compileStatement(sql));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ConnectionWrapper#prepareQueryStatement(java.lang.String)
	 */
	@Override
	public QueryPreparedStatementWrapper prepareQueryStatement(String sql)
			throws SQLException {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		return new AndroidSqliteQueryPreparedStatementWrapper(db, sql);
	}

	@Override
	public void close() throws IOException {
		// TODO: Is there anything to close?
		// dbHelper.close();
	}

	@Override
	public void executeSql(String sql) throws SQLException {
		dbHelper.getWritableDatabase().execSQL(sql);
	}

	@Override
	public boolean isClosed() throws SQLException {
		return false;
	}
}
