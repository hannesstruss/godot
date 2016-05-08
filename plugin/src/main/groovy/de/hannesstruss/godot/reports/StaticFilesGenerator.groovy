package de.hannesstruss.godot.reports

import org.apache.commons.io.IOUtils

class StaticFilesGenerator {
  private static final String[] STATIC_FILES = ["d3.js", "dimple.js"]

  File outputDir

  StaticFilesGenerator(File outputDir) {
    this.outputDir = outputDir
  }

  public void copyStaticFiles() {
    File staticOutputDir = new File(outputDir, "assets")
    staticOutputDir.mkdir()
    STATIC_FILES.each { fileName ->
      def is = getClass().getResource("/report/assets/${fileName}").openStream()
      def os = new FileOutputStream(new File(staticOutputDir, fileName))
      IOUtils.copy(is, os)
      IOUtils.closeQuietly(is)
      IOUtils.closeQuietly(os)
    }
  }
}
