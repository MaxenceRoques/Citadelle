package com.vogella.logger.test;

import java.util.logging.Logger;

public class UseLogger {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Logger getLogger() {
        return LOGGER;
    }
}