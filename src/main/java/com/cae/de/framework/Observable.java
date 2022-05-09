package com.cae.de.framework;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {

  protected List<Observer<T>> observers;

  public Observable() {
    this.observers = new ArrayList<>();
  }

	public void notifyObserver(T t) {
		this.observers.forEach(observer -> observer.update(t));
	}

  public void registerObserver(Observer<T> observer) {
    this.observers.add(observer);
  }
}
