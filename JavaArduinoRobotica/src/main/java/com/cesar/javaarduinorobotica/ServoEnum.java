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
public enum ServoEnum {
    
    ALTURA("A"),
    BASE("B"),
    DISTANCIA("D"),
    GARRA("G"),
    PUNHO("P");
    
    private String identificador;

    private ServoEnum(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    
}
