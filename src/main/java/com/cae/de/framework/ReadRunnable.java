package com.cae.de.framework;

import java.nio.file.Path;

public interface ReadRunnable<T> extends Runnable {

  T read(Path pathToFile);
}
