package ServerClasses;

import java.net.*;
import java.io.*;
import static java.lang.Math.*;

public class ServerUDP {
    public void runServer() throws IOException {   // метод сервера
        DatagramSocket socket = null;   // создание объекта DatagramSocket для приема/передачи дейтаграммы
        try {
            boolean workServer = true;   // создание флага stopServer и его инициализация
            socket = new DatagramSocket(8002);   // привязка сокета к реальному объекту с портом
            System.out.println("Server started.\nAddress: " + socket.getLocalAddress() + ".\nPort: " + socket.getLocalPort() + "\n");

            while(workServer) {
                double firstNumber, secondNumber;
                byte[] bufi = new byte[512];                                         // буфер для приема дейтаграммы
                DatagramPacket recvPacket = new DatagramPacket(bufi, bufi.length);   // создание объекта дейтаграммы для получения данных

                socket.receive(recvPacket);                                 // помещение полученного содержимого в объект дейтаграммы
                String work = new String(recvPacket.getData()).trim();      // извлечение из пакета

                if (work.equals("quit")) {
                    workServer = false;             // остановка сервера
                    continue;
                }

                socket.receive(recvPacket);                     // помещение полученного содержимого в объект дейтаграммы
                firstNumber = Double.parseDouble(new String(recvPacket.getData()));    // извлечение данных из пакета
                socket.receive(recvPacket);
                secondNumber = Double.parseDouble(new String(recvPacket.getData()));

                String result = String.valueOf(ServerUDP.calculator(firstNumber, secondNumber));   // метод вычисления выражения
                System.out.println("x = " + firstNumber + ", y = " + secondNumber + ", result = " + result);

                byte[] bufo = String.valueOf(result).getBytes();        // преобразование
                DatagramPacket sendPacket = new DatagramPacket(bufo, bufo.length, recvPacket.getAddress(), recvPacket.getPort());   // формирование объекта дейтаграммы для отсылки данных
                socket.send(sendPacket);                                // посылка сообщения

                try (FileWriter file = new FileWriter("result.txt", true)) {   //  запись в файл
                    file.write("X = " + firstNumber + ", Y = " + secondNumber + ", Result = " + result + "\r\n");
                    file.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        finally {
            if (socket != null) {
                socket.close();   // закрытие сокета сервера
            }
        }
    }

    static double calculator(double x, double y) {
        double a, b, c, d;
        a = (2*x)*(pow((1+pow(x,2)),2));
        b = x+(pow(abs(1+pow(x,5)),(1/3)));
        c = pow(x,(1/2));
        d = (2*y)+10;
        return (a/b)*(c/d);
    }
}
