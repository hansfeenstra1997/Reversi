package com.company;

public class TicTacToeController implements Controller{

    Connection conn;
    Reader reader;
    Writer writer;

    public TicTacToeController() {
        conn = Connection.getInstance();
        reader = conn.getReader();
        writer = conn.getWriter();
    }

    @Override
    public String readQueue() {
        return reader.queue.get(reader.queue.size() - 1);
    }
}
