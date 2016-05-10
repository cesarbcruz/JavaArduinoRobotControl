/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.javaarduinorobotica;

import java.io.Serializable;

/**
 *
 * @author cesar
 */
public class Comando implements Serializable {

    private String identificadorServo;
    private int posicao;
    private int delay;

    public Comando(String identificadorServo, int posicao, int delay) {
        this.identificadorServo = identificadorServo;
        this.posicao = posicao;
        this.delay = delay;
    }

    public String getIdentificadorServo() {
        return identificadorServo;
    }

    public void setIdentificadorServo(String identificadorServo) {
        this.identificadorServo = identificadorServo;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return this.getIdentificadorServo() + "|" + this.getPosicao() + "|" + this.getDelay();
    }

}
