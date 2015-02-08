package de.hannesstruss.godot

import com.google.gson.GsonBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.joda.time.DateTime

class GodotPlugin implements Plugin<Project> {
  private final gson = GsonFactory.get()

  @Override
  void apply(Project project) {
    def start = System.currentTimeMillis()

    project.gradle.buildFinished { buildResult ->
      def seconds = ((System.currentTimeMillis() - start) / 1000.0)
      log(project, seconds.toDouble(), buildResult.getFailure() == null)
    }

    def generate = project.tasks.create("generateGodotReport", GenerateReportTask)
    generate.inputFile = new File("${project.rootDir}/godot.log")
    generate.outputDir = new File("${project.buildDir}/outputs/godot")
  }

  def log(Project project, double seconds, boolean wasSuccessful) {
    def record = new LogRecord(DateTime.now(), project.gradle.startParameter.taskNames, seconds, wasSuccessful)

    def msg = gson.toJson(record)
    def f = new File("$project.rootDir/godot.log")
    f.append(msg + '\n')
  }
}
