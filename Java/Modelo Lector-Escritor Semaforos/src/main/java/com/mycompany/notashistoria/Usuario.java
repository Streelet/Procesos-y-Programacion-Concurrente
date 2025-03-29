/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notashistoria;

/**
 *
 * @author erick
 */
import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.Semaphore;

class Usuario extends Thread {
    private Interfaz interfaz;
    private Semaphore semaforoNarrador;
    private Semaphore semaforoUsuario;
    private JTextArea areaResumen;
    private Timer temporizadorInactividad;

    public Usuario(Interfaz interfaz, Semaphore semaforoNarrador, Semaphore semaforoUsuario, JTextArea areaResumen) {
        this.interfaz = interfaz;
        this.semaforoNarrador = semaforoNarrador;
        this.semaforoUsuario = semaforoUsuario;
        this.areaResumen = areaResumen;

        temporizadorInactividad = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuarioTermino();
            }
        });
        temporizadorInactividad.setRepeats(false);
    }

    public void agregarEventos() {
        areaResumen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                temporizadorInactividad.restart();
            }
        });
    }

 public void run() {
    try {
        while (true) {
            semaforoUsuario.acquire(); 
            interfaz.bloquearUsuario(false); 
            interfaz.actualizarEstado("El narrador espera...", "El usuario está escribiendo");

            // Espera inactividad del usuario
            temporizadorInactividad.restart();
            while (temporizadorInactividad.isRunning()) {
                Thread.sleep(100);
            }

            interfaz.bloquearUsuario(true); 
            interfaz.actualizarEstado("El narrador puede contar", "El usuario espera...");
            semaforoNarrador.release(); 
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}


    public void usuarioTermino() {
        temporizadorInactividad.stop();
    }
}
