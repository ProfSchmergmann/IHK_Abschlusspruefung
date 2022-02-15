package com.cae.de;

import com.cae.de.utils.io.ExternalStringFileReader;
import com.cae.de.utils.io.ExternalStringFileWriter;

public class Main {
  public static void main(String[] args) {
    var reader = new ExternalStringFileReader();
    var s = reader.read("./test.txt");
    var writer = new ExternalStringFileWriter();
    writer.write(s, "./test_out.txt");
  }
}
