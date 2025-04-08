/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.productor_consumidor_monitores;

/**
 *
 * @author erick
 */
import java.util.Random;

public class Empacador extends Thread {
    private CintaTransportadora cintaTransportadora;
    private Random random = new Random();

    public Empacador(CintaTransportadora cintaTransportadora) {
        this.cintaTransportadora = cintaTransportadora;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                String juguete = cintaTransportadora.tomarJuguete();
                Thread.sleep(random.nextInt(500)); 
            }
            System.out.println("Operario de empaquetado termino su turno.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
