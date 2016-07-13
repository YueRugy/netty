package com.yue.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by yue on 2016/7/11
 */
public class TimeServerHandler implements Runnable {
    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String currentTime;
            String body;
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("the server is receive order " + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ERROR";
                out.println(currentTime);
                out.flush();
            }


        } catch (IOException e) {
           // e.printStackTrace();

        }

    }
}
