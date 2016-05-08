package de.hannesstruss.godot

import de.hannesstruss.godot.datetime.Clock
import org.joda.time.DateTime

class FixedClock implements Clock {
  private final DateTime dateTime

  public FixedClock(DateTime dateTime) {
    this.dateTime = dateTime
  }

  @Override
  DateTime now() {
    return dateTime
  }
}
