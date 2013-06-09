package com.jdom.database.api;

public interface Database {
	void start() throws Exception;

	void shutdown() throws Exception;

	long getSoftwareVersion();

	long getCurrentVersion();
}