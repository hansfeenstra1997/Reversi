package com.company;

import java.util.ArrayList;

public class TicTacToeController implements Controller, Runnable{

    Connection conn;
    ArrayList<String> readerQueue;
    ArrayList<String> writerQueue;

    public TicTacToeController() {
        conn = Connection.getInstance();
        readerQueue = conn.getReader().queue;
        //writerQueue = conn.getWriter().queue;
    }

    @Override
    public void readQueue() {
        String command = readerQueue.get(readerQueue.size() - 1);
        readerQueue.remove(command);
        System.out.println(command);
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
