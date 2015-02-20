package de.hannesstruss.godot

class Formatter {
  public String seconds(int seconds) {
    int secs = seconds % 60
    int mins = (seconds / 60).toInteger() % 60
    int hours = (seconds / 60 / 60).toInteger() % 24
    int days = (seconds / 60 / 60 / 24).toInteger()

    def result = String.format("%02d:%02d:%02d", hours, mins, secs)

    if (days > 0) {
      result = "$days days, $result"
    }

    result
  }
}
