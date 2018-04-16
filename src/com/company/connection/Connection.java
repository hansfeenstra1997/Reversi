package com.company.connection;

import java.io.IOException;
import java.net.Socket;

public class Connection {

    private Reader reader;
    private Writer writer;

    private static Connection connection = null;

    /**
     * Constructor Connection
     */
    private Connection() {
        makeConnection();
    }

    /**
     * Singleton getInstance() to get instance of the Connection
     * @return Connection instance
     */
    public static Connection getInstance() {
        if(connection==null) {
            connection = new Connection();
        }
        return connection;
    }

    /**
     * Make connection with server
     * Create a Reader and a Writer
     */
    private void makeConnection() {
        try {
            Socket clientSocket = new Socket("localhost", 7789);

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

    /**
     * Send connection the writer
     * @param command - command to send to writer
     */
    public void sendCommand(String command) {
        writer.addMessage(command);
    }

    /**
     * Get instance of the Reader
     * @return Reader instance
     */
    public Reader getReader() {
        return reader;
    }
}
