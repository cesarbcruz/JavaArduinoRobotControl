/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.javaarduinorobotica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author cesar
 */
public class TelaPrincipalControle {

    Arduino arduino = null;
    LinkedList<Comando> gravacao = new LinkedList<Comando>();
    private TelaPrincipal telaPrincipal;

    private long posicaoAnteriorBase = 90;
    private long posicaoAnteriorDistancia = 90;
    private long posicaoAnteriorGarra = 90;
    private long posicaoAnteriorAltura = 90;
    private long posicaoAnteriorPunho = 90;

    public TelaPrincipalControle(TelaPrincipal t) throws Exception {
        this.telaPrincipal = t;
        arduino = new Arduino();
    }

    void enviaComando(String identificador, int posicao) {
        String comando = identificador + (String.format("%02d", posicao));
        arduino.comunicacaoArduino(comando);
    }

    void gravarComando() {
        gravaComando(ServoEnum.BASE.getIdentificador(), (int) telaPrincipal.getPosicaoBase().getValue());
        gravaComando(ServoEnum.DISTANCIA.getIdentificador(), (int) telaPrincipal.getPosicaoLink1().getValue());
        gravaComando(ServoEnum.GARRA.getIdentificador(), (int) telaPrincipal.getPosicaoGarra().getValue());
        gravaComando(ServoEnum.ALTURA.getIdentificador(), (int) telaPrincipal.getPosicaoLink2().getValue());
        gravaComando(ServoEnum.PUNHO.getIdentificador(), (int) telaPrincipal.getPosicaoPunho().getValue());
        salvar();
    }

    private void salvarPosicaoAnterior(String identificador, long posicaoAtual) {
        if (identificador.equals(ServoEnum.ALTURA.getIdentificador())) {
            posicaoAnteriorAltura = posicaoAtual;
        } else if (identificador.equals(ServoEnum.BASE.getIdentificador())) {
            posicaoAnteriorBase = posicaoAtual;
        } else if (identificador.equals(ServoEnum.DISTANCIA.getIdentificador())) {
            posicaoAnteriorDistancia = posicaoAtual;
        } else if (identificador.equals(ServoEnum.GARRA.getIdentificador())) {
            posicaoAnteriorGarra = posicaoAtual;
        } else if (identificador.equals(ServoEnum.PUNHO.getIdentificador())) {
            posicaoAnteriorPunho = posicaoAtual;
        }
    }

    private void carregarListaComando() {
        DefaultListModel model = (DefaultListModel) telaPrincipal.getListaComando().getModel();
        model.removeAllElements();
        for (Comando comando : gravacao) {
            model.addElement(comando);
        }
    }

    void editarComando() {
        Comando comando = (Comando) ((DefaultListModel) telaPrincipal.getListaComando().getModel()).getElementAt(telaPrincipal.getListaComando().getSelectedIndex());
        new EditarComando(telaPrincipal, true, comando).setVisible(true);
        salvar();
        carregarListaComando();
    }

    void abrir() throws InterruptedException {
        resetPosicao();
        try {
            if (new File("gravacao").exists()) {
                FileInputStream fileIn = new FileInputStream("gravacao");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                gravacao = (LinkedList<Comando>) in.readObject();
                in.close();
                fileIn.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaPrincipal, "Erro ao abrir arquivo de gravação:\n" + e.getMessage());
        }
        carregarListaComando();
    }

    void removerComando() {
        if (telaPrincipal.getListaComando().getSelectedIndex() > -1) {
            int reply = JOptionPane.showConfirmDialog(telaPrincipal, "Deseja remover o(s) comando(s) selecionado(s)?", "Confirma", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                gravacao.removeAll(telaPrincipal.getListaComando().getSelectedValuesList());
                salvar();
                carregarListaComando();
            }
        }
    }

