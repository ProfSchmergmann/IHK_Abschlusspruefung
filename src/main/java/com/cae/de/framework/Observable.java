package com.cae.de.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Observable Klasse zur implementierung des Observer-Observable Musters.
 *
 * @param <T> der Typ, der als Meldung an die registrierten Observer geschickt wird
 */
public abstract class Observable<T> {

  /** Liste aller Observer. */
  protected List<Observer<T>> observers;

  /** Konstruktor, welcher nur die Liste setzt. */
  public Observable() {
    this.observers = new ArrayList<>();
  }

  /**
   * Methode, um alle Observer zu informieren.
   *
   * @param t das Objekt, was sich geändert hat und worüber informiert werden soll
   */
  public void notifyObserver(T t) {
    this.observers.forEach(observer -> observer.update(t));
  }

  /**
   * Methode, um einen Observer zu registrieren.
   *
   * @param observer der zu registrierende Observer
   */
  public void registerObserver(Observer<T> observer) {
    this.observers.add(observer);
  }
}
