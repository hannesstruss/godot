package de.hannesstruss.godot

import de.hannesstruss.godot.reports.StaticFilesGenerator
import org.junit.Test

import java.nio.file.Files

import static com.google.common.truth.Truth.assertThat

class StaticFilesGeneratorTest {
  private static final File OUTPUT_DIR = new File(System.getProperty("godot.testOutputDir"))

  @Test public void shouldCopyStaticFiles() {
    def tempDir = getTempDir()
    def generator = new StaticFilesGenerator(tempDir)
    generator.copyStaticFiles()

    assertThat(new File(tempDir, "assets/d3.js").exists()).isTrue()
    assertThat(new File(tempDir, "assets/dimple.js").exists()).isTrue()
  }

  private static File getTempDir() {
    return Files.createTempDirectory(OUTPUT_DIR.toPath(), "godot-results-").toFile()
  }
}
