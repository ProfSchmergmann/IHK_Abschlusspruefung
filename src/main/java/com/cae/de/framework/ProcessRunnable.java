package com.cae.de.framework;

public interface ProcessRunnable<T> extends Runnable {

  T process(T t);
}
