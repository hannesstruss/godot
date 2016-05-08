package de.hannesstruss.godot.plugin

import de.hannesstruss.godot.reports.GsonFactory
import de.hannesstruss.godot.reports.LogRecord
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.joda.time.DateTime

class GodotPlugin implements Plugin<Project> {
  private static final GENERATE_TASK_NAME = "generateGodotReport"
  private static final String REPORT_OUTPUT_DIR = "reports/godot"
  private static final String LOGFILE_NAME = "godot.log"

  @Override
  void apply(Project project) {
    // apply base plugin to get a free clean task
    project.apply plugin: BasePlugin

    createProjectExtension(project)

    // Defer to read user configuration.
    project.afterEvaluate {
      createGenerateTask(project)
    }

    initLoggingHook(project)
  }

  private void createProjectExtension(Project project) {
    project.extensions.create("godot", GodotExtension)
  }

  private void createGenerateTask(Project project) {
    def generate = project.tasks.create(GENERATE_TASK_NAME, GenerateReportTask)
    generate.configure {
      inputFile = getLogFile(project)
      outputDir = new File("${project.buildDir}/$REPORT_OUTPUT_DIR")
    }
  }

  private void initLoggingHook(Project project) {
    def start = System.currentTimeMillis()

    project.gradle.buildFinished { buildResult ->
      // don't log generating our report
      if (project.gradle.startParameter.taskNames.findAll({ it.contains(GENERATE_TASK_NAME) }).isEmpty()) {
        def seconds = ((System.currentTimeMillis() - start) / 1000.0)
        log(project, seconds.toDouble(), buildResult.getFailure() == null)
      }
    }
  }

  private void log(Project project, double seconds, boolean wasSuccessful) {
    def gson = GsonFactory.get()

    def record = new LogRecord(DateTime.now(), project.gradle.startParameter.taskNames, seconds, wasSuccessful)

    def msg = gson.toJson(record)
    File f = getLogFile(project)
    f.append(msg + '\n')
  }

  private File getLogFile(Project project) {
    def dir
    if (project.godot.persistLog) {
      dir = new File("$project.gradle.gradleUserHomeDir/godot/${project.getName()}")
    } else {
      dir = new File("${project.buildDir}/$REPORT_OUTPUT_DIR")
    }
    dir.mkdirs()
    return new File(dir, LOGFILE_NAME)
  }
}
