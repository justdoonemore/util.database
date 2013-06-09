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
package com.jdom.database.api;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdom.logging.api.LogFactory;
import com.jdom.logging.api.Logger;

/**
 * @author djohnson
 * 
 */
public class DatabaseUtil {

	private static final Logger log = LogFactory.getLogger(DatabaseUtil.class);

	public static final long UNSET = -1;

	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				log.error("Unable to close the closeable.", e);
			}
		}
	}

	public static void close(final Connection connection) {
		DatabaseUtil.close(new Closeable() {
			public void close() throws IOException {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new IOException(e);
				}
			}
		});
	}

	public static void close(final Statement statement) {
		DatabaseUtil.close(new Closeable() {
			public void close() throws IOException {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new IOException(e);
				}
			}
		});
	}

	public static void close(final ResultSet resultSet) {
		DatabaseUtil.close(new Closeable() {
			public void close() throws IOException {
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw new IOException(e);
				}
			}
		});
	}

}
