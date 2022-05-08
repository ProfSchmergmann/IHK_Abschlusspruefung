package com.cae.de.framework;

import java.io.IOException;

public interface ISolver<P, R> {

  void done();

  ISolver<P, R> input(Producer<P> producer) throws IOException;

  ISolver<P, R> output(Consumer<Data<P, R>> consumer);

  ISolver<P, R> process();

  class Data<P, R> {
    private final P parameter;
    private R result;

    public Data(P parameter, R result) {
      this.parameter = parameter;
      this.result = result;
    }

    public P getParameter() {
      return this.parameter;
    }

    public R getResult() {
      return this.result;
    }

    public void setResult(R result) {
      this.result = result;
    }
  }
}
