package de.hannesstruss.godot.reports

import com.google.gson.Gson

class LogParser {
  public static List<LogRecord> parse(File file) {
    return parse(new FileReader(file))
  }

  public static List<LogRecord> parse(Reader reader) {
    Gson gson = GsonFactory.get()

    List<LogRecord> records = new ArrayList<>()
    for (line in reader) {
      if (line.startsWith('{')) {
        records.add(gson.fromJson(line, LogRecord))
      }
    }
    return records
  }
}
