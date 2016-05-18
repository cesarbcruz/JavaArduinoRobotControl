/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.javaarduinorobotica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author cesar
 */
public class ArquivoEthernet {

    public static void main(String[] args) throws IOException {
        String ip = "192.168.0.100";
        int porta = 8888;
        Socket socket = new Socket(ip, porta);
        DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
        DataInputStream fromServer = new DataInputStream(socket.getInputStream());
        
        toServer.writeBytes("led1");
        toServer.flush();
        socket.close();
    }

}
