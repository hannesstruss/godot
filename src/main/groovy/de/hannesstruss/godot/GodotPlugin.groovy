package de.hannesstruss.godot

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.joda.time.DateTime

class GodotPlugin implements Plugin<Project> {
  private static final GENERATE_TASK_NAME = "generateGodotReport"
  private final gson = GsonFactory.get()

  @Override
  void apply(Project project) {
    // apply base plugin to get a free clean task
    project.apply plugin: BasePlugin

    def start = System.currentTimeMillis()

    project.gradle.buildFinished { buildResult ->
      // don't log generating our report
      if (project.gradle.startParameter.taskNames.findAll({ it.contains(GENERATE_TASK_NAME) }).isEmpty()) {
        def seconds = ((System.currentTimeMillis() - start) / 1000.0)
        log(project, seconds.toDouble(), buildResult.getFailure() == null)
      }
    }

    def outputDir = new File("${project.buildDir}/outputs/godot")

    def generate = project.tasks.create(GENERATE_TASK_NAME, GenerateReportTask)
    generate.inputFile = new File("${project.rootDir}/godot.log")
    generate.outputDir = outputDir
  }

  def log(Project project, double seconds, boolean wasSuccessful) {
    def record = new LogRecord(DateTime.now(), project.gradle.startParameter.taskNames, seconds, wasSuccessful)

    def msg = gson.toJson(record)
    def f = new File("$project.rootDir/godot.log")
    f.append(msg + '\n')
  }
}
