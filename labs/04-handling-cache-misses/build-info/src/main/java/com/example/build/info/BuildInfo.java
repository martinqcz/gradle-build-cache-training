package com.example.build.info;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class BuildInfo {

	private final Date buildTime;
	private final String version;

	public BuildInfo() throws IOException {
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/build-info.properties"));
		buildTime = new Date(Long.valueOf(properties.getProperty("timestamp")));
		version = properties.getProperty("version");
	}

	public Date getBuildTime() {
		return buildTime;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "BuildInfo{" + "buildTime=" + buildTime + ", version='" + version + '\'' + '}';
	}
}