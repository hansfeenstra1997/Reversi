package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader implements Runnable {
    Socket socket;
    BufferedReader inputStream;
    ArrayList<String> queue;

    public Reader(Socket socket) {
        this.socket = socket;
        this.queue = new ArrayList<>();
        try {
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String line;
        Pattern positionPattern = Pattern.compile("MOVE: \"([^}]*)\", DETAILS:");
        Pattern playerPattern = Pattern.compile("PLAYER: \"([^}]*)\", MOVE:");

        Matcher matcher;
        Matcher matcher2;

        while(true) {
            try {
                if ((line = inputStream.readLine()) != null) {
                    System.out.println(line);
                    queue.add(line);
                    if(line.startsWith("SVR GAME MOVE")) {
                        matcher = positionPattern.matcher(line);
                        if(matcher.find()) {
                            int turn = Integer.parseInt(matcher.group(1));
                            System.out.println("Turn: " + turn);

                            matcher2 = playerPattern.matcher(line);
                            int selfOrOpp = 1;
                            if(matcher2.find()) {
                                String name = matcher2.group(1);
                                if(name.equals("tester")) {
                                    selfOrOpp = 1;
                                }
                                else {
                                    selfOrOpp = 2;
                                }
                            }
                        }
                    }
                    if(line.startsWith("SVR GAME YOURTURN")) {
                        System.out.println("Your turn:");
                    }
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
