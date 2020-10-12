package ClientClasses;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP
{
    public void runClient() throws IOException
    {
        DatagramSocket socket = null;   // создание дейтаграммы
        try {
            socket = new DatagramSocket();   // привязка сокета к реальному объету
            boolean workClient = true;
            while(workClient) {
                Scanner scan = new Scanner(System.in);
                System.out.println("Type 'quit' to close program.");
                String Checkwork = scan.nextLine();
                byte[] bufferOut = Checkwork.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(bufferOut, bufferOut.length, InetAddress.getByName("127.0.0.1"), 8002);   // создание дейтаграммы для отсылки данных
                socket.send(sendPacket);   // посылка дейтаграммы

                if (Checkwork.equals("quit")) {
                    workClient = false;   // остановка клиента
                    continue;
                }

                double firstNumber = checkInput("first");
                byte[] bytes1 = String.valueOf(firstNumber).getBytes();

                sendPacket = new DatagramPacket(bytes1, bytes1.length, InetAddress.getByName("127.0.0.1"), 8002);   // создание дейтаграммы для отсылки данных
                socket.send(sendPacket);   // посылка дейтаграммы

                double secondNumber = checkInput("second");
                byte[] bytes2 = String.valueOf(secondNumber).getBytes();
                sendPacket = new DatagramPacket(bytes2, bytes2.length, InetAddress.getByName("127.0.0.1"), 8002);   // создание дейтаграммы для отсылки данных
                socket.send(sendPacket);   // посылка дейтаграммы

                byte[] bufferIn = new byte[512];   // буфер для приема дейтаграммы
                DatagramPacket recvPacket = new DatagramPacket(bufferIn, bufferIn.length);   // создание дейтаграммы для получения данных
                socket.receive(recvPacket);   // получение дейтаграммы
                String result = new String(recvPacket.getData()).trim();   // извлечение данных (версии сервера)
                System.out.println("Result: " + result + "\n");
            }
        }
        finally {
            if (socket != null) {
                socket.close();   // закрытие сокета клиента
            }
        }
    }

    private static double checkInput(String varName)
    {
        System.out.print("Input " + varName + " variable: ");
        String input = (new Scanner(System.in)).next().trim();

        double parameter = 0;
        boolean isParsed = false;
        while(!isParsed)
        {
            try
            {
                parameter = Double.parseDouble(input);
                isParsed = true;
            }
            catch (Exception e)
            {
                System.out.print("Error. Try again: " + varName + ": ");
                input = (new Scanner(System.in)).next().trim();
            }
        }
        return parameter;
    }
}
