package com.cae.de.problem;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class AlgorithmusTest {

  @Test
  void TestAKF() {
    var readThread = new ThreadA(Path.of("input"));
    var writeThread = new ThreadC(Path.of("output"));
    for (var i = 0; i < 10; i++) {
      writeThread.write(Algorithms.solve(readThread.read(Path.of("input/" + i + ".txt"))));
    }
  }
}
