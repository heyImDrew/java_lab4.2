package ClientClasses;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            ClientUDP client = new ClientUDP();   // создание объекта client
            client.runClient();   // вызов метода объекта client
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}