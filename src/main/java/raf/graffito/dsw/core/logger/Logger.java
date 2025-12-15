package raf.graffito.dsw.core.logger;
import raf.graffito.dsw.core.message.Message;
import raf.graffito.dsw.core.message.MessageSubscriber;

public abstract class Logger implements MessageSubscriber {
    protected String format(Message message) {
        String rawType = message.getType() == null ? "" : message.getType().trim().toUpperCase();
        String tip;
        switch (rawType) {
            case "GREŠKA":
            case "GRESKA":
            case "ERROR":
                tip = "ERROR";
                break;
            case "UPOZORENJE":
            case "WARNING":
                tip = "WARNING";
                break;
            case "OBAVEŠTENJE":
            case "OBAVESTENJE":
            case "INFO":
            case "INFORMACIJA":
                tip = "INFO";
                break;
            default:
                tip = rawType.isEmpty() ? "INFO" : rawType;
        }
        return String.format("[%s][%s] %s", tip, message.getTimestamp(), message.getContent());
    }
}
