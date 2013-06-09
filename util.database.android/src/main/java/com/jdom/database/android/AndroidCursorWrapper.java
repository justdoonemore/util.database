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

import com.jdom.database.rawsql.ResultSetWrapper;

/**
 * @author djohnson
 * 
 */
public class AndroidCursorWrapper implements ResultSetWrapper {

	private final Cursor cursor;

	/**
	 * @param cursor
	 */
	public AndroidCursorWrapper(Cursor cursor) {
		this.cursor = cursor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Closeable#close()
	 */
	public void close() throws IOException {
		cursor.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ResultSetWrapper#next()
	 */
	public boolean next() throws SQLException {
		return cursor.moveToNext();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ResultSetWrapper#getLong(int)
	 */
	public long getLong(int i) throws SQLException {
		return cursor.getLong(i - 1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ResultSetWrapper#getString(int)
	 */
	public String getString(int i) throws SQLException {
		return cursor.getString(i - 1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.ResultSetWrapper#getInt(int)
	 */
	public int getInt(int i) throws SQLException {
		return cursor.getInt(i - 1);
	}

}
