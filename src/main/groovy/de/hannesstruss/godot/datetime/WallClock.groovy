package de.hannesstruss.godot.datetime

import org.joda.time.DateTime

class WallClock implements Clock {
  @Override
  DateTime now() {
    return new DateTime()
  }
}
