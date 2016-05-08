package de.hannesstruss.godot.reports

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.joda.time.DateTime

import java.lang.reflect.Type

class DateTimeConverter implements JsonDeserializer<DateTime>, JsonSerializer<DateTime> {
  @Override
  DateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    return new DateTime(jsonElement.getAsString())
  }

  @Override
  JsonElement serialize(DateTime dateTime, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(dateTime.toString())
  }
}
