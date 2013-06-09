package com.jdom.database.rawsql;

import java.sql.SQLException;

import com.jdom.database.api.Database;

public interface RawDatabase extends Database {
	ConnectionWrapper getConnection() throws SQLException;
}
