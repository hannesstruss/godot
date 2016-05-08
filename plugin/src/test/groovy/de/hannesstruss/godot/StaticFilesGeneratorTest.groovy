package de.hannesstruss.godot

import de.hannesstruss.godot.reports.StaticFilesGenerator
import org.junit.Test

import java.nio.file.Files

import static com.google.common.truth.Truth.assertThat

class StaticFilesGeneratorTest {


  @Test public void shouldCopyStaticFiles() {
    def tempDir = TestDirectories.getTempDir()
    def generator = new StaticFilesGenerator(tempDir)
    generator.copyStaticFiles()

    assertThat(new File(tempDir, "assets/d3.js").exists()).isTrue()
    assertThat(new File(tempDir, "assets/dimple.js").exists()).isTrue()
  }
}
