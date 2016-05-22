/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.javaarduinorobotica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 *
 * @author cesar
 */
public class Arduino {

    private ComunicacaoSerial arduino;

    /**
     * Construtor da classe Arduino
     */
    public Arduino() throws Exception {
        arduino = new ComunicacaoSerial(lerPropriedade("portaSerialArduino"), 9600);
    }

    public void comunicacaoArduino(String dados) {
        arduino.enviaDados((dados + "\n").getBytes());
    }

    private String lerPropriedade(String parametro) throws Exception {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/configuracao.properties");
        props.load(file);
        return props.getProperty(parametro);
    }

}
