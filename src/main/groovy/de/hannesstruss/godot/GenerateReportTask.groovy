package de.hannesstruss.godot

import groovy.text.SimpleTemplateEngine
import org.apache.commons.io.IOUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.joda.time.DateTime

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
    def gson = GsonFactory.get()

    List<String> records = inputFile.readLines().findAll({ it.startsWith('{') })
    def models = records.collect { gson.fromJson(it, LogRecord) }

    def generator = new ReportGenerator()
    generator.generate(outputFile, models)

    println "Wrote Godot report to: $outputFile"
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
