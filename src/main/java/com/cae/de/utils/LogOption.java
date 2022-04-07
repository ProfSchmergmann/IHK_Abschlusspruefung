package com.cae.de.utils;

import java.util.Arrays;

/** Enumeration für alle Logging Optionen. */
public enum LogOption {
  /** Konstante für Logging in die Konsole. */
  TRUE("true"),
  /** Konstante kein Logging. */
  FALSE("false"),
  /** Konstante für Logging in eine Datei. */
  FILE("file");
  private final String value;

  LogOption(String value) {
    this.value = value;
  }

  /**
   * Versucht aus einem gegebenen String die Option zu parsen und zurückzugeben.
   *
   * @param value der string
   * @return die Konstante
   */
  public static LogOption getOption(String value) {
    return Arrays.stream(LogOption.values())
        .filter(option -> option.value.equals(value))
        .findFirst()
        .orElse(FALSE);
  }
}
