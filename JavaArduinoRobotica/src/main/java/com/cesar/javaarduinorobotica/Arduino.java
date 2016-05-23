/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.javaarduinorobotica;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author cesar
 */
public class Arduino {

    private ComunicacaoSerial comunicaoSerial;

    /**
     * Construtor da classe Arduino
     */
    public Arduino() throws Exception {
        comunicaoSerial = new ComunicacaoSerial(lerPropriedade("portaSerialArduino"), 9600);
    }

    public void enviarComando(String dados) {
        comunicaoSerial.enviaDados((dados + "\n").getBytes());
    }

    private String lerPropriedade(String parametro) throws Exception {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/configuracao.properties");
        props.load(file);
        return props.getProperty(parametro);
    }

}
