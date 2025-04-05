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
import java.util.List;
import java.util.concurrent.Semaphore;

class Narrador extends Thread {
    private DefaultListModel<String> modeloLista;
    private List<String> historia;
    private Interfaz interfaz;
    private Semaphore semaforoNarrador;
    private Semaphore semaforoUsuario;

    public Narrador(DefaultListModel<String> modeloLista, List<String> historia, Interfaz interfaz, Semaphore semaforoNarrador, Semaphore semaforoUsuario) {
        this.modeloLista = modeloLista;
        this.historia = historia;
        this.interfaz = interfaz;
        this.semaforoNarrador = semaforoNarrador;
        this.semaforoUsuario = semaforoUsuario;
    }

public void run() {
    try {
        for (String parte : historia) {
            semaforoNarrador.acquire(); 
            interfaz.actualizarEstado("El narrador está contando...", "El usuario espera...");
            interfaz.bloquearUsuario(true); 
            modeloLista.addElement(parte); 
            Thread.sleep(8000); 

            interfaz.actualizarEstado("El narrador espera...", "El usuario puede escribir");
            semaforoUsuario.release(); 
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

}
