package de.hannesstruss.godot.datetime

import org.joda.time.DateTime

interface Clock {
  public DateTime now()
}
