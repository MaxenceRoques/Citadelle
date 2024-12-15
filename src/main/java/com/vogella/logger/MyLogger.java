package com.vogella.logger;

import java.io.IOException;
import java.util.logging.*;

public class MyLogger {

    private MyLogger() {
        throw new IllegalStateException("Logger class");
    }

    public static void setup() throws IOException {
        FileHandler fileTxt;
        SimpleFormatter formatterTxt;

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("Logging.txt");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }
}