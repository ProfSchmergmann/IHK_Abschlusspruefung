package com.cae.de.stringproblem;

import com.cae.de.framework.IConsumer;
import com.cae.de.framework.EVAException;
import com.cae.de.framework.ISolver;
import com.cae.de.framework.IProducer;
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
  public ISolver<Pair<String, String>, String> input(IProducer<Pair<String, String>> IProducer)
      throws EVAException {
    this.dataList = new ArrayList<>();
    IProducer.readToList().forEach(pair -> this.dataList.add(new Data<>(pair, "")));
    return this;
  }

  @Override
  public ISolver<Pair<String, String>, String> output(
      IConsumer<Data<Pair<String, String>, String>> IConsumer) throws EVAException {
    for (Data<Pair<String, String>, String> pairStringData : this.dataList) {
      IConsumer.write(pairStringData);
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
