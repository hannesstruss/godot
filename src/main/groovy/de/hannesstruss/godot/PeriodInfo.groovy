package de.hannesstruss.godot

import groovy.time.TimeDuration

class PeriodInfo {
  public static PeriodInfo fromBuilds(String title, List<LogRecord> records) {
    new PeriodInfo(
        title,
        records
            .collect { it.seconds }
            .inject { acc, val -> acc + val },
        records.size()
    )
  }

  PeriodInfo(String title, double secondsSpent, int numBuilds) {
    this.title = title
    this.secondsSpent = secondsSpent
    this.numBuilds = numBuilds
  }

  public final String title;
  public final double secondsSpent;
  public final int numBuilds;

  public String formatTime() {
    int secs = secondsSpent % 60
    int mins = (secondsSpent / 60) % 60
    int hours = (secondsSpent / 60 / 60) % 24
    int days = (secondsSpent / 60 / 60 / 24)

    def result = String.format("%02d:%02d:%02d", hours, mins, secs)

    if (days > 0) {
      result = "$days days, $result"
    }

    result
  }
}
