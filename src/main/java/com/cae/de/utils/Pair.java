package com.cae.de.utils;

/**
 * Record Pair, welches zwei generische Typen beinhalten kann.
 * @param key der Schlüssel
 * @param value der Wert
 * @param <K> der Typ des Schlüssels
 * @param <V> der Type des Wertes
 */
public record Pair<K, V>(K key, V value) {
}
