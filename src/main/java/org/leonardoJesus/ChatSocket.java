package org.leonardoJesus;

import org.leonardoJesus.models.requests.JsonOrder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket para el servidor de la aplicacción ChatPGV
 * @author Leonardo Jesús Figueroa Valdivia
 * @since 2024-01-30
 */
public class ChatSocket {

    private static ServerSocket serverSocket;

    /**
     * Proceso principal del servidor
     * @param args No se utilizan en ningun punto del codigo
     */
    public static void main(String[] args) {
        try{

            serverSocket = new ServerSocket(4126);

            // Aceptamos las solicitudes de los clientes
            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("cliente en "+clientSocket.getInetAddress().getHostAddress());

                //Creamos un nuevo hilo de ejecucion para cada cliente
                Thread clientHandlerThread = new Thread(() -> handleClient(clientSocket));
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Manejador que se ocupa de las peticiones de los clientes
     * @param clientSocket Socket que comunica con el cliente
     */
    public static void handleClient(Socket clientSocket) {
        try{
            System.out.println("Cliente conectado en: "+clientSocket.getInetAddress().getHostAddress());
            //Flujos de entrada y salida del socket
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            //Leemos la peticion, sera en formato json
            String json = (String) in.readObject();

            System.out.println(json);

            //Objeto Gson para convertir entre
            JsonOrder order = JsonManager.gson.fromJson(json, JsonOrder.class);

            System.out.println(order);

            String response = JsonManager.executeOrder(order);

            System.out.println(response);

            out.writeObject(response);

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
