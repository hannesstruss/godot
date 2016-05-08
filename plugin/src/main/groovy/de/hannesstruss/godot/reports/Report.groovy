package de.hannesstruss.godot.reports

import de.hannesstruss.godot.datetime.WallClock

class Report {
  public static void generate(File logFile, File outputDir, String projectName) {
    def records = LogParser.parse(logFile);
    generate(records, outputDir, projectName)
  }

  public static void generate(List<LogRecord> records, File outputDir, String projectName) {
    new StaticFilesGenerator(outputDir).copyStaticFiles()

    def outputFile = new File(outputDir, "report.html")

    def htmlGenerator = new HtmlGenerator(new WallClock())
    htmlGenerator.generate(new FileWriter(outputFile), records, projectName)
  }
}
