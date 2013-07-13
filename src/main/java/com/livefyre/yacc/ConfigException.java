package com.livefyre.yacc;

public class ConfigException extends Exception {
    private static final long serialVersionUID = 1L;

    public ConfigException() {
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
