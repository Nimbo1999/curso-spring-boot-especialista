package io.github.nimbo1999.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantUtils {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
    public static final ZoneId zoneId = ZoneId.systemDefault();

    public static Instant instantNow(String instantTime) {
      if (instantTime == null) {
          return instantNow();
      }

      return Instant.parse(instantTime)
          .atZone(zoneId)
          .toInstant();
    }

    public static Instant instantNow() {
        return Instant
            .now()
            .atZone(zoneId)
            .toInstant();
    }

    public static String toISOString(Instant time) {
        return time.
          atZone(zoneId)
          .format(formatter);
    }
}
