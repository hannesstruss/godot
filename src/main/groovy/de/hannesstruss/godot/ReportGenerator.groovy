package de.hannesstruss.godot

import groovy.text.SimpleTemplateEngine
import org.joda.time.DateTime

class ReportGenerator {
  public String generate(File outputFile, List<LogRecord> logRecords) {
    def resource = getClass().getResource("/report/report.html")
    def template = resource.text
    def engine = new SimpleTemplateEngine()
    def writer = new FileWriter(outputFile)
    engine.createTemplate(template).make(getData(logRecords)).writeTo(writer)
  }

  private static Map getData(List<LogRecord> logRecords) {
    def gson = GsonFactory.get()


//    def infos = [
//        PeriodInfo.fromBuilds("Today", models.findAll { it.loggedAt.isAfter(today) }),
//        PeriodInfo.fromBuilds("Last 30 days", models.findAll { it.loggedAt.isAfter(oneMonthAgo) }),
//        PeriodInfo.fromBuilds("Last Year", models.findAll { it.loggedAt.isAfter(oneYearAgo) })
//    ]

    return [
        records: gson.toJson(logRecords),
        secondsToday: secondsSpentSince({ it }, logRecords),
        secondsLastWeek: secondsSpentSince({ it.minusDays 7 }, logRecords),
        secondsLastMonth: secondsSpentSince({ it.minusDays 31 }, logRecords),
        fmt: new Formatter()
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
