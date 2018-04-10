package com.company.connection;

import java.io.IOException;
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
            Socket clientSocket = new Socket("localhost", 7789);
            //Socket clientSocket = new Socket("145.33.225.170", 7789);

            reader = new Reader(clientSocket);
            writer = new Writer(clientSocket);

            Thread readerThread = new Thread(reader);
            readerThread.start();

            Thread writerThread = new Thread(writer);
            writerThread.start();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(String command) {
        writer.addMessage(command);
    }

    public Reader getReader() {
        return reader;
    }

    public Writer getWriter() {
        return writer;
    }
}
