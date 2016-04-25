/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.javaarduinorobotica;

import gnu.io.CommPortIdentifier;
import java.util.Enumeration;

/**
 *
 * @author cesar
 */
public class SerialCOMq {
 
    protected String[] portas;
    protected Enumeration listaDePortas;
 
    public SerialCOMq() {
        listaDePortas = CommPortIdentifier.getPortIdentifiers();
    }
 
    public String[] ObterPortas() {
        return portas;
    }
    //Lista as portas que estão disponiveis para a comunicaçao
    protected void ListarPortas() {
        int i = 0;
        portas = new String[10];
        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips =
                    (CommPortIdentifier) listaDePortas.nextElement();
            portas[i] = ips.getName();
            System.out.println(portas[i]);
            i++;
        }
    }
    //Identifica se a porta selecionada existe e se esta funcionando
    public boolean PortaExiste(String COMp) {
        String temp;
        boolean e = false;
        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips = (CommPortIdentifier)listaDePortas.nextElement();
            temp = ips.getName();
            if (temp.equals(COMp) == true) {
                e = true;
            }
        }
        return e;
    }
    
    public static void main(String[] args) {
        new SerialCOMq().ListarPortas();
    }
}