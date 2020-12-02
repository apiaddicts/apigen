package net.cloudappi.apigen.generatorcore.generator.base;

import net.cloudappi.apigen.generatorcore.config.Configuration;
import net.cloudappi.apigen.generatorcore.config.ConfigurationObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PomFileGeneratorTest {

    Configuration configuration;

    @BeforeEach
    void prepareTest() {
        configuration = ConfigurationObjectMother.createCompleteConfigurationWithoutEntitiesAndControllers();
    }

    @Test
    void thatGenerates(@TempDir File projectFolder) throws IOException {
        PomGenerator.generate(
                configuration,
                "net.cloudappi.apigen",
                "archetype-parent-spring-boot",
                "0.0.1-SNAPSHOT",
                projectFolder
        );
        assertPomExists(projectFolder);
    }

    private void assertPomExists(File projectFolder) {
        Path pomPath = Paths.get(projectFolder.getPath(), "/pom.xml");
        assertTrue(Files.exists(pomPath), "pom.xml file not generated");
    }
}