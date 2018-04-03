package com.company;

import javafx.application.Platform;

import java.util.ArrayList;

public abstract class Controller implements Runnable{

    Connection conn;
    ArrayList<String> readerQueue;
    ArrayList<String> writerQueue;

    Board board;

    public Controller() {
        conn = Connection.getInstance();
        readerQueue = conn.getReader().queue;
        //writerQueue = conn.getWriter().queue;
    }

    abstract void setMove(int pos);

    void readQueue() {
        String command = readerQueue.get(readerQueue.size() - 1);
        readerQueue.remove(command);
        queueParser(command);
        //System.out.println(command);
    }

    void queueParser(String command){
        //stel: set is gedaan van tegenstander
            //model updaten
            //view updaten

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
