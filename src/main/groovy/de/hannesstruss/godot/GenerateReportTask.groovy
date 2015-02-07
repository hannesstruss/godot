package de.hannesstruss.godot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

class GenerateReportTask extends DefaultTask {
  @OutputDirectory
  File outputDir

  @TaskAction
  public void execute(IncrementalTaskInputs inputs) {
    println "weeee"
    println outputDir
  }
}
