package raf.graffito.dsw.core.logger;

import raf.graffito.dsw.core.message.Message;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileLogger extends Logger {

    private static final Path LOG_PUTANJA = Paths.get("src", "main", "resources", "log.txt");

    public FileLogger() {
        napraviLogFajlAkoNePostoji();
    }

    private void napraviLogFajlAkoNePostoji() {
        try {
            Path direktorijum = LOG_PUTANJA.getParent();
            if (direktorijum != null && !Files.exists(direktorijum)) {
                Files.createDirectories(direktorijum);
            }
            if (!Files.exists(LOG_PUTANJA)) {
                Files.createFile(LOG_PUTANJA);
            }
        } catch (IOException e) {
            System.err.println("Greška prilikom kreiranja log fajla: " + e.getMessage());
        }
    }

    @Override
    public synchronized void update(Message message) {
        String linija = format(message);
        try (BufferedWriter pisac = Files.newBufferedWriter(
                LOG_PUTANJA,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND)) {

            pisac.write(linija);
            pisac.newLine();

        } catch (IOException e) {
            System.err.println("Ne mogu da zapišem u log fajl: " + e.getMessage());
            System.err.println("Poruka koja se nije upisala: " + linija);
        }
    }
}
