package de.hannesstruss.godot

import org.apache.commons.io.IOUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskExecutionException

class GenerateReportTask extends DefaultTask {
  private static final String[] STATIC_FILES = ["d3.js", "dimple.js"]

  File inputFile

  File outputDir

  @TaskAction
  public void generate() {
    outputDir.mkdirs();

    copyStaticFiles();
    generateReportHtml();
  }

  private void generateReportHtml() {
    def outputFile = new File(outputDir, "report.html")

    if (inputFile.exists()) {
      def models = LogParser.parse(inputFile);

      def generator = new ReportGenerator()
      generator.generate(outputFile, models, getProject().name)

      println "Wrote Godot report to: $outputFile"
    } else {
      throw new TaskExecutionException(this, new RuntimeException("Wait a minute, you haven't logged any builds yet!"))
    }
  }

  private void copyStaticFiles() {
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
