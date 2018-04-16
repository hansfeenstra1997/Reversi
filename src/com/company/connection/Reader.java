package com.company.connection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class Reader implements Runnable {
    private BufferedReader inputStream;
    private ArrayList<Map.Entry<String, ArrayList<String>>> queue;
    private Gson gson;

    private static final String[][] responses = {
            {"SVR GAMELIST", "GAMES"},
            {"SVR PLAYERLIST", "PLAYERS"},
            {"SVR GAME MATCH", "MATCH"},
            {"SVR GAME YOURTURN", "YOURTURN"},
            {"SVR GAME MOVE", "MOVE"},
            {"SVR GAME WIN", "WIN"},
            {"SVR GAME LOSS", "LOSS"},
            {"SVR GAME DRAW", "DRAW"},
            {"SVR GAME CHALLENGE", "CHALLENGE"},
            {"SVR GAME CHALLENGE CANCELLED", "CANCELLED"}
    };

    /**
     * Constructor of Reader
     * @param socket - Socket
     */
    Reader(Socket socket) {
        this.queue = new ArrayList<>();
        try {
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e) {
            System.out.println("Failed to create Reader!");
            e.printStackTrace();
        }
        gson = new Gson();
    }

    /**
     * Thread method
     * Constantly reads socket for new lines and adds new lines to the reader queue
     */
    @Override
    public void run() {
        String line;

        while(true) {
            try {
                if ((line = inputStream.readLine()) != null) {
                    queue.add(parseResponse(line));
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses a response received from socket
     * @param response - raw response from socket
     * @return a Map.Entry of a response with key and values
     */
    private Map.Entry<String, ArrayList<String>> parseResponse(String response) {
        if(response.equals("OK")) {
            return new AbstractMap.SimpleEntry<>("OK", new ArrayList<>());
        }

        String key;
        ArrayList<String> values = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonElement obj;

        for(String[] prefix: responses) {
            if(response.startsWith(prefix[0])) {
                key = prefix[1];
                response = response.replace(prefix[0]+" ", "");
                obj = parser.parse(response);

                if(obj.isJsonArray()) {
                    values = new Gson().fromJson(obj, ArrayList.class);
                }
                else if(obj.isJsonObject()) {
                    JsonObject jsonObject = (JsonObject)obj;
                    for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                        values.add(entry.getValue().toString().replace("\"", ""));
                    }
                }
                return new AbstractMap.SimpleEntry<>(key, values);
            }
        }
        return null;
    }

    /**
     * Get instance of the reader queue
     * @return instance of reader queue.
     */
    public ArrayList<Map.Entry<String, ArrayList<String>>> getQueue() {
        return queue;
    }
}
