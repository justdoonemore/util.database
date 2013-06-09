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
package com.jdom.database.rawsql;

import java.util.ArrayList;
import java.util.List;

import com.jdom.database.api.DatabaseUtil;

/**
 * @author djohnson
 * 
 */
public class RawSqlDbStrategy implements DbStrategy {

	private final ConnectionWrapper conn;

	public RawSqlDbStrategy(ConnectionWrapper conn) {
		this.conn = conn;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.DbStrategy#executeSql(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public void executeSql(String sql, Object... params) throws Exception {
		PreparedStatementWrapper stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			stmt.execute();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.close(stmt);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see com.jdom.database.api.DbStrategy#retrieveSql(java.lang.String,
	 *      com.jdom.database.api.ResultSetResolver)
	 */
	@Override
	public <T> List<T> retrieveSql(String sql,
			ResultSetResolver<T> resultSetResolver) throws Exception {
		QueryPreparedStatementWrapper stmt = null;
		ResultSetWrapper resultSet = null;
		try {
			stmt = conn.prepareQueryStatement(sql);
			stmt.execute();
			resultSet = stmt.getResultSet();

			List<T> results = new ArrayList<T>();
			while (resultSet.next()) {
				results.add(resultSetResolver.resolveResultSet(resultSet));
			}
			return results;
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.close(resultSet);
			DatabaseUtil.close(stmt);
		}
	}

}
