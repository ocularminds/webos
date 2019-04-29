/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.webos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A lightweight HTTP Server Add more features to the web . Make it possible to
 * process different pages, render larger content, and handle errors
 *
 * @author Festus Jejelowo
 */
public class WebOS {

    final Logger logger = Logger.getLogger(WebOS.class.getName());

    public WebOS() {
        System.out.println("Starting WebOS...");
    }

    public static void main(String[] args) {
        WebOS os = new WebOS();
        os.createServer();
    }

    public void createServer() {
        try (ServerSocket server = new ServerSocket(8080)) {
            server.setSoTimeout(1000);
            while (true) {
                try (Socket socket = server.accept()) {
                    handleSocket(socket);
                } catch (SocketTimeoutException ex) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    private void handleSocket(Socket socket) {
        try (InputStream in = socket.getInputStream();
                OutputStream os = socket.getOutputStream()) {
            byte[] data = new byte[10000];
            int total = in.read(data);
            String request = new String(Arrays.copyOfRange(data, 0, total));
            String response = "HTTP/1.1 200 OK\r\n\r\nWebOS Server Healthy";
            System.out.println(request);
            os.write(response.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
