package de.hannesstruss.godot.reports

class TimeFormatter {
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

  public String verbalSeconds(int seconds) {
    String unitWord
    int divisor
    if (seconds < 60) {
      unitWord = "second"
      divisor = 1
    } else if (seconds < 60 * 60) {
      unitWord = "minute"
      divisor = 60
    } else if (seconds < 60 * 60 * 24) {
      unitWord = "hour"
      divisor = 60 * 60
    } else {
      unitWord = "day"
      divisor = 60 * 60 * 24
    }

    def num = (seconds / divisor).toInteger()

    if (num != 1) {
      unitWord += "s"
    }

    String numWord
    if (num <= 10) {
      numWord = ["zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"][num]
    } else {
      numWord = num.toString()
    }

    "$numWord $unitWord"
  }
}
