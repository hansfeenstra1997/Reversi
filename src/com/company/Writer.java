package com.company;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Writer implements Runnable {
    private Socket socket;
    private BufferedWriter writer;
    private BlockingQueue<String> writeBuffer;

    public Writer(Socket socket) {
        writeBuffer = new ArrayBlockingQueue<>(100);
        this.socket = socket;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String line;

        while(true){
//            line = scanner.nextLine();
//            if (line != null) {
//                System.out.println(line);
//                writeMessage(line);
//            }
            if(!writeBuffer.isEmpty()) {
                try {
                    writeMessage(writeBuffer.take());
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addMessage(String message) {
        try {
            writeBuffer.put(message);
//            Iterator<String> it = writeBuffer.iterator();
//            while(it.hasNext()) {
//                System.out.println(it.next());
//            }
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void writeMessage(String line) {
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
