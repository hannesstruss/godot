package de.hannesstruss.godot

import groovy.text.SimpleTemplateEngine
import org.joda.time.DateTime
import org.joda.time.LocalDate

class ReportGenerator {
  public String generate(File outputFile, List<LogRecord> logRecords, String projectName) {
    def resource = getClass().getResource("/report/report.html")
    def template = resource.text
    def engine = new SimpleTemplateEngine()
    def writer = new FileWriter(outputFile)
    engine.createTemplate(template).make(getData(logRecords, projectName)).writeTo(writer)
  }

  private static Map getData(List<LogRecord> logRecords, String projectName) {
    def gson = GsonFactory.get()

    return [
        records: gson.toJson(logRecords),
        secondsToday: secondsSpentSince({ it }, logRecords),
        secondsLastWeek: secondsSpentSince({ it.minusDays 7 }, logRecords),
        secondsLastMonth: secondsSpentSince({ it.minusDays 31 }, logRecords),
        projectName: projectName,
        today: new LocalDate().toString("EEEE, MMMM d, y"),
        fmt: new TimeFormatter()
    ]
  }

  private static secondsSpentSince(mkStartDate, List<LogRecord> records) {
    def today = DateTime.now().withTimeAtStartOfDay()
    DateTime startDate = mkStartDate(today)

    records
        .findAll { it.loggedAt.isAfter(startDate) }
        .collect { it.seconds }
        .inject(0, { acc, val -> acc + val })
        .toInteger()
  }
}