    void resetPosicao() throws InterruptedException {
        enviaComando(ServoEnum.ALTURA.getIdentificador(), 90);
        enviaComando(ServoEnum.BASE.getIdentificador(), 90);
        enviaComando(ServoEnum.DISTANCIA.getIdentificador(), 90);
        enviaComando(ServoEnum.GARRA.getIdentificador(), 90);
        telaPrincipal.getPosicaoLink1().setValue(90);
        telaPrincipal.getPosicaoLink2().setValue(90);
        telaPrincipal.getPosicaoBase().setValue(90);
        telaPrincipal.getPosicaoPunho().setValue(90);
        telaPrincipal.getPosicaoGarra().setValue(90);
        posicaoAnteriorBase = 90;
        posicaoAnteriorDistancia = 90;
        posicaoAnteriorGarra = 90;
        posicaoAnteriorAltura = 90;
        posicaoAnteriorPunho = 90;
        Thread.sleep(200);
    }

    private void salvar() {
        try {
            FileOutputStream fileOut = new FileOutputStream("gravacao");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gravacao);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaPrincipal, "Erro ao salvar arquivo de gravação:\n" + e.getMessage());
        }
    }

    private void gravaComando(String identificador, int novaPosicao) {
        long posicaoAnterior = obterPosicaoAnterior(identificador);
        if (novaPosicao >= posicaoAnterior) {
            for (long i = posicaoAnterior; i <= novaPosicao; i++) {
                Comando novoComando = new Comando(identificador, (int) i, 10);
                gravacao.add(novoComando);
            }
        } else {
            for (long i = posicaoAnterior; i >= novaPosicao; i--) {
                Comando novoComando = new Comando(identificador, (int) i, 10);
                gravacao.add(novoComando);
            }
        }
        carregarListaComando();
        salvarPosicaoAnterior(identificador, novaPosicao);
    }

    private long obterPosicaoAnterior(String identificador) {
        if (identificador.equals(ServoEnum.ALTURA.getIdentificador())) {
            return posicaoAnteriorAltura;
        } else if (identificador.equals(ServoEnum.BASE.getIdentificador())) {
            return posicaoAnteriorBase;
        } else if (identificador.equals(ServoEnum.DISTANCIA.getIdentificador())) {
            return posicaoAnteriorDistancia;
        } else if (identificador.equals(ServoEnum.GARRA.getIdentificador())) {
            return posicaoAnteriorGarra;
        } else if (identificador.equals(ServoEnum.PUNHO.getIdentificador())) {
            return posicaoAnteriorPunho;
        }
        return 90;
    }

    void reproduzir(Object[] comandos, TelaPrincipal telaPrincipal) throws InterruptedException {
        resetPosicao();
        int qtde = Integer.parseInt(telaPrincipal.getRepetir().getValue().toString());
        Thread worker = new Thread() {
            public void run() {
                for (int i = 0; i < qtde; i++) {
                    for (Object obj : comandos) {
                        Comando comando = (Comando) obj;
                        if (comando.getIdentificadorServo().equals(ServoEnum.ALTURA.getIdentificador())) {
                            telaPrincipal.getPosicaoLink2().getModel().setValue(comando.getPosicao());
                        } else if (comando.getIdentificadorServo().equals(ServoEnum.BASE.getIdentificador())) {
                            telaPrincipal.getPosicaoBase().getModel().setValue(comando.getPosicao());
                        } else if (comando.getIdentificadorServo().equals(ServoEnum.DISTANCIA.getIdentificador())) {
                            telaPrincipal.getPosicaoLink1().getModel().setValue(comando.getPosicao());
                        } else if (comando.getIdentificadorServo().equals(ServoEnum.GARRA.getIdentificador())) {
                            telaPrincipal.getPosicaoGarra().getModel().setValue(comando.getPosicao());
                        } else if (comando.getIdentificadorServo().equals(ServoEnum.PUNHO.getIdentificador())) {
                            telaPrincipal.getPosicaoPunho().getModel().setValue(comando.getPosicao());
                        }
                        try {
                            Thread.sleep(comando.getDelay());
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        };
        worker.start();
    }

}
