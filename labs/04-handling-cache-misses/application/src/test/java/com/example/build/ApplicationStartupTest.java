package com.example.build;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class ApplicationStartupTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void applicationShouldStartup() throws IOException {
        Stream<Path> integrationTestSamples = Files.list(Paths.get(System.getenv().get("INTEGRATION_TEST_SAMPLES")));
        integrationTestSamples.forEach(path -> Main.main(new String[]{path.toString()})
        );

        String log = systemOutRule.getLog();
        assertTrue("Gradle training", log.contains("Gradle training"));
        assertTrue("Build cache", log.contains("Build cache"));
    }
}
