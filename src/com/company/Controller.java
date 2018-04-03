package com.company;

import java.util.ArrayList;

public abstract class Controller implements Runnable{

    Connection conn;
    ArrayList<String> readerQueue;
    ArrayList<String> writerQueue;

    public Controller() {
        conn = Connection.getInstance();
        readerQueue = conn.getReader().queue;
        //writerQueue = conn.getWriter().queue;
    }

    void readQueue() {
        String command = readerQueue.get(readerQueue.size() - 1);
        readerQueue.remove(command);
        queueParser(command);
        //System.out.println(command);
    }

    void queueParser(String command){

    }

    @Override
    public void run() {
        while(true){
            try{
                if (!readerQueue.isEmpty()){
                    readQueue();
                }
                Thread.sleep(500);
            } catch(InterruptedException e){
                e.printStackTrace();
            }

        }

    }

}
