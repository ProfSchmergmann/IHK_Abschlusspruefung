package com.cae.de.framework;

public interface WriteRunnable<T> extends Runnable {

  boolean write(T t);
}
