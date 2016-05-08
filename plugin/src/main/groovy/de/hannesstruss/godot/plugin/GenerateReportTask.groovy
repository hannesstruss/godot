package de.hannesstruss.godot.plugin

import de.hannesstruss.godot.reports.LogParser
import de.hannesstruss.godot.reports.ReportGenerator
import de.hannesstruss.godot.datetime.WallClock
import de.hannesstruss.godot.reports.StaticFilesGenerator
import org.apache.commons.io.IOUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskExecutionException

class GenerateReportTask extends DefaultTask {
  @InputFile
  File inputFile

  @OutputDirectory
  File outputDir

  @TaskAction
  public void generate() {
    if (inputFile.exists()) {
      outputDir.mkdirs();

      new StaticFilesGenerator(outputDir).copyStaticFiles()

      def outputFile = new File(outputDir, "report.html")
      def records = LogParser.parse(inputFile);

      def generator = new ReportGenerator(new WallClock())
      generator.generate(new FileWriter(outputFile), records, getProject().name)

      println "Wrote Godot report to: $outputFile"
    } else {
      throw new TaskExecutionException(this, new RuntimeException("Wait a minute, you haven't logged any builds yet!"))
    }
  }
}
