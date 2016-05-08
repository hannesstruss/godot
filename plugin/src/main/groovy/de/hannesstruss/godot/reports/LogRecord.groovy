package de.hannesstruss.godot.reports

import org.joda.time.DateTime

class LogRecord {
  public LogRecord(DateTime loggedAt, List<String> taskNames, double seconds, boolean wasSuccessful) {
    this.loggedAt = loggedAt
    this.taskNames = taskNames
    this.seconds = seconds
    this.wasSuccessful = wasSuccessful
  }

  public final DateTime loggedAt
  public final List<String> taskNames
  public final double seconds
  public final boolean wasSuccessful
}
