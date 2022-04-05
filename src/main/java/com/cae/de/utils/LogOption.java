package com.cae.de.utils;

/** Enum for all log options which are true, false or file. */
public enum LogOption {
  TRUE("true"),
  FALSE("false"),
  FILE("file");
  private final String value;

  LogOption(String value) {
    this.value = value;
  }

  public static LogOption getOption(String value) {
    for (var option : LogOption.values()) {
      if (option.value.equals(value)) {
        return option;
      }
    }
    return FALSE;
  }
}
