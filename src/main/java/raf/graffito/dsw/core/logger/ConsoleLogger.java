
package raf.graffito.dsw.core.logger;

import raf.graffito.dsw.core.message.Message;

public class ConsoleLogger extends Logger {
    @Override
    public void update(Message message) {
        System.out.println(format(message));
    }
}