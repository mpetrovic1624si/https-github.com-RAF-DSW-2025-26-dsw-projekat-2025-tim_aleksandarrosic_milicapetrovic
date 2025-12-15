package raf.graffito.dsw.core.message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String content;
    private String type;
    private String timestamp;

    public Message(String content, String type) {
        this.content = content;
        this.type = type;
        this.timestamp = new SimpleDateFormat("dd.MM.yyyy. HH:mm").format(new Date());
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + type + "][" + timestamp + "] " + content;
    }
}
