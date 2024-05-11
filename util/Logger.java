package util;

/**
 * Utility class for logging messages.
 */
public class Logger {
    public static void log(String message) {
        System.out.println(message);
    }

    public static void logError(String message, Throwable throwable) {
        System.err.println(message);
        throwable.printStackTrace();
    }
}
