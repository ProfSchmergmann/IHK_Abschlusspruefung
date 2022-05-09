package com.cae.de.framework;

public interface ProcessRunnable<P, R> extends Runnable {

  R process(P p);
}
