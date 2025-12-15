package raf.graffito.dsw.core.logger;


public class LoggerFactory {
    public static Logger kreirajLogger(String tip) {
        if (tip == null) {
            throw new IllegalArgumentException("Logger ne moze biti null");
        }
        switch (tip.trim().toUpperCase()) {
            case "CONSOLE":
                return new ConsoleLogger();
            case "FILE":
                return new FileLogger();
            default:
                throw new IllegalArgumentException("Nepoznat tip loggera: " + tip);
        }
    }
}