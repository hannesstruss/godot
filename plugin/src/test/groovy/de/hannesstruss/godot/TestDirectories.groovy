package de.hannesstruss.godot

import java.nio.file.Files

class TestDirectories {
  private static final File OUTPUT_DIR = new File(System.getProperty("godot.testOutputDir"))

  static File getTempDir() {
    return Files.createTempDirectory(OUTPUT_DIR.toPath(), "godot-results-").toFile()
  }
}
