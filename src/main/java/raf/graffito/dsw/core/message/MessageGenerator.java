package raf.graffito.dsw.core.message;

import javax.swing.*;

public class MessageGenerator {
    public void generateMessage(String code) {
        String msg;
        switch(code) {
            case "UNAUTHORIZED_ACCESS": msg = "Neovlašćen pristup.";
                break;
            case "INVALID_INPUT": msg = "Neispravan unos (duplikat ili prazno ime).";
                break;
            case "NODE_CANNOT_BE_DELETED": msg = "Ovaj čvor se ne može obrisati.";
                break;
            default: msg = code;
        }
        // show dialog for user and print to console
        JOptionPane.showMessageDialog(null, msg, "Poruka", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("MessageGenerator: " + msg);
    }
}
