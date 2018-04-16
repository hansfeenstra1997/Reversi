package com.company.connection;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Writer implements Runnable {
    private BufferedWriter writer;
    private BlockingQueue<String> writeBuffer;

    /**
     * Constructor of Writer
     * @param socket - socket instance
     */
    Writer(Socket socket) {
        writeBuffer = new ArrayBlockingQueue<>(100);
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thread method - takes messages out of the write buffer and sends them to the server
     */
    @Override
    public void run() {
        while(true){
            try {
                String message = writeBuffer.take();
                writeMessage(message);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add message to writer queue
     * @param message - message to add the queue
     */
    public void addMessage(String message) {
        try {
            writeBuffer.put(message);
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write message to server
     * @param line - line to write
     */
    private void writeMessage(String line) {
        try {
            writer.write(line);
            writer.newLine();
            writer.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
