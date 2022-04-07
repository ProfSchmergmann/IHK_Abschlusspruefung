package com.cae.de.utils;

/**
 * Record Pair, welches ein Schlüssel-Wert Paar repräsentiert.
 * @param key der Schlüssel
 * @param value der Wert
 * @param <K> der Typ des Schlüssels
 * @param <V> der Typ des Wertes
 */
public record Pair<K,V>(K key, V value) {

}
