package com.company.connection;

import com.company.controller.LauncherController;
import sun.misc.Launcher;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Connection {

    private Reader reader;
    private Writer writer;

    private static Connection connection = null;

    private Connection() {
        makeConnection();
    }

    public static Connection getInstance() {
        if(connection==null) {
            connection = new Connection();
        }
        return connection;
    }

    private void makeConnection() {
        try {
            Socket clientSocket = new Socket(LauncherController.getIP(), LauncherController.getPort());
            System.out.println(clientSocket);

            reader = new Reader(clientSocket);
            writer = new Writer(clientSocket);

            Thread readerThread = new Thread(reader);
            readerThread.start();

            Thread writerThread = new Thread(writer);
            writerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendCommand(String command) {
        writer.addMessage(command);
    }

    public Reader getReader() {
        return reader;
    }
}
