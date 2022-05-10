package com.cae.de.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasse um alle Programmargumente analysieren zu können.
 */
public class CmdLineParser {

	private static final String INPUT_FOLDER_STRING = "-inputfolder";
	private static final String OUTPUT_FOLDER_STRING = "-outputfolder";
	private static final String LOG_STRING = "-log";
	private static final String LOG_LEVEL_STRING = "-loglvl";
	private static final String THREAD_POOL_SIZE_STRING = "-poolsize";
	private static final String QUEUE_SIZE_STRING = "-queuesize";
	private static final String SLEEP_TIME_STRING = "-sleep";
	private static final Logger ROOT_LOGGER = Logger.getLogger("");
	private static final Logger LOGGER = Logger.getLogger(CmdLineParser.class.getName());
	private int sleepTime;
	private int threadPoolSize;

	public int getQueueSize() {
		return this.queueSize;
	}

	private int queueSize;
	private String inputFolder;
	private String outputFolder;
	/**
	 * Konstruktor, welcher als Parameter das args-Array der main Methode übergeben bekommen soll.
	 * Hier findet dann auch schon die Analyse statt und auch das Setzen des Loggers.
	 * Die Namen der Ein- und Ausgabeordner können dann über die Getter ausgelesen werden.
	 * @param args die Programmzeilenargumente
	 */
	public CmdLineParser(String[] args) {
		this.inputFolder = "input";
		this.outputFolder = "output";
		this.threadPoolSize = 1;
		this.sleepTime = 1;
		this.queueSize = 1;
		var logOption = LogOption.TRUE;
		for (var i = 0; i < args.length; i++) {
			if (i + 1 < args.length) {
				switch (args[i]) {
					case INPUT_FOLDER_STRING -> this.inputFolder = args[++i];
					case OUTPUT_FOLDER_STRING -> this.outputFolder = args[++i];
					case LOG_STRING -> {
						try {
							logOption = LogOption.getOption(args[++i]);
						} catch (IllegalArgumentException e) {
							LOGGER.log(Level.WARNING, "Konnte " + args[i] + " keiner LOG_OPTION zuordnen."
									+ "LOG_OPTIONen sind true, false, file. Der default Wert ist false.");
						}
					}
					case LOG_LEVEL_STRING -> {
						Level logLevel = switch (args[++i]) {
							case "info" -> Level.INFO;
							case "warning" -> Level.WARNING;
							default -> Level.ALL;
						};
						ROOT_LOGGER.setLevel(logLevel);
					}
					case THREAD_POOL_SIZE_STRING -> this.threadPoolSize = Integer.parseInt(args[++i]);
					case SLEEP_TIME_STRING -> this.sleepTime = Integer.parseInt(args[++i]);
					case QUEUE_SIZE_STRING -> this.queueSize = Integer.parseInt(args[++i]);
				}
			}
		}

		switch (logOption) {
			case FILE -> {
				try {
					for (Handler handler : ROOT_LOGGER.getHandlers()) {
						ROOT_LOGGER.removeHandler(handler);
					}
					FileHandler FILE_HANDLER = new FileHandler("IHK_Abschlusspruefung.log", true);
					ROOT_LOGGER.addHandler(FILE_HANDLER);
				} catch (IOException e) {
					ROOT_LOGGER.log(Level.WARNING, "Konnte keinen FileHandler zum Logger hinzufügen. "
							+ "Logs werden in die Konsole geschrieben.");
				}
			}
			case FALSE -> {
				for (var handler : ROOT_LOGGER.getHandlers()) {
					ROOT_LOGGER.removeHandler(handler);
				}
			}
		}
	}

	public String getInputFolder() {
		return this.inputFolder;
	}

	public String getOutputFolder() {
		return this.outputFolder;
	}

	public int getSleepTime() {
		return this.sleepTime;
	}

	public int getThreadPoolSize() {
		return this.threadPoolSize;
	}

	/**
	 * Enumeration für alle Logging Optionen, welche true, false oder file sind.
	 */
	public enum LogOption {
	  TRUE("true"),
	  FALSE("false"),
	  FILE("file");
	  private final String value;

	  LogOption(String value) {
	    this.value = value;
	  }

	  public static LogOption getOption(String value) {
	    for (var option : LogOption.values()) {
	      if (option.value.equals(value)) {
	        return option;
	      }
	    }
	    return FALSE;
	  }
	}
}
