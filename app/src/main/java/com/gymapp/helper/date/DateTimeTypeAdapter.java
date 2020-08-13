package com.gymapp.helper.date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Custom DateTime Type for Gson serializer
 */
public class DateTimeTypeAdapter extends TypeAdapter<DateTime> {

    final private DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public void write(JsonWriter out, DateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            String date = DATE_FORMAT.print(value);
            out.value(date);
        }
    }

    @Override
    public DateTime read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }

        String dateAsString = reader.nextString();
        return DateTime.parse(dateAsString, DATE_FORMAT);
    }
}