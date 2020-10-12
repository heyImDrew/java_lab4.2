package ServerClasses;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ServerUDP svrUdp = new ServerUDP();   // создание объекта
            svrUdp.runServer();   // вызов метода объекта runServer
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
