package de.hannesstruss.godot.reports

import de.hannesstruss.godot.datetime.Clock
import de.hannesstruss.godot.datetime.WallClock

class Report {
  private Clock clock

  Report() {
    this(new WallClock())
  }

  Report(Clock clock) {
    this.clock = clock
  }

  public void generate(Reader logFileReader, File outputDir, String projectName) {
    def records = LogParser.parse(logFileReader);
    generate(records, outputDir, projectName)
  }

  public void generate(File logFile, File outputDir, String projectName) {
    def records = LogParser.parse(logFile);
    generate(records, outputDir, projectName)
  }

  public void generate(List<LogRecord> records, File outputDir, String projectName) {
    new StaticFilesGenerator(outputDir).copyStaticFiles()

    def outputFile = new File(outputDir, "report.html")

    def htmlGenerator = new HtmlGenerator(clock)
    htmlGenerator.generate(new FileWriter(outputFile), records, projectName)
  }
}
