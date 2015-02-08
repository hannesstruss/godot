package de.hannesstruss.godot

import groovy.text.SimpleTemplateEngine
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.joda.time.DateTime

class GenerateReportTask extends DefaultTask {
  File inputFile

  File outputDir

  @TaskAction
  public void generate() {
    def outputFile = new File(outputDir, "report.html")

    def resource = getClass().getResource("/report/report.html")
    def template = resource.text
    def engine = new SimpleTemplateEngine()
    engine.createTemplate(template).make(getData()).writeTo(new FileWriter(outputFile))

    println "Wrote Godot report to: $outputFile"

  }

  private Map getData() {
    def gson = GsonFactory.get()

    List<String> records = inputFile.readLines().findAll({ it.startsWith('{') })

    def all = records.join(',\n')

    def models = records.collect { gson.fromJson(it, LogRecord) }

    def base = DateTime.now().withTimeAtStartOfDay()
    def today = base
    def oneMonthAgo = base.minusDays(30)
    def oneYearAgo = base.withYear(base.getYear() - 1)

    def infos = [
        PeriodInfo.fromBuilds("Today", models.findAll { it.loggedAt.isAfter(today) }),
        PeriodInfo.fromBuilds("Last 30 days", models.findAll { it.loggedAt.isAfter(oneMonthAgo) }),
        PeriodInfo.fromBuilds("Last Year", models.findAll { it.loggedAt.isAfter(oneYearAgo) })
    ]

    return [records: all, infos: infos ]
  }


}
