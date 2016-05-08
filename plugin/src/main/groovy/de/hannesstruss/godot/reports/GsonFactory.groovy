package de.hannesstruss.godot.reports

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.joda.time.DateTime

class GsonFactory {
  public static Gson get() {
    return new GsonBuilder()
        .registerTypeAdapter(DateTime.class, new DateTimeConverter())
        .create();
  }
}
