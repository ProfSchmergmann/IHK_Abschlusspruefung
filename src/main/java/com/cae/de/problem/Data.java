package com.cae.de.problem;

/**
 * Datenklasse zur Weitergabe der Eingabedaten.
 * @param fileName der Name der Datei
 * @param xStart das x-Array zum Start
 * @param yStart das y-Array zum Start
 */
public record Data(String fileName, int[] xStart, int[] yStart) {
}
