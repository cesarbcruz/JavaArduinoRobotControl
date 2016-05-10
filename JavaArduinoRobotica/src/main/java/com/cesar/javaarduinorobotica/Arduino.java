/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.javaarduinorobotica;

/**
 *
 * @author cesar
 */
public class Arduino {

    private ControlePorta arduino;

    /**
     * Construtor da classe Arduino
     */
    public Arduino() {
        //arduino = new ControlePorta("COM3",9600);//Windows - porta e taxa de transmissão
        arduino = new ControlePorta("/dev/ttyS0", 9600);//Linux - porta e taxa de transmissão
    }

    public void comunicacaoArduino(String dados) {
        arduino.enviaDados((dados + "\n").getBytes());
    }
}
