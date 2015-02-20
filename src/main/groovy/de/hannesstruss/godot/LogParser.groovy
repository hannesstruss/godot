package de.hannesstruss.godot

import com.google.gson.Gson

class LogParser {
  public static List<LogRecord> parse(File file) {
    Gson gson = GsonFactory.get()
    List<String> records = file.readLines().findAll({ it.startsWith('{') })
    records.collect { gson.fromJson(it, LogRecord) }
  }
}
