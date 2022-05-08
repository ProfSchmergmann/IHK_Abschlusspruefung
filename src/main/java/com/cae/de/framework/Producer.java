package com.cae.de.framework;

import java.io.IOException;
import java.util.List;

public abstract class Producer<P> {

  public abstract List<P> readToList() throws IOException;
}
