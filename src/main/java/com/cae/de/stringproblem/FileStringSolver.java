package com.cae.de.stringproblem;

import com.cae.de.framework.Consumer;
import com.cae.de.framework.EVAException;
import com.cae.de.framework.ISolver;
import com.cae.de.framework.Producer;
import com.cae.de.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class FileStringSolver implements ISolver<Pair<String, String>, String> {

  private List<ISolver.Data<Pair<String, String>, String>> dataList;

  @Override
  public void done() {
    this.dataList.clear();
  }

  @Override
  public ISolver<Pair<String, String>, String> input(Producer<Pair<String, String>> producer)
      throws EVAException {
    this.dataList = new ArrayList<>();
    producer.readToList().forEach(pair -> this.dataList.add(new Data<>(pair, "")));
    return this;
  }

  @Override
  public ISolver<Pair<String, String>, String> output(
      Consumer<Data<Pair<String, String>, String>> consumer) throws EVAException {
    for (Data<Pair<String, String>, String> pairStringData : this.dataList) {
      consumer.write(pairStringData);
    }
    return this;
  }

  @Override
  public ISolver<Pair<String, String>, String> process() {
    this.dataList.forEach(
        pairStringData -> pairStringData.setResult(pairStringData.getParameter().value()));
    return this;
  }
}
