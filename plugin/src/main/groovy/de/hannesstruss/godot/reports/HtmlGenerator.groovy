package de.hannesstruss.godot.reports

import de.hannesstruss.godot.datetime.Clock
import groovy.text.SimpleTemplateEngine
import org.joda.time.DateTime
import org.joda.time.LocalDate

class HtmlGenerator {
  Clock clock

  HtmlGenerator(Clock clock) {
    this.clock = clock
  }

  public String generate(Writer outputWriter, List<LogRecord> logRecords, String projectName) {
    def resource = getClass().getResource("/report/report.html")
    def template = resource.text
    def engine = new SimpleTemplateEngine()
    engine
        .createTemplate(template)
        .make(getData(logRecords, projectName))
        .writeTo(outputWriter)
  }

  private Map getData(List<LogRecord> logRecords, String projectName) {
    def gson = GsonFactory.get()

    return [
        records: gson.toJson(logRecords),
        secondsToday: secondsSpentSince({ it }, logRecords),
        secondsLastWeek: secondsSpentSince({ it.minusDays 7 }, logRecords),
        secondsLastMonth: secondsSpentSince({ it.minusDays 31 }, logRecords),
        projectName: projectName,
        today: clock.now(),
        fmt: new TimeFormatter()
    ]
  }

  private secondsSpentSince(mkStartDate, List<LogRecord> records) {
    def today = clock.now().withTimeAtStartOfDay()
    DateTime startDate = mkStartDate(today)

    records
        .findAll { it.loggedAt.isAfter(startDate) }
        .collect { it.seconds }
        .inject(0, { acc, val -> acc + val })
        .toInteger()
  }
}
